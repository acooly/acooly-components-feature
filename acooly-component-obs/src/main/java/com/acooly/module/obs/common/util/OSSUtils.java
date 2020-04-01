package com.acooly.module.obs.common.util;

import com.acooly.module.obs.client.oss.OSSHeaders;
import com.acooly.module.obs.client.oss.ResponseParseException;
import com.acooly.module.obs.common.model.ObjectMetadata;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import static com.acooly.module.obs.client.oss.OSSConstants.*;
import static com.acooly.module.obs.common.HttpHeaders.CONTENT_LENGTH;

public class OSSUtils {

    private static final String BUCKET_NAMING_REGEX = "^[a-z0-9][a-z0-9_\\-]{1,61}[a-z0-9]$";

    public static String composeRequestAuthorization(String accessKeyId, String signature) {
        return OSS_AUTHORIZATION_PREFIX + accessKeyId + OSS_AUTHORIZATION_SEPERATOR + signature;
    }

    public static String trimQuotes(String s) {

        if (s == null) {
            return null;
        }

        s = s.trim();
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }
        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }

    public static void ensureBucketNameValid(String bucketName) {
        if (!validateBucketName(bucketName)) {
            throw new IllegalArgumentException("BucketName Invalid");
        }
    }

    public static void ensureObjectKeyValid(String key) {
        if (!validateObjectKey(key)) {
            throw new IllegalArgumentException("ObjectKeyInvalid");
        }
    }

    /**
     * Validate object name.
     */
    public static boolean validateObjectKey(String key) {

        if (key == null || key.length() == 0) {
            return false;
        }

        byte[] bytes = null;
        try {
            bytes = key.getBytes(DEFAULT_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            return false;
        }

        // Validate exculde xml unsupported chars
        char keyChars[] = key.toCharArray();
        char firstChar = keyChars[0];
        if (firstChar == '/' || firstChar == '\\') {
            return false;
        }

        return (bytes.length > 0 && bytes.length < OBJECT_NAME_MAX_LENGTH);
    }

    /**
     * Validate bucket name.
     */
    public static boolean validateBucketName(String bucketName) {

        if (bucketName == null) {
            return false;
        }

        return bucketName.matches(BUCKET_NAMING_REGEX);
    }

    /**
     * Populate metadata to headers.
     */
    public static void populateRequestMetadata(Map<String, String> headers, ObjectMetadata metadata) {
        Map<String, Object> rawMetadata = metadata.getRawMetadata();
        if (rawMetadata != null) {
            for (Map.Entry<String, Object> entry : rawMetadata.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String key = entry.getKey();
                    //去掉"Content-Length"
                    if (CONTENT_LENGTH.equals(key)) {
                        continue;
                    }

                    String value = entry.getValue().toString();
                    if (key != null) key = key.trim();
                    if (value != null) value = value.trim();
                    headers.put(key, value);
                }
            }
        }

        Map<String, String> userMetadata = metadata.getUserMetadata();
        if (userMetadata != null) {
            for (Map.Entry<String, String> entry : userMetadata.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (key != null) key = key.trim();
                    if (value != null) value = value.trim();
                    headers.put(OSSHeaders.OSS_USER_METADATA_PREFIX + key, value);
                }
            }
        }
    }

    private static String buildCanonicalHost(URI endpoint, String bucket) {
        String host = endpoint.getHost();
        StringBuffer cannonicalHost = new StringBuffer();
        cannonicalHost.append(bucket).append(".").append(host);
        return cannonicalHost.toString();
    }

    public static URI determineFinalEndpoint(URI endpoint, String bucket) {
        try {
            StringBuilder conbinedEndpoint = new StringBuilder();
            conbinedEndpoint.append(String.format("%s://", endpoint.getScheme()));
            conbinedEndpoint.append(buildCanonicalHost(endpoint, bucket));
            conbinedEndpoint.append(
                    endpoint.getPort() != -1 ? String.format(":%s", endpoint.getPort()) : "");
            conbinedEndpoint.append(endpoint.getPath());
            return new URI(conbinedEndpoint.toString());
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    public static String makeResourcePath(String key) {
        return key != null ? OSSUtils.urlEncodeKey(key) : null;
    }

    /**
     * Encode object URI.
     */
    public static String urlEncodeKey(String key) {
        StringBuffer resultUri = new StringBuffer();

        String[] keys = key.split("/");
        resultUri.append(urlEncode(keys[0], DEFAULT_CHARSET_NAME));
        for (int i = 1; i < keys.length; i++) {
            resultUri.append("/").append(urlEncode(keys[i], DEFAULT_CHARSET_NAME));
        }

        if (key.endsWith("/")) {
            // String#split ignores trailing empty strings,
            // e.g., "a/b/" will be split as a 2-entries array,
            // so we have to append all the trailing slash to the uri.
            for (int i = key.length() - 1; i >= 0; i--) {
                if (key.charAt(i) == '/') {
                    resultUri.append("/");
                } else {
                    break;
                }
            }
        }

        return resultUri.toString();
    }

    /**
     * Encode a URL segment with special chars replaced.
     */
    public static String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }

        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("~", "%7E")
                    .replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }

    public static long determineInputStreamLength(InputStream instream, long hintLength) {

        if (hintLength <= 0 || !instream.markSupported()) {
            return -1;
        }

        return hintLength;
    }

    /**
     * Unmarshall object metadata from response headers.
     */
    public static ObjectMetadata parseObjectMetadata(Map<String, String> headers)
            throws ResponseParseException {

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();

            for (Iterator<String> it = headers.keySet().iterator(); it.hasNext(); ) {
                String key = it.next();

                if (key.indexOf(OSSHeaders.OSS_USER_METADATA_PREFIX) >= 0) {
                    key = key.substring(OSSHeaders.OSS_USER_METADATA_PREFIX.length());
                    objectMetadata.addUserMetadata(
                            key, headers.get(OSSHeaders.OSS_USER_METADATA_PREFIX + key));
                } else if (key.equals(OSSHeaders.LAST_MODIFIED) || key.equals(OSSHeaders.DATE)) {
                    try {
                        objectMetadata.setHeader(key, DateUtil.parseRfc822Date(headers.get(key)));
                    } catch (ParseException pe) {
                        throw new ResponseParseException(pe.getMessage(), pe);
                    }
                } else if (key.equals(OSSHeaders.CONTENT_LENGTH)) {
                    Long value = Long.valueOf(headers.get(key));
                    objectMetadata.setHeader(key, value);
                } else if (key.equals(OSSHeaders.ETAG)) {
                    objectMetadata.setHeader(key, trimQuotes(headers.get(key)));
                } else {
                    objectMetadata.setHeader(key, headers.get(key));
                }
            }

            return objectMetadata;
        } catch (Exception e) {
            throw new ResponseParseException(e.getMessage(), e);
        }
    }
}

package com.acooly.module.obs.client.oss;

import com.acooly.core.utils.net.HttpResult;
import com.acooly.core.utils.net.Https;
import com.acooly.module.obs.ObsProperties;
import com.acooly.module.obs.client.oss.model.GenericRequest;
import com.acooly.module.obs.client.oss.model.GetObjectRequest;
import com.acooly.module.obs.client.oss.model.PutObjectRequest;
import com.acooly.module.obs.client.oss.model.PutObjectResult;
import com.acooly.module.obs.client.oss.parser.AliyunOSSResponseParser;
import com.acooly.module.obs.client.oss.parser.BaseMessageResponseParser;
import com.acooly.module.obs.common.HttpMesssage;
import com.acooly.module.obs.common.HttpMethod;
import com.acooly.module.obs.common.model.ObjectMetadata;
import com.acooly.module.obs.common.model.ObsObject;
import com.acooly.module.obs.common.util.DateUtil;
import com.acooly.module.obs.common.util.HttpUtil;
import com.acooly.module.obs.common.util.Mimetypes;
import com.acooly.module.obs.exceptions.ClientException;
import com.acooly.module.obs.exceptions.ObsException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.*;
import org.apache.http.entity.InputStreamEntity;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.acooly.module.obs.client.oss.ResponseMessage.HTTP_SUCCESS_STATUS_CODE;
import static com.acooly.module.obs.common.util.CodingUtils.assertTrue;
import static com.acooly.module.obs.common.util.IOUtils.checkFile;
import static com.acooly.module.obs.common.util.IOUtils.newRepeatableInputStream;
import static com.acooly.module.obs.common.util.OSSUtils.*;

/**
 * @author shuijing
 */
@Slf4j
public class OSSObjectOperation {

    protected static final PutObjectReponseParser putObjectReponseParser =
            new PutObjectReponseParser();
    protected static HttpRequestFactory httpRequestFactory = new HttpRequestFactory();
    protected static EmptyResponseParser emptyResponseParser = new EmptyResponseParser();
    protected volatile URI endpoint;
    private ObsProperties obsProperties;

    public OSSObjectOperation(ObsProperties obsProperties) {
        this.obsProperties = obsProperties;
    }

    private static void populateGetObjectRequestHeaders(
            GetObjectRequest getObjectRequest, Map<String, String> headers) {
        if (getObjectRequest.getModifiedSinceConstraint() != null) {
            headers.put(
                    OSSHeaders.GET_OBJECT_IF_MODIFIED_SINCE,
                    DateUtil.formatRfc822Date(getObjectRequest.getModifiedSinceConstraint()));
        }

        if (getObjectRequest.getUnmodifiedSinceConstraint() != null) {
            headers.put(
                    OSSHeaders.GET_OBJECT_IF_UNMODIFIED_SINCE,
                    DateUtil.formatRfc822Date(getObjectRequest.getUnmodifiedSinceConstraint()));
        }
    }

    public static void safeCloseResponse(ResponseMessage response) {
        try {
            response.close();
        } catch (IOException e) {
            //ig
        }
    }

    public URI createAndgetEndpoint() {
        this.endpoint = URI.create(obsProperties.getAliyun().getEndpoint());
        return this.endpoint;
    }

    public PutObjectResult putObject(PutObjectRequest putObjectRequest)
            throws ObsException, ClientException {

        Assert.notNull(putObjectRequest, "putObjectRequest");

        PutObjectResult result =
                writeObjectInternal(WriteMode.OVERWRITE, putObjectRequest, putObjectReponseParser);

        return result;
    }

    private <RequestType extends PutObjectRequest, ResponseType> ResponseType writeObjectInternal(
            WriteMode mode, RequestType originalRequest, ResponseParser<ResponseType> responseParser) {

        final String bucketName = originalRequest.getBucketName();
        final String key = originalRequest.getKey();
        InputStream originalInputStream = originalRequest.getInputStream();
        ObjectMetadata metadata = originalRequest.getMetadata();
        if (metadata == null) {
            metadata = new ObjectMetadata();
        }
        Assert.notNull(bucketName, "bucketName不能为空");
        Assert.notNull(key, "key不能为空");

        ensureBucketNameValid(bucketName);
        ensureObjectKeyValid(key);

        //inputstream
        InputStream repeatableInputStream = null;
        if (originalRequest.getFile() != null) {
            File toUpload = originalRequest.getFile();

            if (!checkFile(toUpload)) {
                log.info("Illegal file path: " + toUpload.getPath());
                throw new ClientException("Illegal file path: " + toUpload.getPath());
            }
            metadata.setContentLength(toUpload.length());
            if (metadata.getContentType() == null) {
                metadata.setContentType(Mimetypes.getInstance().getMimetype(toUpload));
            }

            try {
                repeatableInputStream = new RepeatableFileInputStream(toUpload);
            } catch (IOException ex) {
                log.error("Cannot locate file to upload: ", ex);
                throw new ClientException("Cannot locate file to upload: ", ex);
            }

        } else {
            assertTrue(originalInputStream != null, "Please specify input stream or file to upload");

            if (metadata.getContentType() == null) {
                metadata.setContentType(Mimetypes.getInstance().getMimetype(key));
            }

            try {
                repeatableInputStream = newRepeatableInputStream(originalInputStream);
            } catch (IOException ex) {
                log.error("Cannot wrap to repeatable input stream: ", ex);
                throw new ClientException("Cannot wrap to repeatable input stream: ", ex);
            }
        }

        Map<String, String> headers = new HashMap<>();
        populateRequestMetadata(headers, metadata);

        RequestMessage httpRequest =
                new OSSRequestMessageBuilder()
                        .setEndpoint(createAndgetEndpoint())
                        .setMethod(WriteMode.getMappingMethod(mode))
                        .setBucket(bucketName)
                        .setKey(key)
                        .setHeaders(headers)
                        //.setParameters(params)
                        .setInputStream(repeatableInputStream)
                        .setInputSize(
                                determineInputStreamLength(repeatableInputStream, metadata.getContentLength()))
                        .setOriginalRequest(originalRequest)
                        .build();
        //暂时不支持append file
        //    Map<String, String> params = new LinkedHashMap<>();
        //    populateWriteObjectParams(mode, originalRequest, params);
        //retryStrategy
        //      RetryStrategy retryStrategy = context.getRetryStrategy() != null ?
        //          context.getRetryStrategy() : this.getDefaultRetryStrategy();

        return sendImpl(httpRequest, responseParser, bucketName, key, mode, true);
    }

    /**
     * Delete an object.
     */
    public void deleteObject(GenericRequest genericRequest) throws ObsException, ClientException {

        Assert.notNull(genericRequest, "genericRequest");

        String bucketName = genericRequest.getBucketName();
        String key = genericRequest.getKey();

        Assert.notNull(bucketName, "bucketName");
        ensureBucketNameValid(bucketName);
        Assert.notNull(key, "key");
        ensureObjectKeyValid(key);

        RequestMessage request =
                new OSSRequestMessageBuilder()
                        .setEndpoint(createAndgetEndpoint())
                        .setMethod(HttpMethod.DELETE)
                        .setBucket(bucketName)
                        .setKey(key)
                        .setOriginalRequest(genericRequest)
                        .build();

        sendImpl(request, emptyResponseParser, bucketName, key, null, true);
    }

    public ObsObject getObject(GetObjectRequest getObjectRequest)
            throws ObsException, ClientException {
        Assert.notNull(getObjectRequest, "getObjectRequest");

        String bucketName = null;
        String key = null;
        RequestMessage request = null;
        if (!getObjectRequest.isUseUrlSignature()) {
            bucketName = getObjectRequest.getBucketName();
            key = getObjectRequest.getKey();
            Assert.notNull(bucketName, "bucketName不能为空");
            Assert.notNull(key, "key不能为空");

            ensureBucketNameValid(bucketName);
            ensureObjectKeyValid(key);

            Map<String, String> headers = new HashMap<String, String>();
            populateGetObjectRequestHeaders(getObjectRequest, headers);

            //        Map<String, String> params = new HashMap<String, String>();
            //        populateResponseHeaderParameters(params, getObjectRequest.getResponseHeaders());
            //        String process = getObjectRequest.getProcess();
            //        if (process != null) {
            //            params.put(RequestParameters.SUBRESOURCE_PROCESS, process);
            //        }

            request =
                    new OSSRequestMessageBuilder()
                            .setEndpoint(createAndgetEndpoint())
                            .setMethod(HttpMethod.GET)
                            .setBucket(bucketName)
                            .setKey(key)
                            .setHeaders(headers)
                            //.setParameters(params)
                            .setOriginalRequest(getObjectRequest)
                            .build();
        } else {
            request = new RequestMessage(getObjectRequest);
            request.setMethod(HttpMethod.GET);
            request.setAbsoluteUrl(getObjectRequest.getAbsoluteUri());
            request.setUseUrlSignature(true);
            request.setHeaders(getObjectRequest.getHeaders());
        }
        GetObjectResponseParser parser = new GetObjectResponseParser(bucketName, key);

        ObsObject obsObject = sendImpl(request, parser, bucketName, key, null, false);
        return obsObject;
    }

    /**
     * 内部封装的http请求实现
     */
    private <T> T sendImpl(
            RequestMessage httpRequest,
            ResponseParser<T> responseParser,
            String bucketName,
            String key,
            WriteMode mode,
            boolean closeStream)
            throws ObsException, ClientException {

        //加上认证header
        //Authorization: OSS qn6qrrqxo2oawuk53otfjbyc:qZzjF3DUtd+yK16BdhGtFcCVknM=
        HttpMethod method = (mode == null) ? httpRequest.getMethod() : WriteMode.getMappingMethod(mode);
        RequestSigner signer = createSigner(method, bucketName, key);
        signer.sign(httpRequest);

        WrapperRequest wrapperRequest = buildRequest(httpRequest);
        HttpRequestBase httpRequestBase = httpRequestFactory.createHttpRequest(wrapperRequest);

        Https instance = Https.getInstance();
        instance.connectTimeout(obsProperties.getTimeout() / 2);
        instance.readTimeout(obsProperties.getTimeout() / 2);
        HttpResult result;
        //不关闭inputStream
        if (!closeStream) {
            result =
                    instance.execute(
                            httpRequestBase, wrapperRequest.getHeaders(), OSSConstants.DEFAULT_CHARSET_NAME);
        } else {
            result =
                    instance.execute(
                            null,
                            httpRequestBase,
                            wrapperRequest.getHeaders(),
                            false,
                            OSSConstants.DEFAULT_CHARSET_NAME);
        }

        HttpUtil.convertHeaderCharsetFromIso88591(result.getHeaders());

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatusCode(result.getStatus());
        responseMessage.setHeaders(result.getHeaders());
        responseMessage.setUri(wrapperRequest.getUri());
        responseMessage.setBuketName(bucketName);
        responseMessage.setResult(result.getBody());
        if (!closeStream) {
            responseMessage.setContent(((Https.HttpResultEx) result).getContent());
        }
        //解析结果
        try {
            return responseParser.parse(responseMessage);
        } catch (ResponseParseException rpe) {
            throw new ObsException("Unable to parse response error", rpe);
        }
    }

    private WrapperRequest buildRequest(RequestMessage requestMessage) throws ClientException {

        WrapperRequest request = new WrapperRequest();
        request.setMethod(requestMessage.getMethod());
        //request.setUseChunkEncoding(requestMessage.isUseChunkEncoding());

        if (requestMessage.isUseUrlSignature()) {
            request.setUri(requestMessage.getAbsoluteUrl().toString());
            request.setUseUrlSignature(true);

            request.setContent(requestMessage.getContent());
            request.setContentLength(requestMessage.getContentLength());
            request.setHeaders(requestMessage.getHeaders());

            return request;
        }

        request.setHeaders(requestMessage.getHeaders());
        // The header must be converted after the request is signed,
        // otherwise the signature will be incorrect.
        if (request.getHeaders() != null) {
            HttpUtil.convertHeaderCharsetToIso88591(request.getHeaders());
        }

        final String delimiter = "/";
        String uri = requestMessage.getEndpoint().toString();
        if (!uri.endsWith(delimiter)
                && (requestMessage.getResourcePath() == null
                || !requestMessage.getResourcePath().startsWith(delimiter))) {
            uri += delimiter;
        }

        if (requestMessage.getResourcePath() != null) {
            uri += requestMessage.getResourcePath();
        }

        String paramString =
                HttpUtil.paramToQueryString(
                        requestMessage.getParameters(), OSSConstants.DEFAULT_CHARSET_NAME);

        /*
         * For all non-POST requests, and any POST requests that already have a
         * payload, we put the encoded params directly in the URI, otherwise,
         * we'll put them in the POST request's payload.
         */
        boolean requestHasNoPayload = requestMessage.getContent() != null;
        boolean requestIsPost = requestMessage.getMethod() == HttpMethod.POST;
        boolean putParamsInUri = !requestIsPost || requestHasNoPayload;
        if (paramString != null && putParamsInUri) {
            uri += "?" + paramString;
        }

        request.setUri(uri);

        if (requestIsPost && requestMessage.getContent() == null && paramString != null) {
            // Put the param string to the request body if POSTing and
            // no content.
            try {
                byte[] buf = paramString.getBytes(OSSConstants.DEFAULT_CHARSET_NAME);
                ByteArrayInputStream content = new ByteArrayInputStream(buf);
                request.setContent(content);
                request.setContentLength(buf.length);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("EncodingFailed", e);
            }
        } else {
            request.setContent(requestMessage.getContent());
            request.setContentLength(requestMessage.getContentLength());
        }

        return request;
    }

    private RequestSigner createSigner(HttpMethod method, String bucketName, String key) {
        String resourcePath =
                "/" + ((bucketName != null) ? bucketName + "/" : "") + ((key != null ? key : ""));

        return new OSSRequestSigner(method.toString(), resourcePath, this.obsProperties);
    }

    private static enum WriteMode {

        /* If object already not exists, create it. otherwise, append it with the new input */
        APPEND("APPEND"),

        /* No matter object exists or not, just overwrite it with the new input */
        OVERWRITE("OVERWRITE");

        private final String modeAsString;

        private WriteMode(String modeAsString) {
            this.modeAsString = modeAsString;
        }

        public static HttpMethod getMappingMethod(WriteMode mode) {
            switch (mode) {
                case APPEND:
                    return HttpMethod.POST;

                case OVERWRITE:
                    return HttpMethod.PUT;

                default:
                    throw new IllegalArgumentException("Unsuported write mode" + mode.toString());
            }
        }

        @Override
        public String toString() {
            return this.modeAsString;
        }
    }

    public interface ResponseParser<T> {
        T parse(ResponseMessage response) throws ResponseParseException;
    }

    public static class HttpRequestFactory {
        public HttpRequestBase createHttpRequest(WrapperRequest request) {
            String uri = request.getUri();

            HttpRequestBase httpRequest;
            HttpMethod method = request.getMethod();

            if (method == HttpMethod.POST) {
                HttpPost postMethod = new HttpPost(uri);
                if (request.getContent() != null) {
                    postMethod.setEntity(new InputStreamEntity(request.getContent()));
                }
                httpRequest = postMethod;
            } else if (method == HttpMethod.PUT) {
                HttpPut putMethod = new HttpPut(uri);
                putMethod.setEntity(new InputStreamEntity(request.getContent()));
                httpRequest = putMethod;
            } else if (method == HttpMethod.GET) {
                httpRequest = new HttpGet(uri);
            } else if (method == HttpMethod.DELETE) {
                httpRequest = new HttpDelete(uri);
            } else if (method == HttpMethod.HEAD) {
                httpRequest = new HttpHead(uri);
            } else if (method == HttpMethod.OPTIONS) {
                httpRequest = new HttpOptions(uri);
            } else {
                throw new ClientException("Unknown HTTP method name: " + method.toString());
            }
            return httpRequest;
        }
    }

    @Data
    public static class WrapperRequest extends HttpMesssage {
        private String uri;
        private HttpMethod method;
        private boolean useUrlSignature = false;
        // private boolean useChunkEncoding = false;
    }

    public static final class PutObjectReponseParser implements ResponseParser<PutObjectResult> {
        @Override
        public PutObjectResult parse(ResponseMessage response) throws ResponseParseException {
            PutObjectResult result = new PutObjectResult();
            try {
                result.setETag(trimQuotes(response.getHeaders().get(OSSHeaders.ETAG)));
                result.setRequestId(response.getRequestId());
                result.getResponse().setUri(response.getUri());
                result.getResponse().setStatusCode(response.getStatusCode());
                result.setBuketName(response.getBuketName());
                //出错情况
                if (response.getStatusCode() != HTTP_SUCCESS_STATUS_CODE && response.getResult() != null) {
                    Document document = AliyunOSSResponseParser.getInstance().parse(response.getResult());
                    NodeList messageNode = document.getElementsByTagName(AliyunOSSResponseParser.MESSAGE);
                    Element line = (Element) messageNode.item(0);
                    String message = BaseMessageResponseParser.getCharacterDataFromElement(line);
                    result.getResponse().setErrorResponseAsString(message);
                }
                //setCRC(result, response);
                return result;
            } catch (Exception e) {
                if (e instanceof IOException
                        || e instanceof ParserConfigurationException
                        || e instanceof SAXException) {
                    throw new ResponseParseException("解析返回xml失败");
                }
            } finally {
                safeCloseResponse(response);
            }
            return result;
        }
    }

    public static final class GetObjectResponseParser implements ResponseParser<ObsObject> {
        private String bucketName;
        private String key;

        public GetObjectResponseParser(final String bucketName, final String key) {
            this.bucketName = bucketName;
            this.key = key;
        }

        @Override
        public ObsObject parse(ResponseMessage response) throws ResponseParseException {
            ObsObject ossObject = new ObsObject();
            ossObject.setBucketName(this.bucketName);
            ossObject.setKey(this.key);
            ossObject.setObjectContent(response.getContent());
            //            ossObject.setRequestId(response.getRequestId());
            //            ossObject.setResponse(response);
            try {
                ossObject.setObjectMetadata(parseObjectMetadata(response.getHeaders()));
                //setServerCRC(ossObject, response);
                return ossObject;
            } catch (ResponseParseException e) {
                // Close response only when parsing exception thrown.
                safeCloseResponse(response);
                // Rethrow
                throw e;
            }
        }
    }

    public static final class EmptyResponseParser implements ResponseParser<ResponseMessage> {

        @Override
        public ResponseMessage parse(ResponseMessage response) throws ResponseParseException {
            // Close response and return it directly without parsing.
            response.setStatusCode(response.getStatusCode());

            safeCloseResponse(response);
            return response;
        }
    }
}

package com.acooly.module.obs.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Utility class used to determine the mimetype of files based on file extensions.
 *
 * @author shuijing
 */
@Slf4j
public class Mimetypes {

    /* The default MIME type */
    public static final String DEFAULT_MIMETYPE = "application/octet-stream";

    private static Mimetypes mimetypes = null;

    private HashMap<String, String> extensionToMimetypeMap = new HashMap<String, String>();

    private Mimetypes() {
    }

    public static synchronized Mimetypes getInstance() {
        if (mimetypes != null) return mimetypes;

        mimetypes = new Mimetypes();
        InputStream is = mimetypes.getClass().getResourceAsStream("/util/mime.types");
        if (is != null) {
            log.debug("Loading mime types from file in the classpath: mime.types");

            try {
                mimetypes.loadMimetypes(is);
            } catch (IOException e) {
                log.error("Failed to load mime types from file in the classpath: mime.types", e);
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        } else {
            log.warn("Unable to find 'mime.types' file in classpath");
        }
        return mimetypes;
    }

    public void loadMimetypes(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;

        while ((line = br.readLine()) != null) {
            line = line.trim();

            if (line.startsWith("#") || line.length() == 0) {
                // Ignore comments and empty lines.
            } else {
                StringTokenizer st = new StringTokenizer(line, " \t");
                if (st.countTokens() > 1) {
                    String extension = st.nextToken();
                    if (st.hasMoreTokens()) {
                        String mimetype = st.nextToken();
                        extensionToMimetypeMap.put(extension.toLowerCase(), mimetype);
                    }
                }
            }
        }
    }

    public String getMimetype(String fileName) {
        int lastPeriodIndex = fileName.lastIndexOf(".");
        if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < fileName.length()) {
            String ext = fileName.substring(lastPeriodIndex + 1).toLowerCase();
            if (extensionToMimetypeMap.keySet().contains(ext)) {
                String mimetype = (String) extensionToMimetypeMap.get(ext);
                return mimetype;
            }
        }
        return DEFAULT_MIMETYPE;
    }

    public String getMimetype(File file) {
        return getMimetype(file.getName());
    }
}

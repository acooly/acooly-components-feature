package com.acooly.module.obs.client.oss.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author shuijing
 */
@Getter
@Setter
public abstract class WebServiceRequest {

    public static final WebServiceRequest NOOP = new WebServiceRequest() {
    };

    //    private ProgressListener progressListener = ProgressListener.NOOP;

    private Map<String, String> parameters = new LinkedHashMap<String, String>();
    private Map<String, String> headers = new LinkedHashMap<String, String>();

    //    public void setProgressListener(ProgressListener progressListener) {
    //        this.progressListener = (progressListener == null) ?
    //                ProgressListener.NOOP : progressListener;
    //    }
    //
    //    public ProgressListener getProgressListener() {
    //        return progressListener;
    //    }
    //
    //    public <T extends WebServiceRequest> T withProgressListener(ProgressListener progressListener) {
    //        setProgressListener(progressListener);
    //        @SuppressWarnings("unchecked")
    //        T t = (T)this;
    //        return t;
    //    }

}

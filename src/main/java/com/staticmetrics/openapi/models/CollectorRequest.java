package com.staticmetrics.openapi.models;

import java.io.Serializable;

public class CollectorRequest implements Serializable {
    private String urlOpenApiFile;

    public String getUrlOpenApiFile() { return urlOpenApiFile; }
}

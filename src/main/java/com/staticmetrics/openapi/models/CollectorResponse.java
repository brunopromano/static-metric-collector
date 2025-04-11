package com.staticmetrics.openapi.models;

import java.io.Serializable;
import java.util.List;

public class CollectorResponse implements Serializable {
    private List<String> endpoints;

    public List<String> getEndpoints() { return endpoints; }

    public void setEndpoints(List<String> endpoints) { this.endpoints = endpoints; }
}

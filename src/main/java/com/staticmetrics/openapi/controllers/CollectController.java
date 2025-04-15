package com.staticmetrics.openapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staticmetrics.openapi.models.CollectorRequest;
import com.staticmetrics.openapi.models.CollectorResponse;
import com.staticmetrics.openapi.models.Measurement;
import com.staticmetrics.openapi.models.Metric;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/collector")
public class CollectController {

    @PostMapping
    public ResponseEntity<CollectorResponse> collectMetricData(@RequestBody CollectorRequest request) {
        OpenAPI openAPI = new OpenAPIV3Parser().read(request.getUrlOpenApiFile());

        int numEndpoints = openAPI.getPaths().size();

        Metric metric = new Metric(
                "Operations per service",
                "openapi");

        Measurement measurement = new Measurement(
            request.getUrlOpenApiFile(),
            numEndpoints,
            "operations",
            Instant.now()
        );

        CollectorResponse response = new CollectorResponse(metric, measurement);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
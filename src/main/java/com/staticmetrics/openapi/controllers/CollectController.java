package com.staticmetrics.openapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.staticmetrics.openapi.models.CollectorRequest;
import com.staticmetrics.openapi.models.CollectorResponse;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;

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

        List<String> verbsAndPaths = extractVerbsAndPaths(openAPI);

        CollectorResponse response = new CollectorResponse();
        response.setEndpoints(verbsAndPaths);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // TODO: Criar camada service e remover da controller
    private List<String> extractVerbsAndPaths(OpenAPI openAPI) {
        List<String> results = new ArrayList<>();
        Paths paths = openAPI.getPaths();

        paths.forEach((path, pathItem) -> {
            Map<PathItem.HttpMethod, Operation> operations = pathItem.readOperationsMap();

            operations.keySet().forEach(httpVerb -> {
                String verbPath = httpVerb.name() + " " + path;
                results.add(verbPath);
            });
        });

        return results;
    }
    
}
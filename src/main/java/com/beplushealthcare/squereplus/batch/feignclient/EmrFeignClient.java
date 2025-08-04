package com.beplushealthcare.squereplus.batch.feignclient;


import com.beplushealthcare.squereplus.batch.feignclient.dto.EdgeRetrieveOutPatientTreatmentHistoryRequest;
import com.beplushealthcare.squereplus.batch.feignclient.dto.EdgeRetrieveWalkInTreatmentOutPatientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.Map;

@FeignClient(name = "EMR", url = "{}")
public interface EmrFeignClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<String> getAccessToken(
            URI uri,
            @RequestBody Map<String, ?> data
    );


    @GetMapping
    ResponseEntity<String> verifyToken(
            URI uri,
            @RequestHeader Map<String, String> headers
    );

    @GetMapping
    ResponseEntity<String> edgeRetrieveOutPatientTreatmentHistory(
            URI uri,
            @RequestHeader Map<String, String> headers,
            @SpringQueryMap EdgeRetrieveOutPatientTreatmentHistoryRequest request
    );

    @GetMapping
    ResponseEntity<String> edgeRetrieveWalkInTreatmentOutPatient(
            URI uri,
            @RequestHeader Map<String, String> headers,
            @SpringQueryMap EdgeRetrieveWalkInTreatmentOutPatientRequest request
    );
}

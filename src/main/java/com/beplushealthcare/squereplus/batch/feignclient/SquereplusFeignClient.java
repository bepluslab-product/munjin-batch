package com.beplushealthcare.squereplus.batch.feignclient;

import com.beplushealthcare.squereplus.batch.feignclient.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.util.List;

@FeignClient(name = "SQUEREPLUS", url = "{}")
public interface SquereplusFeignClient {

    @GetMapping()
    ResponseEntity<Object> getHospitalAccountsByYkiho(URI uri, @SpringQueryMap String ykiho);
}

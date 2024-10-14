package com.beplushealthcare.munjin.batch.feignclient;

import com.beplushealthcare.munjin.batch.feignclient.dto.BuminAlimTalkRequest;
import com.beplushealthcare.munjin.batch.feignclient.dto.BuminAlimTalkResponse;
import com.beplushealthcare.munjin.batch.feignclient.dto.BuminLoginRequest;
import com.beplushealthcare.munjin.batch.feignclient.dto.BuminLoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;

@FeignClient(name = "BUMIN", url = "{}")
public interface BuminKakaoAlimTalkFeignClient {

    @PostMapping()
    ResponseEntity<BuminLoginResponse> buminLogin(URI uri, BuminLoginRequest request);

    @PostMapping()
    ResponseEntity<BuminAlimTalkResponse> buminAlimTalkSend(URI uri, @RequestHeader(name = "Authorization") String token, BuminAlimTalkRequest request);
}
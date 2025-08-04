package com.beplushealthcare.squereplus.batch.service;

import com.beplushealthcare.squereplus.batch.enums.BuminType;
import com.beplushealthcare.squereplus.batch.feignclient.SquereplusFeignClient;
import com.beplushealthcare.squereplus.batch.feignclient.dto.HospitalAccountResponse;
import com.beplushealthcare.squereplus.batch.properties.SquereplusProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SquerePlusService {
    private final SquereplusProperties squereplusProperties;
    private final SquereplusFeignClient squereplusFeignClient;

    public List<HospitalAccountResponse> getHospitalAccounts(BuminType buminType) {
//        ResponseEntity<List<HospitalAccountResponse>> response = null;
        ResponseEntity<Object> response = null;
        try {
            response = squereplusFeignClient.getHospitalAccountsByYkiho(new URL(squereplusProperties.getHospitalAccountsPath()).toURI(), buminType.getYkiho());
        } catch (Exception e) {
//            throw new GlobalException(ResponseCode.BUMIN_LOGIN_API_ERROR);
            log.error("bumin login error.. {}", buminType.name() );
        }
//        return response.getBody();
        return null;
    }
}


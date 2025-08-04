package com.beplushealthcare.squereplus.batch.service;

import com.beplushealthcare.squereplus.batch.enums.BuminType;
import com.beplushealthcare.squereplus.batch.feignclient.BuminKakaoAlimTalkFeignClient;
import com.beplushealthcare.squereplus.batch.feignclient.EmrFeignClient;
import com.beplushealthcare.squereplus.batch.feignclient.dto.*;
import com.beplushealthcare.squereplus.batch.properties.BuminProperties;
import com.beplushealthcare.squereplus.batch.properties.EMRProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class EdgeService {
    private final EmrFeignClient emrFeignClient;
    private final BuminKakaoAlimTalkFeignClient buminKakaoAlimTalkFeignClient;
    private final BuminProperties buminProperties;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;
    private final EMRProperties properties;
    private final String defaultHeaderValue = "beplushealthcare";
    private final String EDGE_NEXT_TOKEN_KEY = "edgeNext:token:hospitalId:";

    public EmrLoginResponse getAccessToken(EdgeLoginRequest request) throws Exception {
        String edgeNextToken = redisService.getRedisValue(EDGE_NEXT_TOKEN_KEY + request.getHospitalId());
        if (edgeNextToken == null) {
            try {
                ResponseEntity<String> response = emrFeignClient.getAccessToken(new URI(properties.getEdge().getLoginPath()),
                        request.toQueryMap()
                );
                EmrLoginResponse emrLoginResponse = objectMapper.readValue(response.getBody(), EmrLoginResponse.class);
                redisService.setRedisValue(
                        EDGE_NEXT_TOKEN_KEY + request.getHospitalId(),
                        emrLoginResponse.getAccessToken(),
                        emrLoginResponse.getExpiresIn() + 600000);
                return emrLoginResponse;
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return new EmrLoginResponse(edgeNextToken);
    }

    public List<EdgeRetrieveOutPatientTreatmentHistoryResponse> retrieveOutPatientHistory(
            String tenantId,
            String hospitalId,
            String authorization,
            EdgeRetrieveOutPatientTreatmentHistoryRequest request
    ) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("tenantId", tenantId);
        headers.put("hospitalId", hospitalId);
        headers.put("viewId", defaultHeaderValue);
        headers.put("displayId", defaultHeaderValue);
        headers.put("employeeId", defaultHeaderValue);
        headers.put("employeeName", defaultHeaderValue);
        headers.put("cultureId", "ko-kr");
        headers.put("timeZoneId", "1");
        headers.put("isBuiltInEmployee", "FALSE");
        headers.put("Authorization", "Bearer " + authorization);

        List<EdgeRetrieveOutPatientTreatmentHistoryResponse> response = null;
        try {
            response = objectMapper.readValue(emrFeignClient.edgeRetrieveOutPatientTreatmentHistory(new URI(properties.getEdge().getPatientHistoryPath()), headers, request).getBody(), new TypeReference<List<EdgeRetrieveOutPatientTreatmentHistoryResponse>>() {});
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return response;
    }

    private void verifyToken(String accessToken, String tenantId, String hospitalId) {
//        emrFeignClient.verifyToken()
    }

//    public List<EdgeRetrieveWalkInTreatmentOutPatientResponse> retrieveWalkInOutPatient(
//            String tenantId,
//            String hospitalId,
//            String authorization,
//            EdgeRetrieveWalkInTreatmentOutPatientRequest request
//    ) {
//
//        Map<String, String> headers = new HashMap<>();
//        headers.put("tenantId", tenantId);
//        headers.put("hospitalId", hospitalId);
//        headers.put("viewId", defaultHeaderValue);
//        headers.put("displayId", defaultHeaderValue);
//        headers.put("employeeId", defaultHeaderValue);
//        headers.put("employeeName", defaultHeaderValue);
//        headers.put("cultureId", "ko-kr");
//        headers.put("timeZoneId", "1");
//        headers.put("isBuiltInEmployee", "FALSE");
//        headers.put("Authorization", "Bearer " + authorization);
////        headers.put("email","");
////        headers.put("additionalInfoJSON","");
//
//        List<EdgeRetrieveWalkInTreatmentOutPatientResponse> response = null;
//        try {
//            response = objectMapper.readValue(emrFeignClient.edgeRetrieveWalkInTreatmentOutPatient(new URI(properties.getEdge().getPatientWalkInPath()), headers, request).getBody(), new TypeReference<List<EdgeRetrieveWalkInTreatmentOutPatientResponse>>() {});
//        } catch (Exception e) {
//            throw new GlobalException(ResponseCode.UNKNOWN);
//        }
//        return response;
//
//    }
//
//    public List<EdgeRetrieveOutPatientTreatmentHistoryResponse> retrieveOutPatientHistory(
//            String tenantId,
//            String hospitalId,
//            String authorization,
//            EdgeRetrieveOutPatientTreatmentHistoryRequest request
//    ) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("tenantId", tenantId);
//        headers.put("hospitalId", hospitalId);
//        headers.put("viewId", defaultHeaderValue);
//        headers.put("displayId", defaultHeaderValue);
//        headers.put("employeeId", defaultHeaderValue);
//        headers.put("employeeName", defaultHeaderValue);
//        headers.put("cultureId", "ko-kr");
//        headers.put("timeZoneId", "1");
//        headers.put("isBuiltInEmployee", "FALSE");
//        headers.put("Authorization", "Bearer " + authorization);
//
//        List<EdgeRetrieveOutPatientTreatmentHistoryResponse> response = null;
//        try {
//            response = objectMapper.readValue(emrFeignClient.edgeRetrieveOutPatientTreatmentHistory(new URI(properties.getEdge().getPatientHistoryPath()), headers, request).getBody(), new TypeReference<List<EdgeRetrieveOutPatientTreatmentHistoryResponse>>() {});
//        } catch (Exception e) {
//            throw new GlobalException(e.getMessage());
//        }
//        return response;
//
//    }
}

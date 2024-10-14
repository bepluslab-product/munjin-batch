package com.beplushealthcare.munjin.batch.jobs.steps;

import com.beplushealthcare.munjin.batch.enums.BuminType;
import com.beplushealthcare.munjin.batch.feignclient.BuminKakaoAlimTalkFeignClient;
import com.beplushealthcare.munjin.batch.feignclient.EmrFeignClient;
import com.beplushealthcare.munjin.batch.feignclient.dto.*;
import com.beplushealthcare.munjin.batch.properties.BuminProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 *
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SendKakaoAlimTalkToOutPatientHistoryItemReader {

    private final EmrFeignClient emrFeignClient;
    private final BuminKakaoAlimTalkFeignClient buminKakaoAlimTalkFeignClient;
    private final BuminProperties buminProperties;

    @StepScope
    @Bean("c.b.a.l.o.m.Reader")
//    protected FlatFileItemReader<EdgeRetrieveOutPatientTreatmentHistoryResponse> outPatientHistoryReader(
    protected ItemReader<EdgeRetrieveOutPatientTreatmentHistoryResponse> outPatientHistoryReader(
    ) throws IOException {
        BuminLoginResponse login = buminLogin(BuminType.SEOUL);

//        BuminAlimTalkResponse buminAlimTalkResponse = buminLogin(logi);
//        log.info("begin reading.. {}", bu);
////        getAccessToken()
//        emrFeignClient.edgeRetrieveOutPatientTreatmentHistory()
        System.out.println(login);
        return null;
    }

    public BuminLoginResponse buminLogin(BuminType buminType) {
        ResponseEntity<BuminLoginResponse> response = null;
        try {
            response = buminKakaoAlimTalkFeignClient.buminLogin(new URL(buminProperties.getLoginUrl()).toURI(), BuminLoginRequest.of(buminType));
        } catch (Exception e) {
//            throw new GlobalException(ResponseCode.BUMIN_LOGIN_API_ERROR);
            log.error("bumin login error.. {}", buminType.name() );
        }
        return response.getBody();
    }

//    public EmrLoginResponse getAccessToken(EdgeLoginRequest edgeLoginRequest) {
////        String edgeNextToken = redisService.getRedisValue(EDGE_NEXT_TOKEN_KEY + request.getHospitalId());
//
//        try {
//            ResponseEntity<String> response = emrFeignClient.getAccessToken(new URI(properties.getEdge().getLoginPath()),
//                    request.toQueryMap()
//            );
//            EmrLoginResponse emrLoginResponse = objectMapper.readValue(response.getBody(), EmrLoginResponse.class);
//            redisService.setRedisValue(
//                    EDGE_NEXT_TOKEN_KEY + request.getHospitalId(),
//                    emrLoginResponse.getAccessToken(),
//                    emrLoginResponse.getExpiresIn() + 600000);
//            return emrLoginResponse;
//        } catch (Exception e) {
//            throw new GlobalException(ResponseCode.EDGE_NEXT_AUTH_TOKEN_API_ERROR);
//        }
//        return new EmrLoginResponse(edgeNextToken);
//    }
}

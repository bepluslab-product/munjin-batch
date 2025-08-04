package com.beplushealthcare.squereplus.batch.jobs.steps;

import com.beplushealthcare.squereplus.batch.enums.BuminType;
import com.beplushealthcare.squereplus.batch.feignclient.dto.HospitalAccountResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SendKakaoAlimTalkToOutPatientHistoryItemReaderTest {

    @Autowired
    private SendKakaoAlimTalkToOutPatientHistoryItemReader service;

//    @Test
//    void test() {
//        List<HospitalAccountResponse> aa = service.getHospitalAccounts(BuminType.SEOUL);
//        System.out.println(aa.size());
//    }
}
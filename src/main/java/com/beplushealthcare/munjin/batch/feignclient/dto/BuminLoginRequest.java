package com.beplushealthcare.munjin.batch.feignclient.dto;

import com.beplushealthcare.munjin.batch.enums.BuminType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BuminLoginRequest {
    private String hospitalId;

    private String company;

    private String accessKey;

    public static BuminLoginRequest of(BuminType buminType) {
        return BuminLoginRequest.builder()
                .hospitalId(buminType.getYkiho())
                .company("beplusheathcare-send")    // 오탈자 확인
                .accessKey("6a03e541-4ecc-465a-9319-f80ee4b71395")
                .build();
    }
}

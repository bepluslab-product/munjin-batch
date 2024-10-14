package com.beplushealthcare.munjin.batch.feignclient.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BuminLoginResponse {
    private String name;

    private String accessToken;
}

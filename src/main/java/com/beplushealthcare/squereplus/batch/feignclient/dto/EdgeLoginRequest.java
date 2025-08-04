package com.beplushealthcare.squereplus.batch.feignclient.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class EdgeLoginRequest {
    private String clientId;

    private String grantType;

    private String clientSecret;

    private String scope;

    private String hospitalId;

    public Map<String, String> toQueryMap() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("client_id", clientId);
        queryMap.put("grant_type", grantType);
        queryMap.put("client_secret", clientSecret);
        queryMap.put("scope", scope);
        return queryMap;
    }
}

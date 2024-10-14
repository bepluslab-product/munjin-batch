package com.beplushealthcare.munjin.batch.enums;

import com.beplushealthcare.munjin.batch.properties.EMRProperties;
import lombok.Getter;

@Getter
public enum BuminType {

    SEOUL("seoul", "11101334", "BUMIN007045", "", "", "", "", "", ""),
    BUSAN("busan", "21100632", "BUMIN100037", "", "", "", "", "", ""),
    HAEUNDAE("haeundae", "21100641", "BUMIN000013", "", "", "", "", "", "");

    private String code;
    private String ykiho;
    private String templateCode;
    private String scope;
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String tenantId;
    private String hospitalId;

    BuminType(String code, String ykiho, String templateCode, String scope, String grantType, String clientId, String clientSecret, String tenantId, String hospitalId) {
        this.code = code;
        this.ykiho = ykiho;
        this.templateCode = templateCode;
        this.scope = scope;
        this.grantType = grantType;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tenantId = tenantId;
        this.hospitalId = hospitalId;
    }

    public static void initialize(EMRProperties.Auth auth) {
        for (EMRProperties.Auth.HospitalProperties hospital : auth.getHospitals()) {
            for (BuminType value : BuminType.values()) {
                if (value.code.equalsIgnoreCase(hospital.getCode())) {
                    value.scope = auth.getScope();
                    value.grantType = auth.getGrantType();
                    value.clientId = hospital.getClientId();
                    value.clientSecret = hospital.getClientSecret();
                    value.tenantId = hospital.getTenantId();
                    value.hospitalId = hospital.getHospitalId();
                }
            }
        }
    }

}

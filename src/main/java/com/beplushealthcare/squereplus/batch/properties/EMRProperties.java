package com.beplushealthcare.squereplus.batch.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "emr")
public class EMRProperties {

    private Edge edge;

    @Setter
    @Getter
    public static class Edge {
        private String url;

        private User user;

        private Path path;

        private Auth auth;

        public String getLoginPath() {
            return path.getBaseUrl() + path.getLoginPath();
        }

        public String getVerificationPath() {
            return path.getProxyBaseUrl() + path.getVerificationPath();
        }

        public String getPatientHistoryPath() {
            return path.getProxyBaseUrl() + path.getPatientHistoryPath();
        }

        public String getPatientWalkInPath() {
            return path.getProxyBaseUrl() + path.getPatientWalkInPath();
        }
    }

    @Setter
    public static class User {
        private String username;

        private String password;
    }

    @Setter
    @Getter
    public static class Path {
        private String baseUrl;
        private String loginPath;
        private String verificationPath;
        private String proxyBaseUrl;
        private String patientHistoryPath;
        private String patientWalkInPath;
    }

    @Setter
    @Getter
    public static class Auth {
        private String scope;
        private String grantType;
        private List<HospitalProperties> hospitals;

        @Getter
        @Setter
        public static class HospitalProperties {
            private String code;
            private String clientId;
            private String clientSecret;
            private String tenantId;
            private String hospitalId;

        }
    }
}

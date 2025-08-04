package com.beplushealthcare.squereplus.batch.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "squereplus")
public class SquereplusProperties {
    private Path path;

    public String getHospitalAccountsPath() {
        return path.getBaseUrl() + path.getHospitalAccountsPath();
    }
    @Setter
    @Getter
    public static class Path {
        private String baseUrl;
        private String hospitalAccountsPath;
    }
}

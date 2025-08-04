package com.beplushealthcare.squereplus.batch.properties;

import com.beplushealthcare.squereplus.batch.enums.BuminType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "bumin")
public class BuminProperties {
    private Path path;

    public String getLoginUrl() {
        return path.getBaseUrl() + path.getLoginUrl();
    }

    public String getSendAlimTalkUrl(BuminType buminType) {
        return path.getBaseUrl() + String.format(path.getSendAlimtalk(), buminType.getCode());
    }

    @Getter
    @Setter
    public static class Path {
        private String baseUrl;

        private String loginUrl;

        private String sendAlimtalk;
    }
}

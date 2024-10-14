package com.beplushealthcare.munjin.batch.enums;

import com.beplushealthcare.munjin.batch.properties.EMRProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuminTypeInitializer {

    private final EMRProperties emrProperties;

    @PostConstruct
    public void init() {
        BuminType.initialize(emrProperties.getEdge().getAuth());

    }
}

package com.beplushealthcare.munjin.batch.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum EventStatus {
    SUCCESS, FAIL, ERROR, RETRY_EXCEEDED
}

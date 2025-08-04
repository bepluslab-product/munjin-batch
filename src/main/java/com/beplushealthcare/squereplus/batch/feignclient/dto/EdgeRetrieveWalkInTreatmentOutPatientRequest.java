package com.beplushealthcare.squereplus.batch.feignclient.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class EdgeRetrieveWalkInTreatmentOutPatientRequest {
    private String searchStartDateTime; // 조회 시작 일시
    private String searchEndDateTime; // 조회 종료 일시
    private String patientDisplayId; // 환자 등록 번호
    private String encounterId; // 수진ID
    private String departmentDisplayCode; // 진료과 코드
    private String attendingPhysicianDisplayId; // 진료의 사번
    private boolean includeCancellation; // 취소 보함 여부
}

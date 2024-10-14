package com.beplushealthcare.munjin.batch.feignclient.dto;

import lombok.Getter;

@Getter
public class EdgeRetrieveWalkInTreatmentOutPatientResponse {
    private String patientDisplayId; // 환자 아이디
    private String patientName; // 환자 명
    private String telephoneNumber; // 연락처
    private String genderCode; // 성별
    private String birthDate; // 생년월일
    private String accountId; // 수납수진 ID
    private String encounterId; // 수진 ID
    private String departmentId; // 진료과 ID
    private String departmentCode; // 진료과 코드
    private String departmentName; // 진료과 명
    private String attendingPhysicianId; // 진료의 ID
    private String attendingPhysicianCode; // 진료의 코드
    private String attendingPhysicianName; // 진료의 명
    private String scheduleDatetime; // 진료예약 일시
    private String eligibilityTypeCode; // 환자 유형
    private String eligibilityTypeName; // 환자 유형 명
    private String coverageTypeCode; // 유형 보조
    private String coverageTypeName; // 유형 보조 명
    private String accountClassCode; // 접수 구분
    private String accountClassName; // 접수 구분 명
    private String treatmentTypeCode; // 예약 구분
    private String treatmentTypeName; // 예약 구분 명
    private String purposeTypeCode; // 방문 목적
    private String purposeTypeName;  // 방문 목적 명
    private String throughTypeCode; // 접수 경로
    private String throughTypeName; // 접수 경로 명
    private String accountRemark; // 예약 비고
    private String encounterTypeCode; // 예약 상세
    private String encounterTypeName; // 예약 상세 명
    private String reasonCode; // 진료 코드
    private String reasonName; // 진료 목적
    private String checkinActivationStatusCode; // 진료 상태
    private String checkinActivationStatusName; // 진료 상태 명
    private boolean isCanceled; // 취소 여부
    private String cancellationDatetime; // 취소 일시
}

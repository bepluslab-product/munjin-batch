package com.beplushealthcare.munjin.batch.feignclient.dto;

import com.beplushealthcare.munjin.batch.enums.BuminType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BuminAlimTalkRequest {
    private String patientId;
    private BizTalk bizTalk;

    @Builder
    @Getter
//    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class BizTalk {
        private String templateCode;
        private String content;
        private Button button;
    }

    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class Button {
        private Integer btnType;
        private String buttonName;
        private String buttonType;
        private String urlPc;
        private String urlMobile;
    }

    public static BuminAlimTalkRequest of(String patientId,
                                          String patientName,
                                          String reservationDateTime,
                                          String dockerName,
                                          String munjinUrl,
                                          BuminType buminType) {
        return BuminAlimTalkRequest.builder()
                .patientId(patientId)
                .bizTalk(BizTalk.builder()
                        .templateCode(buminType.getTemplateCode())
                        .content(getContent(patientName, reservationDateTime, dockerName))
                        .button(Button.builder()
                                .btnType(2)
                                .buttonName("문진작성하기")
                                .buttonType("WL")
                                .urlPc(munjinUrl)
                                .urlMobile(munjinUrl)
                                .build())
                        .build())
                .build();
    }

    public static String getContent(String patientName, String reservationDateTime, String dockerName) {
        return String.format("""
                [모바일문진 작성안내]\s
                %s 님의 증상을 병원 방문 전 주치의에게 전달할 수 있는 모바일 문진입니다.
                
                ◈ 내원 전 작성하시면 진료 진행이 좀 더 수월할 수 있습니다
                ◈ 응답해 주신 내용은 의료법에 의거하여 비밀이 철저히 보장됩니다.
                ◈ 이전에 서면으로 작성하신 분들은 작성하지 않으셔도 됩니다.
                
                [예약사항]
                %s 주치의(%s)로 진료예약되셨습니다.
                전화문의: 1577-7582
                (평일: 09시~18시, 토요일: 09시~13시)
                """, patientName, reservationDateTime, dockerName);
    }
}

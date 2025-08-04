package com.beplushealthcare.squereplus.batch.repository;

import com.beplushealthcare.squereplus.batch.enums.EventStatus;
import com.beplushealthcare.squereplus.batch.enums.EventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "event_log")
@Entity
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID")
    private Long id;

    @Comment("이벤트 타입")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Comment("이벤트 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Comment("이벤트 요청 request")
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Comment("응답 message")
    @Column(columnDefinition = "text")
    private String responseMessage;

    @Comment("에러 message")
    @Column(columnDefinition = "text")
    private String errorMessage;

    @Comment("재시도 횟수")
    private int retryCount;

    // 파라미터
    @ElementCollection
    @CollectionTable(name = "event_log_parameter",
            joinColumns = {@JoinColumn(name = "event_log_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "param_name")
    @Column(name = "param_value", columnDefinition = "text")
    private Map<String, String> params;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createDate;


    public void retry() {
        final int EXCEED_COUNT = 5;
        this.retryCount++;
        if (this.retryCount >= EXCEED_COUNT) {
            this.eventStatus = EventStatus.RETRY_EXCEEDED;
        }

    }

    public void success() {
        this.eventStatus = EventStatus.SUCCESS;
    }

}

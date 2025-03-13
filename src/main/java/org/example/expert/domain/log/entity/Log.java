package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.log.enums.RequestType;
import org.example.expert.domain.user.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "log")
public class Log extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    private String message;

    @Builder
    public Log(Long userId, Long targetId, RequestType requestType, String message) {
        this.userId = userId;
        this.targetId = targetId;
        this.requestType = requestType;
        this.message = message;
    }
}

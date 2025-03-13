package org.example.expert.domain.todo.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TodoSearchCondition {

    private String title;
    private String managerNickname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public LocalDateTime getStartDateTime() {
        return (this.startDate != null) ? startDate.atStartOfDay() : null;
    }

    public LocalDateTime getEndDateTime() {
        return (endDate != null) ? endDate.atTime(23, 59) : null;
    }
}

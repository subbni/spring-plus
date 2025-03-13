package org.example.expert.domain.log.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.log.consts.LogMessage;
import org.example.expert.domain.log.entity.Log;
import org.example.expert.domain.log.enums.RequestType;
import org.example.expert.domain.log.repository.LogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LogService {

    private final LogRepository logRepository;

    public void saveManagerRegisterLog(Long userId, Long managerUserId, Long todoId) {
        String message = MessageFormat.format(LogMessage.MANAGER_REGISTER_MESSAGE, userId, managerUserId, todoId);
        logRepository.save(
                Log.builder()
                        .userId(userId)
                        .targetId(todoId)
                        .requestType(RequestType.TODO_MANAGER_REGISTER)
                        .message(message)
                        .build()
        );
    }
}

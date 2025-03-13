package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

    private final Long id;
    private final String title;
    private final Long countOfManagers;
    private final Long countOfComments;

    public TodoSearchResponse(Long id, String title, Long countOfManagers, Long countOfComments) {
        this.id = id;
        this.title = title;
        this.countOfManagers = countOfManagers;
        this.countOfComments = countOfComments;
    }
}

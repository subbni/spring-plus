package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.request.TodoSearchCondition;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoQueryRepository {

    Optional<Todo> findTodoByIdWithUser(@Param("todoId") Long todoId);
    Page<TodoSearchResponse> searchTodos(TodoSearchCondition condition, Pageable pageable);
}

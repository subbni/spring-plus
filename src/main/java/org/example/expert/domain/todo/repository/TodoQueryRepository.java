package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TodoQueryRepository {

    Optional<Todo> findTodoByIdWithUser(@Param("todoId") Long todoId);

}

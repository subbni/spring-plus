package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;

import java.util.Optional;

@RequiredArgsConstructor
public class TodoQueryRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findTodoByIdWithUser(Long todoId) {
        QTodo qTodo = QTodo.todo;
        QUser qUser = QUser.user;

        Todo todo = queryFactory
                .select(qTodo)
                .from(qTodo)
                .leftJoin(qTodo.user, qUser).fetchJoin()
                .where(qTodo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(todo);
    }
}

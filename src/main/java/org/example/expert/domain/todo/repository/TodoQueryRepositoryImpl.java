package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.request.TodoSearchCondition;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;

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

    @Override
    public Page<TodoSearchResponse> searchTodos(TodoSearchCondition condition, Pageable pageable) {
        QTodo qTodo = todo;
        QManager qManager = QManager.manager;
        QUser qUser = QUser.user;
        QComment qComment = QComment.comment;

        List<TodoSearchResponse> results = queryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        qTodo.id,
                        qTodo.title,
                        qManager.countDistinct(),
                        qComment.countDistinct()
                ))
                .from(qTodo)
                .leftJoin(qTodo.managers, qManager)
                .leftJoin(qManager.user, qUser)
                .leftJoin(qTodo.comments, qComment)
                .where(
                        titleContains(condition.getTitle()),
                        managerNicknameContains(condition.getManagerNickname()),
                        createdAtBetween(condition.getStartDateTime(), condition.getEndDateTime())
                )
                .groupBy(qTodo.id)
                .orderBy(qTodo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(qTodo.countDistinct())
                        .from(qTodo)
                        .leftJoin(qTodo.managers, qManager)
                        .leftJoin(qManager.user, qUser)
                        .where(
                                titleContains(condition.getTitle()),
                                managerNicknameContains(condition.getManagerNickname()),
                                createdAtBetween(condition.getStartDateTime(), condition.getEndDateTime())
                        )
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression titleContains(String title) {
        return StringUtils.hasText(title) ? todo.title.contains(title) : null;
    }

    private BooleanExpression managerNicknameContains(String nickname) {
        return StringUtils.hasText(nickname)
                ? QManager.manager.user.nickname.contains(nickname)
                : null;
    }

    private BooleanExpression createdAtBetween(LocalDateTime from, LocalDateTime to) {
        BooleanExpression condition = null;

        if (from != null) {
            condition = todo.createdAt.goe(from);
        }
        if (to != null) {
            condition = (condition != null) ? condition.and(todo.createdAt.loe(to)) : todo.createdAt.loe(to);
        }

        return condition;
    }
}

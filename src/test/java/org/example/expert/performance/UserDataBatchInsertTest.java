package org.example.expert.performance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@SpringBootTest(properties = "spring.profiles.active=test")
public class UserDataBatchInsertTest {
/*
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final int BATCH_SIZE = 10000; // 배치 크기
    private static final int TOTAL_USERS = 1000000; // 전체 삽입할 유저 수
    private static final Random RANDOM = new Random();

    @Test
    public void generate() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false); // 자동 커밋 해제

            String sql = "INSERT INTO users (email, password, nickname, user_role, created_at, modified_at) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            String encodedPassword = passwordEncoder.encode("password123!");

            for (int i = 1; i <= TOTAL_USERS; i++) {
                String email = "user" + i + "_" + UUID.randomUUID() + "@example.com";
                String nickname = generateRandomNickname();
                String role = (i % 2 == 0) ? "ROLE_USER" : "ROLE_ADMIN";

                ps.setString(1, email);
                ps.setString(2, encodedPassword);
                ps.setString(3, nickname);
                ps.setString(4, role);
                ps.setString(5, LocalDateTime.now().toString());
                ps.setString(6, LocalDateTime.now().toString());
                ps.addBatch();

                if (i % BATCH_SIZE == 0) {
                    ps.executeBatch();
                    conn.commit();
                    System.out.println(i + " users inserted");
                }
            }

            ps.executeBatch(); // 마지막 남은 데이터 처리
            conn.commit(); // 최종 커밋
            System.out.println("All data inserted successfully");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); // 예외 발생 시 롤백
                    System.err.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            // 리소스 정리
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String generateRandomNickname() {
        return "사용자_" + UUID.randomUUID().toString().substring(0, 8);
    }
 */
}

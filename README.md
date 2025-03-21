# SPRING PLUS
## 과제 개요
- 기간 : `2025.03.10` ~ `2025.03.20`
- 주요 키워드 : `JPA 심화`, `테스트 코드`, `성능 최적화`

## Lv.1
### 1. 코드 개선 퀴즈 - @Transactional의 이해
- [구현 코드](https://github.com/subbni/spring-plus/pull/1)
### 2. 코드 추가 퀴즈 - JWT의 이해
- [구현 코드](https://github.com/subbni/spring-plus/pull/1)
### 3. 코드 개선 퀴즈 -  JPA의 이해
- [구현 코드](https://github.com/subbni/spring-plus/pull/2/commits/b6a01cc814c63735188e76efdab3135bdd544034)
### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해
- [구현 코드](https://github.com/subbni/spring-plus/pull/3/commits/e3c0672863b7c6183df8eea95d807587f681fd14)
### 5. 코드 개선 퀴즈 - AOP의 이해
- [구현 코드](https://github.com/subbni/spring-plus/pull/4/commits/4b2201c73149a924bba66ac7de1f3ca02fc8d6bd)

## Lv.2
### 6. JPA Cascade
- [구현 코드](https://github.com/subbni/spring-plus/pull/6/commits/260e7f4ddb208b3fc196da111a73e9c86b20f552)
### 7. N+1
- [구현 코드](https://github.com/subbni/spring-plus/pull/7/commits/191c4f48fd8bd7c1b72c4ffd11b3626bcb8de9e4)
### 8. QueryDSL
- [구현 코드](https://github.com/subbni/spring-plus/pull/8/files)
### 9. Spring Security
- [구현 코드](https://github.com/subbni/spring-plus/pull/9/files)

## Lv.3
### 10. QueryDSL 을 사용하여 검색 기능 만들기
- [구현 코드](https://github.com/subbni/spring-plus/pull/10/commits/1dd6138680a87712cf731d5c1fc28ebbe9e653bc)
### 11. Transaction 심화
- [구현 코드](https://github.com/subbni/spring-plus/pull/11/commits/e4a6614703e61c0830c4b4b7364edf218bbbf20a)
### 12. AWS 활용
#### 12-1. EC2
- Public IPv4 : `3.36.150.240`
- [health check API 구현](https://github.com/subbni/spring-plus/commit/ea6f519e121f7cd5a3bd03fb38f97a0f4e560821)
- health check API : `GET /health` : `http://3.36.150.240:8080/health`
  <img width="1000" alt="Screenshot 2025-03-20 at 14 32 10" src="https://github.com/user-attachments/assets/59921484-8515-4f08-83bc-cfc29dbffba3" />
- 콘솔창
  <img width="1000" alt="Screenshot 2025-03-20 at 13 45 34" src="https://github.com/user-attachments/assets/996fefef-f7ec-471a-af1f-de5c85cbffe3" />

#### 12-2 RDS
- EC2 Springboot 서버와 연결
- 콘솔창
  <img width="1000" alt="Screenshot 2025-03-20 at 13 45 50" src="https://github.com/user-attachments/assets/5dfcb947-e30f-4429-bc7a-53e09bfe85ac" />

#### 12-3 S3
- [프로필 이미지 업로드/삭제/조회 API 구현](https://github.com/subbni/spring-plus/blob/main/src/main/java/org/example/expert/domain/user/controller/UserProfileController.java)
- 콘솔창
  <img width="1000" alt="Screenshot 2025-03-20 at 13 46 31" src="https://github.com/user-attachments/assets/a1c47d80-f0cc-4c20-a2e6-f06970d75a7f" />


### 13. 대용량 데이터 처리
- [닉네임으로 유저 목록 검색 API 작성](https://github.com/subbni/spring-plus/commit/c3765b727e7800ceac8c56f183bdef57b9f3efbc)
> 사용자 데이터 1,000,000개 입력 - [테스트 코드](https://github.com/subbni/spring-plus/blob/main/src/test/java/org/example/expert/performance/UserDataBatchInsertTest.java)
>  <img width="1000" alt="Screenshot 2025-03-19 at 15 18 33" src="https://github.com/user-attachments/assets/b58004d7-17d5-48cc-9c84-43b14c55ee71" />

> 테스트 실행 - thread 100개로 1초 간격으로 100개의 요청 전송
>  <img width="1000" alt="Screenshot 2025-03-19 at 17 03 33" src="https://github.com/user-attachments/assets/5698ad79-4337-4f6d-b349-a461d7015cbe" />
> - 평균 응답 : 4325ms (4.3초)
> - 최대 응답 : 7319ms (7.3초)
> - 최소 응답 : 1363ms (1.3초)

#### [1] nickname 컬럼에 인덱스 설정
- users 테이블의 nickname 컬럼에 BTREE 인덱스 생성
  <img width="1000" alt="Screenshot 2025-03-20 at 14 10 16" src="https://github.com/user-attachments/assets/6ebc6085-0c99-4a35-abfb-9349d98e3d5d" />
> 테스트 실행 - thread 100개로 1초 간격으로 100개의 요청 전송
>  <img width="1000" alt="Screenshot 2025-03-19 at 17 03 33" src="https://github.com/user-attachments/assets/5698ad79-4337-4f6d-b349-a461d7015cbe" />
> - 평균 응답 : 12ms
> - 최대 응답 : 109ms
> - 최소 응답 : 4ms

- 평균 응답 시간이 4345ms에서 12ms로 약 99.7% 감소


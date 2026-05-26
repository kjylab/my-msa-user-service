# my-msa-user-service

Troica Market MSA의 **사용자(User) 서비스**. 회원 정보 조회를 담당하며, auth-service와 동일한 PostgreSQL `auth_db`의 `users` 테이블을 공유한다.

## 아키텍처

### 모듈 구성 (헥사고날)

```
user/                 ← 순수 도메인 + 유스케이스
  domain/             ← UserDomainEntity, UserRole, UserException
  application/
    port/inbound/     ← CreateUserCommand, FetchMeQuery
    port/outbound/    ← UserCommandOutboundPort, UserQueryOutboundPort
    service/          ← UserCommandService, UserQueryService
    dto/              ← UserDto
  adapter/
    infrastructure/
      jpa/            ← PostgreSQL 구현체 (UserPersistenceAdapter)
      configuration/  ← JpaConfig, SecurityConfig
    presentation/
      web/inbound/    ← UserRestController

user-service/         ← 실행 진입점 (Spring Boot)
  UserRestControllerAdapter
  GlobalExceptionHandler
```

## REST API

| 메서드 | 경로 | 설명 | 인증 |
|--------|------|------|------|
| POST | `/api/v1/users` | 회원 생성 | 불필요 |
| POST | `/api/v1/users/me` | 내 정보 조회 | JWT 필요 |

> user-api-gateway의 `/api/v1/users/**` 라우팅으로 접근

## 의존 인프라

| 인프라 | 용도 |
|--------|------|
| PostgreSQL (`auth_db`, `users` 테이블) | 사용자 정보 저장 (auth-service와 공유) |

> `users` 테이블을 auth-service와 공유하므로 `spring.jpa.hibernate.ddl-auto: update` 사용

## 실행 포트

| 포트 | 용도 |
|------|------|
| 8080 | HTTP REST API |

## 관측성 (Observability)

- `/prometheus` 엔드포인트로 메트릭 노출
- OTLP 트레이싱: Tempo(`tempo.monitoring.svc.cluster.local:4318`)로 전송
- sampling probability: 1.0

## CI/CD 흐름

```
GitHub push
  → JAR 빌드
  → Docker 이미지 빌드 + Docker Hub push (jyupk/my-msa-user-service)
  → my-msa-manifest-values/user-service/values-release.yaml 의 tag를 커밋 SHA로 업데이트
  → ArgoCD 감지 → 클러스터 롤링 업데이트
```

## 로컬 Docker 빌드

```bash
docker build --no-cache -t ktcloud-msa-user-service:latest -f Containerfile .
```

## 관련 레포

| 레포 | 역할 |
|------|------|
| [my-msa-auth-service](https://github.com/kjylab/my-msa-auth-service) | auth-service (auth_db users 테이블 공유) |
| [my-msa-user-api-gateway](https://github.com/kjylab/my-msa-user-api-gateway) | 게이트웨이 (user 라우팅) |
| [my-msa-manifest-values](https://github.com/kjylab/my-msa-manifest-values) | Helm values |

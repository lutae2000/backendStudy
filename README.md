# Back-end Study

마이크로서비스 아키텍쳐를 위한 프론트엔드 / 백엔드 분리 연습 프로젝트

스프링 부트로 구현된 백엔드 구현부

사용된 기술
- JPA => 직접 쿼리 작성하는 Mybatis가 아닌 JPA를 이용하여 쿼리 자동 생성 
- JWT 발급 및 인증 => 토큰, basic 방식보다 서버 요청이 적음
- 서블릿 필터 => 서블릿 Request 받기전 필터 / 여러개 구현가능
- 스프링부트 => Gladle 사용법 확인
- CORS 이슈 처리 => 타 URL에 요청시 보안문제인 CORS 허용

참고: React.js 스프링부트 AWS로 배우는 웹개발 101 

![스크린샷 2022-01-02 오전 4 16 11](https://user-images.githubusercontent.com/40047335/147858457-e94eae7d-9c5e-4809-b06f-09257b821fbd.png)


AWS Elastic Beantalk 생성
```
$ create eb -- 
```

AWS Elastic Beantalk 배포
```
$ gradlew build 
$ eb deploy
```


AWS RDS 구성 설명
```
$ rds describe-db-instances --region [리전]
```

AWS Auto Scaling group
```
$ aws autoscaling describe-auto-scaling-groups --region [리전]
```



배포중 이슈사항
1. AWS Elastic BeanTalk 배포시 Git이 연동 되어있을때 .git 을 삭제 하거나 Commit & Push 를 모두 해야만 수정된 빌드버전으로 업로드 됨
2. DNS가 변경된 경우 CORS 허용을 해야 함(SSL적용후 HTTPS 인 경우에도 포함)



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


JWT 토큰 방식의 장점
- 보안성있는 인증을 위해 매번 DB에 연결후 인증하는 방식이 있으나 비효율적
- 토큰을 받은 후 expiration 날짜까지 유효하므로 토큰만으로 인증하고 DB 연결후 다시 인증하지 않아도 됨 
-  header | payload | signiture

header: type, 알고리즘 종류
payload(내용): 토큰 발급자, 토큰 목적, 발행일, 만료일등
signature: base64로 헤더와 payload를 인코딩


Servlet filter는 여러개를 만들어서 사용할 수 있음


AWS Elastic Beantalk 생성
```
$ eb init
$ eb create --elb-type application --instance-type t3.micro
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



# spring-custom-jwt-authentication

스프링 시큐어리티를 이용한 JWT토큰 기반의 인증처리를 배워본다.  
이 소스는 최소한의 코딩으로만 구성된것이다.

## 로그인
/api/token으로 로그인정보를 보낸다  
소스에 사용자정보를 하드코딩해놓았으나 이부분을 다른 컴포넌트로 대체하면 된다.
```
curl  -d '{"username":"user", "password":"1234"}' -H "Content-Type:application/json" -X POST  'http://localhost:8080/api/token'
{
 "timestamp":"2020-05-11T14:07:42.723+0000",
 "status":401,
 "error":"Unauthorized",
 "message":"Unauthorized","path":"/api/token"
 }
```

사용자정보가 맞을 경우 토큰을 돌려준다.

```
curl  -d '{"username":"admin", "password":"0987!@"}' -H "Content-Type:application/json" -X POST  'http://localhost:8080/api/token'

{"token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4OTIwNjEzOSwiZXhwIjoxNTg5MjA2MTk5fQ.XIRBPbCewxRc1Gu96RdW2A2jEu2i2oXW4-IDZ2vej-I"}
```


## 토큰 이용하기

주어진 토큰을 이용해서 요청 하기

```
curl  -H "Content-Type:application/json" 
 -H "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTU4OTIwNjEzOSwiZXhwIjoxNTg5MjA2MTk5fQ.XIRBPbCewxRc1Gu96RdW2A2jEu2i2oXW4-IDZ2vej-I" 'http://localhost:8080/api/hello'
```

## application.yml

```yml
spring:
  custom:
    login: /api/token,POST 
    authority: ROLE_USER
    jwt:
      ttl: 1m
      secret: secret!@#z?_43

```
ttl이 1m 이면 즉 1분, 토큰의 생존시간이다 세션의 타임아웃시간이라고 생각하면 쉽다.  
1h라고 수정하면 1시간, 1d는 하루 아래참조할 것

### 참조

https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-conversion-duration

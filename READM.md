# FastCampus Restaurants Study

#### npm server 실행
```
npm start //3000번 포트로 실행된다
```
#### 데이터 넣기 및 읽기
- httpie를 설치하였으므로 터미널에서 아래 명령 실행
~~~
http POST localhost:8080/restaurants name=BeRyong address=Seoul //쓰기
http GET localhost:8080/restaurants    //읽기
~~~

#### CORS 처리하기
- 스프링에서는 @CrossOrigin을 컨트롤러에 적용해주면 해결된다.

#### Restaurant Update - Patch
~~~
http POST localhost:8080/restaurants name=BeRyong address=Seoul //쓰기
http GET localhost:8080/restaurants    //읽기
http PATCH localhost:8080/restaurants/1 name=BeRyong address=Busan
//menuitems.json 파일의 내용 넣기
http PATCH localhost:8080/restaurants/1/menuitems < menuitems.json
~~~

#### @Transactional 선언적 트랜잭션
- 트랜잭션의 범위, 롤백 규칙 등을 정의한다.
- 트랜잭션 특징
    - 원자성 : 한 트래잭션 내에서 실행한 작업들흔 하나로 간주된다.
    - 일관성 : 트랜잭션은 일관성 있는 데이터 베이스 상태를 유지한다.
    - 격리성 : 동시에 실행되는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야 한다.
    - 지속성 : 트랜잭션을 성공적으로 마치면 결과가 항상 저장되어야 한다.
- 업데이트 실행시에 Service에 @Transactional 어노테이션을 명시해주면, repository의 save를 호출 하지 않아도 트랜잭션의 특성으로 데이터가 변한다.

#### Validation
- 기존 web-starter에 의존성이 있었지만, 따로 분리되어 아래 의존성을 추가해야한다.
- Validation은 유효성을 검사하는데 사용하는 어노테이션
  - @NotEmpty : 비어있으면 안된다. 
  - @Valid : 해당 객체의 유효성 검사를 하겠다. (Restaurant 객체에 @NotEmpty등의 조건이 정의 되어 있어야 하며, controller에서 @Valid 적용)
~~~
implementation 'org.springframework.boot:spring-boot-starter-validation'
~~~
#### ControllerAdvice
- 에러 핸들링을 위한 어노테이션
- 예외 관련된 코드 처리를 위해 @ControllerAdvice와 @ExceptionHandler를 사용한다.
  - @ExceptionHandler는 @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메소드에서 처리해준다.
     - 하나의 클래스에 관한것. @Service 어노테이션은 안된다.
  - @ControllerAdvice는 모든 @Cotontroller의 예외를 잡아준다.
    - 모든 컨트롤러에 관한
~~~
@Test
  public void detailWithNotExisted() throws Exception {
      given(restaurantService.getRestaurant(404L))
              .willThrow(new RestaurantNotFoundException(404L));

      mockMvc.perform(get("/restaurants/404"))
              .andExpect(status().isNotFound())
      .andExpect(content().string("{}"));
  }
  
//=========================

/**
 * 에러 처리
 */
@ControllerAdvice
public class RestaurantErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public String handlerNotFound(){
        return "{}";

    }
}
~~~
##gradle 실행
~~~
./gradlew build
~~~
-에러 발생
  -스프링 부트를 사용하지 않는 eatgo-common은 build.gralde에 다음을 추가한다.
~~~
jar{
    enabled = true
}

bootJar{
    enabled = false
}
~~~
- test 실행
  - 인텔리제이에서 테스트 환경을 그래들로 변경 
    - 그래들을 추가하여 폴더선택
    - task에 clean test 추가
- 터미널 그래들 테스트 명령(위와 같은동작)
~~~
./gradlew test
~~~
## H2 영속성 - 파일로 저장
- h2는 인메모리방식과 파일방식으로 데이터를 저장할 수 있다.
- jpa의 hibernate ddl-auto 설정
  - 아래와 같이 설정하면 프로그램을 재시작해도 데이터가 남아 있다.
~~~
spring:
  datasource:
    url: jdbc:h2:~/data/study.restauarnt
  jpa:
    hibernate:
      ddl-auto: update
~~~
## 프로파일(운영환경)에 맞게 실행
- yml 설정 ---로 운영환경 구분
~~~
spring:
  datasource:
    url: jdbc:h2:~/data/study.restauarnt
  jpa:
    hibernate:
      ddl-auto: update
---
spring:
  profiles: test
  datasource:
    url: jdbc:h2:mem:test
~~~
- terminal 명령어
~~~
SPRING_PROFILES_ACTIVE=test ./gradlew test
~~~
## 암호화
- Spring Security 이용
  <br> implementation 'org.springframework.boot:spring-boot-starter-security' 의존성 추
  
~~~
  @Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .headers().frameOptions().disable();
    }

}
  ~~~
- BCrypt
<br> Spring Security에서 기본으로 제공
~~~
public User registerUser(String email, String name, String password) {
PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
String encodedPassword = passwordEncoder.encode(password);

        User newUser = User.builder()
                .email(email)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

        return userRepository.save(newUser);
    }
~~~
- 사용자 중복 확인
~~~
public User registerUser(String email, String name, String password) {

        Optional<User> existed = userRepository.findByEmail(email);

        if(existed.isPresent()){
            throw new EmailExistedException(email);
        }
        ...
}
// 커스텀 예외
public class EmailExistedException extends RuntimeException{

    public EmailExistedException(String email){
        super("Email is already registered: " + email);
    }

}
~~~
## 인증(Authentication)
- JWT(JSON Web Token)
  - Header
  - Payload : Claims라는 단위로 정보 저장
  - Signature : 서명이 담김. HMAC-SHA256 이용
  - .으로 위의 3가지가 연결되며 Base64 URL Encoding으로 데이터 전송
  - jsonwebtoken.io 라이브러리 이용
  ~~~
  implementation 'io.jsonwebtoken:jjwt-api:0.10.7'
    runtime 'io.jsonwebtoken:jjwt-impl:0.10.7'
    runtime 'io.jsonwebtoken:jjwt-jackson:0.10.7'
  ~~~
- JWT 주의사항
  - secret 키는 자체관리가 필요하다. 보통 yml파일에 보관
## 인가(Authorization)
- Stateless
- Filter
  - BasicAuthenticationFilter
  - 스프링에서는 내부적으로 AuthenticationToken 객체 활용
   UsernamePasswordAuthenticationToken 사용이 대표적
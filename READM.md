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
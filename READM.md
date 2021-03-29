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
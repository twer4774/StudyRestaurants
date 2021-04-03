package walter.study.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import walter.study.restaurant.domain.RestaurantNotFoundException;

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

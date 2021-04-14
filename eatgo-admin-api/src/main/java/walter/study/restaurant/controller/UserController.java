package walter.study.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import walter.study.restaurant.application.UserService;
import walter.study.restaurant.domain.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //1. User list
    @GetMapping("/users")
    public List<User> list() {
        return userService.getUserList();
    }

    //2. User create -> 회원 가입
    @PostMapping("/users")
    public ResponseEntity<?> crate(
            @RequestBody User resource

    ) throws URISyntaxException {
        userService.addUser(resource.getEmail(), resource.getName());

        String url = "/users/1";
        return ResponseEntity.created(new URI(url)).body("{}");
    }
    //3. User update

    @PatchMapping("/users/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody User resource
    ){
        String email = resource.getEmail();
        String name = resource.getName();
        Long level = resource.getLevel();

        userService.updateUser(id, email, name, level);

        return "{}";
    }

    //4. User delete -> level 0 => 아무것도 못함
    //(1: customer, 2: restaurant owner, 3: admin)
    @DeleteMapping("/users/{id}")
    public String delete(
            @PathVariable("id") Long id
    ) {
        return "{}";
    }
}

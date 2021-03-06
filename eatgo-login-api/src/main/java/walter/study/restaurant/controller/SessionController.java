package walter.study.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import walter.study.restaurant.application.UserService;
import walter.study.restaurant.controller.dto.request.SessionRequestDto;
import walter.study.restaurant.controller.dto.response.SessionResponseDto;
import walter.study.restaurant.domain.User;
import walter.study.restaurant.utils.JwtUtil;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
public class SessionController {

    private final UserService userService;

    private final JwtUtil jwtUtil;


    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
            ) throws URISyntaxException {

        User user = userService.authenticate(resource.getEmail(), resource.getPassword());

        String accessToken = jwtUtil.createToken(user.getId(),
                user.getName(),
                user.isRestaurantOwner() ? user.getRestaurantId() : null);

        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(SessionResponseDto.builder().accessToken(accessToken).build());
    }

}

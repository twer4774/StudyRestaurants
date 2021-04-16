package walter.study.restaurant.controller.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class SessionRequestDto {

    private String email;

    private String password;

}

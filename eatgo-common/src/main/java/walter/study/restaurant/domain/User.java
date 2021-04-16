package walter.study.restaurant.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Setter
    @NotEmpty
    private String email;

    @Setter
    @NotEmpty
    private String name;

    private String password;

    @Setter
    @NotNull
    private Long level;

    public Boolean isAdmin() {
        return level >= 100;
    }

    public Boolean isActive() {
        return level > 0;
    }

    public void deactive(){
        level = 0L;
    }

    @JsonIgnore
    public String getAccessToken() {
        if(password == null){
            return "";
        }
        return password.substring(0, 10);
    }
}

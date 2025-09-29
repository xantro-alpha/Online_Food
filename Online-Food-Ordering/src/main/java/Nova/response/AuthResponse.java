package Nova.response;

import Nova.model.USER_ROLE;
import Nova.model.User;
import lombok.Data;

@Data
public class AuthResponse {
    private String  jwt;

    private String message;

    private USER_ROLE role;



}

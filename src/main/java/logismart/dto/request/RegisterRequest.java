package logismart.dto.request;

import logismart.entity.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private UserRole role;
}
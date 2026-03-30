package springboot_new.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {
    private Long id;
    private String username;
    private String password;
    private String role;

    public AuthDto(Long id, String username, String password) {
    }
}


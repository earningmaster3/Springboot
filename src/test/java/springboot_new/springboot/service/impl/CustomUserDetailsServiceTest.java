package springboot_new.springboot.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import springboot_new.springboot.dto.AuthResDto;
import springboot_new.springboot.dto.RegisterReqDto;
import springboot_new.springboot.entity.User;
import springboot_new.springboot.exception.BadRequestException;
import springboot_new.springboot.repository.UserRepo;
import springboot_new.springboot.security.JwtService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loginReturnsSuccessMessageWhenCredentialsAreValid() {
        RegisterReqDto dto = new RegisterReqDto("john", "secret");
        User user = new User();
        user.setUsername("john");
        user.setPassword("encoded-password");

        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("secret", "encoded-password")).thenReturn(true);
        when(jwtService.generatetoken("john")).thenReturn("jwt-token");

        AuthResDto response = customUserDetailsService.login(dto);

        assertEquals("john", response.getUsername());
        assertEquals("jwt-token", response.getToken());
        assertEquals("User has been logged in successfully", response.getMessage());
    }

    @Test
    void loginRejectsInvalidPassword() {
        RegisterReqDto dto = new RegisterReqDto("john", "wrong-password");
        User user = new User();
        user.setUsername("john");
        user.setPassword("encoded-password");

        when(userRepo.findByUsername("john")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> customUserDetailsService.login(dto));

        assertEquals("invalid password", exception.getMessage());
    }
}

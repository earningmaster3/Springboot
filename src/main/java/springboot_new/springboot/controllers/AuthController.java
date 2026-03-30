package springboot_new.springboot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot_new.springboot.dto.AuthResDto;
import springboot_new.springboot.dto.RegisterReqDto;
import springboot_new.springboot.service.UserDetailsService;


@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final UserDetailsService userDetailsService;

    public AuthController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResDto> register(@RequestBody RegisterReqDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.register(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@RequestBody RegisterReqDto dto){
        return ResponseEntity.ok(userDetailsService.login(dto));
    }
}

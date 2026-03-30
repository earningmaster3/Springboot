package springboot_new.springboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import springboot_new.springboot.dto.AuthResDto;
import springboot_new.springboot.dto.RegisterReqDto;
import springboot_new.springboot.entity.User;
import springboot_new.springboot.exception.BadRequestException;
import springboot_new.springboot.repository.UserRepo;
import springboot_new.springboot.security.JwtService;
import springboot_new.springboot.service.UserDetailsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService, org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResDto register(RegisterReqDto dto) {
        if (userRepo.existsByUsername(dto.getUsername())) {
            throw new BadRequestException("username already exists: " + dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("USER");

        User savedUser = userRepo.save(user);
        String token = jwtService.generatetoken(savedUser.getUsername());

        return new AuthResDto(token, savedUser.getUsername(), "registered");
    }

    @Override
    public AuthResDto login(RegisterReqDto dto) {
        User user = userRepo.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BadRequestException("username doesnt exist: " + dto.getUsername()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException("invalid password");
        }

        String token = jwtService.generatetoken(user.getUsername());
        return new AuthResDto(token, user.getUsername(), "User has been logged in successfully");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}

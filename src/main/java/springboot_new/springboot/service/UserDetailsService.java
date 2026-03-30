package springboot_new.springboot.service;

import springboot_new.springboot.dto.AuthResDto;
import springboot_new.springboot.dto.RegisterReqDto;

public interface UserDetailsService {

    AuthResDto register(RegisterReqDto dto);
    AuthResDto login(RegisterReqDto dto);
}

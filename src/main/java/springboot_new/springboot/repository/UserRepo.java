package springboot_new.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot_new.springboot.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}

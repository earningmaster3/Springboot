package springboot_new.springboot.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot_new.springboot.entity.Student;

public interface StudentRepo extends JpaRepository<Student,Long> {

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email);
}

package springboot_new.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
@Column(unique = true, nullable = false)
    public String username;
@Column(nullable = false)
    public String password;

    public String role; //user or admin

}

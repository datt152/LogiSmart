package logismart.entity;

import jakarta.persistence.*;
import logismart.entity.enums.UserRole;
import lombok.Data;

@Table(name="users")
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    private String fullName;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

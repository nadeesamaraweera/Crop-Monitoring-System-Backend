package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user")
@Entity
public class UserEntity implements Serializable{
    @Id
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    public enum Role {
        MANAGER, ADMINISTRATIVE, SCIENTIST, OTHER
    }


}

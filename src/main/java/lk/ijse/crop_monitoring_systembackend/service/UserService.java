package lk.ijse.crop_monitoring_systembackend.service;

import lk.ijse.crop_monitoring_systembackend.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void saveUser(UserDTO user);
    void updateUser(String email, UserDTO user);
    boolean searchUser(String email);
    boolean deleteUser(String email);
    UserDetailsService userDetailsService();

}

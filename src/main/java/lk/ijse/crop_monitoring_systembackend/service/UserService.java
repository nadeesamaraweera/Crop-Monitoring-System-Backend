package lk.ijse.crop_monitoring_systembackend.service;

import lk.ijse.crop_monitoring_systembackend.dto.UserDTO;

public interface UserService {
    void saveUser(UserDTO user);
    void updateUser(String email, UserDTO user);
    boolean searchUser(String email);
    boolean deleteUser(String email);
}

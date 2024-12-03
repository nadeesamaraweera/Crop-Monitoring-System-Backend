package lk.ijse.crop_monitoring_systembackend.service.impl;

import lk.ijse.crop_monitoring_systembackend.dao.StaffDAO;
import lk.ijse.crop_monitoring_systembackend.dao.UserDAO;
import lk.ijse.crop_monitoring_systembackend.dto.UserDTO;
import lk.ijse.crop_monitoring_systembackend.entity.StaffEntity;
import lk.ijse.crop_monitoring_systembackend.entity.UserEntity;
import lk.ijse.crop_monitoring_systembackend.exception.DataPersistFailedException;
import lk.ijse.crop_monitoring_systembackend.exception.NotFoundException;
import lk.ijse.crop_monitoring_systembackend.service.UserService;
import lk.ijse.crop_monitoring_systembackend.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIMPL implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StaffDAO staffDAO;

    @Autowired
    private MappingUtil mappingUtil;

    @Override
    public void saveUser(UserDTO user) {
        user.setRole(UserDTO.Role.valueOf(getUserRole(user.getEmail()).name()));
        UserEntity savedUser = userDAO.save(mappingUtil.userConvertToEntity(user));
        if (savedUser != null) {
            System.out.println("User saved successfully");
        } else {
            throw new DataPersistFailedException("User save unsuccessful");
        }
    }

    @Override
    public void updateUser(String email, UserDTO user) {
        Optional<UserEntity> tmpUserEntity = userDAO.findByEmail(email);
        if (tmpUserEntity.isPresent()){
            UserEntity userEntity = mappingUtil.userConvertToEntity(user);
            tmpUserEntity.get().setPassword(userEntity.getPassword());
            System.out.println("User password updated successfully: " + tmpUserEntity.get());
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public boolean searchUser(String email) {
        Optional<StaffEntity> user = staffDAO.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public boolean deleteUser(String email) {
        Optional<UserEntity> user = userDAO.findByEmail(email);
        if (user.isPresent()) {
            userDAO.delete(user.get());
            return true;
        } else {
            throw new NotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private StaffEntity.Role getUserRole(String email) {
        return staffDAO.findByEmail(email)
                .map(StaffEntity::getRole)
                .orElseThrow(() ->
                        new NotFoundException("User not found for email: " + email));
    }
}

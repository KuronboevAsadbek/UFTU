package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.dto.UserDTO;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.domain.enumeration.Role;
import uz.uftu.ls.exceptions.UserException;
import uz.uftu.ls.repository.UserRepository;
import uz.uftu.ls.service.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(UserDTO userDTO, Principal principal) {
        log.info("The process of creating a user has started");

        if (Boolean.TRUE.equals(checkUsername(userDTO.getUsername()))) {
            log.error("Bu username band, {}", userDTO.getUsername());
            throw new UserException("Bu username band");
        }

        User user = UserDTO.map2Entity(userDTO);
        if (principal != null) {
            user.setCreatedBy(principal.getName());
        }
        user.setUsername(userDTO.getUsername().trim().toLowerCase());
        if (Boolean.TRUE.equals(checkPassword(userDTO.getPassword()))) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        } else {
            log.error("Parol juda qisqa,{}", userDTO.getPassword());
            throw new UserException("Parol juda qisqa");
        }
        Role role = Role.valueOf(userDTO.getRoleName());
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public Boolean checkUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean checkPassword(String password) {
        return password.length() >= 4;
    }

    @Override
    public ResponseDTO<Page<User>> getAllStudents(Pageable pageable, Long userId) {
        ResponseDTO<Page<User>> responseDTO = new ResponseDTO<>();
        Page<User> users;
        if (userId != null) {
            users = userRepository.findAllOrStudentId(pageable, userId);
        } else {
            users = userRepository.findAll(pageable);
        }
        responseDTO.setData(users);
        responseDTO.setSuccess(true);
        responseDTO.setRecordsTotal(users.getTotalElements());
        responseDTO.setMessage("Talabalar ro'yxati muaffaqiyatli olindi");
        return responseDTO;
    }

    @Override
    public void deleteStudent(Long userId) {
        userRepository.deleteById(userId);
    }
}

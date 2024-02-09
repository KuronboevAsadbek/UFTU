package uz.uftu.ls.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.dto.UserDTO;
import uz.uftu.ls.domain.entity.User;

import java.security.Principal;

public interface UserService {

    void createUser(UserDTO userDTO, Principal principal);

    Boolean checkUsername(String username);

    Boolean checkPassword(String password);

    ResponseDTO<Page<User>> getAllStudents(Pageable pageable, Long userId);

    void deleteStudent(Long userId);

    User getMe(Principal principal);


}

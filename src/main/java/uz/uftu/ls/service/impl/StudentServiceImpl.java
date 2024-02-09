package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.exceptions.UserException;
import uz.uftu.ls.repository.UserRepository;
import uz.uftu.ls.service.StudentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {


    private final UserRepository userRepository;

    @Override
    public User getStudent(String userName) {
        try {
            log.info("The process of getting a student has started");
            return userRepository.findByUsername(userName);
        } catch (Exception e) {
            log.error("Foydalanuvchi topilmadi, {}", userName);
            throw new UserException("Foydalanuvchi topilmadi");
        }
    }


}

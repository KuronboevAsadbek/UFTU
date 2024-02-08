package uz.uftu.ls.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import uz.uftu.ls.controller.VM.LoginVM;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.dto.UserDTO;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.exceptions.UserException;
import uz.uftu.ls.jwtUtils.JwtTokenProvider;
import uz.uftu.ls.repository.UserRepository;
import uz.uftu.ls.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@Tag(name = "Foydalanuvchi", description = "Foydalunvhilarni ro'yxatga olish va login qilish qismi")
@RequiredArgsConstructor
@Slf4j
public class UserJwtController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;


    @Operation(summary = "Foydalanuvchi ro'yxatga olish uchun API", description = "Foydalanuvchi ro'yxatga olish uchun API")
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<String>> createUser(@Valid @RequestBody UserDTO userDTO, Principal principal) {
        try {
            userService.createUser(userDTO, principal);
            ResponseDTO<String> responseDto = new ResponseDTO<>();
            responseDto.setMessage("Foydalanuvchi muvaffaqiyatli ro'yxatdan o'tkazildi");
            responseDto.setSuccess(true);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new UserException(e.getMessage());
        }
    }


    @Operation(summary = "Foydalanuvchi login qilish uchun API", description = "Foydalanuvchi login qilish uchun API")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginVM loginVM) {
        try {
            User user = userRepository.findByUsername(loginVM.getUsername().trim().toLowerCase());
            if (user == null) {
                throw new UserException("Bu foydalanuvch mavjud emas");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVM.getUsername().trim().toLowerCase(), loginVM.getPassword()));
            return new ResponseEntity<>(jwtTokenProvider.createToken(user.getUsername().trim().toLowerCase()), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserException("Login yoki parol xato");
        }
    }

    @Operation(summary = "Foydalanuvchi haqida ma'lumot olish", description = "Foydalanuvchi haqida ma'lumot olish")
    @GetMapping("/getMe")
    public ResponseEntity<?> getMe(Principal principal) {
        return ResponseEntity.ok(userService.getMe(principal));
    }
}

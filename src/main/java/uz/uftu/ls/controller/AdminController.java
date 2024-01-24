package uz.uftu.ls.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.entity.*;
import uz.uftu.ls.repository.*;
import uz.uftu.ls.service.FileStorageService;
import uz.uftu.ls.service.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileStorageService fileStorageService;
    private final UniversityRepository universityRepository;
    private final FacultyRepository facultyRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final ScienceRepository scienceRepository;
    private final UserService userService;

    @Operation(summary = "UserAvatar Yuklash uchun api")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<FileStorage>> upload(@RequestParam("file") MultipartFile multipartFile,
                                                           @RequestParam(required = false) Long userId) {
        return new ResponseEntity<>(fileStorageService.save(multipartFile, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "Universitet qo'shish uchun api")
    @PostMapping("/university")
    public ResponseEntity<?> addUniversity(@RequestBody University university) {
        return ResponseEntity.ok(universityRepository.save(university));
    }

    @Operation(summary = "Universitetlarni olish uchun api")
    @GetMapping("/university")
    public ResponseEntity<?> getUniversity() {
        return ResponseEntity.ok(universityRepository.findAll());
    }

    @Operation(summary = "Fakultet qo'shish uchun api")
    @PostMapping("/faculty")
    public ResponseEntity<?> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyRepository.save(faculty));
    }

    @Operation(summary = "Fakultetlarni olish uchun api")
    @GetMapping("/faculty")
    public ResponseEntity<?> getFaculty() {
        return ResponseEntity.ok(facultyRepository.findAll());
    }

    @Operation(summary = "Fakultetlarni universitet ID raqami bo'yicha olish uchun api")
    @GetMapping("/faculty/{universityId}")
    public ResponseEntity<?> getFacultyByUniversityId(@PathVariable Long universityId) {
        return ResponseEntity.ok(facultyRepository.findAllByUniversityId(universityId));
    }

    @Operation(summary = "Yo'nalish qo'shish uchun api")
    @PostMapping("/fieldOfStudy")
    public ResponseEntity<?> addFieldOfStudy(@RequestBody FieldOfStudy fieldOfStudy) {
        return ResponseEntity.ok(fieldOfStudyRepository.save(fieldOfStudy));
    }

    @Operation(summary = "Yo'nalishlarni olish uchun api")
    @GetMapping("/fieldOfStudy")
    public ResponseEntity<?> getFieldOfStudy() {
        return ResponseEntity.ok(fieldOfStudyRepository.findAll());
    }

    @Operation(summary = "Yo'nalishlarni fakultet ID raqami bo'yicha olish uchun api")
    @GetMapping("/fieldOfStudy/{facultyId}")
    public ResponseEntity<?> getFieldOfStudyByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok(fieldOfStudyRepository.findAllByFacultyId(facultyId));
    }

    @Operation(summary = "Fan qo'shish uchun api")
    @PostMapping("/science")
    public ResponseEntity<?> addScience(@RequestBody Science science) {
        return ResponseEntity.ok(scienceRepository.save(science));
    }

    @Operation(summary = "Fanlarni olish uchun api")
    @GetMapping("/science")
    public ResponseEntity<?> getScience() {
        return ResponseEntity.ok(scienceRepository.findAll());
    }

    @Operation(summary = "Fanlarni yo'nalishning ID raqami bo'yicha olish uchun api")
    @GetMapping("/science/{fieldOfStudyId}")
    public ResponseEntity<?> getScienceByFieldOfStudyId(@PathVariable Long fieldOfStudyId) {
        return ResponseEntity.ok(scienceRepository.findAllByFieldOfStudyId(fieldOfStudyId));
    }

    @Operation(summary = "Studentlarni olish uchun api", description = "param sifatida (page, size) beriladi (user_id berilsa bitta qaytadi (required = false))")
    @GetMapping("/student/{userId}")
    public ResponseEntity<?> getStudent(Pageable pageable,
                                        @PathVariable(required = false) Long userId) {
        return ResponseEntity.ok(userService.getAllStudents(pageable, userId));
    }

}

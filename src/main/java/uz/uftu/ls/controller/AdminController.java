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
import uz.uftu.ls.repository.FacultyRepository;
import uz.uftu.ls.repository.FieldOfStudyRepository;
import uz.uftu.ls.repository.ScienceRepository;
import uz.uftu.ls.repository.UniversityRepository;
import uz.uftu.ls.service.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final FileStorageService fileStorageService;
    private final UserService userService;
    private final FacultyService facultyService;
    private final ScienceService scienceService;
    private final FieldOfStudyService fieldOfStudyService;
    private final UniversityService universityService;

    @Operation(summary = "UserAvatar Yuklash uchun api")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDTO<FileStorage>> upload(@RequestParam("file") MultipartFile multipartFile,
                                                           @RequestParam(required = false) Long userId) {
        return new ResponseEntity<>(fileStorageService.save(multipartFile, userId), HttpStatus.CREATED);
    }

    @Operation(summary = "Universitet qo'shish uchun api")
    @PostMapping("/university")
    public ResponseEntity<?> addUniversity(@RequestBody University university) {
        return ResponseEntity.ok(universityService.create(university));
    }

    @Operation(summary = "Universitetlarni olish uchun api")
    @GetMapping("/university")
    public ResponseEntity<?> getUniversity() {
        return ResponseEntity.ok(universityService.getAll());
    }

    @Operation(summary = "Fakultet qo'shish uchun api")
    @PostMapping("/faculty")
    public ResponseEntity<?> addFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.create(faculty));
    }

    @Operation(summary = "Fakultetlarni olish uchun api")
    @GetMapping("/faculty")
    public ResponseEntity<?> getFaculty() {
        return ResponseEntity.ok(facultyService.getAll());
    }

    @Operation(summary = "Fakultetlarni universitet ID raqami bo'yicha olish uchun api")
    @GetMapping("/faculty/{universityId}")
    public ResponseEntity<?> getFacultyByUniversityId(@PathVariable Long universityId) {
        return ResponseEntity.ok(facultyService.getAllByUniversityId(universityId));
    }

    @Operation(summary = "Yo'nalish qo'shish uchun api")
    @PostMapping("/fieldOfStudy")
    public ResponseEntity<?> addFieldOfStudy(@RequestBody FieldOfStudy fieldOfStudy) {
        return ResponseEntity.ok(fieldOfStudyService.create(fieldOfStudy));
    }

    @Operation(summary = "Yo'nalishlarni olish uchun api")
    @GetMapping("/fieldOfStudy")
    public ResponseEntity<?> getFieldOfStudy() {
        return ResponseEntity.ok(fieldOfStudyService.getAll());
    }

    @Operation(summary = "Yo'nalishlarni fakultet ID raqami bo'yicha olish uchun api")
    @GetMapping("/fieldOfStudy/{facultyId}")
    public ResponseEntity<?> getFieldOfStudyByFacultyId(@PathVariable Long facultyId) {
        return ResponseEntity.ok(fieldOfStudyService.getAllByFacultyId(facultyId));
    }

    @Operation(summary = "Fan qo'shish uchun api")
    @PostMapping("/science")
    public ResponseEntity<?> addScience(@RequestBody Science science) {
        return ResponseEntity.ok(scienceService.create(science));
    }

    @Operation(summary = "Fanlarni olish uchun api")
    @GetMapping("/science")
    public ResponseEntity<?> getScience() {
        return ResponseEntity.ok(scienceService.getAll());
    }

    @Operation(summary = "Fanlarni yo'nalishning ID raqami bo'yicha olish uchun api")
    @GetMapping("/science/{fieldOfStudyId}")
    public ResponseEntity<?> getScienceByFieldOfStudyId(@PathVariable Long fieldOfStudyId) {
        return ResponseEntity.ok(scienceService.getAllByFieldOfStudyId(fieldOfStudyId));
    }

    @Operation(summary = "Studentlarni olish uchun api", description = "param sifatida (page, size) beriladi (user_id berilsa bitta qaytadi (required = false))")
    @GetMapping("/student")
    public ResponseEntity<?> getStudent(Pageable pageable,
                                        @RequestParam(required = false) Long userId) {
        return ResponseEntity.ok(userService.getAllStudents(pageable, userId));
    }

    @Operation(summary = "Studentni o'chirish uchun api", description = "param sifatida user_id beriladi (required = true)")
    @DeleteMapping("/student/{userId}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long userId) {
        userService.deleteStudent(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Fakultetni o'chirish uchun api", description = "param sifatida faculty_id beriladi (required = true)")
    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "fieldOfStudy ni o'chirish uchun api",
            description = "param sifatida fieldOfStudy_id beriladi (required = true)")
    @DeleteMapping("/fieldOfStudy/{id}")
    public ResponseEntity<?> deleteFieldOfStudy(@PathVariable Long id) {
        fieldOfStudyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "science ni o'chirish uchun api",
            description = "param sifatida science_id beriladi (required = true)")
    @DeleteMapping("/science/{id}")
    public ResponseEntity<?> deleteScience(@PathVariable Long id) {
        scienceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "university ni o'chirish uchun api",
            description = "param sifatida university_id beriladi (required = true)")
    @DeleteMapping("/university/{id}")
    public ResponseEntity<?> deleteUniversity(@PathVariable Long id) {
        universityService.delete(id);
        return ResponseEntity.ok().build();
    }

}

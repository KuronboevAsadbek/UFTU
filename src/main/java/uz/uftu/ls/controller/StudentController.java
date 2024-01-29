package uz.uftu.ls.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.entity.FileStorage;
import uz.uftu.ls.service.FileStorageService;
import uz.uftu.ls.service.StudentService;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;

import static uz.uftu.ls.service.impl.FileStorageServiceImpl.getFileUrlResourceResponseEntity;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final FileStorageService fileStorageService;
    private final StudentService studentService;

    @Value("${upload.folder}")
    private String uploadFolder;

    @Operation(summary = "Rasmini yoki videolarni ko'rsatish apisi (hashId yoki fileId orqali)")
    @GetMapping(value = "/preview", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public ResponseEntity<FileUrlResource> previewFile(
            @RequestParam(value = "hashId", required = false) String hashId,
            @RequestParam(value = "fileId", required = false) Long fileId
    ) throws IOException {
        return getFileUrlResourceResponseEntity(hashId, fileId, fileStorageService, uploadFolder);
    }

    @Operation(summary = "Foydalanuvchi haqida ma'lumot olish")
    @GetMapping("/getStudent")
    public ResponseEntity<?> getStudent(Principal principal) {
        return ResponseEntity.ok(studentService.getStudent(principal.getName()));
    }

    @Operation(summary = "Fayl yuklash")
    @GetMapping(value = "/download", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    public ResponseEntity downloadFile(@RequestParam String hashId) throws IOException {

        ResponseDTO<FileStorage> fileStorage = fileStorageService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(fileStorage.getData().getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getData().getContentType()))
                .contentLength(fileStorage.getData().getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getData().getUploadPath())));
    }

}

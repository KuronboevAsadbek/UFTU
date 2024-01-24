package uz.uftu.ls.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.uftu.ls.service.FileStorageService;

import java.io.IOException;

import static uz.uftu.ls.service.impl.FileStorageServiceImpl.getFileUrlResourceResponseEntity;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final FileStorageService fileStorageService;

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
}

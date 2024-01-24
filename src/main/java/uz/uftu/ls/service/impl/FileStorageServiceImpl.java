package uz.uftu.ls.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.entity.FileStorage;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.repository.FileStorageRepository;
import uz.uftu.ls.repository.UserRepository;
import uz.uftu.ls.service.FileStorageService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Objects;


@Service
@Slf4j

public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final UserRepository userRepository;
    private final Hashids hashids;
    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageServiceImpl(FileStorageRepository fileStorageRepository, UserRepository userRepository) {
        this.fileStorageRepository = fileStorageRepository;
        this.userRepository = userRepository;
        this.hashids = new Hashids(getClass().getName(), 6);

    }

    public static ResponseEntity<FileUrlResource> getFileUrlResourceResponseEntity(@RequestParam(value = "hashId", required = false) String hashId, @RequestParam(value = "fileId", required = false) Long fileId, FileStorageService fileStorageService, String uploadFolder) throws MalformedURLException {
        if (hashId == null && fileId == null) {
            return ResponseEntity.badRequest().build();
        }
        FileStorage fileStorage = null;
        if (hashId == null) {
            fileStorage = fileStorageService.findById(fileId).getData();
        } else if (fileId == null) {
            fileStorage = fileStorageService.findByHashId(hashId).getData();
        }
        assert fileStorage != null;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(fileStorage.getName(), StandardCharsets.UTF_8) + "\"")
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder, fileStorage.getUploadPath())));
    }

    @Override
    public ResponseDTO<FileStorage> save(MultipartFile multipartFile, Long userId) {
        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setOriginalName(cutFileOriginalName(Objects.requireNonNull(multipartFile.getOriginalFilename())));
        fileStorage.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorageRepository.save(fileStorage);

        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/", this.uploadFolder,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DATE)));
        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            log.info("Upload folder created");
        }
        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.DATE),
                fileStorage.getHashId(),
                fileStorage.getExtension()));
        fileStorage = fileStorageRepository.save(fileStorage);
        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User topilmadi"));
            user.setFileStorage(fileStorage);
            userRepository.save(user);
        }
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException ignored) {
            log.error("File upload error");
        }
        responseDTO.setSuccess(true);
        responseDTO.setMessage("Fayl mufoqiyatli yuklandi");
        responseDTO.setData(fileStorage);
        return responseDTO;

    }

    public ResponseDTO<FileStorage> findByHashId(String hashId) {
        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        responseDTO.setData(fileStorageRepository.findByHashId(hashId));
        responseDTO.setMessage("Hujjat muvaffaqiyatli topildi");
        responseDTO.setSuccess(true);
        responseDTO.setRecordsTotal(1L);
        return responseDTO;
    }

    public ResponseDTO<FileStorage> findById(Long fileId) {
        ResponseDTO<FileStorage> responseDTO = new ResponseDTO<>();
        responseDTO.setData(fileStorageRepository.findById(fileId).orElse(null));
        responseDTO.setMessage("Hujjat muvaffaqiyatli topildi");
        responseDTO.setSuccess(true);
        responseDTO.setRecordsTotal(1L);
        return responseDTO;
    }

    @Override
    public ResponseDTO<String> delete(String hashId) {
        ResponseDTO<String> responseDTO = new ResponseDTO<>();
        FileStorage fileStorage = findByHashId(hashId).getData();
        File file = new File(String.format("%s/%s", this.uploadFolder, fileStorage.getUploadPath()));
        if (file.delete()) {
            fileStorageRepository.delete(fileStorage);
            responseDTO.setMessage("Fayl mufoqiyatli o'chirildi");
            responseDTO.setSuccess(true);
        } else {
            responseDTO.setMessage("Fayl o'chirilmadi. Sabab: " + file.delete());
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }

    @Override
    public FileStorage getOneFileStorage(Long id) {
        return fileStorageRepository.findById(id).orElseThrow(() -> new RuntimeException("File topilmadi"));
    }

    @Override
    public String cutFileOriginalName(String name) {
        int lastDotIndex = name.lastIndexOf(".");
        return name.substring(0, lastDotIndex);
    }
}

package uz.uftu.ls.service;

import org.springframework.web.multipart.MultipartFile;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.entity.FileStorage;

public interface FileStorageService {
    ResponseDTO<FileStorage> save(MultipartFile multipartFile, Long userId);

    ResponseDTO<FileStorage> findByHashId(String hashId);

    ResponseDTO<FileStorage> findById(Long fileId);

    ResponseDTO<String> delete(String hashId);

    FileStorage getOneFileStorage(Long id);

    String cutFileOriginalName(String name);
}

package uz.uftu.ls.repository;

import uz.uftu.ls.domain.entity.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {

    FileStorage findByHashId(String hashId);

    @Query(value = "DELETE FROM file_storage WHERE id = ?1", nativeQuery = true)
    @Modifying
    void deleteByUserId(Long id);
}

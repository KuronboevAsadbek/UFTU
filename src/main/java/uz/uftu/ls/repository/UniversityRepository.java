package uz.uftu.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.University;

import java.util.List;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    @Query(value = "SELECT * FROM university WHERE is_deleted=False", nativeQuery = true)
    List<University> findAll();

    @Query(value = "UPDATE university SET is_deleted = True WHERE id=:id", nativeQuery = true)
    @Modifying
    void deleteById(Long id);
}

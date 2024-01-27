package uz.uftu.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.FieldOfStudy;

import java.util.List;

@Repository
public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
    @Query(value = "SELECT * FROM field_of_study WHERE is_deleted=False", nativeQuery = true)
    List<FieldOfStudy> findAll();

    @Query(value = "SELECT * FROM field_of_study WHERE is_deleted=False AND faculty_id=:facultyId", nativeQuery = true)
    List<FieldOfStudy> findAllByFacultyId(Long facultyId);

    @Query(value = "UPDATE field_of_study SET is_deleted = True WHERE id=:id", nativeQuery = true)
    @Modifying
    void deleteById(Long id);
}

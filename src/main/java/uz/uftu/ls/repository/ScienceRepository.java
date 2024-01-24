package uz.uftu.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.Science;

import java.util.List;

@Repository
public interface ScienceRepository extends JpaRepository<Science, Long> {
    @Query(value = "SELECT * FROM science WHERE is_deleted=False", nativeQuery = true)
    List<Science> findAll();

    @Query(value = """
            SELECT s.* FROM science s
            JOIN field_of_study_science fss ON s.id = fss.science_id
            JOIN field_of_study fs ON fss.field_of_study_id = fs.id
            WHERE fs.id = ?1 AND fs.is_deleted = false AND s.is_deleted = false"""
            , nativeQuery = true)
    List<Science> findAllByFieldOfStudyId(Long fieldOfStudyId);
}

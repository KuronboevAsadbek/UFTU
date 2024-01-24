package uz.uftu.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query(value = "SELECT * FROM faculty WHERE is_deleted=False", nativeQuery = true)
    List<Faculty> findAll();

    @Query(value = "SELECT * FROM faculty WHERE is_deleted=False AND university_id=:universityId", nativeQuery = true)
    List<Faculty> findAllByUniversityId(Long universityId);
}

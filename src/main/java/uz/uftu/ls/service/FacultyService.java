package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.Faculty;
import uz.uftu.ls.domain.entity.University;

import java.util.List;

public interface FacultyService {
    //

    Faculty create(Faculty faculty);

    Faculty update(Faculty faculty, Long id);

    Faculty getById(Long id);

    void delete(Long id);

    List<Faculty> getAll();

   List<Faculty> getAllByUniversityId(Long universityId);
}

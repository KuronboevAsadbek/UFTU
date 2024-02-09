package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.Faculty;

import java.util.List;

public interface FacultyService {


    Faculty create(Faculty faculty);

    Faculty update(Faculty faculty, Long id);

    void delete(Long id);

    List<Faculty> getAll();

    List<Faculty> getAllByUniversityId(Long universityId);
}

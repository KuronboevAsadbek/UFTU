package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.University;

import java.util.List;

public interface UniversityService {

    University create(University university);

    University update(University university, Long id);

    University getById(Long id);

    void delete(Long id);

    List<University> getAll();


}

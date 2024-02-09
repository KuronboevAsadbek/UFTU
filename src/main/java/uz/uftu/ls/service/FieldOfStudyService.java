package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.FieldOfStudy;

import java.util.List;

public interface FieldOfStudyService {


    FieldOfStudy create(FieldOfStudy fieldOfStudy);

    FieldOfStudy update(FieldOfStudy fieldOfStudy, Long id);

    void delete(Long id);

    List<FieldOfStudy> getAll();

    List<FieldOfStudy> getAllByFacultyId(Long facultyId);
}

package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.Science;

import java.util.List;

public interface ScienceService {
    //

    Science create(Science science, Long fieldOfStudyId);

    Science update(Science science, Long id);

    void delete(Long id);

    List<Science> getAll();

    List<Science> getAllByFieldOfStudyId(Long fieldOfStudyId);


}

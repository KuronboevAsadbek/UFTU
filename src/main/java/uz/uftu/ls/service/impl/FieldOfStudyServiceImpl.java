package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.domain.entity.FieldOfStudy;
import uz.uftu.ls.exceptions.FieldOfStudyException;
import uz.uftu.ls.repository.FieldOfStudyRepository;
import uz.uftu.ls.service.FieldOfStudyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FieldOfStudyServiceImpl implements FieldOfStudyService {
    //

    private final FieldOfStudyRepository fieldOfStudyRepository;
    @Override
    public FieldOfStudy create(FieldOfStudy fieldOfStudy) {
        try {
            log.info("FieldOfStudy qo'shildi: {}", fieldOfStudy);
            return fieldOfStudyRepository.save(fieldOfStudy);
        }catch (Exception e){
            log.error("FieldOfStudy qo'shilmadi, {}", e.getMessage());
            throw new FieldOfStudyException("FieldOfStudy qo'shilmadi");
        }
    }

    @Override
    public FieldOfStudy update(FieldOfStudy fieldOfStudy, Long id) {
        try {
            FieldOfStudy fieldOfStudy1 = fieldOfStudyRepository.findById(id).orElseThrow(()
                    -> new FieldOfStudyException("FieldOfStudy id bo'yicha qidirilmadi"));
            fieldOfStudy1.setName(fieldOfStudy.getName());
            fieldOfStudy1.setFaculty(fieldOfStudy.getFaculty());

            log.info("FieldOfStudy yangilandi: {}", fieldOfStudy);
            return fieldOfStudyRepository.save(fieldOfStudy1);

        }catch (Exception e){
            log.error("FieldOfStudy yangilanmadi, {}", e.getMessage());
            throw new FieldOfStudyException("FieldOfStudy yangilanmadi");
        }
    }

    @Override
    public FieldOfStudy getById(Long id) {
        log.info("FieldOfStudy id bo'yicha qidirildi: {}", id);
        return fieldOfStudyRepository.findById(id).orElseThrow(()
                -> new FieldOfStudyException("FieldOfStudy id bo'yicha qidirilmadi"));
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("FieldOfStudy id bo'yicha o'chirildi: {}", id);
            fieldOfStudyRepository.deleteById(id);
        }catch (Exception e){
            log.error("FieldOfStudy id bo'yicha o'chirilmadi, {}", e.getMessage());
            throw new FieldOfStudyException("FieldOfStudy id bo'yicha o'chirilmadi");
        }

    }

    @Override
    public List<FieldOfStudy> getAll() {
       try {
           log.info("FieldOfStudy list olinildi");
           return fieldOfStudyRepository.findAll();

       }catch (Exception e){
           log.error("FieldOfStudy bo'sh, {}", e.getMessage());
           throw new FieldOfStudyException("FieldOfStudy bo'sh");
       }
    }

    @Override
    public List<FieldOfStudy> getAllByFacultyId(Long facultyId) {
       try {
              log.info("FieldOfStudy list olinildi");
           return fieldOfStudyRepository.findAllByFacultyId(facultyId);
       }catch (Exception e){
           log.error("FieldOfStudy bo'sh, {}", e.getMessage());
           throw new FieldOfStudyException("FieldOfStudy bo'sh");
       }
    }
}

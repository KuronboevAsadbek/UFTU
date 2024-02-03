package uz.uftu.ls.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uftu.ls.domain.entity.FieldOfStudy;
import uz.uftu.ls.domain.entity.Science;
import uz.uftu.ls.exceptions.ScienceException;
import uz.uftu.ls.repository.FieldOfStudyRepository;
import uz.uftu.ls.repository.ScienceRepository;
import uz.uftu.ls.service.ScienceService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScienceServiceImpl implements ScienceService {
    private final ScienceRepository scienceRepository;
    private final FieldOfStudyRepository fieldOfStudyRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Science create(Science science, Long fieldOfStudyId) {
        FieldOfStudy fieldOfStudy = fieldOfStudyRepository.findById(fieldOfStudyId).orElseThrow();
        try {
            log.info("Science qo'shildi: {}", science);
            Science savedScience = scienceRepository.save(science);
            entityManager.createNativeQuery("INSERT INTO field_of_study_science (field_of_study_id, science_id) VALUES (?, ?)")
                    .setParameter(1, fieldOfStudy.getId())
                    .setParameter(2, savedScience.getId())
                    .executeUpdate();
            return savedScience;
        } catch (Exception e) {
            log.error("Science qo'shilmadi, {}", e.getMessage());
            throw new ScienceException("Science qo'shilmadi");
        }
    }

    @Override
    public Science update(Science science, Long id) {
        try {
            Science science1 = scienceRepository.findById(id).orElseThrow();
            science1.setName(science.getName());
            log.info("Science yangilandi: {}", science);
            return scienceRepository.save(science1);

        } catch (Exception e) {
            log.error("Science yangilanmadi, {}", e.getMessage());
            throw new ScienceException("Science yangilanmadi");
        }
    }

    @Override
    public Science getById(Long id) {
        log.info("Science id bo'yicha qidirildi: {}", id);
        return scienceRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Science id bo'yicha qidirilmadi"));
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("Science id bo'yicha o'chirildi: {}", id);
            scienceRepository.deleteById(id);

        } catch (Exception e) {
            log.error("Science id bo'yicha o'chirilmadi, {}", e.getMessage());
            throw new ScienceException("Science id bo'yicha o'chirilmadi");
        }

    }

    @Override
    public List<Science> getAll() {
        try {
            log.info("Science ro'yxati qaytarildi");
            return scienceRepository.findAll();

        } catch (Exception e) {
            log.error("Science ro'yxati bo'sh, {}", e.getMessage());
            throw new ScienceException("Science ro'yxati bo'sh");
        }
    }

    @Override
    public List<Science> getAllByFieldOfStudyId(Long fieldOfStudyId) {
        try {
            log.info("Science ro'yxati qaytarildi");
            return scienceRepository.findAllByFieldOfStudyId(fieldOfStudyId);

        } catch (Exception e) {
            log.error("Science ro'yxati bo'sh, {}", e.getMessage());
            throw new ScienceException("Science ro'yxati bo'sh");
        }
    }
}

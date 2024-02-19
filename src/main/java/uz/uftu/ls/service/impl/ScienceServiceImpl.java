package uz.uftu.ls.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Science create(Science science, List<Long> fieldOfStudyId) {
        fieldOfStudyId.stream().map(id -> fieldOfStudyRepository.findById(id).orElseThrow()).forEach(fieldOfStudy -> {
            if (science.getId() == null) {
                try {
                    entityManager.createNativeQuery("INSERT INTO field_of_study_science (field_of_study_id, science_id) VALUES (?, ?)")
                            .setParameter(1, fieldOfStudy.getId())
                            .setParameter(2, scienceRepository.save(science).getId())
                            .executeUpdate();
                } catch (Exception e) {
                    log.error("Science qo'shilmadi, {}", e.getMessage());
                    throw new ScienceException("Science qo'shilmadi");
                }
            }
        });
        return science;
    }

    @Override
    public Science update(Science science) {

        try {
            log.info("Science ma'lumotlari o'zgartirildi: {}", science);
            return scienceRepository.save(science);

        } catch (Exception e) {
            log.error("Science ma'lumotlari o'zgartirilmadi, {}", e.getMessage());
            throw new ScienceException("Science ma'lumotlari o'zgartirilmadi");
        }
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

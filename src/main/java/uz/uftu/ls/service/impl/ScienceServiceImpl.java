package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.domain.entity.Science;
import uz.uftu.ls.exceptions.ScienceException;
import uz.uftu.ls.repository.ScienceRepository;
import uz.uftu.ls.service.ScienceService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScienceServiceImpl implements ScienceService {
    private final ScienceRepository scienceRepository;

    @Override
    public Science create(Science science) {
        try {
            log.info("Science qo'shildi: {}", science);
            return scienceRepository.save(science);

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

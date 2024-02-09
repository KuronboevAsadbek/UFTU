package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.domain.entity.Faculty;
import uz.uftu.ls.exceptions.FacultyException;
import uz.uftu.ls.repository.FacultyRepository;
import uz.uftu.ls.service.FacultyService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    @Override
    public Faculty create(Faculty faculty) {
        try {
            log.info("Fakultet qo'shildi: {}", faculty);
            faculty.setIsDeleted(false);
            return facultyRepository.save(faculty);

        } catch (Exception e) {
            log.error("Fakultet qo'shilmadi, {}", e.getMessage());
            throw new RuntimeException("Fakultet qo'shilmadi");
        }

    }

    @Override
    public Faculty update(Faculty faculty) {
        try {
            log.info("Fakultet yangilandi: {}", faculty);
            if (faculty.getIsDeleted() == null) {
                faculty.setIsDeleted(false);
            }
            return facultyRepository.save(faculty);
        } catch (Exception e) {
            log.error("Fakultet yangilanmadi, {}", e.getMessage());
            throw new RuntimeException("Fakultet yangilanmadi");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("Fakultet id bo'yicha o'chirildi: {}", id);
            facultyRepository.deleteById(id);

        } catch (Exception e) {
            log.error("Fakultet id bo'yicha o'chirilmadi, {}", e.getMessage());
            throw new FacultyException("Fakultet id bo'yicha o'chirilmadi");
        }

    }

    @Override
    public List<Faculty> getAll() {
        try {
            log.info("Fakultetlar ro'yxati olinildi");
            return facultyRepository.findAll();

        } catch (Exception e) {
            log.error("Fakultetlar ro'yxati olinmadi, {}", e.getMessage());
            throw new FacultyException("Fakultetlar ro'yxati olinmadi");
        }
    }

    @Override
    public List<Faculty> getAllByUniversityId(Long universityId) {
        try {
            return facultyRepository.findAllByUniversityId(universityId);
        } catch (Exception e) {
            log.error("Fakultetlar ro'yxati olinmadi, {}", e.getMessage());
            throw new RuntimeException("Fakultetlar ro'yxati olinmadi");
        }
    }
}

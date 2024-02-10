package uz.uftu.ls.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.domain.entity.University;
import uz.uftu.ls.exceptions.UniversityException;
import uz.uftu.ls.repository.UniversityRepository;
import uz.uftu.ls.service.UniversityService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public University create(University university) {
        try {
            log.info("University created");
            university.setIsDeleted(false);
            return universityRepository.save(university);

        } catch (Exception e) {
            log.error("University not created");
            throw new UniversityException("University not created");
        }
    }

    @Override
    public University update(University university) {
        try {
            if (university.getId() == null) {
                throw new UniversityException("University id is null");
            }
            log.info("University updated");
            return universityRepository.save(university);
        } catch (Exception e) {
            log.error("University not updated");
            throw new UniversityException("University not updated");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            log.info("University deleted");
            universityRepository.deleteById(id);

        } catch (Exception e) {
            log.error("University not deleted");
            throw new UniversityException("University not deleted");
        }
    }

    @Override
    public List<University> getAll() {
        try {
            log.info("University found");
            return universityRepository.findAll();

        } catch (Exception e) {
            log.error("University not found");
            throw new UniversityException("University not found");
        }
    }
}

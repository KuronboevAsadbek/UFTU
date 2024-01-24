package uz.uftu.ls.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String userName);

    boolean existsByUsername(String userName);

    @Query(value = "SELECT * FROM users u where u.role = 'ROLE_STUDENT'", nativeQuery = true)
    Page<User> findAllOrStudentId(Pageable pageable, Long userId);

    @Query(value = "UPDATE users SET is_deleted = True WHERE id = ?1", nativeQuery = true)
    @Transactional
    @Modifying
    void deleteById(Long userId);
}

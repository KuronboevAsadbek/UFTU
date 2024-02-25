package uz.uftu.ls.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.uftu.ls.service.ListOfCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListOfCountImp implements ListOfCount {

    private final EntityManager entityManager;

    @Override
    public Map<String, Object> getListOfCount() {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String sql = """
                    SELECT 'students_count' as name, COUNT(u.*) FROM users u WHERE role='ROLE_STUDENT'
                    UNION ALL
                    SELECT 'admins_count' as name, COUNT(u.*) FROM users u WHERE role='ROLE_ADMIN'
                    UNION ALL
                    SELECT 'daily_added_student' as name, COUNT(u.*) FROM users u WHERE role='ROLE_STUDENT' AND u.created_at >= (now() - interval '1 day')
                    UNION ALL
                    SELECT 'weekly_added_student' as name, COUNT(u.*) FROM users u WHERE role='ROLE_STUDENT' AND u.created_at >= (now() - interval '7 day')
                    UNION ALL
                    SELECT 'monthly_added_student' as name, COUNT(u.*) FROM users u WHERE role='ROLE_STUDENT' AND u.created_at >= (now() - interval '30 day')
                    UNION ALL
                    SELECT 'faculties_count' as name, COUNT(f.*) FROM faculty f WHERE f.is_deleted = FALSE
                    UNION ALL
                    SELECT 'field_of_study_count' as name, COUNT(fos.*) FROM field_of_study fos WHERE fos.is_deleted = FALSE
                    UNION ALL
                    SELECT 'file_storage_count' as name, COUNT(fs.*) FROM file_storage fs
                    UNION ALL
                    SELECT 'sciences_count' as name, COUNT(s.*) FROM science s WHERE s.is_deleted = FALSE
                    UNION ALL
                    SELECT 'total_uzs_payments' as name, COALESCE(SUM(p.amount), 0) FROM payment p WHERE p.payment_status = 'PAID' AND p.currency = 'UZS'
                    UNION ALL
                    SELECT 'total_usd_payments' as name, COALESCE(SUM(p.amount), 0) FROM payment p WHERE p.payment_status = 'PAID' AND p.currency = 'USD'
                    UNION ALL
                    SELECT 'total_eur_payments' as name, COALESCE(SUM(p.amount), 0) FROM payment p WHERE p.payment_status = 'PAID' AND p.currency = 'EUR'
                    """;
            Query query = entityManager.createNativeQuery(sql);
            List<?> result = query.getResultList();

            for (Object o : result) {
                Object[] row = (Object[]) o;
                resultMap.put((String) row[0], row[1]);
            }
        } catch (Exception e) {
            log.error("Error in getListOfCount: {}", e.getMessage());
        }
        return resultMap;
    }

    @Override
    public String getListOfCountByFaculty(Long facultyId) {
        
        try{
            String sql = """
                    SELECT COUNT(*) FROM users WHERE field_of_study_id IN
                    (SELECT fos.id FROM field_of_study fos where faculty_id = :facultyId)
                    """;
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("facultyId", facultyId);
            return query.getSingleResult().toString();
        }catch (Exception e){
            log.error("Error in getListOfCountByFaculty: {}", e.getMessage());
            throw new RuntimeException("Error in getListOfCountByFaculty");
        }

    }


}

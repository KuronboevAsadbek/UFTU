package uz.uftu.ls.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.uftu.ls.domain.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

package uz.uftu.ls.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.uftu.ls.domain.entity.Payment;
import uz.uftu.ls.exceptions.PaymentException;
import uz.uftu.ls.repository.PaymentRepository;
import uz.uftu.ls.repository.UserRepository;
import uz.uftu.ls.service.PaymentService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final EntityManager entityManager;
    @Override
    @Transactional
    public Payment createPayment(Payment payment, Long userId) {
        try {
            log.info("Creating payment");
            if (payment.getId() != null) {
               return updatePayment(payment);
            }
            Payment savedPayment = paymentRepository.save(payment);
            entityManager.createNativeQuery("INSERT INTO user_payments (user_id, payment_id) VALUES (?, ?)")
                    .setParameter(1, userId)
                    .setParameter(2, savedPayment.getId())
                    .executeUpdate();
            return savedPayment;

        }catch (Exception e){
            log.error("Error while creating payment", e);
            throw new PaymentException("Error while creating payment");
        }

    }

    @Override
    public Payment updatePayment(Payment payment) {
        try {
            log.info("Updating payment");
            paymentRepository.findById(payment.getId()).orElseThrow();
            return paymentRepository.save(payment);
        }catch (Exception e){
            log.error("Error while updating payment", e);
            throw new PaymentException("Error while updating payment");

        }

    }

    @Override
    public void deletePayment(Long id) {
        try {
            log.info("Deleting payment");
            paymentRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting payment", e);
            throw new PaymentException("Error while deleting payment");

        }
    }



}

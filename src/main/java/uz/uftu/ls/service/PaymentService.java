package uz.uftu.ls.service;

import uz.uftu.ls.domain.entity.Payment;

import java.util.List;

public interface PaymentService {
    //

    Payment createPayment(Payment payment, Long userId);

    Payment updatePayment(Payment payment);

    void deletePayment(Long id);




}

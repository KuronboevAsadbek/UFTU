package uz.uftu.ls.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import uz.uftu.ls.domain.enumeration.CurrencyType;
import uz.uftu.ls.domain.enumeration.PaymentStatus;
import uz.uftu.ls.domain.enumeration.PaymentType;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_date", nullable = false, updatable = false)
    @CurrentTimestamp
    private Timestamp paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", insertable = false, nullable = false, columnDefinition = "varchar(255) default 'PAID'")
    private PaymentStatus paymentStatus;

}

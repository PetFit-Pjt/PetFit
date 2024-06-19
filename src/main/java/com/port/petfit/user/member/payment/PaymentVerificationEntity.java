package com.port.petfit.user.member.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PaymentVerificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String impUid;
    private String merchantUid;
    private Double paidAmount;
    private String customerUid;

    // 생성자, getter 및 setter
}
package com.example.demo1.service;

import com.example.demo1.model.Payment;
import com.example.demo1.model.PaymentStatus;
import com.example.demo1.repository.PaymentRepo;
import com.example.demo1.translator.Translator;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.example.demo1.constants.MessageConstants.NOTFOUND;

@Service
@RequiredArgsConstructor
@Builder
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final Translator translator;

    public Payment makePayment(Long ticketId, String fare) {
        Payment payment = Payment.builder()
                .ticketId(ticketId)
                .fare(fare)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentRepo.save(payment);
    }

    public Payment getPaymentById(Long id, Locale locale) {
        return paymentRepo.findById(id)
                .orElseThrow(()->new RuntimeException(translator.toLocale("payment.not.found",locale)));
    }
}

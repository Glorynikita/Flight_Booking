package com.example.demo1.controller;

import com.example.demo1.model.Payment;
import com.example.demo1.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    private ResponseEntity<Payment> pay(@RequestParam Long ticketId, @RequestParam String fare) {
        Payment payment = paymentService.makePayment(ticketId, fare);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id, Locale locale) {
        return ResponseEntity.ok(paymentService.getPaymentById(id, locale));
    }
}

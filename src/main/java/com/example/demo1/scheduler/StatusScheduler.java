package com.example.demo1.scheduler;

import com.example.demo1.model.BookingStatus;
import com.example.demo1.model.Payment;
import com.example.demo1.model.PaymentStatus;
import com.example.demo1.model.Ticket;
import com.example.demo1.repository.PaymentRepo;
import com.example.demo1.repository.TicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StatusScheduler {
    private final PaymentRepo paymentRepo;
    private final TicketRepo ticketRepo;

    @Scheduled(fixedRate = 120000)
    public void schedulePayment() {
        List<Payment> pendingPayments = paymentRepo.findAll()
                .stream()
                .filter(Payment -> Payment.getPaymentStatus() == PaymentStatus.PENDING)
                .toList();

        pendingPayments.forEach(payment -> {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepo.save(payment);
        });
        if(!pendingPayments.isEmpty()){
            System.out.println("Processed " + pendingPayments.size() + " payments");
        }
    }

    @Scheduled(fixedRate = 300000)
    public void scheduleTicket() {
        List<Ticket> pendingTickets = ticketRepo.findAll()
                .stream()
                .filter(Ticket -> Ticket.getBookingStatus() == BookingStatus.PENDING)
                .toList();
        pendingTickets.forEach(ticket -> {
            ticket.setBookingStatus(BookingStatus.CONFIRMED);
            ticketRepo.save(ticket);
        });
        if(!pendingTickets.isEmpty()){
            System.out.println("Processed " + pendingTickets.size() + " tickets");
        }
    }
}

package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
     /*   Payment payment = new Payment();
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        if (paymentMode(mode) == false) {
            throw new Exception("Payment mode not detected");
        } else if (reservation==null) {
            throw new Exception("reservation not found");
        } else {
            int totalAmount = reservation.getNumberOfHours() * reservation.getSpot().getPricePerHour();
            if (amountSent == totalAmount) {
                payment.setPaymentCompleted(true);
                if(mode.equalsIgnoreCase("cash")) {
                    payment.setPaymentMode(PaymentMode.CASH);
                }
                else if(mode.equalsIgnoreCase("card")){
                    payment.setPaymentMode(PaymentMode.CARD);
                }
                else {
                    payment.setPaymentMode(PaymentMode.UPI);
                }
                payment.setReservation(reservation);
                paymentRepository2.save(payment);
                reservation.setPayment(payment);
                reservationRepository2.save(reservation);
                return payment;

            } else {
                throw new Exception("Insufficient Amount");
            }
        }
    }
    public boolean paymentMode(String mode){
        if(mode.equalsIgnoreCase("cash")||
                mode.equalsIgnoreCase("UPI")||mode.equalsIgnoreCase("card")){
            return true;
        }
        return false;
    }
}
*/
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        boolean paymentModeValid = false;
        PaymentMode validPaymentMode = null;
        if ((reservation.getSpot().getPricePerHour() * reservation.getNumberOfHours()) <= amountSent) {
            for (PaymentMode paymentMode : PaymentMode.values()) {
                if (paymentMode.name().equals(mode.toUpperCase())) {
                    paymentModeValid = true;
                    validPaymentMode = paymentMode;
                    break;
                }
            }
            if (!paymentModeValid) {
                throw new Exception("Payment mode not detected");
            }else{
                Payment payment = new Payment(true, validPaymentMode);
                payment.setReservation(reservation);
                reservation.setPayment(payment);
                reservationRepository2.save(reservation);
                return payment;
            }
        }else{
            throw new Exception("Insufficient Amount");
        }
    }
}
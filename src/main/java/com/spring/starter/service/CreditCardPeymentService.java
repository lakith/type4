package com.spring.starter.service;

import com.spring.starter.model.CrediitCardPeyment;
import com.spring.starter.model.CreditCardPaymentBreakDown;
import org.springframework.http.ResponseEntity;

public interface CreditCardPeymentService {

    public ResponseEntity<?> getCreditCardPaymentRequest(int creditCardPeymentId);

    public ResponseEntity<?> addCreditCardPeyment(CrediitCardPeyment crediitCardPeyment, int customerTransactionRequestId) throws Exception;

    public ResponseEntity<?> creditCardPaymentBreakdown(int crediitCardPeymentId,CreditCardPaymentBreakDown creditCardPaymentBreakDown);
}

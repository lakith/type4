package com.spring.starter.service;

import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.model.CrediitCardPeyment;
import com.spring.starter.model.CreditCardPaymentBreakDown;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface CreditCardPeymentService {

    public ResponseEntity<?> getCreditCardPaymentRequest(int creditCardPeymentId);

    public ResponseEntity<?> addCreditCardPeyment(CrediitCardPeyment crediitCardPeyment, int customerTransactionRequestId) throws Exception;

    public ResponseEntity<?> creditCardPaymentBreakdown(int crediitCardPeymentId,CreditCardPaymentBreakDown creditCardPaymentBreakDown);

    public ResponseEntity<?> addSignatureForCardPayment(MultipartFile multipartFile, int customerTransactionRequestId);

    public ResponseEntity<?> addFileToSLIPCardPayment(MultipartFile file , String fileType, int customerServiceRequestId);

    public ResponseEntity<?> updateCardPayment (CrediitCardPeyment crediitCardPeyment,
                                         int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception;

    public ResponseEntity<?> getCardPaymentUpdateRecords(int requestId);

}

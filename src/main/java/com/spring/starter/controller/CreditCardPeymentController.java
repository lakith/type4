package com.spring.starter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.model.Bank;
import com.spring.starter.model.Branch;
import com.spring.starter.model.CrediitCardPeyment;
import com.spring.starter.model.CreditCardPaymentBreakDown;
import com.spring.starter.service.CreditCardPeymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/Transaction-request/credit-card-payment")
public class CreditCardPeymentController {

    @Autowired
    CreditCardPeymentService creditCardPeymentService;

    @PostMapping
    public ResponseEntity<?> addNewCreditCardPayment(@RequestBody @Valid CrediitCardPeyment crediitCardPeyment
                                                                    ,@RequestParam(name="requestId") int requestId) throws Exception {
        return creditCardPeymentService.addCreditCardPeyment(crediitCardPeyment,requestId);
    }

    @PutMapping
    public ResponseEntity<?> upadteCreditCardPayment(@RequestBody @Valid CrediitCardPeyment crediitCardPeyment
            ,@RequestParam(name="requestId") int requestId) throws Exception {
        return creditCardPeymentService.addCreditCardPeyment(crediitCardPeyment,requestId);
    }

    @GetMapping("/{creditCardPaymentId}")
    public ResponseEntity<?> getCreditCardPayment(@PathVariable int creditCardPaymentId){
        return creditCardPeymentService.getCreditCardPaymentRequest(creditCardPaymentId);
    }

    @PostMapping("/credit_card-payment-denominations")
    private ResponseEntity<?> paymentDenominations(@RequestParam(name="requestId") int requestId,@RequestBody @Valid CreditCardPaymentBreakDown breakDown){
        return  creditCardPeymentService.creditCardPaymentBreakdown(requestId,breakDown);
    }

    @PutMapping("/add-signaute-and-update")
    public ResponseEntity<?> addSignatureForSLIP(@RequestParam MultipartFile file,
                                                 @RequestParam int customerTrasactionRequestId,
                                                 @RequestParam(required = false) String message) {
        return creditCardPeymentService.addSignatureForCardPayment(file,customerTrasactionRequestId);
    }

    @PutMapping("/add-files")
    public ResponseEntity<?> addFilesForSLIP(@RequestParam MultipartFile file,
                                             @RequestParam int customerTrasactionRequestId,
                                             @RequestParam String fileType){
        return creditCardPeymentService.addFileToSLIPCardPayment(file,fileType,customerTrasactionRequestId);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCashDeposit(@RequestParam MultipartFile file,
                                               @RequestParam String fundTransfer,
                                               @RequestParam int customerServiceRequestId,
                                               @RequestParam(required = false) String comment) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        CrediitCardPeyment crediitCardPeyment = mapper.readValue(fundTransfer, CrediitCardPeyment.class);

        DetailsUpdateDTO detailsUpdateDTO = new DetailsUpdateDTO();
        detailsUpdateDTO.setComment(comment);
        detailsUpdateDTO.setFile(file);

        return creditCardPeymentService.updateCardPayment(crediitCardPeyment,customerServiceRequestId, detailsUpdateDTO);
    }

    @GetMapping("/credit-card-payment-update-records")
    public ResponseEntity<?> getCreditCardPaymentUpdateRecords(@RequestParam(name="requestId") int requestId) {
        return creditCardPeymentService.getCardPaymentUpdateRecords(requestId);
    }

    @GetMapping
    public CrediitCardPeyment test(){

        Bank bank = new Bank();
        bank.setMx_bank_code(7011);

        Branch branch = new Branch();
        branch.setBranch_id(1);

        CrediitCardPeyment crediitCardPeyment = new CrediitCardPeyment();
        crediitCardPeyment.setTelephoneNumber("0342252011");
        crediitCardPeyment.setPaymenntMethod("cash");
        crediitCardPeyment.setBank(bank);
        crediitCardPeyment.setBranch(branch);
        crediitCardPeyment.setChequenumber("1232132132");
        crediitCardPeyment.setDate(java.util.Calendar.getInstance().getTime());
        crediitCardPeyment.setValueOf5000Notes(15000);
        crediitCardPeyment.setValueOf2000Notes(18000);
        crediitCardPeyment.setValueof1000Notes(10000);
        crediitCardPeyment.setValueOf500Notes(500);
        crediitCardPeyment.setValueOf100Notes(300);
        crediitCardPeyment.setValueOf50Notes(150);
        crediitCardPeyment.setValueOf20Notes(60);
        crediitCardPeyment.setValueOf10Notes(10);
        crediitCardPeyment.setValueOfcoins(5);
        crediitCardPeyment.setTotal(1200000);

        return crediitCardPeyment;
    }

}

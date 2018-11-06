package com.spring.starter.controller;

import com.spring.starter.model.Bank;
import com.spring.starter.model.Branch;
import com.spring.starter.model.CrediitCardPeyment;
import com.spring.starter.model.CreditCardPaymentBreakDown;
import com.spring.starter.service.CreditCardPeymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{creditCardPaymentId}")
    public ResponseEntity<?> getCreditCardPayment(@PathVariable int creditCardPaymentId){
        return creditCardPeymentService.getCreditCardPaymentRequest(creditCardPaymentId);
    }

    @PostMapping("/credit_card-payment-denominations")
    private ResponseEntity<?> paymentDenominations(@RequestParam(name="requestId") int requestId,CreditCardPaymentBreakDown breakDown){
        return  creditCardPeymentService.creditCardPaymentBreakdown(requestId,breakDown);
    }

    @GetMapping
    public CrediitCardPeyment test(){

        Bank bank = new Bank();
        bank.setMx_bank_code(7011);

        Branch branch = new Branch();
        branch.setBranch_id(1);

        CrediitCardPeyment crediitCardPeyment = new CrediitCardPeyment();
        crediitCardPeyment.setTelephoneNumber("0342252011");
        crediitCardPeyment.setPaymenntMethod("CASH");
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

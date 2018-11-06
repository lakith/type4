package com.spring.starter.service.impl;

import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.CreditCardPeymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CreditCardPeymentServiceImpl implements CreditCardPeymentService {

    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private CreditCardPeymentRepository creditCardPeymentRepository;
    @Autowired
    private CreditCardPaymentBreakDownRepository creditCardPaymentBreakDownRepository;


    public ResponseEntity<?> addCreditCardPeyment(CrediitCardPeyment crediitCardPeyment, int customerTransactionRequestId) throws Exception {

        Optional<CustomerTransactionRequest> optionalRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);
        ResponseModel responseModel = new ResponseModel();

        if(!optionalRequest.isPresent()){
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        int id = optionalRequest.get().getTransactionRequest().getDigiFormId();

        if(id != TransactionIdConfig.CREDIT_CARD_PEYMENT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        Optional<CrediitCardPeyment> crediitCardPeymentOptional = creditCardPeymentRepository.getFormFromCSR(customerTransactionRequestId);
        if(crediitCardPeymentOptional.isPresent()){
            crediitCardPeyment.setCrediitCardPeymentId(crediitCardPeymentOptional.get().getCrediitCardPeymentId());
        }

        Optional<Bank> optionalBank = bankRepository.findById(crediitCardPeyment.getBank().getMx_bank_code());
        if(!optionalBank.isPresent()){
            responseModel.setMessage("Invalid Bank Details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
         Optional<Branch> optionalBranch = branchRepository.findById(crediitCardPeyment.getBranch().getBranch_id());
        if(!optionalBank.isPresent()){
            responseModel.setMessage("Invalid Branch Details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        if (crediitCardPeyment.getPaymenntMethod() == "CASH") {
            if (crediitCardPeyment.getValueOf10Notes() == 0 && crediitCardPeyment.getValueOf20Notes() == 0 &&
                    crediitCardPeyment.getValueOf50Notes() == 0 && crediitCardPeyment.getValueOf100Notes() == 0 &&
                    crediitCardPeyment.getValueOf500Notes() == 0 && crediitCardPeyment.getValueof1000Notes() == 0 &&
                    crediitCardPeyment.getValueOf2000Notes() == 0 && crediitCardPeyment.getValueOf2000Notes() == 0) {
                responseModel.setMessage("Please fill cash details");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            double sum = (double) (crediitCardPeyment.getValueOf5000Notes() + crediitCardPeyment.getValueOf2000Notes()
                    + crediitCardPeyment.getValueof1000Notes() + crediitCardPeyment.getValueOf100Notes() +
                    crediitCardPeyment.getValueOf500Notes() + crediitCardPeyment.getValueOf50Notes() +
                    crediitCardPeyment.getValueOf20Notes() + crediitCardPeyment.getValueOf10Notes() +
                    crediitCardPeyment.getValueOfcoins());
            if (sum != crediitCardPeyment.getTotal()) {
                responseModel.setMessage("Incorrect Cash Total");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }
        }

        crediitCardPeyment.setBank(optionalBank.get());
        crediitCardPeyment.setBranch(optionalBranch.get());
        crediitCardPeyment.setCustomerTransactionRequest(optionalRequest.get());

        try{
            crediitCardPeyment = creditCardPeymentRepository.save(crediitCardPeyment);
            return new ResponseEntity<>(crediitCardPeyment,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> creditCardPaymentBreakdown(int crediitCardPeymentId, CreditCardPaymentBreakDown creditCardPaymentBreakDown) {
        Optional<CrediitCardPeyment> optional = creditCardPeymentRepository.findById(crediitCardPeymentId);
        if(!optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            Optional<CreditCardPaymentBreakDown> breakDownOptional=creditCardPaymentBreakDownRepository.findBreakDown(crediitCardPeymentId);
            if (breakDownOptional.isPresent()){
                creditCardPaymentBreakDown.setCreditCardPaymentBreakDownId(breakDownOptional.get().getCreditCardPaymentBreakDownId());
            }

            creditCardPaymentBreakDown.setCrediitCardPeyment(optional.get());
            try {
                creditCardPaymentBreakDown = creditCardPaymentBreakDownRepository.save(creditCardPaymentBreakDown);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

            CrediitCardPeyment object = optional.get();
            object.setCreditCardPaymentBreakDown(creditCardPaymentBreakDown);

            try {
                object = creditCardPeymentRepository.save(object);
                return new ResponseEntity<>(object,HttpStatus.OK);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

        }
    }

    public ResponseEntity<?> getCreditCardPaymentRequest(int creditCardPeymentId){
        ResponseModel responseModel = new ResponseModel();
        Optional<CrediitCardPeyment> crediitCardPeymentOptional = creditCardPeymentRepository.findById(creditCardPeymentId);
        if(crediitCardPeymentOptional.isPresent()){
            return new ResponseEntity<>(crediitCardPeymentOptional.get(),HttpStatus.OK);
        } else {
            responseModel.setMessage("Incorrect primary key.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
    }
}

package com.spring.starter.service;


import com.spring.starter.DTO.AuthorizeDTO;
import com.spring.starter.DTO.TTNumberDTO;
import com.spring.starter.model.AuthorizerDataTransaction;
import com.spring.starter.model.CSRDataTransaction;
import com.spring.starter.model.TransactionCustomer;
import com.spring.starter.model.TransactionRequest;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface TrancsactionRequestService {

    public ResponseEntity<?> addNewServiceRequest(TransactionRequest transactionRequest);

    public ResponseEntity<?> getBankServices();

    public ResponseEntity<?> addCustomer(TransactionCustomer transactionCustomer);

    public ResponseEntity<?> getAllTransactionCustomerDetails();

    public ResponseEntity<?> getOneCustomerDetails(int id);

    public ResponseEntity<?> viewATransactionRequest(int requestId);

    public ResponseEntity<?> getCustomerDetailsFormIndentity(String identity);

    public ResponseEntity<?> updateTransactionCustomerDetails(TransactionCustomer transactionCustomer , int recordId);

    public ResponseEntity<?> getCustomerDetailsOfAIdentityFilterByDate(String identity, String date);

    public ResponseEntity<?> getTransactionCustomerDetailsFilterByDate(String date);

    public ResponseEntity<?> addCustomerToATransactionRequest(int transactionCustomerId , int trasactionRequestId);

    public ResponseEntity<?> getAllCustomerTransactionRequests(int customerId);

    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate(int customerId, String date);

    public ResponseEntity<?> viewTransactionRequest (int customerTransactionRequest);

    public ResponseEntity<?> getAllUncompleteRequests(String date);

    public ResponseEntity<?> completeTransactionRequest(int transactionCustomerRequest, Principal principal);

    public ResponseEntity<?> getAllCustomerTransactionRequests();

    public ResponseEntity<?> getAllAuthorizeRequestsofACustomer(int customerId);

    public ResponseEntity<?> getAllCustomerTransactionRequestsByDate(String date);

    public ResponseEntity<?> transactionRequestsetsoftReject(int requestId);

    public ResponseEntity<?> transactionRequestSaveAuthorizeStatus(int requestId, AuthorizeDTO authorizeDTO);

    public ResponseEntity<?> transactionRequestSaveTTNumber(int requestId, TTNumberDTO numberDTO);

    public ResponseEntity<?> addAuthorizeDataToATransaction(AuthorizerDataTransaction authorizerDataTransaction, Principal principal, int requestId);

    public ResponseEntity<?> addCSRAuthorizeDataToATransaction(CSRDataTransaction csrDataTransaction , Principal principal, int requestId);

    public ResponseEntity<?> getUncompletedRequests(int customerId);

    public ResponseEntity<?> getAllTransactionRequests();

}

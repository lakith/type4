package com.spring.starter.controller;

import com.spring.starter.DTO.AuthorizeDTO;
import com.spring.starter.DTO.CustomerTransactionDTO;
import com.spring.starter.DTO.TTNumberDTO;
import com.spring.starter.model.AuthorizerDataTransaction;
import com.spring.starter.model.CSRDataTransaction;
import com.spring.starter.model.TransactionCustomer;
import com.spring.starter.model.TransactionRequest;
import com.spring.starter.service.TrancsactionRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/Transaction-Request")
public class TransactionRequestController {

    @Autowired
    private TrancsactionRequestService trancsactionRequestService;

    @PostMapping("/add-service")
    public ResponseEntity<?> addNewTransactionService(@RequestBody @Valid TransactionRequest transactionRequest){
        return trancsactionRequestService.addNewServiceRequest(transactionRequest);
    }

    @GetMapping("/ger-all-service")
    public ResponseEntity<?> getAllTransactionService(){
        return trancsactionRequestService.getBankServices();
    }

    @PostMapping("/add-transaction-customer")
    public ResponseEntity<?> addNewCustomer(@RequestBody @Valid TransactionCustomer transactionCustomer){
        return trancsactionRequestService.addCustomer(transactionCustomer);
    }

    @PutMapping("/update-transaction-customer/{recordId}")
    public ResponseEntity<?> updateTransactionCustomerDetails(@RequestBody @Valid TransactionCustomer transactionCustomer
                                                                                           ,@PathVariable int recordId){
        return trancsactionRequestService.updateTransactionCustomerDetails(transactionCustomer,recordId);
    }

    @GetMapping("/get-all-transaction-customers")
    public ResponseEntity<?> getAllCusmtomers(){
        return trancsactionRequestService.getAllTransactionCustomerDetails();
    }

    @GetMapping("/get-one-transaction-customer/{recordId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int recordId){
        return trancsactionRequestService.getOneCustomerDetails(recordId);
    }

    @GetMapping("/get-customer-from-identity/{identity}")
    public ResponseEntity<?> getCustomerFromIdentity(@PathVariable String identity){
        return trancsactionRequestService.getCustomerDetailsFormIndentity(identity);
    }

    @GetMapping("/get-customer-from-identity")
    public ResponseEntity<?> getCustomerFromIdentityFilterByDate(@RequestParam(name = "identification")
                                                              String identification, @RequestParam("date") String date){
        return trancsactionRequestService.getCustomerDetailsOfAIdentityFilterByDate(identification,date);
    }

    @GetMapping("/get-all-transaction-requests")
    public ResponseEntity<?> getAllTransactionRequests(){
        return trancsactionRequestService.getAllTransactionRequests();
    }

    @GetMapping("/get-trasanction-customer-records-filter-by-date")
    public ResponseEntity<?> getTransactionRecordsFilterByDate(@RequestParam("date") String date){
        return trancsactionRequestService.getTransactionCustomerDetailsFilterByDate(date);
    }

    @PostMapping("/add-customer-to-trasaction-request")
    public ResponseEntity<?> addCustomerToATransactionRequest(@RequestBody CustomerTransactionDTO customerTransactionDTO){
        return trancsactionRequestService.addCustomerToATransactionRequest(customerTransactionDTO.getCustomerId(),
                                                                      customerTransactionDTO.getTransactionRequestId());
    }

    @GetMapping("/get-all-customer-transaction-requests")
    public ResponseEntity<?> getAllTransactionRequests(@RequestParam("customerRequestId") int customerID){
        return trancsactionRequestService.getAllCustomerTransactionRequests(customerID);
    }

    @GetMapping("/get-all-customer-transaction-requests-filter-by-date")
    public ResponseEntity<?> getAllCustomerTransactionRequestsFilterByDate
                                  (@RequestParam("customerRequestId") int customerId,@RequestParam("date") String date){
        return trancsactionRequestService.getAllCustomerTransactionRequestsFilterByDate(customerId,date);
    }

    @GetMapping("/get-form-of-a-transactionRequest")
    public ResponseEntity<?> getTransactionRequestView(@RequestParam("customerRequestId") int customerId){
        return trancsactionRequestService.viewTransactionRequest(customerId);
    }

    @GetMapping("/get-uncompleted-requests")
    public ResponseEntity<?> getAllUncompleteRequests(@RequestParam("date") String date){
        return trancsactionRequestService.getAllUncompleteRequests(date);
    }

    @GetMapping("/view-transaction-request")
    public ResponseEntity<?> viewTransactionRequest(@RequestParam("requestId") int requestId){
        return trancsactionRequestService.viewATransactionRequest(requestId);
    }

    @GetMapping("/get-All-Authorize-Requests")
    public ResponseEntity<?> getAllAuthoriseRequestsBydate(){
        return trancsactionRequestService.getAllCustomerTransactionRequests();
    }

    @GetMapping("/get-All-Authorize-Requests-By-cutomerId")
    public ResponseEntity<?> getAllAuthorizeRequestsOfACustomer(@RequestParam("customerRequestId") int customerId){
        return trancsactionRequestService.getAllAuthorizeRequestsofACustomer(customerId);
    }

    @GetMapping("/get-all-Authorize-Requests-By-Date")
    public ResponseEntity<?> getAllCutomerTransactionRequestsByDate(@RequestParam("date") String date){
        return trancsactionRequestService.getAllCustomerTransactionRequestsByDate(date);
    }

    @PostMapping("/saveTTNumber")
    public ResponseEntity<?> saveTTNumber(@RequestBody TTNumberDTO numberDTO,@RequestParam("RequestId") int requestId){
        return trancsactionRequestService.transactionRequestSaveTTNumber(requestId,numberDTO);
    }

    @PostMapping("/save-Authorize-Status")
    public ResponseEntity<?> saveAuthorizeStatus(@RequestBody AuthorizeDTO numberDTO,@RequestParam("RequestId") int requestId){
        return trancsactionRequestService.transactionRequestSaveAuthorizeStatus(requestId,numberDTO);
    }

    @GetMapping("/set-Soft-reject")
    public ResponseEntity<?> setSoftReject(@RequestParam("RequestId") int requestId){
        return trancsactionRequestService.transactionRequestsetsoftReject(requestId);
    }

    @PostMapping("/setAuthorizeData")
    public ResponseEntity<?> setAuthorizeData(@RequestBody AuthorizerDataTransaction authorizerDataTransaction,Principal principal,@RequestParam("requestId") int requestId){
        return trancsactionRequestService.addAuthorizeDataToATransaction(authorizerDataTransaction,principal,requestId);
    }

    @PostMapping("/set-csr-data")
    public ResponseEntity<?> addCSRAuthorizeDataToATransaction(@RequestBody CSRDataTransaction csrDataTransaction,@RequestParam("requestId") int requestId,Principal principal) {
        return trancsactionRequestService.addCSRAuthorizeDataToATransaction(csrDataTransaction,principal,requestId);
    }

    @GetMapping("complete-a-transaction-request")
    public ResponseEntity<?> completeATransactionRequest(@RequestParam(name="requestId") int requestId,
                                                                                                   Principal principal){
        return trancsactionRequestService.completeTransactionRequest(requestId,principal);
    }

    @GetMapping("/get-uncomplted-reqests")
    public ResponseEntity<?> getUncompletedRequests(@RequestParam(name = "customerServiceRequestId") int customerServiceRequestId){
        return trancsactionRequestService.getUncompletedRequests(customerServiceRequestId);
    }

}

package com.spring.starter.service;

import com.spring.starter.DTO.AccountStatementIssueRequestDTO;
import com.spring.starter.DTO.BankStatementAccountDTO;
import com.spring.starter.DTO.DuplicatePassBookRequestDTO;
import com.spring.starter.model.PassbookRequest;
import com.spring.starter.model.StatementFrequency;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface BankStatementPassBookService {

    public ResponseEntity<?> estatementService(BankStatementAccountDTO bankStatementAccountDTO, int customerServiceRequistId);

    public ResponseEntity<?> statementFrequencyService(StatementFrequency statementFrequency, int customerServiceRequistId);

    public ResponseEntity<?> duplicatePassBookRequest(DuplicatePassBookRequestDTO duplicatePassBookRequestDTO, HttpServletRequest request);

    public ResponseEntity<?> AccountStatement(AccountStatementIssueRequestDTO accountStatementIssueRequestDTO, HttpServletRequest request);

    public ResponseEntity<?> passbookRequest(PassbookRequest passbookRequest, int requestId) throws Exception;

}

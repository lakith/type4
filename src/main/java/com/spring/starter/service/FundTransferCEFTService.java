package com.spring.starter.service;

import com.spring.starter.DTO.FileDTO;
import com.spring.starter.DTO.FundTransferCEFTDTO;
import com.spring.starter.DTO.TransactionSignatureDTO;
import com.spring.starter.model.FundTransferCEFT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FundTransferCEFTService {

    public ResponseEntity<?> addNewFundTransferCEFTRequest(FundTransferCEFT fundTransferSLIPS, int customerTransactionRequest) throws Exception;

    public ResponseEntity<?> getFundTransferCEFTRequest(int fundTransferSlipID);

    ResponseEntity<?> saveCEFTSignature(TransactionSignatureDTO transactionSignatureDTO) throws Exception;

    ResponseEntity<?> uploadFilesToFundTransfers(FileDTO fileDTO) throws Exception;

    public ResponseEntity<?> updateFundTransferCEFTService(MultipartFile file, FundTransferCEFT fundTransferCEFT,
                                                           int customerServiceRequestId, String comment) throws Exception;

    public ResponseEntity<?> getCEFTUpdateRecords(int requestId);
}

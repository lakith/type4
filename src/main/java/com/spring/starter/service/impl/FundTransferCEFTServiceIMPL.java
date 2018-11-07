package com.spring.starter.service.impl;

import com.spring.starter.DTO.*;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FundTransferCEFTService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class FundTransferCEFTServiceIMPL implements FundTransferCEFTService {

    @Autowired
    CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    FundTransferCEFTRepository fundTransferCEFTRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    FundTransferCEFTFileRepository fundTransferCEFTFileRepository;

    @Autowired
    FundTransferCEFTUpdateRecordsRepository fundTransferCEFTUpdateRecordsRepository;

    @Autowired
    FundTransferCEFTErrorRecordsRepository fundTransferCEFTErrorRecordsRepository;

    @Autowired
    private FundTransferCEFTBreakDownRepository fundTransferCEFTBreakDownRepository;


    @Override
    public ResponseEntity<?> addNewFundTransferCEFTRequest(FundTransferCEFT fundTransferCEFT, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferCEFT.isPresent()){
            fundTransferCEFT.setFundTransferCEFTId(optionalTransferCEFT.get().getFundTransferCEFTId());
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferCEFT.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("Invalid bank details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferCEFT.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferCEFT.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("Invalid bank branch details");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferCEFT.setBranch(branch.get());
        }

        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferCEFT = fundTransferCEFTRepository.save(fundTransferCEFT);
            return new ResponseEntity<>(fundTransferCEFT,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferCEFTRequest(int fundTransferCEFTID) {
        Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.findById(fundTransferCEFTID);
        if(!optionalTransferCEFT.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferCEFT,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> saveCEFTSignature(TransactionSignatureDTO signatureDTO) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest;
        try {
            customerTransactionRequest = customerTransactionRequestRepository.findById(signatureDTO.getCustomerTransactionId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transfer CEFT Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transfer CEFT Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Optional<FundTransferCEFT> optionalFundTransferCEFT;


        try {
            optionalFundTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(signatureDTO.getCustomerTransactionId());

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (!optionalFundTransferCEFT.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }else {

            int customerTransactionRequestId =  customerTransactionRequest.get().getCustomerTransactionRequestId();

            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = signatureDTO.getFile().getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location = ("/Fund_transfer_CEFT/signatures/update_record_verifications/" +customerTransactionRequestId);
            String filename = "" + customerTransactionRequestId + "_uuid-" + randomUUIDString + extention;
            String url = fileStorage.fileSaveWithRenaming(signatureDTO.getFile(), location, filename);
            location = "" + location + "/" + filename;
            if (url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                FundTransferCEFT fundTransferCEFT=optionalFundTransferCEFT.get();
                fundTransferCEFT.setUrl(location);

                try {
                    fundTransferCEFTRepository.save(fundTransferCEFT);
                    responseModel.setMessage("Transfer CEFT updated successfully!");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                }catch (Exception ex){
                    responseModel.setMessage("Something went wrong with the database connection!");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
                }

            }
        }
    }

    @Override
    public ResponseEntity<?> uploadFilesToFundTransfers(FileDTO fileDTO) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        try {
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(fileDTO.getCustomerTransactionRequestId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transaction Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        Optional<FundTransferCEFT> optional=fundTransferCEFTRepository.getFormFromCSR(fileDTO
                .getCustomerTransactionRequestId());
        FundTransferCEFTFiles fundTransferCEFTFile = new FundTransferCEFTFiles();
        if(!optional.isPresent()){
            responseModel.setMessage("Invalid customer transaction id!");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
        } else {
            String location =  ("/fund_transfer/file_uploads/" + fileDTO.getCustomerTransactionRequestId()
                    +"/Customer Files");
            String url = fileStorage.fileSave(fileDTO.getFile(),location);
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload File!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                fundTransferCEFTFile.setFundTransferCEFTS(optional.get());
                fundTransferCEFTFile.setFileType(fileDTO.getFileType());
                fundTransferCEFTFile.setFileUrl(url);

                try {
                    fundTransferCEFTFile = fundTransferCEFTFileRepository.save(fundTransferCEFTFile);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
                if(fundTransferCEFTFile == null){
                    responseModel.setMessage("Something went Wrong!");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                } else {
                    FundTransferCEFT fundTransferCEFT = optional.get();
                    List<FundTransferCEFTFiles> fundTransferFiles = fundTransferCEFT.getFundTransferCEFTFiles();
                    if(fundTransferFiles.isEmpty()){
                        fundTransferFiles = new ArrayList<>();
                        fundTransferFiles.add(fundTransferCEFTFile);
                    } else {
                        fundTransferFiles.add(fundTransferCEFTFile);
                    }
                    fundTransferCEFT.setFundTransferCEFTFiles(fundTransferFiles);
                    try{
                        fundTransferCEFTRepository.save(fundTransferCEFT);
                        responseModel.setMessage("Withdrawal Files uploded successfull!");
                        responseModel.setStatus(true);
                        return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                    } catch (Exception e) {
                        throw new Exception(e.getMessage());
                    }
                }

            }
        }
    }

    @Override
    public ResponseEntity<?> updateFundTransferCEFTService(MultipartFile file, FundTransferCEFT fundTransferCEFT,
                                                           int customerServiceRequestId, String comment) throws Exception {
        ResponseModel responseModel=new ResponseModel();
        Optional<FundTransferCEFT> optional;
        Optional<CustomerTransactionRequest> customerTransactionRequest;
        try {
            customerTransactionRequest = customerTransactionRequestRepository
                    .findById(customerServiceRequestId);
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT){
                responseModel.setMessage("Invalid Transaction Request Id!");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        try {
            optional = fundTransferCEFTRepository.getFormFromCSR(customerServiceRequestId);

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (!optional.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferCEFT.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("Invalid bank details!");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        fundTransferCEFT.setFundTransferCEFTId(optional.get().getFundTransferCEFTId());
        fundTransferCEFT.setCustomerTransactionRequest(customerTransactionRequest.get());

        FundTransferCEFTUpdateRecords fundTransferCEFTUpdateRecords = new FundTransferCEFTUpdateRecords();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = file.getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/fund_transfer_ceft/signatures/update_record_verifications/" + customerServiceRequestId );
        String filename = customerServiceRequestId + "_uuid-"+ randomUUIDString+"."+extention;
        String url = fileStorage.fileSaveWithRenaming(file,location,filename);
        location = location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {
            fundTransferCEFTUpdateRecords.setComment(comment);
            fundTransferCEFTUpdateRecords.setUrl(location);
            fundTransferCEFTUpdateRecords.setCustomerTransactionRequest(customerTransactionRequest.get());

            fundTransferCEFTUpdateRecords=fundTransferCEFTUpdateRecordsRepository.save(fundTransferCEFTUpdateRecords);
            List<FundTransferCEFTErrorRecords> fundTransferCEFTErrorRecords = new ArrayList<>();
            fundTransferCEFTErrorRecords = getAllSlipsRecordErrors(optional.get(),fundTransferCEFT,fundTransferCEFTUpdateRecords);

            fundTransferCEFTUpdateRecords.setFundTransferCEFTErrorRecords(fundTransferCEFTErrorRecords);

            fundTransferCEFTUpdateRecords=fundTransferCEFTUpdateRecordsRepository.save(fundTransferCEFTUpdateRecords);

            if (fundTransferCEFTUpdateRecords==null){
                responseModel.setMessage("Something went wrong with the database connection.");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            }else{
                try {
                    fundTransferCEFTRepository.save(fundTransferCEFT);
                    responseModel.setMessage("Fund Transfer SLIPS updated successfully.");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }

    }

    @Override
    public ResponseEntity<?> getCEFTUpdateRecords(int requestId) {
        List<FundTransferCEFTUpdateRecords> records =fundTransferCEFTUpdateRecordsRepository
                .getAllFormFromCSR(requestId);

        if(records.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<FundTransferCEFTUpdateDTO> updatedRecords = new ArrayList<>();

            for(FundTransferCEFTUpdateRecords ceftUpdateRecords : records  ) {
                FundTransferCEFTUpdateDTO updateRecordsDTO = new FundTransferCEFTUpdateDTO();
                updateRecordsDTO.setFundTransferUpdateRecordsId(ceftUpdateRecords.getFundTrasnferCEFTUpdateRecordsId());
                updateRecordsDTO.setSignatureUrl(ceftUpdateRecords.getUrl());
                updateRecordsDTO.setComment(ceftUpdateRecords.getComment());
                updateRecordsDTO.setCustomerTransactionRequest(ceftUpdateRecords.getCustomerTransactionRequest());

                List<UpdateRecordsListDTO> updateRecordsListDTOS = new ArrayList<>();

                for (FundTransferCEFTErrorRecords recordErrors : ceftUpdateRecords.getFundTransferCEFTErrorRecords()) {

                    String value = recordErrors.getOldValue();
                    String[] parts = value.split(":");


                    ErrorRecordsView oldErrorRecordsView = new ErrorRecordsView();
                    oldErrorRecordsView.setValueType(parts[0]);
                    oldErrorRecordsView.setValue(parts[1]);

                    value = recordErrors.getNewValue();
                    parts = value.split(":");

                    ErrorRecordsView newErrorRecordsView = new ErrorRecordsView();
                    newErrorRecordsView.setValueType(parts[0]);
                    newErrorRecordsView.setValue(parts[1]);

                    UpdateRecordsListDTO updateRecordsListDTO = new UpdateRecordsListDTO();
                    updateRecordsListDTO.setOldValue(oldErrorRecordsView);
                    updateRecordsListDTO.setNewValue(oldErrorRecordsView);

                    updateRecordsListDTOS.add(updateRecordsListDTO);
                }

                updateRecordsDTO.setList(updateRecordsListDTOS);
                updatedRecords.add(updateRecordsDTO);
            }

            return new ResponseEntity<>(updatedRecords, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> fundtransferCEFTBreakdown(int fundTransferCEFTId, FundTransferCEFTBreakDown breakDown) {

        Optional<FundTransferCEFT> optional = fundTransferCEFTRepository.findById(fundTransferCEFTId);
        if(!optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Optional<FundTransferCEFTBreakDown> breakDownOptional=fundTransferCEFTBreakDownRepository.findBreakDown(fundTransferCEFTId);
            if (breakDownOptional.isPresent()){
                breakDown.setFundTransferCEFTBreakDownId(breakDownOptional.get().getFundTransferCEFTBreakDownId());
            }
            breakDown.setFundTransferCEFT(optional.get());
            try {
                breakDown.setDate(java.util.Calendar.getInstance().getTime());
                breakDown = fundTransferCEFTBreakDownRepository.save(breakDown);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

            FundTransferCEFT object = optional.get();
            object.setFundTransferCEFTBreakDown(breakDown);

            try {
                object = fundTransferCEFTRepository.save(object);
                return new ResponseEntity<>(breakDown,HttpStatus.OK);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

        }
    }

    private List<FundTransferCEFTErrorRecords> getAllSlipsRecordErrors(FundTransferCEFT fundTransferCEFTOld,FundTransferCEFT fundTransferCEFTNew,FundTransferCEFTUpdateRecords fundTransferCEFTUpdateRecords){

        List<FundTransferCEFTErrorRecords> records= new ArrayList<>();
        FundTransferCEFTErrorRecords fundTransferCEFTErrorRecords;

        if(!fundTransferCEFTOld.getCreditAccountNo().equals(fundTransferCEFTNew.getCreditAccountNo())){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\" Credit_Card_Account_Number \":\""+fundTransferCEFTOld.getCreditAccountNo()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\" Credit_Card_Account_Number \":\""+fundTransferCEFTNew.getCreditAccountNo()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        if(!fundTransferCEFTOld.getAccountName().equals(fundTransferCEFTNew.getAccountName())){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\" Account_Holder_Name \":\""+fundTransferCEFTOld.getAccountName()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\" Account_Holder_Name \":\""+fundTransferCEFTNew.getAccountName()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        if(fundTransferCEFTOld.getAmmount() != fundTransferCEFTNew.getAmmount()){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\"Amount \":\""+fundTransferCEFTOld.getAmmount()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\"Amount \":\""+fundTransferCEFTNew.getAmmount()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        if(fundTransferCEFTOld.getBank().getMx_bank_code() != fundTransferCEFTNew.getBank().getMx_bank_code()){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\" Bank_Details \":\""+fundTransferCEFTOld.getBank().getMx_bank_code()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\" Bank_Details \":\""+fundTransferCEFTNew.getBank().getMx_bank_code()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        if(fundTransferCEFTOld.getBranch().getBranch_id() != fundTransferCEFTNew.getBranch().getBranch_id()){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\" Branch_Details \":\""+fundTransferCEFTOld.getBranch().getBranch_id()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\" Branch_Details \":\""+fundTransferCEFTNew.getBranch().getBranch_id()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        if(!fundTransferCEFTOld.getReason().equals(fundTransferCEFTNew.getReason())){
            fundTransferCEFTErrorRecords = new FundTransferCEFTErrorRecords();
            fundTransferCEFTErrorRecords.setOldValue("{\"Reason \":\""+fundTransferCEFTOld.getReason()+"\"}");
            fundTransferCEFTErrorRecords.setNewValue("{\"Reason \":\""+fundTransferCEFTNew.getReason()+"\"}");
            fundTransferCEFTErrorRecords.setFundTransferCEFTUpdateRecords(fundTransferCEFTUpdateRecords);
            fundTransferCEFTErrorRecords = fundTransferCEFTErrorRecordsRepository.save(fundTransferCEFTErrorRecords);
            records.add(fundTransferCEFTErrorRecords);
        }
        return records;

    }


}

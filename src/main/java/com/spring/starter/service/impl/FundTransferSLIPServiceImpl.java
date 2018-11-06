package com.spring.starter.service.impl;

import com.spring.starter.DTO.DetailsUpdateDTO;
import com.spring.starter.DTO.ErrorRecordsView;
import com.spring.starter.DTO.FundTransferSLIPSUpdateDTO;
import com.spring.starter.DTO.UpdateRecordsListDTO;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.FundTransferSLIPService;
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
public class FundTransferSLIPServiceImpl implements FundTransferSLIPService {

    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    private FundTransferSLIPRepository fundTransferSLIPRepository;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private FundTransferSLIPSFilesRepository fundTransferSLIPSFilesRepository;
    @Autowired
    private FundTransferSLIPSErrorRecordsRepository fundTransferSLIPSErrorRecordsRepository;
    @Autowired
    private FundTransferSLIPSUpdateRecordRepository fundTransferSLIPSUpdateRecordRepository;
    @Autowired
    private FundTransferSLIPSBreakDownRepository fundTransferSLIPSBreakDownRepository;

    private ResponseModel responseModel = new ResponseModel();

    @Override
    public ResponseEntity<?> addNewFundTransferSlipRequest(FundTransferSLIPS fundTransferSLIPS, int customerTransactionRequestId) throws Exception {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        if(optionalTransferSLIPS.isPresent()){
            fundTransferSLIPS.setFundTransferSLIPSId(optionalTransferSLIPS.get().getFundTransferSLIPSId());
        }

        Optional<Bank> bank = bankRepository.findById(fundTransferSLIPS.getBank().getMx_bank_code());
        if(!bank.isPresent()){
            responseModel.setMessage("invalied bank details.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            fundTransferSLIPS.setBank(bank.get());
        }

        Optional<Branch> branch = branchRepository.findById(fundTransferSLIPS.getBranch().getBranch_id());
        if(!branch.isPresent()){
            responseModel.setMessage("invalied bank branch details.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }  else {
            fundTransferSLIPS.setBranch(branch.get());
        }

        fundTransferSLIPS.setCustomerTransactionRequest(customerTransactionRequest.get());
        try{
            fundTransferSLIPS = fundTransferSLIPRepository.save(fundTransferSLIPS);
            return new ResponseEntity<>(fundTransferSLIPS,HttpStatus.CREATED);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> addFileToSLIP(MultipartFile file , String fileType, int customerServiceRequestId){
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerServiceRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerServiceRequestId);
        if(!optionalTransferSLIPS.isPresent()){
           responseModel.setMessage("There is no record to upload files");
        }   else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = file.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/fundTransferSLIP/file_uploads/" + customerServiceRequestId);
            String filename = customerServiceRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(file,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                FundTransferSLIPSFiles fundTransferSLIPSFile = new FundTransferSLIPSFiles();
                fundTransferSLIPSFile.setFileUrl(location);
                fundTransferSLIPSFile.setFileType(fileType);
                fundTransferSLIPSFile.setFundTransferSLIPS(optionalTransferSLIPS.get());

                try {
                    fundTransferSLIPSFile = fundTransferSLIPSFilesRepository.save(fundTransferSLIPSFile);
                } catch (Exception e){
                    throw new CustomException(e.getMessage());
                }
                List<FundTransferSLIPSFiles> fundTransferSLIPSFiles;
                if(optionalTransferSLIPS.get().getFundTransferSLIPSIds().isEmpty()){
                    fundTransferSLIPSFiles = new ArrayList<>();
                } else {
                    fundTransferSLIPSFiles = optionalTransferSLIPS.get().getFundTransferSLIPSIds();
                }

                fundTransferSLIPSFiles.add(fundTransferSLIPSFile);
                FundTransferSLIPS fundTransferSLIPS = optionalTransferSLIPS.get();
                fundTransferSLIPS.setFundTransferSLIPSIds(fundTransferSLIPSFiles);

                try {
                    fundTransferSLIPRepository.save(fundTransferSLIPS);
                    responseModel.setMessage("file upload successfully");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }

            }

        }
        return  null;
    }

    @Override
    public ResponseEntity<?> updateSLIP(FundTransferSLIPS fundTransferSLIPS, int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception {
        Optional<CustomerTransactionRequest> customerTransactionRequest;

        try {
            customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
        if (!customerTransactionRequest.isPresent()) {
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        Optional<FundTransferSLIPS> optionalTransferSLIPS;

        try {
            optionalTransferSLIPS= fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (!optionalTransferSLIPS.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        fundTransferSLIPS.setFundTransferSLIPSId(optionalTransferSLIPS.get().getFundTransferSLIPSId());
        fundTransferSLIPS.setCustomerTransactionRequest(customerTransactionRequest.get());

        FundTransferSLIPSUpdateRecord fundTransferSLIPSUpdateRecord= new FundTransferSLIPSUpdateRecord();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = detailsUpdateDTO.getFile().getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/fund_transfer_slips/signatures/update_record_verifications/" + customerTransactionRequestId );
        String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(detailsUpdateDTO.getFile(),location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            fundTransferSLIPSUpdateRecord.setSignatureUrl(location);
            fundTransferSLIPSUpdateRecord.setComment(detailsUpdateDTO.getComment());
            fundTransferSLIPSUpdateRecord.setCustomerTransactionRequest(customerTransactionRequest.get());

            fundTransferSLIPSUpdateRecord=fundTransferSLIPSUpdateRecordRepository.save(fundTransferSLIPSUpdateRecord);

            List<FundTransferSLIPSErrorRecords> fundTransferSLIPSErrorRecords= new ArrayList<>();
            fundTransferSLIPSErrorRecords=getAllSlipsRecordErrors(optionalTransferSLIPS.get(),fundTransferSLIPS,fundTransferSLIPSUpdateRecord);

            fundTransferSLIPSUpdateRecord.setFundTransferSLIPSErrorRecords(fundTransferSLIPSErrorRecords);

            fundTransferSLIPSUpdateRecord=fundTransferSLIPSUpdateRecordRepository.save(fundTransferSLIPSUpdateRecord);

            if (fundTransferSLIPSUpdateRecord==null){
                responseModel.setMessage("Something went wrong with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            }else{
                try {
                    fundTransferSLIPRepository.save(fundTransferSLIPS);
                    responseModel.setMessage("Fund Transfer SLIPS updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }

        }
    }

    @Override
    public ResponseEntity<?> getSLIPUpdateRecords(int requestId) {
        List<FundTransferSLIPSUpdateRecord> slipsList =fundTransferSLIPSUpdateRecordRepository
                .getAllFormFromCSR(requestId);

        if(slipsList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<FundTransferSLIPSUpdateDTO> fundTransferSLIPSUpdateRecords = new ArrayList<>();
            for(FundTransferSLIPSUpdateRecord record : slipsList) {
                FundTransferSLIPSUpdateDTO updateRecordsDTO = new FundTransferSLIPSUpdateDTO();
                updateRecordsDTO.setFundTransferSLIPRecordsId(record.getFundTransferSLIPSUpdateRecordId());
                updateRecordsDTO.setSignatureUrl(record.getSignatureUrl());
                updateRecordsDTO.setComment(record.getComment());
                updateRecordsDTO.setCustomerTransactionRequest(record.getCustomerTransactionRequest());

                List<UpdateRecordsListDTO> updateRecordsListDTOS = new ArrayList<>();

                for (FundTransferSLIPSErrorRecords recordErrors : record.getFundTransferSLIPSErrorRecords()) {

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
                fundTransferSLIPSUpdateRecords.add(updateRecordsDTO);
            }
            return new ResponseEntity<>(fundTransferSLIPSUpdateRecords, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> fundtransferSLIPBreakdown(int fundTransferSLIPSId, FundTransferSLIPSBreakDown breakDown) {
        Optional<FundTransferSLIPS> optional = fundTransferSLIPRepository.findById(fundTransferSLIPSId);
        if(!optional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Optional<FundTransferSLIPSBreakDown> breakDownOptional=fundTransferSLIPSBreakDownRepository.findBreakDown(fundTransferSLIPSId);
            if (breakDownOptional.isPresent()){
                breakDown.setFundTransferSLIPSBreakDownId(breakDownOptional.get().getFundTransferSLIPSBreakDownId());
            }
            breakDown.setFundTransferSLIPS(optional.get());
            try {
                breakDown = fundTransferSLIPSBreakDownRepository.save(breakDown);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

            FundTransferSLIPS object = optional.get();
            object.setFundTransferSLIPSBreakDown(breakDown);

            try {
                object = fundTransferSLIPRepository.save(object);
                return new ResponseEntity<>(object,HttpStatus.OK);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

        }
    }


    private List<FundTransferSLIPSErrorRecords> getAllSlipsRecordErrors(FundTransferSLIPS fundTransferSLIPSOLD,FundTransferSLIPS fundTransferSLIPSNew,FundTransferSLIPSUpdateRecord fundTransferSLIPSUpdateRecord){

        List<FundTransferSLIPSErrorRecords> records= new ArrayList<>();
        FundTransferSLIPSErrorRecords fundTransferSLIPSErrorRecords;

        if(!fundTransferSLIPSOLD.getCreditAccountNo().equals(fundTransferSLIPSNew.getCreditAccountNo())){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Credit Card Account Number \":\""+fundTransferSLIPSOLD.getCreditAccountNo()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Credit Card Account Number \":\""+fundTransferSLIPSNew.getCreditAccountNo()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        if(!fundTransferSLIPSOLD.getAccountName().equals(fundTransferSLIPSNew.getAccountName())){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Account Holder Name \":\""+fundTransferSLIPSOLD.getAccountName()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Account Holder Name \":\""+fundTransferSLIPSNew.getAccountName()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        if(fundTransferSLIPSOLD.getAmmount() != fundTransferSLIPSNew.getAmmount()){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Amount \":\""+fundTransferSLIPSOLD.getAmmount()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Amount \":\""+fundTransferSLIPSNew.getAmmount()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        if(fundTransferSLIPSOLD.getBank().getMx_bank_code() != fundTransferSLIPSNew.getBank().getMx_bank_code()){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Bank Details \":\""+fundTransferSLIPSOLD.getBank().getMx_bank_code()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Bank Details \":\""+fundTransferSLIPSNew.getBank().getMx_bank_code()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        if(fundTransferSLIPSOLD.getBranch().getBranch_id() != fundTransferSLIPSNew.getBranch().getBranch_id()){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Branch Details \":\""+fundTransferSLIPSOLD.getBranch().getBranch_id()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Branch Details \":\""+fundTransferSLIPSNew.getBranch().getBranch_id()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        if(!fundTransferSLIPSOLD.getReason().equals(fundTransferSLIPSNew.getReason())){
            fundTransferSLIPSErrorRecords = new FundTransferSLIPSErrorRecords();
            fundTransferSLIPSErrorRecords.setOldValue("{\" Reason \":\""+fundTransferSLIPSOLD.getReason()+"\"}");
            fundTransferSLIPSErrorRecords.setNewValue("{\" Reason \":\""+fundTransferSLIPSNew.getReason()+"\"}");
            fundTransferSLIPSErrorRecords.setFundTransferSLIPSUpdateRecord(fundTransferSLIPSUpdateRecord);
            fundTransferSLIPSErrorRecords = fundTransferSLIPSErrorRecordsRepository.save(fundTransferSLIPSErrorRecords);
            records.add(fundTransferSLIPSErrorRecords);
        }
        return records;

    }

    @Override
    public ResponseEntity<?> addSignatureForSLIP(MultipartFile multipartFile, int customerTransactionRequestId){

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalied Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            responseModel.setMessage("Invalied Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(customerTransactionRequestId);
        if(!optionalTransferSLIPS.isPresent()){
           responseModel.setMessage("There is no record in the db for update");
           responseModel.setStatus(false);
           return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = multipartFile.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/fundTransferSLIP/signatures/" + customerTransactionRequestId);
            String filename = customerTransactionRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(multipartFile,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                FundTransferSLIPS fundTransferSLIPS = optionalTransferSLIPS.get();
                fundTransferSLIPS.setUrl(location);

                try{
                    fundTransferSLIPRepository.save(fundTransferSLIPS);
                    responseModel.setMessage("fund transfer saved successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    responseModel.setMessage("Something went wrong with the database connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.SERVICE_UNAVAILABLE);
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> getFundTransferSlipRequest(int fundTransferSlipID){
        Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.findById(fundTransferSlipID);
        if(!optionalTransferSLIPS.isPresent()){
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("No content For that id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(optionalTransferSLIPS,HttpStatus.OK);
        }
    }
}

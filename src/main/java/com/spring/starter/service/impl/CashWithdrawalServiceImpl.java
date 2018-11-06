package com.spring.starter.service.impl;

import com.spring.starter.DTO.*;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.CashWithdrawalService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class CashWithdrawalServiceImpl implements CashWithdrawalService {

    @Autowired
    private CashWithdrawalRepository cashWithdrawalRepository;

    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private FileStorage fileStorage;

    @Autowired
    private CashWithdrawalFileRepositiry cashWithdrawalFileRepositiry;

    @Autowired
    private CashWithDrawalRecordErrorsRepository cashWithDrawalRecordErrorsRepository;

    @Autowired
    CashWithdrawalUpdateRecordsRepository cashWithdrawalUpdateRecordsRepository;

    @Autowired
    CashWithDrawalBreakDownRepositroy cashWithDrawalBreakDownRepositroy;


    private ResponseModel responseModel = new ResponseModel();


    @Override
    public ResponseEntity<?> cashWithdrawal(CashWithdrawalDTO cashWithdrawalDTO, int customerTransactionRequestId) {

        try {
            CashWithdrawal cashWithdrawal = new CashWithdrawal();

            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);

            if(!customerTransactionRequest.isPresent()){
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<Branch> branchOptional= branchRepository.findById(cashWithdrawalDTO.getNdbBranchId());

            if(!branchOptional.isPresent()){
                responseModel.setMessage("Invalid Bank Branch");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.WITHDRAWALS){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }

            Optional<CashWithdrawal> optional=cashWithdrawalRepository.getFormFromCSR(customerTransactionRequestId);
            if (optional.isPresent()){
                cashWithdrawal.setCashWithdrawalId(optional.get().getCashWithdrawalId());
            }

            cashWithdrawal.setCustomerTransactionRequest(customerTransactionRequest.get());
            cashWithdrawal.setBranch(branchOptional.get());
            cashWithdrawal.setDate(cashWithdrawalDTO.getDate());
            cashWithdrawal.setAccountNo(cashWithdrawalDTO.getAccountNo());
            cashWithdrawal.setAccountHolder(cashWithdrawalDTO.getAccountHolder());
            cashWithdrawal.setCurrency(cashWithdrawalDTO.getCurrency());
            cashWithdrawal.setAmount(cashWithdrawalDTO.getAmount());
            cashWithdrawal.setAmountInWords(cashWithdrawalDTO.getAmountInWords());
            cashWithdrawal.setIdentity(cashWithdrawalDTO.getIdentity());

            CashWithdrawal save=cashWithdrawalRepository.save(cashWithdrawal);
            if (save!=null){
                return new ResponseEntity<>(save,HttpStatus.CREATED);
            }else{
                responseModel.setMessage("Failed To Save ");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseModel.setMessage("Failed To Save");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> cashWithdrawalBreakdown(int cashwithdrawalID,CashWithDrawalBreakDown cashWithDrawalBreakDown){
        Optional<CashWithdrawal> cashWithdrawal = cashWithdrawalRepository.findById(cashwithdrawalID);
        if(!cashWithdrawal.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            Optional<CashWithDrawalBreakDown> breakDownOptional=cashWithDrawalBreakDownRepositroy.findBreakDown(cashwithdrawalID);
            if (breakDownOptional.isPresent()){
                cashWithDrawalBreakDown.setCashWithDrawalBreakDownId(breakDownOptional.get().getCashWithDrawalBreakDownId());
            }

            cashWithDrawalBreakDown.setCashWithdrawal(cashWithdrawal.get());
            try {
                cashWithDrawalBreakDown = cashWithDrawalBreakDownRepositroy.save(cashWithDrawalBreakDown);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

            CashWithdrawal ca = cashWithdrawal.get();
            ca.setCashWithDrawalBreakDown(cashWithDrawalBreakDown);

            try {
                ca = cashWithdrawalRepository.save(ca);
                return new ResponseEntity<>(cashWithDrawalBreakDown,HttpStatus.OK);
            } catch (Exception e){
                throw new CustomException(e.getMessage());
            }

        }
    }

    @Override
    public ResponseEntity<?> updateCashWithdrawal (CashWithdrawalDTO cashWithdrawalDTO, int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO) throws Exception {

        CashWithdrawal cashWithdrawal = new CashWithdrawal();
        Optional<CustomerTransactionRequest> customerTransactionRequest;
        ResponseModel responseModel = new ResponseModel();

        try {
            customerTransactionRequest = customerTransactionRequestRepository
                    .findById(customerTransactionRequestId);
        } catch (Exception e) {
            throw  new Exception(e.getMessage());
        }
        if (!customerTransactionRequest.isPresent()) {
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        Optional<Branch> branchOptional;
        try {
            branchOptional = branchRepository.findById(cashWithdrawalDTO.getNdbBranchId());
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

        if (!branchOptional.isPresent()) {
            responseModel.setMessage("Invalid Bank Branch");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }

        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.WITHDRAWALS){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<CashWithdrawal> optionalCashWithdrawal;

        try {
            optionalCashWithdrawal = cashWithdrawalRepository.getFormFromCSR(customerTransactionRequestId);

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        if (!optionalCashWithdrawal.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        cashWithdrawal.setCustomerTransactionRequest(customerTransactionRequest.get());
        cashWithdrawal.setBranch(branchOptional.get());
        cashWithdrawal.setDate(cashWithdrawalDTO.getDate());
        cashWithdrawal.setAccountNo(cashWithdrawalDTO.getAccountNo());
        cashWithdrawal.setAccountHolder(cashWithdrawalDTO.getAccountHolder());
        cashWithdrawal.setCurrency(cashWithdrawalDTO.getCurrency());
        cashWithdrawal.setAmount(cashWithdrawalDTO.getAmount());
        cashWithdrawal.setAmountInWords(cashWithdrawalDTO.getAmountInWords());
        cashWithdrawal.setIdentity(cashWithdrawalDTO.getIdentity());
        cashWithdrawal.setCashWithdrawalId(optionalCashWithdrawal.get().getCashWithdrawalId());

        CashWithdrawalUpdateRecords cashWithdrawalUpdateRecords = new CashWithdrawalUpdateRecords();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = detailsUpdateDTO.getFile().getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/cash_withdrawals/signatures/update_record_verifications/" + customerTransactionRequestId );
        String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(detailsUpdateDTO.getFile(),location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage(" Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            cashWithdrawalUpdateRecords.setSignatureUrl(location);
            cashWithdrawalUpdateRecords.setComment(detailsUpdateDTO.getComment());
            cashWithdrawalUpdateRecords.setCustomerTransactionRequest(customerTransactionRequest.get());

            cashWithdrawalUpdateRecords = cashWithdrawalUpdateRecordsRepository.save(cashWithdrawalUpdateRecords);

            List<CashWithDrawalRecordErrors> listofcashWithDrawalRecordErrors = new ArrayList<>();
            listofcashWithDrawalRecordErrors = getAllCashwithDrawalRecordErrors(optionalCashWithdrawal.get(), cashWithdrawal,cashWithdrawalUpdateRecords);

            cashWithdrawalUpdateRecords.setCashWithDrawalRecordErrors(listofcashWithDrawalRecordErrors);

            cashWithdrawalUpdateRecords = cashWithdrawalUpdateRecordsRepository.save(cashWithdrawalUpdateRecords);

            if(cashWithdrawalUpdateRecords == null){
                responseModel.setMessage("Something went wrong with the database connection");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            } else {
                try {
                    cashWithdrawalRepository.save(cashWithdrawal);
                    responseModel.setMessage("Cash withdrawal updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
    }

    @Override
    public ResponseEntity<?> saveTrasnsactionSignature(TransactionSignatureDTO signatureDTO) throws Exception {
        try {
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository.findById(signatureDTO.getCustomerTransactionId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.WITHDRAWALS){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        Optional<CashWithdrawal> optional=cashWithdrawalRepository.getFormFromCSR(signatureDTO.getCustomerTransactionId());
        if(!optional.isPresent()){
            responseModel.setMessage("Invalied customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>("There is no data present for that id", HttpStatus.NO_CONTENT);
        } else {
            String location = ("/cash_withdrawals/signatures/" + signatureDTO.getCustomerTransactionId());
            String url = fileStorage.fileSave(signatureDTO.getFile(),location);
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                CashWithdrawal cashWithdrawal = optional.get();
                cashWithdrawal.setSignatureUrl(url);
                if(cashWithdrawalRepository.save(cashWithdrawal) != null){
                    responseModel.setMessage("Signature added successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel, HttpStatus.CREATED);
                } else {
                    responseModel.setMessage("Something went wrong with the db connection");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
                }
            }
        }
    }


    @Override
    public ResponseEntity<?> uploadFilesToCashWithdrawls(FileDTO fileDTO) throws Exception {
        try {
            Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                    .findById(fileDTO.getCustomerTransactionRequestId());
            if (!customerTransactionRequest.isPresent()) {
                responseModel.setMessage("Invalid Transaction Request");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            }

            int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
            if(id != TransactionIdConfig.WITHDRAWALS){
                responseModel.setMessage("Invalid Transaction Request Id");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
        Optional<CashWithdrawal> optional=cashWithdrawalRepository.getFormFromCSR(fileDTO
                .getCustomerTransactionRequestId());
        if(!optional.isPresent()){
            responseModel.setMessage("Invalid customer transaction id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
        } else {
            String location =  ("/cash_withdrawals/file_uploads/" + fileDTO.getCustomerTransactionRequestId()
                    +"/Customer Files");
            String url = fileStorage.fileSave(fileDTO.getFile(),location);
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload File");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                CashWithdrawalFile cashWithdrawalFile = new CashWithdrawalFile();
                cashWithdrawalFile.setCashWithdrawal(optional.get());
                cashWithdrawalFile.setFileType(fileDTO.getFileType());
                cashWithdrawalFile.setFileUrl(url);

                try {
                    cashWithdrawalFile = cashWithdrawalFileRepositiry.save(cashWithdrawalFile);
                } catch (Exception e){
                    throw new Exception(e.getMessage());
                }
                if(cashWithdrawalFile == null){
                    responseModel.setMessage("Something went Wrong");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
                } else {
                    CashWithdrawal cashWithdrawal = optional.get();
                    List<CashWithdrawalFile> cashWithdrawalFiles = optional.get().getCashWithdrawalFile();
                    if(cashWithdrawalFiles.isEmpty()){
                        cashWithdrawalFiles = new ArrayList<>();
                        cashWithdrawalFiles.add(cashWithdrawalFile);
                    } else {
                        cashWithdrawalFiles.add(cashWithdrawalFile);
                    }
                    cashWithdrawal.setCashWithdrawalFile(cashWithdrawalFiles);
                    try{
                        cashWithdrawalRepository.save(cashWithdrawal);
                        responseModel.setMessage("Withdrawal Files uploded successfully");
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
    public ResponseEntity<?> getCashWithdrawalUpdateRecords(int requestId){
       List<CashWithdrawalUpdateRecords> cashWithdrawalUpdateRecords =cashWithdrawalUpdateRecordsRepository
               .getAllFormFromCSR(requestId);

       if(cashWithdrawalUpdateRecords.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } else {

           List<UpdateRecordsCashWithdrawal> updateRecordsCashWithdrawallist = new ArrayList<>();

           for(CashWithdrawalUpdateRecords record : cashWithdrawalUpdateRecords) {

               UpdateRecordsCashWithdrawal updateRecordsCashWithdrawal = new UpdateRecordsCashWithdrawal();
               updateRecordsCashWithdrawal.setCashWithdrawalUpdateRecordsId(record.getCashWithdrawalUpdateRecordsId());
               updateRecordsCashWithdrawal.setSignatureUrl(record.getSignatureUrl());
               updateRecordsCashWithdrawal.setComment(record.getComment());
               updateRecordsCashWithdrawal.setCustomerTransactionRequest(record.getCustomerTransactionRequest());

               List<UpdateRecordsListDTO> updateRecordsListDTOS = new ArrayList<>();

               for (CashWithDrawalRecordErrors recordErrors : record.getCashWithDrawalRecordErrors()) {
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

               updateRecordsCashWithdrawal.setCashWithDrawalRecordErrors(updateRecordsListDTOS);
               updateRecordsCashWithdrawallist.add(updateRecordsCashWithdrawal);
           }
           return new ResponseEntity<>(updateRecordsCashWithdrawallist,HttpStatus.OK);
       }

    }

    private List<CashWithDrawalRecordErrors> getAllCashwithDrawalRecordErrors(CashWithdrawal cashWithdrawalOld , CashWithdrawal cashWithdrawalNew, CashWithdrawalUpdateRecords cashWithdrawalUpdateRecords ) throws ParseException {


        List<CashWithDrawalRecordErrors> listofcashWithDrawalRecordErrors = new ArrayList<>();
        CashWithDrawalRecordErrors cashWithDrawalRecordErrors;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(cashWithdrawalNew.getDate());
        Date dates= sdf.parse(strdate);

        if(cashWithdrawalOld.getDate().compareTo(dates)==0) {
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("date:"+cashWithdrawalOld.getDate());
            cashWithDrawalRecordErrors.setNewValue("date:"+cashWithdrawalNew.getDate());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(cashWithdrawalOld.getAccountNo() != cashWithdrawalNew.getAccountNo()) {
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("accountNo:"+cashWithdrawalOld.getAccountNo());
            cashWithDrawalRecordErrors.setNewValue("accountNo:"+cashWithdrawalNew.getAccountNo());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(!cashWithdrawalOld.getAccountHolder().equals(cashWithdrawalNew.getAccountHolder())){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("accountHolder:"+cashWithdrawalOld.getAccountHolder());
            cashWithDrawalRecordErrors.setNewValue("accountHolder:"+cashWithdrawalNew.getAccountHolder());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(!cashWithdrawalOld.getCurrency().equals(cashWithdrawalNew.getCurrency())){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("currency:"+cashWithdrawalOld.getCurrency());
            cashWithDrawalRecordErrors.setNewValue("currency:"+cashWithdrawalNew.getCurrency());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(cashWithdrawalOld.getAmount() != cashWithdrawalNew.getAmount()){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("amount:"+cashWithdrawalOld.getAmount());
            cashWithDrawalRecordErrors.setNewValue("amount:"+cashWithdrawalNew.getAmount());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);

        }
        if(!cashWithdrawalOld.getAmountInWords().equals(cashWithdrawalNew.getAmountInWords())){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();

            String var = "{\"amountInWords\":\""+cashWithdrawalNew.getAmountInWords()+"\"}";
            cashWithDrawalRecordErrors.setOldValue("amountInWords:"+cashWithdrawalOld.getAmountInWords());
            cashWithDrawalRecordErrors.setNewValue("amountInWords:"+cashWithdrawalNew.getAmountInWords());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(!cashWithdrawalOld.getIdentity().equals(cashWithdrawalNew.getIdentity())){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();
            cashWithDrawalRecordErrors.setOldValue("identity:"+cashWithdrawalOld.getIdentity());
            cashWithDrawalRecordErrors.setNewValue("identity:"+cashWithdrawalNew.getIdentity());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        if(cashWithdrawalOld.getBranch().getBranch_id() != cashWithdrawalNew.getBranch().getBranch_id()){
            cashWithDrawalRecordErrors = new CashWithDrawalRecordErrors();

            cashWithDrawalRecordErrors.setOldValue("ndbBranch:"+cashWithdrawalOld.getBranch().getMx_branch_name());
            cashWithDrawalRecordErrors.setNewValue("ndbBranch:"+cashWithdrawalNew.getBranch().getMx_branch_name());
            cashWithDrawalRecordErrors.setCashWithdrawalUpdateRecords(cashWithdrawalUpdateRecords);
            cashWithDrawalRecordErrors = cashWithDrawalRecordErrorsRepository.save(cashWithDrawalRecordErrors);
            listofcashWithDrawalRecordErrors.add(cashWithDrawalRecordErrors);
        }
        return listofcashWithDrawalRecordErrors;
    }
}

package com.spring.starter.service.impl;

import com.spring.starter.DTO.*;
import com.spring.starter.Exception.CustomException;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.service.CreditCardPeymentService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private CreditCardPaymentFilesRepository creditCardPaymentFilesRepository;
    @Autowired
    private CreditCardPaymentUpdateRecordRepository creditCardPaymentUpdateRecordRepository;
    @Autowired
    private CreditCardPaymentErrorRecordsRepository creditCardPaymentErrorRecordsRepository;
    private ResponseModel responseModel = new ResponseModel();


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
                creditCardPaymentBreakDown.setDate(java.util.Calendar.getInstance().getTime());
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

    @Override
    public ResponseEntity<?> addSignatureForCardPayment(MultipartFile multipartFile, int customerTransactionRequestId) {

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerTransactionRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.CREDIT_CARD_PEYMENT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<CrediitCardPeyment> optional = creditCardPeymentRepository.getFormFromCSR(customerTransactionRequestId);
        if(!optional.isPresent()){
            responseModel.setMessage("There is no record in the db  for update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        } else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = multipartFile.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/Credit Card Payment/Signatures/" + customerTransactionRequestId);
            String filename = customerTransactionRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(multipartFile,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed ")) {
                responseModel.setMessage(" Failed To Upload Signature");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {
                CrediitCardPeyment crediitCardPeyment = optional.get();
                crediitCardPeyment.setUrl(location);

                try{
                    creditCardPeymentRepository.save(crediitCardPeyment);
                    responseModel.setMessage("Credit Card Payment saved successfully");
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
    public ResponseEntity<?> addFileToSLIPCardPayment(MultipartFile file, String fileType, int customerServiceRequestId) {

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(customerServiceRequestId);
        if(!customerTransactionRequest.isPresent()){
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.CREDIT_CARD_PEYMENT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }
        Optional<CrediitCardPeyment> optional = creditCardPeymentRepository.getFormFromCSR(customerServiceRequestId);
        if(!optional.isPresent()){
            responseModel.setMessage("There is no record to upload file");
        }   else {
            UUID uuid = UUID.randomUUID();
            String randomUUIDString = uuid.toString();

            String extention = file.getOriginalFilename();
            extention = FilenameUtils.getExtension(extention);

            String location =  ("/Credit Card Payment/file_uploads/" + customerServiceRequestId);
            String filename = customerServiceRequestId + "_uuid-"+ randomUUIDString+"."+extention;
            String url = fileStorage.fileSaveWithRenaming(file,location,filename);
            location = location+"/"+filename;
            if(url.equals("Failed")) {
                responseModel.setMessage(" Failed To Upload File");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
            } else {

                CreditCardPaymentFiles creditCardPaymentFiles = new CreditCardPaymentFiles();
                creditCardPaymentFiles.setFileUrl(location);
                creditCardPaymentFiles.setFileType(fileType);
                creditCardPaymentFiles.setCrediitCardPeyment(optional.get());

                try {
                    creditCardPaymentFiles = creditCardPaymentFilesRepository.save(creditCardPaymentFiles);
                } catch (Exception e){
                    throw new CustomException(e.getMessage());
                }

                List<CreditCardPaymentFiles> cardPaymentFiles;
                if(optional.get().getCreditCardPaymentFiles().isEmpty()){
                    cardPaymentFiles = new ArrayList<>();
                } else {
                    cardPaymentFiles = optional.get().getCreditCardPaymentFiles();
                }

                cardPaymentFiles.add(creditCardPaymentFiles);
                CrediitCardPeyment crediitCardPeyment = optional.get();
                crediitCardPeyment.setCreditCardPaymentFiles(cardPaymentFiles);

                try {
                    creditCardPeymentRepository.save(crediitCardPeyment);
                    responseModel.setMessage("file uploaded successfully");
                    responseModel.setStatus(false);
                    return new ResponseEntity<>(responseModel,HttpStatus.OK);
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }

            }

        }
        return  null;
    }

    @Override
    public ResponseEntity<?> updateCardPayment(CrediitCardPeyment crediitCardPeyment, int customerTransactionRequestId, DetailsUpdateDTO detailsUpdateDTO){

        Optional<CustomerTransactionRequest> customerTransactionRequest;

        try {
            customerTransactionRequest = customerTransactionRequestRepository.findById(customerTransactionRequestId);
        } catch (Exception e) {
            throw  new CustomException(e.getMessage());
        }
        if (!customerTransactionRequest.isPresent()) {
            responseModel.setMessage("Invalid Transaction Request");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        }
        int id = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();
        if(id != TransactionIdConfig.CREDIT_CARD_PEYMENT){
            responseModel.setMessage("Invalid Transaction Request Id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.BAD_REQUEST);
        }

        Optional<CrediitCardPeyment> optionalCrediitCardPeyment;

        try {
            optionalCrediitCardPeyment= creditCardPeymentRepository.getFormFromCSR(customerTransactionRequestId);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }

        if (!optionalCrediitCardPeyment.isPresent()){
            responseModel.setMessage("There is no record to update");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.NO_CONTENT);
        }

        crediitCardPeyment.setCreditCardPaymentFiles(optionalCrediitCardPeyment.get().getCreditCardPaymentFiles());
        crediitCardPeyment.setCustomerTransactionRequest(customerTransactionRequest.get());
        //
        crediitCardPeyment.setCrediitCardPeymentId(optionalCrediitCardPeyment.get().getCrediitCardPeymentId());
        CreditCardPaymentUpdateRecord creditCardPaymentUpdateRecord= new CreditCardPaymentUpdateRecord();

        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        String extention = detailsUpdateDTO.getFile().getOriginalFilename();
        extention = FilenameUtils.getExtension(extention);

        String location =  ("/Credit Card Payment/signatures/update_record_verifications/" + customerTransactionRequestId );
        String filename = ""+customerTransactionRequestId + "_uuid-"+ randomUUIDString+extention;
        String url = fileStorage.fileSaveWithRenaming(detailsUpdateDTO.getFile(),location,filename);
        location = ""+location+"/"+filename;
        if(url.equals("Failed")) {
            responseModel.setMessage("Failed To Upload Signature");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel, HttpStatus.BAD_REQUEST);
        } else {

            creditCardPaymentUpdateRecord.setSignatureUrl(location);
            creditCardPaymentUpdateRecord.setComment(detailsUpdateDTO.getComment());
            creditCardPaymentUpdateRecord.setCustomerTransactionRequest(customerTransactionRequest.get());

            creditCardPaymentUpdateRecord=creditCardPaymentUpdateRecordRepository.save(creditCardPaymentUpdateRecord);

            List<CreditCardPaymentErrorRecords> creditCardPaymentErrorRecords= new ArrayList<>();
            creditCardPaymentErrorRecords=getAllRecordErrors(optionalCrediitCardPeyment.get(),crediitCardPeyment,creditCardPaymentUpdateRecord);

            creditCardPaymentUpdateRecord.setCreditCardPaymentErrorRecords(creditCardPaymentErrorRecords);

            creditCardPaymentUpdateRecord=creditCardPaymentUpdateRecordRepository.save(creditCardPaymentUpdateRecord);

            if (creditCardPaymentUpdateRecord==null){
                responseModel.setMessage("Something went wrong with the database connection ");
                responseModel.setStatus(false);
                return new ResponseEntity<>(responseModel, HttpStatus.SERVICE_UNAVAILABLE);
            }else{
                try {
                    creditCardPeymentRepository.save(crediitCardPeyment);
                    responseModel.setMessage("Credit Card Payment updated successfully");
                    responseModel.setStatus(true);
                    return new ResponseEntity<>(responseModel,HttpStatus.CREATED);
                } catch (Exception e) {
                    throw new CustomException(e.getMessage());
                }
            }

        }

    }

    @Override
    public ResponseEntity<?> getCardPaymentUpdateRecords(int requestId) {

        List<CreditCardPaymentUpdateRecord> creditList =creditCardPaymentUpdateRecordRepository
                .getAllFormFromCSR(requestId);

        if(creditList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            List<CreditPaymentUpdateDTO> creditPaymentUpdateDTOS = new ArrayList<>();

            for(CreditCardPaymentUpdateRecord record : creditList) {

                CreditPaymentUpdateDTO updateRecordsDTO = new CreditPaymentUpdateDTO();
                updateRecordsDTO.setCreditPaymentUpdateRecordsId(record.getCreditCardPaymentUpdateRecordId());
                updateRecordsDTO.setSignatureUrl(record.getSignatureUrl());
                updateRecordsDTO.setComment(record.getComment());
                updateRecordsDTO.setCustomerTransactionRequest(record.getCustomerTransactionRequest());

                List<UpdateRecordsListDTO> updateRecordsListDTOS = new ArrayList<>();

                for (CreditCardPaymentErrorRecords recordErrors : record.getCreditCardPaymentErrorRecords()) {

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
                creditPaymentUpdateDTOS.add(updateRecordsDTO);
            }
            return new ResponseEntity<>(creditPaymentUpdateDTOS, HttpStatus.OK);
        }

    }

    private List<CreditCardPaymentErrorRecords> getAllRecordErrors(CrediitCardPeyment crediitCardPeymentOld,CrediitCardPeyment crediitCardPeymentNew,CreditCardPaymentUpdateRecord creditCardPaymentUpdateRecord){

        List<CreditCardPaymentErrorRecords> records= new ArrayList<>();
        CreditCardPaymentErrorRecords creditCardPaymentErrorRecords;

        if(!crediitCardPeymentOld.getTelephoneNumber().equals(crediitCardPeymentNew.getTelephoneNumber())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" TelephoneNumber :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setNewValue(" TelephoneNumber :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }
        if(!crediitCardPeymentOld.getPaymenntMethod().equals(crediitCardPeymentNew.getPaymenntMethod())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" Payment Method :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setNewValue(" Payment Method :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(!crediitCardPeymentOld.getChequenumber().equals(crediitCardPeymentNew.getChequenumber())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" Cheque Number :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setNewValue(" Cheque Number :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getAmmount()!=(crediitCardPeymentNew.getAmmount())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" Amount :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setNewValue(" Amount :"+crediitCardPeymentOld.getTelephoneNumber()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf5000Notes()!=(crediitCardPeymentNew.getValueOf5000Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 5000 Notes :"+crediitCardPeymentOld.getValueOf5000Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 5000 Notes :"+crediitCardPeymentOld.getValueOf5000Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf2000Notes()!=(crediitCardPeymentNew.getValueOf2000Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 2000 Notes :"+crediitCardPeymentOld.getValueOf2000Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 2000 Notes :"+crediitCardPeymentOld.getValueOf2000Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueof1000Notes()!=(crediitCardPeymentNew.getValueof1000Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 1000 Notes :"+crediitCardPeymentOld.getValueof1000Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 1000 Notes :"+crediitCardPeymentOld.getValueof1000Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf500Notes()!=(crediitCardPeymentNew.getValueOf500Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 500 Notes :"+crediitCardPeymentOld.getValueOf500Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 500 Notes :"+crediitCardPeymentOld.getValueOf500Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf100Notes()!=(crediitCardPeymentNew.getValueOf100Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 100 Notes :"+crediitCardPeymentOld.getValueOf100Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 100 Notes :"+crediitCardPeymentOld.getValueOf100Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf50Notes()!=(crediitCardPeymentNew.getValueOf50Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 50 Notes :"+crediitCardPeymentOld.getValueOf50Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 50 Notes :"+crediitCardPeymentOld.getValueOf50Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf20Notes()!=(crediitCardPeymentNew.getValueOf20Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 20 Notes :"+crediitCardPeymentOld.getValueOf20Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 20 Notes :"+crediitCardPeymentOld.getValueOf20Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOf10Notes()!=(crediitCardPeymentNew.getValueOf10Notes())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" 10 Notes :"+crediitCardPeymentOld.getValueOf10Notes()+"");
            creditCardPaymentErrorRecords.setNewValue(" 10 Notes :"+crediitCardPeymentOld.getValueOf10Notes()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getValueOfcoins()!=(crediitCardPeymentNew.getValueOfcoins())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" Coins :"+crediitCardPeymentOld.getValueOfcoins()+"");
            creditCardPaymentErrorRecords.setNewValue(" Coins :"+crediitCardPeymentOld.getValueOfcoins()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }

        if(crediitCardPeymentOld.getTotal()!=(crediitCardPeymentNew.getTotal())){
            creditCardPaymentErrorRecords = new CreditCardPaymentErrorRecords();
            creditCardPaymentErrorRecords.setOldValue(" Total :"+crediitCardPeymentOld.getTotal()+"");
            creditCardPaymentErrorRecords.setNewValue(" Total :"+crediitCardPeymentOld.getTotal()+"");
            creditCardPaymentErrorRecords.setCreditCardPaymentUpdateRecord(creditCardPaymentUpdateRecord);
            creditCardPaymentErrorRecords = creditCardPaymentErrorRecordsRepository.save(creditCardPaymentErrorRecords);
            records.add(creditCardPaymentErrorRecords);
        }



        return records;

    }

}

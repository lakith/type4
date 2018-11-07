package com.spring.starter.service.impl;

import com.spring.starter.DTO.CustomerFilesDTO;
import com.spring.starter.DTO.DailyPdfReportDTO;
import com.spring.starter.DTO.TifGenarateDTO;
import com.spring.starter.Repository.*;
import com.spring.starter.configuration.TransactionIdConfig;
import com.spring.starter.model.*;
import com.spring.starter.pdfGenerator.DailyReport;
import com.spring.starter.pdfGenerator.TifReports;
import com.spring.starter.service.PdfService;
import com.spring.starter.util.FileStorage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class PdfServiceImpl implements PdfService {


    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private CSRQueueRepository csrQueueRepository;
    @Autowired
    private CashdepositRepositiry cashdepositRepositiry;
    @Autowired
    private CashWithdrawalRepository cashWithdrawalRepository;
    @Autowired
    private BillPaymentRepository billPaymentRepository;
    @Autowired
    private FundTransferSLIPRepository fundTransferSLIPRepository;
    @Autowired
    private FundTransferCEFTRepository fundTransferCEFTRepository;
    @Autowired
    private FundTransferWithinNDBRepository fundTransferWithinNDBRepository;
    @Autowired
    private CreditCardPeymentRepository creditCardPeymentRepository;

    @Override
    public ResponseEntity<?> dailyReport() {

        Date date=java.util.Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(date);
        try {
            Date dates= sdf.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<DailyPdfReportDTO> dtoList=new ArrayList<>();

        List<CustomerTransactionRequest> list=customerTransactionRequestRepository.getAllTransactionCustomerRequestsByDate(date);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{

            for (CustomerTransactionRequest request : list){

                DailyPdfReportDTO dto=new DailyPdfReportDTO();

                dto.setCustomerTransactionRequestId(request.getCustomerTransactionRequestId());
                dto.setDigFormType(request.getTransactionRequest().getDigiFormType());
                dto.setIdentification(request.getTransactionCustomer().getIdentification());
                dto.setRequestCompleteDate(request.getRequestCompleteDate());
                if (request.getStaffUser().isEmpty()){
                    continue;
                }else{
                    int size=request.getStaffUser().size();
                    dto.setEpfNumber(request.getStaffUser().get(size-1).getEpfNumber());
                    dto.setStaffName(request.getStaffUser().get(size-1).getName());
                }
                dto.setQueueNumber(csrQueueRepository.getCSRQueueNumberByCustomerId(request.getCustomerTransactionRequestId()));

                dtoList.add(dto);

            }

            ByteArrayInputStream bis = DailyReport.report(dtoList);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=DailyReportsOF"+strdate+".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

//            try {
//
//                System.out.println(IOUtils.copy(bis, new FileOutputStream("File Storage/Daily Report OF.pdf")));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        }


    }

    @Override
    public ResponseEntity<?> tifGenarate() {

        int reportNo=1;

        Date date=java.util.Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strdate = sdf.format(date);
        try {
            Date dates= sdf.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<CustomerTransactionRequest> list=customerTransactionRequestRepository.getAllTransactionCustomerRequestsByDateAndStatus(date);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{

            for (CustomerTransactionRequest request : list){

                TifGenarateDTO dto  = new TifGenarateDTO();

                dto.setCustomerTransactionRequestId(request.getCustomerTransactionRequestId());
                dto.setTransactionRequestName(request.getTransactionRequest().getDigiFormType());
                dto.setIdentification(request.getTransactionCustomer().getIdentification());

                String url=getURL(request.getCustomerTransactionRequestId());
                if (url.matches("null")){
                    continue;
                }

                dto.setSignatureURL(url);
                if (request.getStaffUser().isEmpty()){
                    continue;
                }else{
                    int size=request.getStaffUser().size();
                    dto.setEpfNumber(request.getStaffUser().get(size-1).getEpfNumber());
                    dto.setName(request.getStaffUser().get(size-1).getName());
                }

                dto.setList(getList(request.getCustomerTransactionRequestId()));

                ByteArrayInputStream bis = TifReports.report(dto);

                try {

                    IOUtils.copy(bis, new FileOutputStream("File Storage/"+strdate+"/Tif Report "+reportNo+".pdf"));
                    reportNo++;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return new ResponseEntity<>(HttpStatus.OK);

        }

    }

    private String getURL(int transactionCustomerRequest){

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(transactionCustomerRequest);

        if(!customerTransactionRequest.isPresent()){
            return "null";
        }

        int transactionRequrstId = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();

        if(transactionRequrstId == TransactionIdConfig.DEPOSITS) {
            Optional<CashDeposit> cashDeposit = cashdepositRepositiry.getFormFromCSR(transactionCustomerRequest);
            if(cashDeposit.isPresent()){
                return cashDeposit.get().getSignatureUrl();
            }

        } else if (transactionRequrstId == TransactionIdConfig.WITHDRAWALS){
            Optional<CashWithdrawal> cashWithdrawalOpt = cashWithdrawalRepository.getFormFromCSR(transactionCustomerRequest);
            if(cashWithdrawalOpt.isPresent()){
                return cashWithdrawalOpt.get().getSignatureUrl();
            }

        } else if (transactionRequrstId == TransactionIdConfig.BILLPAYMENT){
            Optional<BillPayment> billPaymentOpt = billPaymentRepository.getFormFromCSR(transactionCustomerRequest);
            if(billPaymentOpt.isPresent()){
                return billPaymentOpt.get().getSignatureUrl();
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
            Optional<FundTransferWithinNDB> fundTransferWithinNDBOpt = fundTransferWithinNDBRepository.getFormFromCSR
                    (transactionCustomerRequest);
            if(fundTransferWithinNDBOpt.isPresent()){
                return fundTransferWithinNDBOpt.get().getSignatureUrl();
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT) {
            Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(transactionRequrstId);
            if(optionalTransferCEFT.isPresent()){
                return optionalTransferCEFT.get().getUrl();
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(transactionRequrstId);
            if(optionalTransferSLIPS.isPresent()){
                return optionalTransferSLIPS.get().getUrl();
            }

        } else if(transactionRequrstId == TransactionIdConfig.CREDIT_CARD_PEYMENT){
            Optional<CrediitCardPeyment> crediitCardPeymentOptional = creditCardPeymentRepository.getFormFromCSR(transactionRequrstId);
            if(crediitCardPeymentOptional.isPresent()){
                return crediitCardPeymentOptional.get().getSignatureUrl();
            }

        } else {
            return "null";
        }

        return "null";
    }

    private List<CustomerFilesDTO> getList(int transactionCustomerRequest){

        List<CustomerFilesDTO> filesDTOS=new ArrayList<>();

        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerTransactionRequest> customerTransactionRequest = customerTransactionRequestRepository
                .findById(transactionCustomerRequest);

        if(!customerTransactionRequest.isPresent()){
            return filesDTOS;
        }

        int transactionRequrstId = customerTransactionRequest.get().getTransactionRequest().getDigiFormId();

        if(transactionRequrstId == TransactionIdConfig.DEPOSITS) {
            Optional<CashDeposit> cashDeposit = cashdepositRepositiry.getFormFromCSR(transactionCustomerRequest);
            if(cashDeposit.isPresent()){
                List<CashDepositFile> files = cashDeposit.get().getCashDepositFiles();
                if (!files.isEmpty()){
                    for (CashDepositFile file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }

        } else if (transactionRequrstId == TransactionIdConfig.WITHDRAWALS){
            Optional<CashWithdrawal> cashWithdrawalOpt = cashWithdrawalRepository.getFormFromCSR(transactionCustomerRequest);
            if(cashWithdrawalOpt.isPresent()){
                List<CashWithdrawalFile> files = cashWithdrawalOpt.get().getCashWithdrawalFile();
                if (!files.isEmpty()){
                    for (CashWithdrawalFile file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }

        } else if (transactionRequrstId == TransactionIdConfig.BILLPAYMENT){

            return null;

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_WITHIN_NDB) {
            Optional<FundTransferWithinNDB> fundTransferWithinNDBOpt = fundTransferWithinNDBRepository.getFormFromCSR
                    (transactionCustomerRequest);
            if(fundTransferWithinNDBOpt.isPresent()){
                List<FundTransferWithinNDBFile> files = fundTransferWithinNDBOpt.get().getFundTransferWithinNDBFiles();
                if (!files.isEmpty()){
                    for (FundTransferWithinNDBFile file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_CEFT) {
            Optional<FundTransferCEFT> optionalTransferCEFT = fundTransferCEFTRepository.getFormFromCSR(transactionRequrstId);
            if(optionalTransferCEFT.isPresent()){
                List<FundTransferCEFTFiles> files = optionalTransferCEFT.get().getFundTransferCEFTFiles();
                if (!files.isEmpty()){
                    for (FundTransferCEFTFiles file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }

        } else if(transactionRequrstId == TransactionIdConfig.FUND_TRANSFER_TO_OTHER_BANKS_SLIP){
            Optional<FundTransferSLIPS> optionalTransferSLIPS = fundTransferSLIPRepository.getFormFromCSR(transactionRequrstId);
            if(optionalTransferSLIPS.isPresent()){
                List<FundTransferSLIPSFiles> files = optionalTransferSLIPS.get().getFundTransferSLIPSIds();
                if (!files.isEmpty()){
                    for (FundTransferSLIPSFiles file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }


        } else if(transactionRequrstId == TransactionIdConfig.CREDIT_CARD_PEYMENT){
            Optional<CrediitCardPeyment> crediitCardPeymentOptional = creditCardPeymentRepository.getFormFromCSR(transactionRequrstId);
            if(crediitCardPeymentOptional.isPresent()){
                List<CreditCardPaymentFiles> files = crediitCardPeymentOptional.get().getCreditCardPaymentFiles();
                if (!files.isEmpty()){
                    for (CreditCardPaymentFiles file: files){
                        CustomerFilesDTO dto= new CustomerFilesDTO(file.getFileUrl(),file.getFileType());
                        filesDTOS.add(dto);
                    }
                }
            }

        } else {
            return filesDTOS;
        }

        return filesDTOS;
    }
}

package com.spring.starter.service.impl;

import com.spring.starter.DTO.DailyPdfReportDTO;
import com.spring.starter.Repository.CSRQueueRepository;
import com.spring.starter.Repository.CustomerTransactionRequestRepository;
import com.spring.starter.model.CustomerTransactionRequest;
import com.spring.starter.pdfGenerator.DailyReport;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class PdfServiceImpl implements PdfService {


    @Autowired
    private CustomerTransactionRequestRepository customerTransactionRequestRepository;
    @Autowired
    private CSRQueueRepository csrQueueRepository;
    @Autowired
    private FileStorage fileStorage;

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

        if (!list.isEmpty()) {

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

            HttpHeaders headers= new HttpHeaders();
            headers.add("Content-Disposition","inline; filename=DailyReportOf"+strdate+".pdf");

            return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bis));

//            try {
//
//                System.out.println(IOUtils.copy(bis, new FileOutputStream("File Storage/Daily Report OF.pdf")));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}

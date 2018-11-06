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
    public void dailyReport() {

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

        }else{

            for (CustomerTransactionRequest request : list){

                DailyPdfReportDTO dto=new DailyPdfReportDTO();

                dto.setCustomerTransactionRequestId(request.getCustomerTransactionRequestId());
                dto.setDigFormType(request.getTransactionRequest().getDigiFormType());
                dto.setIdentification(request.getTransactionCustomer().getIdentification());
                dto.setRequestCompleteDate(request.getRequestCompleteDate());
                if(request.getAuthorizerDataTransaction().getStaffUser() == null){
                    continue;
                }
                dto.setEpfNumber(request.getAuthorizerDataTransaction().getStaffUser().getEpfNumber());
                dto.setStaffName(request.getAuthorizerDataTransaction().getStaffUser().getName());
                dto.setQueueNumber(csrQueueRepository.getCSRQueueNumberByCustomerId(request.getCustomerTransactionRequestId()));

                dtoList.add(dto);

            }

            ByteArrayInputStream bis = DailyReport.report(dtoList);

            try {
                IOUtils.copy(bis, new FileOutputStream("Daily Report OF "+date+""));

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}

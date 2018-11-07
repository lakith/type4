package com.spring.starter.controller;

import com.spring.starter.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/daily-report")
    public ResponseEntity<?> dailyReport() {
        return pdfService.dailyReport();
    }

}

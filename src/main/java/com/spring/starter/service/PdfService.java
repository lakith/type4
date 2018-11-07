package com.spring.starter.service;

import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface PdfService {

    public ResponseEntity<?> dailyReport();

}

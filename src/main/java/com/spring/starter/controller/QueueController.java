package com.spring.starter.controller;

import com.spring.starter.components.SheduleMethods;
import com.spring.starter.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/queue")
@CrossOrigin
public class QueueController {

	@Autowired
	SheduleMethods sheduleMethods;

	@GetMapping("/queue-reset")
	public ResponseEntity<?> QueueReset(){
		ResponseModel responseModel = new ResponseModel();
/*		sheduleMethods.migrateAndDeleteDataCSR();
		sheduleMethods.migrateAndDeleteDataTeller();*/
		responseModel.setStatus(true);
		responseModel.setMessage("Queue Reset Successfully");
		return new ResponseEntity<>(responseModel,HttpStatus.OK);
	}

}

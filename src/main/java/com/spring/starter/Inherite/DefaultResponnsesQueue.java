package com.spring.starter.Inherite;

import com.spring.starter.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultResponnsesQueue {

    protected ResponseEntity<?> returnResponse(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("There is no that kinda queue number");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    protected ResponseEntity<?> returnResponseHold(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete a holded queue number ");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    protected ResponseEntity<?> returnResponseComplete(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete an already completed request");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

    protected ResponseEntity<?> returnResponseReject(){
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("You cant complete a rejected a queue number");
        responseModel.setStatus(false);
        return new ResponseEntity<>(responseModel,HttpStatus.OK);
    }

}

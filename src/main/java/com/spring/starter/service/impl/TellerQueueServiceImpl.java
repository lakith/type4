package com.spring.starter.service.impl;

import com.spring.starter.DTO.CSRDisplayDTO;
import com.spring.starter.DTO.TellerDisplayDTO;
import com.spring.starter.DTO.TellerQueueDTO;
import com.spring.starter.Inherite.DefaultResponnsesQueue;
import com.spring.starter.Repository.TellerQueueRepository;
import com.spring.starter.Repository.TransactionCustomerRepository;
import com.spring.starter.model.*;
import com.spring.starter.service.TellerQueueService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TellerQueueServiceImpl extends DefaultResponnsesQueue implements TellerQueueService {

    @Autowired
    private TellerQueueRepository tellerQueueRepository;

    @Autowired
    private TransactionCustomerRepository transactionCustomerRepository;


    @Override
    public ResponseEntity<?> genarateNormalQueuNumber(int customerTransactionRequestId){

        ResponseModel responseModel = new ResponseModel();

        Optional<TransactionCustomer> customerOptional = transactionCustomerRepository.findById(customerTransactionRequestId);

        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        Optional<TellerQueue> tellerQueueOptional = tellerQueueRepository.checkTokenStatus(customerOptional.get().getTransactionCustomerId());
        if(tellerQueueOptional.isPresent()){
            TellerQueueDTO tellerQueueDTO = new TellerQueueDTO();
            tellerQueueDTO.setMessage("You have already taken a token.");
            tellerQueueDTO.setTellerQueue(tellerQueueOptional.get());
            return new ResponseEntity<>(tellerQueueDTO,HttpStatus.OK);
        }

        int currentQueueNumber = tellerQueueRepository.getCurrentListCount();
        currentQueueNumber = currentQueueNumber + 1;

        TellerQueue tellerQueue = new TellerQueue();
        tellerQueue.setQueueNumber(Integer.toString(currentQueueNumber)+" - T");
        tellerQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueueNumIdentification(currentQueueNumber);
        tellerQueue.setTransactionCustomer(customerOptional.get());
        //tellerQueue.setKey("D_JCpUlso16sg2RZdXNAv9_IJqyEWu0z");

        try {
            tellerQueue = tellerQueueRepository.save(tellerQueue);
            TellerDisplayDTO tellerDisplayDTO = new TellerDisplayDTO();
            tellerDisplayDTO.setSuccess(true);
            tellerDisplayDTO.setMessage(tellerQueue.getQueueNumber());
            tellerDisplayDTO.setTellerQueueId(tellerQueue.getTelerQueueId());
            tellerDisplayDTO.setTransactionCustomer(tellerQueue.getTransactionCustomer());
            tellerDisplayDTO.setQueueNumber(tellerQueue.getQueueNumber());
            tellerDisplayDTO.setComplete(tellerQueue.isComplete());
            tellerDisplayDTO.setComment(tellerQueue.getComment());
            tellerDisplayDTO.setHold(tellerQueue.isHold());
            tellerDisplayDTO.setQueueReject(tellerQueue.isQueueReject());
            tellerDisplayDTO.setQueuePending(tellerQueue.isQueuePending());
            tellerDisplayDTO.setQueueStartDate(tellerQueue.getQueueStartDate());
            tellerDisplayDTO.setQueueEndDate(tellerQueue.getQueueEndDate());
            tellerDisplayDTO.setQueueNumIdentification(tellerQueue.getQueueNumIdentification());
            tellerDisplayDTO.setKey("D_JCpUlso16sg2RZdXNAv9_IJqyEWu0z");
            return new ResponseEntity<>(tellerDisplayDTO,HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnTellerQueueNumber(currentQueueNumber+1,customerOptional),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(returnTellerQueueNumber(currentQueueNumber+1,customerOptional),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getCSRQueueList()
    {
        ResponseModel responseModel = new ResponseModel();
        List<TellerQueue> tellerQueues = tellerQueueRepository.getAllQueueNumbers();
        if(tellerQueues.isEmpty()){
            responseModel.setMessage("There are no queue numbers issued yet.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tellerQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> holdAQueueNumber(int customerId){
        Optional<TellerQueue> tellerQueueOptional = tellerQueueRepository.getTellerQueueByCustomerId(customerId);
        if(!tellerQueueOptional.isPresent()){
            return returnResponse();
        }
        TellerQueue tellerQueue = tellerQueueOptional.get();
        tellerQueue.setHold(true);
        tellerQueue.setQueuePending(false);
        tellerQueue = tellerQueueRepository.save(tellerQueue);
        return new ResponseEntity<>(tellerQueue,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHoldQueueList(){
        ResponseModel responseModel = new ResponseModel();

        List<TellerQueue> tellerQueues = tellerQueueRepository.getholdQueue();
        if(tellerQueues.isEmpty()){
            responseModel.setMessage("There is no hold queue.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tellerQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getPendingQueue(){
        ResponseModel responseModel = new ResponseModel();

        List<TellerQueue> tellerQueues = tellerQueueRepository.getpendingQueue();
        if(tellerQueues.isEmpty()){
            responseModel.setMessage("There is no pending queue.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tellerQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> completedQueueList(){
        ResponseModel responseModel = new ResponseModel();
        List<TellerQueue> tellerQueues = tellerQueueRepository.getCompletedQueue();
        if(tellerQueues.isEmpty()){
            responseModel.setMessage("There is no complete queue.");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(tellerQueues,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> continueAHoldQueueNumber(int customerId) {

        Optional<TellerQueue> tellerQueueOptional = tellerQueueRepository.getTellerQueueByCustomerId(customerId);
        if(!tellerQueueOptional.isPresent()){
            return returnResponse();
        } else {
            TellerQueue tellerQueue = tellerQueueOptional.get();
            tellerQueue.setHold(false);
            tellerQueue.setQueuePending(true);

            tellerQueue =  tellerQueueRepository.save(tellerQueue);
            return new ResponseEntity<>(tellerQueue,HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> issueAPriorityToken(int customerTrasactionRequestId){
        ResponseModel responseModel = new ResponseModel();
        List<TellerQueue> tellerQueues = tellerQueueRepository.getpendingQueue();

        Optional<TransactionCustomer> optionalTransactionCustomer = transactionCustomerRepository.findById(customerTrasactionRequestId);

        if(!optionalTransactionCustomer.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        Optional<TellerQueue> tellerQueueOptional = tellerQueueRepository.checkTokenStatus(optionalTransactionCustomer.get().getTransactionCustomerId());
        if(tellerQueueOptional.isPresent()){
            TellerQueueDTO tellerQueueDTO = new TellerQueueDTO();
            tellerQueueDTO.setMessage("You have already taken  a token.");
            tellerQueueDTO.setTellerQueue(tellerQueueOptional.get());
            return new ResponseEntity<>(tellerQueueDTO,HttpStatus.OK);
        }

        double number = tellerQueues.get(1).getQueueNumIdentification();
        int decimal = (int) number;
        double fraction = number - decimal;
        if(fraction > 0.0){
            for(int q = 2; q < tellerQueues.size();q++){
                number = tellerQueues.get(q).getQueueNumIdentification();
                decimal = (int) number;
                fraction = number - decimal;
                double track = fraction;
                if(fraction == 0.0){
                    number = tellerQueues.get(q-1).getQueueNumIdentification();
                    decimal = (int) number;
                    fraction = number - decimal;
                    break;
                } else if (track == 0.19){
                    for(int a = q+1 ; a < tellerQueues.size();a++){
                        number = tellerQueues.get(q).getQueueNumIdentification();
                        decimal = (int) number;
                        fraction = number - decimal;
                        if(fraction == 0.0){
                            break;
                        }
                    }
                    break;
                }
            }
        }
        if(fraction == 0.0){
            fraction= 0.1;
            number = tellerQueues.get(0).getQueueNumIdentification()+fraction;
        } else {
            number = tellerQueues.get(0).getQueueNumIdentification()+fraction+0.01;
        }

        TellerQueue tellerQueue= new TellerQueue();
        tellerQueue.setQueueNumber(Double.toString(number)+" - T");
        tellerQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueueNumIdentification(number);
        tellerQueue.setTransactionCustomer(optionalTransactionCustomer.get());
        //tellerQueue.setKey("D_JCpUlso16sg2RZdXNAv9_IJqyEWu0z");

        try {
            tellerQueue = tellerQueueRepository.save(tellerQueue);
            TellerDisplayDTO tellerDisplayDTO = new TellerDisplayDTO();
            tellerDisplayDTO.setSuccess(true);
            tellerDisplayDTO.setMessage(tellerQueue.getQueueNumber());
            tellerDisplayDTO.setTellerQueueId(tellerQueue.getTelerQueueId());
            tellerDisplayDTO.setTransactionCustomer(tellerQueue.getTransactionCustomer());
            tellerDisplayDTO.setQueueNumber(tellerQueue.getQueueNumber());
            tellerDisplayDTO.setComplete(tellerQueue.isComplete());
            tellerDisplayDTO.setComment(tellerQueue.getComment());
            tellerDisplayDTO.setHold(tellerQueue.isHold());
            tellerDisplayDTO.setQueueReject(tellerQueue.isQueueReject());
            tellerDisplayDTO.setQueuePending(tellerQueue.isQueuePending());
            tellerDisplayDTO.setQueueStartDate(tellerQueue.getQueueStartDate());
            tellerDisplayDTO.setQueueEndDate(tellerQueue.getQueueEndDate());
            tellerDisplayDTO.setQueueNumIdentification(tellerQueue.getQueueNumIdentification());
            tellerDisplayDTO.setKey("D_JCpUlso16sg2RZdXNAv9_IJqyEWu0z");
            return new ResponseEntity<>(tellerDisplayDTO, HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnprorityTellerQueueNumber(number+0.01,optionalTransactionCustomer),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(returnprorityTellerQueueNumber(number+0.01,optionalTransactionCustomer),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> issueARedundentToken(int customerTransactionRequestId){
        ResponseModel responseModel = new ResponseModel();
        List<TellerQueue>  tellerQueues= tellerQueueRepository.getpendingQueue();

        Optional<TransactionCustomer> customerOptional = transactionCustomerRepository.findById(customerTransactionRequestId);
        if(!customerOptional.isPresent()){
            responseModel.setMessage("Invalid customer id");
            responseModel.setStatus(false);
            return new ResponseEntity<>(responseModel,HttpStatus.OK);
        }

        double number = tellerQueues.get(0).getQueueNumIdentification();
        number = number+0.2;

        TellerQueue tellerQueue = new TellerQueue();
        tellerQueue.setQueueNumber(Double.toString(number)+" - T");
        tellerQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueueNumIdentification(number);
        tellerQueue.setTransactionCustomer(customerOptional.get());


        try {
            tellerQueue = tellerQueueRepository.save(tellerQueue);
            return new ResponseEntity<>(tellerQueue, HttpStatus.OK);
        } catch (ConstraintViolationException e)
        {
            return new ResponseEntity<>(returnprorityTellerQueueNumber(number+0.02,customerOptional),HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(returnprorityTellerQueueNumber(number+0.02,customerOptional),HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> completeAQeueueNumber(int customerId){
        Optional<TellerQueue> tellerQueueOptional= tellerQueueRepository.getTellerQueueByCustomerId(customerId);
        if(!tellerQueueOptional.isPresent()) {
            return returnResponse();
        } else {
            TellerQueue tellerQueue = tellerQueueOptional.get();
            if(tellerQueue.isHold()){
                return returnResponseHold();
            } else if(tellerQueue.isComplete()) {
                return returnResponseComplete();
            } else if(tellerQueue.isQueueReject()){
                return returnResponseReject();
            } else {
                tellerQueue.setComplete(true);
                tellerQueue.setQueuePending(false);
                tellerQueue.setQueueEndDate(java.util.Calendar.getInstance().getTime());
                return new ResponseEntity<>(tellerQueue,HttpStatus.OK);
            }
        }
    }


    private ResponseEntity<?> returnTellerQueueNumber(int currentQueueNumber,Optional<TransactionCustomer> customerOptional){
        TellerQueue tellerQueue = new TellerQueue();
        tellerQueue.setQueueNumber(Integer.toString(currentQueueNumber)+" - T");
        tellerQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueueNumIdentification(currentQueueNumber);
        tellerQueue.setTransactionCustomer(customerOptional.get());



        tellerQueue = tellerQueueRepository.save(tellerQueue);
        TellerDisplayDTO tellerDisplayDTO = new TellerDisplayDTO();
        tellerDisplayDTO.setSuccess(true);
        tellerDisplayDTO.setMessage(tellerQueue.getQueueNumber());
        tellerDisplayDTO.setTellerQueueId(tellerQueue.getTelerQueueId());
        tellerDisplayDTO.setTransactionCustomer(tellerQueue.getTransactionCustomer());
        tellerDisplayDTO.setQueueNumber(tellerQueue.getQueueNumber());
        tellerDisplayDTO.setComplete(tellerQueue.isComplete());
        tellerDisplayDTO.setComment(tellerQueue.getComment());
        tellerDisplayDTO.setHold(tellerQueue.isHold());
        tellerDisplayDTO.setQueueReject(tellerQueue.isQueueReject());
        tellerDisplayDTO.setQueuePending(tellerQueue.isQueuePending());
        tellerDisplayDTO.setQueueStartDate(tellerQueue.getQueueStartDate());
        tellerDisplayDTO.setQueueEndDate(tellerQueue.getQueueEndDate());
        tellerDisplayDTO.setQueueNumIdentification(tellerQueue.getQueueNumIdentification());
        return new ResponseEntity<>(tellerDisplayDTO,HttpStatus.CREATED);
    }

    private ResponseEntity<?> returnprorityTellerQueueNumber(double currentQueueNumber,Optional<TransactionCustomer> customerOptional){
        TellerQueue tellerQueue = new TellerQueue();
        tellerQueue.setQueueNumber(Double.toString(currentQueueNumber)+" - T");
        tellerQueue.setQueueStartDate(java.util.Calendar.getInstance().getTime());
        tellerQueue.setQueuePending(true);
        tellerQueue.setQueueNumIdentification(currentQueueNumber);
        tellerQueue.setTransactionCustomer(customerOptional.get());

        tellerQueue = tellerQueueRepository.save(tellerQueue);
        return new ResponseEntity<>(tellerQueue,HttpStatus.CREATED);
    }

}

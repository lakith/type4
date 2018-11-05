package com.spring.starter.components;

import com.spring.starter.Repository.BillPaymentRepository;
import com.spring.starter.Repository.CSRQueueRepository;
import com.spring.starter.Repository.TellerQueueRepository;
import com.spring.starter.model.CSRQueue;
import com.spring.starter.model.TellerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class SheduleMethods {

    @Autowired
    CSRQueueRepository csrQueueRepository;

    @Autowired
    TellerQueueRepository tellerQueueRepository;

    @Autowired
    private EntityManager em;

    public void migrateAndDeleteDataCSR(){
        javax.persistence.Query q1 = em.createNativeQuery("INSERT INTO csr_queue_history_details SELECT * FROM csr_queue");
        q1.executeUpdate();
        csrQueueRepository.deleteAll();
        javax.persistence.Query q2 = em.createNativeQuery("ALTER TABLE csr_queue AUTO_INCREMENT = 1", CSRQueue.class);
        q2.executeUpdate();

    }

    public void migrateAndDeleteDataTeller(){
        javax.persistence.Query q1 = em.createNativeQuery("INSERT INTO teller_queue_history_details SELECT * FROM teller_queue");
        q1.executeUpdate();
        tellerQueueRepository.deleteAll();
        javax.persistence.Query q2 = em.createNativeQuery("ALTER TABLE teller_queue AUTO_INCREMENT = 1", TellerQueue.class);
        q2.executeUpdate();

    }

}

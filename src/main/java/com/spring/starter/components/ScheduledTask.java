package com.spring.starter.components;

import com.spring.starter.Repository.LoginlogsRepository;
import com.spring.starter.model.CSRQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class ScheduledTask {

    @Autowired
    LoginlogsRepository loginlogsRepository;

    @Autowired
    SheduleMethods sheduleMethods;

    private EntityManager em;




/*    @Transactional
    @Scheduled(fixedRate = ScheduleConfig.FIVE_HOUR_INTERVAL)
    public void removeDeactivatedAccounts2() {

        try {

            List<ServiceRequestRepository>

        } catch (Exception e) {

            e.printStackTrace();
        }
    }*/


    @Transactional
    @Scheduled(cron = "0 0 9 * * ?")
    public void removeDeactivatedAccounts() {
        try {
            sheduleMethods.migrateAndDeleteDataCSR();
            sheduleMethods.migrateAndDeleteDataTeller();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

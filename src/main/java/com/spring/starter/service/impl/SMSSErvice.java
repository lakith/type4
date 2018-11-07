package com.spring.starter.service.impl;

import com.spring.starter.DTO.SmsResponse;
import com.spring.starter.DTO.smsDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SMSSErvice {

    public void sendSMS(){
        RestTemplate restTemplate = new RestTemplate();
/*        String fooResourceUrl
                = "http://10.96.198.25:7000/sms/send";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));*//*

        smsDTO request = new smsDTO();
        request.setPassword("ndb123");
        request.setApplicationId("APP_000001");

        List<String> stringList = new ArrayList<>();
        stringList.add("tel:94773820436");

*//*        ResponseEntity<SmsResponse> response = restTemplate
                .exchange("http://10.96.198.25:7000/sms/send", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));*//*

        password;
        message;
        destinationAddresses;
        applicationId;*/

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("password", "ndb123");
        map.add("message", "hello world");
        map.add("destinationAddresses","[tel:94773820436]");
        map.add("applicationId","APP_000001");


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("http://10.96.198.25:7000/sms/send", request , String.class );
        System.out.println(request);
    }

}

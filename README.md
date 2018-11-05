RestTemplate restTemplate = new RestTemplate();
String fooResourceUrl
  = "http://localhost:8080/spring-rest/foos";
ResponseEntity<String> response
  = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);
assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));


{
  "statusCode": "S1000",
  "requestId": "101811051352462077",
  "statusDetail": "Request was successfully processed",
  "destinationResponses": [
    {
      "statusCode": "S1000",
      "timeStamp": "20181105135246",
      "address": "tel:94773820436",
      "statusDetail": "Request was successfully processed",
      "messageId": "101811051352462077"
    }
  ],
  "version": "1.0"
}

package com.SAA.POC.JSON;

import com.SAA.POC.JSON.Service.CollegeService;
import com.SAA.POC.JSON.VO.College;
import com.SAA.POC.SizeCalculator.SizeCalc;

import org.springframework.http.*;

import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;

public class JSONClient {

    private  static ArrayList<Float> jsonSize = new ArrayList<>();
    public static ArrayList<Float> getJsonSize(){
        return jsonSize;
    }
    public static long start(String ip,int iterationCount){
        College college = CollegeService.getCollege(iterationCount);
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<College> requestEntity = new HttpEntity<College>(college, headers);

        try {

            jsonSize.add((float)SizeCalc.sizeof(requestEntity.getBody())/1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
     //  restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String URL = "http://"+ip+":8080/echoCollege";
        Instant start = Instant.now();
        ResponseEntity<College> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, College.class);
        Instant end = Instant.now();
       // System.out.println("started: "+start.atZone(ZoneId.systemDefault()));
      //  System.out.println("reached: "+end.atZone(ZoneId.systemDefault()));
        //System.out.println(response.getHeaders().getContentLength());
        College collegeResponse= response.getBody();
        //Server hit @:2019-10-09T16:04:48.439
        //Server hit @:2019-10-09T16:04:49.045
      //     College collegeResponse = restTemplate.postForObject(URL, College.class,College.class);
        return Duration.between(start, end).toMillis();
      //      System.out.println(" Its your JSON response in "
       //             + (time) + " milliseconds!!\n\nEchoing response");


    }


}

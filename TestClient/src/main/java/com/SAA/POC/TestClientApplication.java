package com.SAA.POC;

import com.SAA.POC.ExcelWriter.ExcelWriter;
import com.SAA.POC.JSON.JSONClient;
import com.SAA.POC.Protobuf.BulkLoadClient;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class TestClientApplication extends Thread {

    private static final String SERVER_IP = "localhost";
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Long> JSONResults = new ArrayList<>();
    private static ArrayList<Long> ProtobuffResults = new ArrayList<>();
    private static boolean fixed = false;
    public static void main(String[] args) {
        warmUp();

        while(true){
            System.out.println("1.Automatic(we will fire the request from 1 to 10,501 data values with incrementation of 100/constant size)");
            System.out.println("2.exit");
            int option = input.nextInt();
            if (option == 1) {
                automaticTest();
                System.out.println("Your test results are recorded in Test_Result.xls file, do check!!");
            }
            if(option ==2){
                System.exit(0);
            }

        }
    }

    public static void warmUp() {
        System.out.println("Warming-up protobuf..be patient it's just 10 Secs!!");
        Instant start = Instant.now();
        long startSeconds = start.getEpochSecond();
        while (Instant.now().getEpochSecond() - startSeconds <= 10) {
            BulkLoadClient.start(SERVER_IP, 6000, 10301, false);
        }
    }

    private static void automaticTest() {

        System.out.println("Finished warming up, let's get started..");
        System.out.println("Enter type of request\n1.Fixed(1MB)\n2.Variable(1KB-1MB)");
        int requestType = input.nextInt();

        if (requestType == 1) {
            fixed = true;
        }
        if (requestType == 2) {
            fixed = false;
        }

        System.out.println("Testing started.");
        TestClientApplication obj = new TestClientApplication();
        obj.start();

        for (int i = 1; i <= 10501; i += 100) {
            //System.out.printf("incr = "+i);
            if (fixed) {
                ProtobuffResults.add(BulkLoadClient.start(SERVER_IP, 6000, 10301, true));
            } else {
                ProtobuffResults.add(BulkLoadClient.start(SERVER_IP, 6000, i, true));
            }
        }

//        for (int i = 1; i <= 10501; i += 100) {
//
//            JSONResults.add(JSONClient.start(SERVER_IP, 10301));
//        }
        try {
            obj.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ExcelWriter.init();
        ExcelWriter.writeResults(JSONResults, ProtobuffResults, fixed);
        ExcelWriter.writeSize(BulkLoadClient.getProtobufSize(), JSONClient.getJsonSize(), fixed);
        ExcelWriter.writeToFile();
        JSONResults.clear();
        ProtobuffResults.clear();
    }


    @Override
    public void run() {
        boolean fixed = getValueOfFixed();
        for (int i = 1; i <= 10501; i += 100) {
            if(fixed==true) {
                JSONResults.add(JSONClient.start(SERVER_IP, 10301));
            }
            else{
                JSONResults.add(JSONClient.start(SERVER_IP, i));
            }
        }
    }

    private boolean getValueOfFixed() {
        return fixed;
    }
}

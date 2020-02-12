package com.SAA.POC.Protobuf;

import com.SAA.POC.SizeCalculator.SizeCalc;
import com.SAA.VO.College.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class BulkLoadClient {
    private static ArrayList<Float> protobufSize = new ArrayList<Float>();

    public static ArrayList<Float> getProtobufSize() {
        return protobufSize;
    }

    public static long start(String ip_address, int port, int iterationCount,boolean add) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(ip_address, port).usePlaintext() // disable
                // SSL
                .disableServiceConfigLookUp()
                .build();
        EchoServiceGrpc.EchoServiceBlockingStub echoStub = EchoServiceGrpc.newBlockingStub(channel);

        // creating load
        College.Builder college = College.newBuilder(); // used for bulk data.

        for (int i = 1; i <= iterationCount; i++) {
            Student student = Student.newBuilder()
                    .setId((int) Math.random())
                    .setMobile(1234567890)
                    .setAddress("Baker Street Lane " + i)
                    .setName("john Watson" + i)
                    .build();
            Teacher teacher = Teacher.newBuilder()
                    .setId((int) Math.random())
                    .setAddress("Baskerville street Lane" + i)
                    .setName("Sherlock Holmes" + i)
                    .setMobile(221222222)
                    .build();

            college.addTeacher(teacher).addStudent(student); // keep on adding
        }

        College collegeBuild = college.build();

        EchoRequest request = EchoRequest.newBuilder()
                .setCollege(collegeBuild)
                .build();
        if(add==true) {
            protobufSize.add((float) request.getSerializedSize() / 1024);
        }
        Instant start = Instant.now();
        EchoResponse response = echoStub.echo(request); // gRPC call
        Instant end = Instant.now();
        long time = Duration.between(start, end).toMillis();
        College collegeResponse = response.getCollege();
        channel.shutdown();
        return time;
    }


}

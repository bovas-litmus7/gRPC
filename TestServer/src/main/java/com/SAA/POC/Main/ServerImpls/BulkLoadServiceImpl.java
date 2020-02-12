package com.SAA.POC.Main.ServerImpls;

import com.SAA.VO.College.*;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;

public class BulkLoadServiceImpl extends EchoServiceGrpc.EchoServiceImplBase {
    @Override
    public void echo(EchoRequest request, StreamObserver<EchoResponse> responseObserver) {
        System.out.println("gRPC hit");
        College requestEcho = request.getCollege();
        College response = requestEcho; // since its echo we just pass the request as such :P

        EchoResponse responseEcho = EchoResponse.newBuilder()
                .setCollege(response)
                .build();
        responseObserver.onNext(responseEcho.getDefaultInstanceForType());
        responseObserver.onCompleted();

    }

}

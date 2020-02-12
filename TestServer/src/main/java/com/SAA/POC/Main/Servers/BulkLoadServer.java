package com.SAA.POC.Main.Servers;


import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class BulkLoadServer {
    public static void init (BindableService serviceClass, int port) {


        Server server = ServerBuilder
                .forPort(port)
                .addService(serviceClass)
                .build();

        try {
            server.start();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                System.out.println("server shutting down...");
                server.shutdown();
                System.out.println("Server shut down successful");
            }

        });

        try {
            server.awaitTermination();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

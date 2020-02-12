package com.SAA.POC.Main;

import com.SAA.POC.Main.ServerImpls.BulkLoadServiceImpl;
import com.SAA.POC.Main.Servers.BulkLoadServer;
import io.grpc.BindableService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerStarter {

	public static void main(String[] args) {
		SpringApplication.run(ServerStarter.class, args); // to enable rest services at 8080

		BulkLoadServiceImpl bulkLoadService = new BulkLoadServiceImpl();
		BulkLoadServer.init((BindableService) bulkLoadService,6000);

	}

}

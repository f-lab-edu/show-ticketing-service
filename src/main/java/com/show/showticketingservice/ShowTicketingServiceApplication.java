package com.show.showticketingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ShowTicketingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShowTicketingServiceApplication.class, args);
	}

}

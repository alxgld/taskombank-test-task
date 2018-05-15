package com.gladunalexander.clientservice;

import com.gladunalexander.clientservice.domain.Client;
import com.gladunalexander.clientservice.service.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ClientService clientService) {
		return args -> {
			Client client1 = new Client();
			client1.setFirstName("Alexander");
			client1.setLastName("Boyko");
			client1.setMiddleName("Anatolievich");
			client1.setEmail("vladislav@gmail.com");
			client1.setPhoneNumber("0931829974");

            Client client2 = new Client();
            client2.setFirstName("Alexander");
            client2.setLastName("Ponomarenko");
            client2.setMiddleName("Sergeevich");
            client2.setEmail("alexander@gmail.com");
            client2.setPhoneNumber("0672139210");

            Client client3 = new Client();
            client3.setFirstName("Diana");
            client3.setLastName("Hutornaia");
            client3.setMiddleName("Alexandrovna");
            client3.setEmail("diana@mail.ru");
            client3.setPhoneNumber("0978627421");

            clientService.addClient(client1);
            clientService.addClient(client2);
            clientService.addClient(client3);
		};
	}
}

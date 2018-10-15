package com.viktor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import com.viktor.controller.OffersController;
import com.viktor.offer.Offer;
import com.viktor.repository.OffersRepository;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class App {
	
	private static final Logger logger = Logger.getLogger(OffersController.class);
	
	public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }
	
	@Bean
	public CommandLineRunner setup(OffersRepository offersRepository) {
		return (args) -> {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
	        
			offersRepository.save(new Offer("Offer 1", 5.99, df.parse("2018-10-1 3:00 PM GMT+1:00"), true));
			offersRepository.save(new Offer("Offer 2", 10.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true));
			offersRepository.save(new Offer("Offer 3", 15.99, df.parse("2021-10-1 3:00 PM GMT+1:00"), true));
			offersRepository.save(new Offer("Offer 4", 20.99, df.parse("2022-10-1 3:00 PM GMT+1:00"), true));
			logger.info("The sample data has been generated");
		};
	}
	
}

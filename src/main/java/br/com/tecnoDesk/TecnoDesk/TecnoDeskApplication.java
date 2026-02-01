package br.com.tecnoDesk.TecnoDesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.tecnoDesk.TecnoDesk", "exception" })
public class TecnoDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(TecnoDeskApplication.class, args);
	}

}

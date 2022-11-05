package org.programmers.kdtspringdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VoucherProgramApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(VoucherProgramApplication.class, args);
		VoucherProgram voucherProgram = applicationContext.getBean(VoucherProgram.class);
		voucherProgram.run();
	}

}
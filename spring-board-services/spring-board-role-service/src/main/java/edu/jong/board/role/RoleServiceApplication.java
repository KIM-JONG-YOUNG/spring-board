package edu.jong.board.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import edu.jong.board.common.BoardConstants;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class RoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoleServiceApplication.class, args);
	}

}

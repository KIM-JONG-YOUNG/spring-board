package edu.jong.board.member_role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import edu.jong.board.common.BoardConstants;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class MemberRoleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberRoleServiceApplication.class, args);
	}

}

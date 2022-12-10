package edu.jong.board.member_role.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import edu.jong.board.common.BoardConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class MemberRoleDomainTests {

	@Test
	void contextLoad() {
		log.info("Create Table!!");
	}

}

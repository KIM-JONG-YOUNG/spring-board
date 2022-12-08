package edu.jong.board.domain.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import edu.jong.board.common.BoardConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@SpringBootApplication(scanBasePackages = BoardConstants.ROOT_PACKAGE)
public class SpringBoardDomainTests {

	@Test
	void contextLoad() {
		log.info("Context Load!!");
	}
	
}

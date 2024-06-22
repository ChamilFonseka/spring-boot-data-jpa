package dev.chafon.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ApplicationTests {

	@Autowired
	private PostgreSQLContainer<?> postgreSQLContainer;

	@Test
	void contextLoads() {
		assertThat(postgreSQLContainer.isCreated()).isTrue();
		assertThat((postgreSQLContainer.isRunning())).isTrue();
	}
}

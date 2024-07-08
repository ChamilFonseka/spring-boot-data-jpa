package dev.chafon.datajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }
}

package dev.chafon.datajpa;

import org.springframework.boot.SpringApplication;

public class TestSpringBootDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.from(SpringBootDataJpaApplication::main)
        .with(TestContainersConfiguration.class)
        .run(args);
  }
}

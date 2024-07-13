package dev.chafon.datajpa;

import dev.chafon.datajpa.pet.cat.Cat;
import dev.chafon.datajpa.pet.dog.Dog;
import dev.chafon.datajpa.pet.PetRepository;
import dev.chafon.datajpa.pet.PetType;
import net.datafaker.Faker;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootDataJpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }

  @Bean
  @Profile("dev")
  ApplicationRunner applicationRunner(PetRepository petRepository) {
    return args -> {
      Faker faker = new Faker();

      net.datafaker.providers.base.Cat fakeCat = faker.cat();
      petRepository.save(
          Cat.builder()
              .name(fakeCat.name())
              .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
              .breed(fakeCat.breed())
              .registry(fakeCat.registry())
              .type(PetType.CAT)
              .build());

      net.datafaker.providers.base.Dog fakeDog = faker.dog();
      petRepository.save(
          Dog.builder()
              .name(fakeDog.name())
              .dateOfBirth(faker.date().birthdayLocalDate(1, 15))
              .breed(fakeDog.breed())
              .size(fakeDog.size())
              .sound(fakeDog.sound())
              .coatLength(fakeDog.coatLength())
              .type(PetType.DOG)
              .build());
    };
  }
}

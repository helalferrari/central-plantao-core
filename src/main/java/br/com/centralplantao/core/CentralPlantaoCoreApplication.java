package br.com.centralplantao.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.centralplantao")
@EntityScan(basePackages = "br.com.centralplantao")
@EnableJpaRepositories(basePackages = "br.com.centralplantao")
public class CentralPlantaoCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CentralPlantaoCoreApplication.class, args);
    }

}

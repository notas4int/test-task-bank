package com.projects.notas4int.bankservce;

import com.projects.notas4int.bankservce.mappers.BankAccountMapper;
import com.projects.notas4int.bankservce.mappers.ClientMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BankServceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankServceApplication.class, args);
    }

    @Bean
    public BankAccountMapper bankAccountMapper() {
        return Mappers.getMapper(BankAccountMapper.class);
    }

    @Bean
    public ClientMapper clientMapper() {
        return Mappers.getMapper(ClientMapper.class);
    }
}

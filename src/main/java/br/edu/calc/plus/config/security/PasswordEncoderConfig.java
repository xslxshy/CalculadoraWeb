package br.edu.calc.plus.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Define o PasswordEncoder em uma classe separada da SecurityConfiguration.
 * Antes, o bean ficava dentro da propria SecurityConfiguration, que tambem o
 * injetava, criando uma referencia circular que podia impedir a aplicacao de
 * iniciar (por exemplo ao executar o JAR).
 */
@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

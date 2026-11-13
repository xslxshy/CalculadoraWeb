package br.edu.calc.plus;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.edu.calc.plus.domain.Usuario;
import br.edu.calc.plus.repo.UsuarioRepo;
import java.util.Random;

@SpringBootApplication
public class CalculatorPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorPlusApplication.class, args);
	}
	
	 @Autowired
	 private UsuarioRepo uDao;
	 
	 private PasswordEncoder password = new BCryptPasswordEncoder();

    @Bean
    public CommandLineRunner runner() {
        return (args) -> {
        	
        	if (uDao.count()  == 0) {
                Usuario u = new Usuario(null, "Daves Martins", "daves", "daves@daves", password.encode( "123456" ), "JF", 
                		LocalDate.now().minusYears(30)); 
                		
                
                uDao.save(u);
                                
            }

            System.out.println("####### Calculator Web - Started #######");
        };

    }

    @Bean
    public Random aleatorio(){
        return new Random(new Date().getTime());
    }
    
}

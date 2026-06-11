package br.edu.ifsul.cstsi.projetotds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ProjetoTdsApplication {

    private final Environment env;

    public ProjetoTdsApplication(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjetoTdsApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String port = env.getProperty("server.port", "8080");
        System.out.println("\n======================================");
        System.out.println("  Swagger UI: http://localhost:" + port + "/swagger-ui/index.html");
        System.out.println("======================================\n");
    }

}

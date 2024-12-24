package com.aluracursos.literalura.Configuracion;

import com.aluracursos.literalura.Principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private final Principal principal;

    public AppRunner(Principal principal) {
        this.principal = principal;
    }

    @Override
    public void run(String... args) throws Exception {
        principal.mostrarMenu();
    }

}

package com.aluracursos.literalura.Service;

import com.aluracursos.literalura.Model.Libros;
import com.aluracursos.literalura.Repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibrosService {

    @Autowired
    private LibrosRepository librosRepository;

    public List<Libros> mostrarTodosLibrosIdioma(String idioma){
        return librosRepository.mostrarListaPorIdioma(idioma);
    }

    public List<Libros> mostrarTodosLibros() {
        return librosRepository.findAll();
    }

    public Optional<Libros> comprobarExistenciaLibro(String nombre){
        return librosRepository.comprobarExistenciaLibro(nombre);
    }

    public void guardarLibro(Libros libro){
        librosRepository.save(libro);
    }


}

package com.aluracursos.literalura.Repository;

import com.aluracursos.literalura.Model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros,Long> {
    @Query("SELECT a FROM Libros a WHERE LOWER(a.titulo) LIKE LOWER(concat('%', :nombre, '%'))")
    Optional<Libros> comprobarExistenciaLibro(String nombre);

    @Query("SELECT l FROM Libros l WHERE :idioma = '' OR UPPER(l.idioma) = UPPER(:idioma)")
    List<Libros> mostrarListaPorIdioma(@Param("idioma") String idioma);
}

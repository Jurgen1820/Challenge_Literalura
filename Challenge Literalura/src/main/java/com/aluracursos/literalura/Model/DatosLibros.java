package com.aluracursos.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutores> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Double descargas
) {
}

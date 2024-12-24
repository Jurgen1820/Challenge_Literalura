package com.aluracursos.literalura.Model;

import jakarta.persistence.*;

import java.util.OptionalDouble;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "libros_seq")
    @SequenceGenerator(name = "libros_seq", sequenceName = "libros_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Double descargas;

    @ManyToOne
    @JoinColumn(name = "autores_id")
    private Autores autores;



    public Libros(){}

    public Libros(DatosLibros datosLibros) {
        String titulo = datosLibros.titulo();
        if (titulo.length() > 255) {
            this.titulo = titulo.substring(0, 255).trim();
        } else {
            this.titulo = titulo.trim();
        }
        this.idioma = datosLibros.idioma().isEmpty() ? Idioma.DESCONOCIDO : Idioma.fromString(datosLibros.idioma().get(0));
        this.descargas = OptionalDouble.of(Double.valueOf(datosLibros.descargas())).orElse(0);
    }

    public Autores getAutores() {
        return autores;
    }

    @Override
    public String toString() {
        return "Libros Encontrado \n" +
                "titulo: '" + titulo + '\'' +
                ", autor: '" + obtenerNombreAutor() + '\'' +
                ", idioma: " + idioma +
                ", descargas: " + descargas ;
    }
    public String obtenerNombreAutor() {
        if (autores != null) {
            return autores.getNombres().split(",")[0].trim();
        } else {
            return "Desconocido";
        }
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }

}

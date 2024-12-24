package com.aluracursos.literalura.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autores {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autores_seq")
    @SequenceGenerator(name = "autores_seq", sequenceName = "autores_seq", allocationSize = 1)
    private Long id;


    @Column(unique = true)
    private String nombres;
    private Integer nacimiento;
    private Integer muerte;
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Libros> librosEscritos;

    public Autores() {
    }

    public Autores(List<DatosAutores> autor) {
        if (autor != null && !autor.isEmpty()) {
            this.nombres = autor.get(0).nombres();
            this.nacimiento = autor.get(0).nacimiento();
            this.muerte = autor.get(0).muerte();
        } else {
            this.nombres = "Desconocido";
            this.nacimiento = 0;
            this.muerte = 0;
        }
    }

    public Set<Libros> getLibrosEscritos() {
        return librosEscritos;
    }

    public void setLibrosEscritos(Set<Libros> librosEscritos) {
        this.librosEscritos = librosEscritos;
    }

    @Override
    public String toString() {
        return "Autores " +
                "nombres=: '" + nombres + '\'' +
                ", nacimiento: '" + nacimiento + '\'' +
                ", muerte: '" + muerte + '\'' +
                ", libros: " + mostrarTitulosLibros().get(0).toString();
    }

    private List<String> mostrarTitulosLibros(){
        var  titulo = librosEscritos.stream().map(Libros::getTitulo).collect(Collectors.toList());
        return titulo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }

}

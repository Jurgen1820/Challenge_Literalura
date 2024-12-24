package com.aluracursos.literalura.Principal;

import com.aluracursos.literalura.Model.Autores;
import com.aluracursos.literalura.Model.Datos;
import com.aluracursos.literalura.Model.DatosLibros;
import com.aluracursos.literalura.Model.Libros;
import com.aluracursos.literalura.Service.AutoresService;
import com.aluracursos.literalura.Service.ConsultaAPI;
import com.aluracursos.literalura.Service.ConvierteDatos;
import com.aluracursos.literalura.Service.LibrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal {

    private final String URL_BASE = "https://gutendex.com/books/";
    private final Scanner teclado = new Scanner(System.in);
    private ConsultaAPI consumoAPI = new ConsultaAPI();
    private List<Libros> libros;
    private List<Autores> autores;

    ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    AutoresService autoresService;

    @Autowired
    LibrosService librosService;

    public void mostrarMenu(){
        var opcion = -1;
        while (opcion != 0) {
            do {
                try {
                    var menu = """
                            1 - Buscar Libros
                            2 - Lista de Libros Registrados
                            3 - Lista de Autores Registrados
                            4 - Buscar autores vivos en un determinado año
                            5 - Lista de Libros por idioma
                            0 - Salir
                            """;
                    System.out.println(menu);
                    opcion = teclado.nextInt();
                    teclado.nextLine();
                }catch (InputMismatchException e){
                    System.out.println("Error: Por favor, ingrese un número válido");
                    teclado.nextLine();
                }
            }while (opcion>5 || opcion<0);
            switch (opcion) {
                case 1:
                    buscarLibros();
                    break;
                case 2:
                    mostrarLibros();
                    break;
                case 3:
                    listaDeAutores();
                    break;
                case 4:
                    buscarAutoresPorAnio();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }
    }

    private void buscarAutoresPorAnio() {
        System.out.println("\nIngresa el año para buscar autores: ");
        Integer anio = teclado.nextInt();

        autores = autoresService.buscarAutoresPorAnio(anio);
        autores.forEach(System.out::println);

    }

    private void listaDeAutores() {
        autores = autoresService.mostrarAutores();
        autores.forEach(System.out::println);
    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("\nIngresa el idioma a listar ( 1.Español , 2.Ingles , 3.idioma Desconocido)\n ");
        var idiomaEleccion = teclado.nextInt();
        String idiomaEleccion2 = "";
        switch (idiomaEleccion) {
            case 1:
                idiomaEleccion2 = "ESPAÑOL";
                break;
            case 2:
                idiomaEleccion2 = "INGLES";
                break;
            case 3:
                idiomaEleccion2 = "DESCONOCIDO";
                break;
            default:
                System.out.println("Eleccion incorrecta de idioma\n");
        }
        libros = librosService.mostrarTodosLibrosIdioma(idiomaEleccion2);
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros de ese idioma registrado.\n");
        } else {
            libros.forEach(System.out::println);
        }
    }


    private void mostrarLibros() {
        libros = librosService.mostrarTodosLibros();

        libros.stream().sorted(Comparator.comparing(Libros::getId))
                .forEach(System.out::println);
    }

    private void buscarLibros() {
        System.out.println("\nIngresa nombre o parte del nombre a buscar: ");
        var busqueda = teclado.nextLine();
        var json2 = consumoAPI.obtenerDatos(URL_BASE + "?search=" + busqueda.replace(" ", "%20"));
        System.out.println(json2);

        var datosBusqueda = conversor.convertirDatos(json2, Datos.class);

        Optional<DatosLibros> datosLibrosOptional = datosBusqueda.libros().stream()
                .filter(libro -> libro.titulo().toLowerCase().contains(busqueda.toLowerCase()))
                .findFirst();

        if (datosLibrosOptional.isPresent()) {
            DatosLibros datosLibros = datosLibrosOptional.get();
            System.out.println("\nLibro encontrado: " + datosLibros.titulo());

            // Obtener el nombre del autor
            String nombreAutor = datosLibros.autor() != null ? datosLibros.autor().get(0).nombres() : "Desconocido";
            System.out.println("Autor: " + nombreAutor);


            System.out.println("\n¿Deseas guardar este libro y autor? 1.Sí  2.No");
            int opcion = teclado.nextInt();
            teclado.nextLine();
            if (opcion == 1) {
                Autores autor = datosLibros.autor() != null ? new Autores(datosLibros.autor()) : null;
                Optional<Autores> existenciaAutores = autoresService.comprobarExistenciaDeAutor(nombreAutor);

                if (existenciaAutores.isPresent()) {
                    System.out.println("\n*** Autor ya existe en la base de datos. ***");
                    autor = existenciaAutores.get(); // Usar el autor existente
                }
                try {
                    autoresService.guardarAutores(autor);
                    Optional<Libros> existenciaLibro = librosService.comprobarExistenciaLibro(datosLibros.titulo());

                    if (!existenciaLibro.isPresent()) {
                        Libros libro = new Libros(datosLibros);
                        libro.setAutores(autor);
                        librosService.guardarLibro(libro);
                    } else {
                        System.out.println("\n*** Libro ya existe en la base de datos. ***");
                    }
                } catch (Exception e) {
                    System.out.println("\n*** Error al intentar guardar el libro y/o autor: " + e.getMessage() + " ***");
                }

            } else {
                System.out.println("\n*** Entendido!. ***");
            }

        } else {
            System.out.println("\n*** Libro no encontrado. ***");
        }
    }

}

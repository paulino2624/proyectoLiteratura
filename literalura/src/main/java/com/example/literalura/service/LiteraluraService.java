package com.example.literalura.service;

import com.example.literalura.dto.*;
import com.example.literalura.model.Autor;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.AutorRepository;
import com.example.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LiteraluraService {

    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos convierteDatos;
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public LiteraluraService(ConsumoAPI consumoAPI,
                             ConvierteDatos convierteDatos,
                             AutorRepository autorRepository,
                             LibroRepository libroRepository) {
        this.consumoAPI = consumoAPI;
        this.convierteDatos = convierteDatos;
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void buscarYGuardarLibro(String tituloBuscado) {

        String url = "https://gutendex.com/books/?search=" + tituloBuscado.replace(" ", "+");

        String json = consumoAPI.obtenerDatos(url);

        RespuestaGutendex respuesta =
                convierteDatos.obtenerDatos(json, RespuestaGutendex.class);

        if (respuesta.resultados().isEmpty()) {
            System.out.println("Libro no encontrado.");
            return;
        }

        DatosLibro datosLibro = respuesta.resultados().get(0);

        DatosAutor datosAutor = datosLibro.autores().get(0);

        Optional<Autor> autorExistente =
                autorRepository.findByNombre(datosAutor.nombre());

        Autor autor = autorExistente.orElseGet(() ->
                autorRepository.save(
                        new Autor(
                                datosAutor.nombre(),
                                datosAutor.añoNacimiento(),
                                datosAutor.añoFallecimiento()
                        )
                )
        );

        Libro libro = new Libro(
                datosLibro.titulo(),
                datosLibro.idiomas().get(0),
                datosLibro.numeroDescargas(),
                autor
        );

        libroRepository.save(libro);

        System.out.println("Libro guardado correctamente.");
    }

    public void listarLibros() {
        libroRepository.findAll().forEach(libro -> {
            System.out.println("----- LIBRO -----");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getNumeroDescargas());
            System.out.println("-----------------\n");
        });
    }

    public void listarAutores() {
        autorRepository.findAll().forEach(autor -> {
            System.out.println("----- AUTOR -----");
            System.out.println("Nombre: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getAñoNacimiento());
            System.out.println("Fallecimiento: " + autor.getAñoFallecimiento());
            System.out.println("-----------------\n");
        });
    }

    public void listarLibrosPorIdioma(String idioma) {
        libroRepository.findByIdioma(idioma).forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("-----------------\n");
        });
    }

    public void listarAutoresVivos(Integer anio) {
        autorRepository.autoresVivosEnAnio(anio).forEach(autor -> {
            System.out.println("Autor: " + autor.getNombre());
        });
    }


}
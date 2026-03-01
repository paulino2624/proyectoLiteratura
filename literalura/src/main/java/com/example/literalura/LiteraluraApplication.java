package com.example.literalura;

import com.example.literalura.service.LiteraluraService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(LiteraluraApplication.class, args);

		LiteraluraService service = context.getBean(LiteraluraService.class);
		Scanner scanner = new Scanner(System.in);

		int opcion = -1;

		while (opcion != 0) {

			System.out.println("""
                    
                    ==== LITERALURA ====
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);

			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {

				case 1 -> {
					System.out.println("Escribe el título:");
					String titulo = scanner.nextLine();
					service.buscarYGuardarLibro(titulo);
				}

				case 2 -> service.listarLibros();

				case 3 -> service.listarAutores();

				case 4 -> {
					System.out.println("Escribe el año:");
					Integer anio = scanner.nextInt();
					service.listarAutoresVivos(anio);
				}

				case 5 -> {
					System.out.println("Escribe el idioma (ej: en, es, fr):");
					String idioma = scanner.nextLine();
					service.listarLibrosPorIdioma(idioma);
				}

				case 0 -> System.out.println("Saliendo...");

				default -> System.out.println("Opción inválida.");
			}
		}
	}
}
package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.añoNacimiento <= :anio AND (a.añoFallecimiento IS NULL OR a.añoFallecimiento >= :anio)")
    List<Autor> autoresVivosEnAnio(Integer anio);
}


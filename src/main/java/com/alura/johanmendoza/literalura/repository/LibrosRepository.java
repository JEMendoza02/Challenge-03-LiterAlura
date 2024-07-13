package com.alura.johanmendoza.literalura.repository;

import com.alura.johanmendoza.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//El repositorio es el encargado de hacer las consultas a la BD
public interface LibrosRepository extends JpaRepository<Libros, Long> {
    List<Libros> findByTituloContainingIgnoreCase(String titulo);
    @Query("SELECT l FROM Libros l JOIN l.idiomas i WHERE i = :nombreIdioma")
    List<Libros> findByIdioma(@Param("nombreIdioma") String nombreIdioma);
}

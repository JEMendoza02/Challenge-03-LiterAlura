package com.alura.johanmendoza.literalura.repository;
import java.util.Optional;
import com.alura.johanmendoza.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
    public interface AutoresRepository extends JpaRepository<Autores, Long> {
        Optional<Autores> findByNombre(String nombre);
}

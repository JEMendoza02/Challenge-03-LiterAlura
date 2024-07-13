package com.alura.johanmendoza.literalura.services;

import com.alura.johanmendoza.literalura.model.AutorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alura.johanmendoza.literalura.repository.AutoresRepository;
import com.alura.johanmendoza.literalura.repository.LibrosRepository;
import com.alura.johanmendoza.literalura.model.Libros;
import com.alura.johanmendoza.literalura.model.Autores;

import java.util.List;


@Service
public class LibroService {

    @Autowired
    private LibrosRepository datosLibrosRepository;

    @Autowired
    private AutoresRepository datosAutorRepository;

    @Transactional
    public void guardarLibro(Libros libro) {
        Autores autor = libro.getAutor();
        if (autor != null) {
            autor = obtenerOcrearAutor(new AutorDTO(autor.getNombre(), autor.getFechaNacimiento().toString()));
            libro.setAutor(autor);
        }
        datosLibrosRepository.save(libro);
    }

    @Transactional
    public Autores obtenerOcrearAutor(AutorDTO autorDTO) {
        return datosAutorRepository.findByNombre(autorDTO.nombre())
                .orElseGet(() -> {
                    Autores nuevoAutor = new Autores();
                    nuevoAutor.setNombre(autorDTO.nombre());
                    nuevoAutor.setFechaNacimiento(autorDTO.fechaNacimiento());
                    return datosAutorRepository.save(nuevoAutor);
                });
    }
    @Transactional(readOnly = true)
    public List<Libros> obtenerTodosLosLibros() {
        return datosLibrosRepository.findAll();
    }
    public List<Libros> obtenerLibrosPorIdioma(String nombreIdioma) {
        return datosLibrosRepository.findByIdioma(nombreIdioma); // Este método se definirá a continuación.
    }

}
package com.alura.johanmendoza.literalura.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alura.johanmendoza.literalura.repository.AutoresRepository;
import com.alura.johanmendoza.literalura.model.Autores;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AutorService {

    @Autowired
    private AutoresRepository autorRepository;

    public List<Autores> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }
}

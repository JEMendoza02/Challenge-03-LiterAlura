package com.alura.johanmendoza.literalura.model;
import jakarta.persistence.*;
import com.alura.johanmendoza.literalura.model.Autores;

import java.util.List;
@Entity
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String titulo;
    @ManyToOne
    @JoinColumn(name="autor_id")
    private Autores autor;
    @ElementCollection
    @CollectionTable(name = "libros_idiomas", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "nombre_idioma")
    private List<String> idiomas;
    private Double numeroDescargas;
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
    //Metodo to String para imprimit todos los datos
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro{");
        sb.append("id=").append(Id);
        sb.append(", titulo='").append(titulo).append('\'');
        sb.append(", autor=").append(autor != null ? autor.toString() : "null");
        sb.append(", numeroDescargas=").append(numeroDescargas);
        sb.append('}');
        return sb.toString();
    }

}

package com.alura.johanmendoza.literalura.services;

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}

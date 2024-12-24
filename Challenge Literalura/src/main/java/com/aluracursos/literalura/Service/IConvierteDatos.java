package com.aluracursos.literalura.Service;

public interface IConvierteDatos {

    <T> T convertirDatos(String json, Class<T> clase);

}

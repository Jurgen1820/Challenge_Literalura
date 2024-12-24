package com.aluracursos.literalura.Model;

public enum Idioma {
    INGLES("en"),
    ESPAÑOL("es"),
    DESCONOCIDO("");

    private String idioma;

    Idioma (String idioma){
        this.idioma = idioma;
    }


    public static Idioma fromString(String text) {
        for (Idioma idioma1 : Idioma.values()) {
            if (idioma1.idioma.equalsIgnoreCase(text)) {
                return idioma1;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}

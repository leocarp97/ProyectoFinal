package com.proyectoFinal.enums;

public enum Idioma {
    INGLES("Ingles"), ESPAÑOL("Español"), PORTUGUES("Portugués"), FRANCES("Frances"), ITALIANO("Italiano"), RUSO("Ruso"), ALEMAN("Aleman"), CHINO("Chino"), JAPONES("Japones");

    private String displayValue;

    private Idioma(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}

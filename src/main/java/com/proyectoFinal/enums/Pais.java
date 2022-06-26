package com.proyectoFinal.enums;

public enum Pais {

    REINO_UNIDO("Reino Unido"), ARGENTINA("Argentina"), ITALIA("Italia"), ESTADOS_UNIDOS("Estados Unidos"), FRANCIA("Francia"), ALEMANIA("Alemania"), COLOMBIA("Colombia"), PERU("Peru"), URUGUAY("Uruguay"), CHILE("Chile"), CHINA("China"), ESPAÑA("España"), ECUADOR("Ecuador"), PORTUGAL("Portugal"), BRASIL("Brasil");

    private String displayValue;

    private Pais(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}

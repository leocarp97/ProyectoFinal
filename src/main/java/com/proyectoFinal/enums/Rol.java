package com.proyectoFinal.enums;

public enum Rol {

    ALUMNO("Alumno"), PROFESOR("Profesor"),ADMIN("Administrador");

    
    
    private String displayValue;

    private Rol(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
    
}

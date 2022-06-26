
package com.proyectoFinal.enums;

public enum Turno {
    
    MAÑANA("Mañana"), TARDE("Tarde"), NOCHE("Noche");
    
    private String displayValue;

    private Turno(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}

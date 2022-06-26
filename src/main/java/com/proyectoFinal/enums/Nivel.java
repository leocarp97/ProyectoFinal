
package com.proyectoFinal.enums;

public enum Nivel {
    PRINCIPIANTE_A1("Principiante A1"), PRINCIPIANTE_A2("Principiante A2"), INTERMEDIO_B1("Intermedio B1"), INTERMEDIO_B2("Intermedio B2"), AVANZADO_C1("Avanzado C1"), AVANZADO_C2("Avanzado C2");

      private String displayValue;

    private Nivel(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }
}

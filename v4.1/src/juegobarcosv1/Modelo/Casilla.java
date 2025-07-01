/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Casilla de Hundir la flota
 * 
 */
public class Casilla {
    private int coordenadaX;
    private int coordenadaY;
    private boolean tocada;

    /**
     * Crea una casilla
     * 
     */
    public Casilla(int coordenadaX, int coordenadaY, boolean tocada) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.tocada = tocada;
    }

    /**
     * Crea una casilla desde un fichero
     * 
     */
    public Casilla(Scanner scanner) throws IOException {
        coordenadaX = scanner.nextInt();
        coordenadaY = scanner.nextInt();
        tocada = scanner.nextBoolean();
    }
    
    /**
     * 
     * devuelve la fila de la posicion 
     */
    public int devolverfila() {
        return coordenadaX;
    }
    
    /**
     * 
     * devuelve la fila de la posicion 
     */
    public int devolverColumna() {
        return coordenadaY;
    }
    
     /**
     * devuelve el estado de la casilla
     * 
     */
    public boolean tocada() {
        return tocada;
    }

    /**
     * Dispara a una casilla y le da la condicion de tocada
     * 
     */
    public void disparar(Casilla casilla) {
        this.tocada = true;
    }
    
    /**
     * devuelve verdad si la casilla esta dentro de los limites
     * 
     */
    public boolean valida(int filas, int columnas) {
        return (coordenadaX >= 0 && coordenadaY >= 0 &&
                filas > coordenadaX && columnas > coordenadaY);

    }

    /**
     * equals()
     * 
     */
    public boolean equals(Object casilla) {
        if (this == casilla) {
            return true;
        }
        if (!(casilla instanceof Casilla)) {
            return false;
        }
        return (this.coordenadaX == ((Casilla) casilla).coordenadaX &&
                (this.coordenadaY == ((Casilla) casilla).coordenadaY));
    }

    /**
     * hasCode()
     * 
     */
    public int hashCode() {
        int resultado = 17;
        resultado = 37 * resultado;
        resultado = 37 * resultado + coordenadaX;
        return 37 * resultado + coordenadaY;
    }


    /**
     * Guarda una casilla en un fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.print(coordenadaX + " " +
                coordenadaY + " " +
                tocada + " ");
    }

}

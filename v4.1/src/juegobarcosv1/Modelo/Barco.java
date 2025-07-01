/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Modelo;

import juegobarcosv1.Controlador.HundirLaFlota;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Barco de Hundir la flota
 * 
 */

public class Barco {
    Random azar = new Random();

    public List<Casilla> casillas;
    private TipoBarco tipoBarco;

    /**
     * Construye un barco
     * 
     */
    public Barco(TipoBarco tipoBarco) {
        this.tipoBarco = tipoBarco;
        this.casillas = generarCasillas(tipoBarco.ID);
    }
    
    /**
     * 
     * Constructor para reconstruir un barco de un fichero 
     */
     public Barco(String ID,List<Casilla> casillas) {
          this.tipoBarco = new TipoBarco(ID);
          this.casillas = casillas;
        
    }
     
    /**
     * Devuelve el tamaño del barco
     * 
     */
    public List<Casilla> devolverCasillas() {
        return casillas;
    }
    
    /**
     * 
     * @devuelve el tipo de barco 
     */

    public String devolverTipo(){
        return tipoBarco.ID;
    }
     
    /**
     * Construye un barco desde un fichero
     * 
     */
    public Barco(Scanner scanner) throws IOException {
        casillas = new ArrayList<>();
        int longitud = scanner.nextInt();
 
        for (int i = 0; i < longitud; i++) {
            casillas.add(new Casilla(scanner));
        }
    }
    
    /**
     * 
     * Genera las casillas de un barco de forma aleatoria  
     */
    public List<Casilla> generarCasillas(String IdBarco) {
        List<Casilla> casillas = new ArrayList<>();
        casillas = tipoBarco.formaBarco(IdBarco);
        int longitud = casillas.size();

        int x = azar.nextInt(HundirLaFlota.FILAS);
        int y = azar.nextInt(HundirLaFlota.COLUMNAS);
        boolean orientacion = azar.nextBoolean();
        casillas.clear();

        for (int i = 0; i < longitud; i++) {
            int incrementarFilas = 0;
            int incrementarColumnas = 0;
            if (orientacion) {
                incrementarFilas = i;
            } else {
                incrementarColumnas = i;
            }
            casillas.add(new Casilla(x + incrementarFilas, y + 
                        incrementarColumnas, false));
        }
        return casillas;
    }


    /**
     * Comprueba si un barco se solapa con otro
     * 
     */
    public boolean solapa(Barco barco) {
        for (Casilla casilla : casillas) {
            for (Casilla casillaBarco : barco.devolverCasillas()) {
                if (casilla.equals(casillaBarco)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * devuelve verdadi si la casilla ha sido tocada y pertenece al barco
     * 
     */
    public boolean tocada(Casilla casillaSugerida) {
        for (Casilla casilla : casillas) {
            if (casilla.equals(casillaSugerida) &&
                casilla.tocada()) {
                return true;
            }
        }
        return false;
    }

    /**
     * devuelve verdad si la casilla coincide con alguna del barco
     * 
     */
    public boolean disparar(Casilla casillaSugerida) {
        for (Casilla casilla : casillas) {
            if (casilla.equals(casillaSugerida)) {
                casilla.disparar(casillaSugerida);
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve verdad si todas las posiciones
     * del barco estan tocadas
     * 
     */
    public boolean estaHundido() {
        for (Casilla casilla : casillas) {
            if (! casilla.tocada()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Guarda una partida en un fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.print(casillas.size()+ " ");
        for (Casilla casilla : casillas) {
            casilla.guardar(pw);
        }
        pw.println();
    }
}
/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Controlador;

import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import juegobarcosv1.Vista.TableroVista;
import juegobarcosv1.Modelo.Tablero;

import juegobarcosv1.Modelo.Casilla;
import java.io.PrintWriter;
import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import juegobarcosv1.Modelo.Barco;
import juegobarcosv1.Controlador.HundirLaFlota;
import static juegobarcosv1.Controlador.HundirLaFlota.VERSION;
import juegobarcosv1.Modelo.TipoBarco;
import juegobarcosv1.Vista.JuegoVista;


/**
 * Partida de Hundir la flota
 * 
 */

public class Partida {
    Random random = new Random();

    public enum ResultadoDisparo {
        DISPARO_INVALIDO, BARCO_TOCADO,
        DISPARO_FALLADO, ADYACENTE,BARCO_HUNDIDO
    };

    public static final int MAX_INTENTOS = 15;
    public static final char INICIO_COORDENADAS = 'A';
    public static final char SIMBOLO_BARCO = '*';
    public static final char SIMBOLO_AGUA = 'X';

    private Tablero miTablero;
    private List<Casilla> jugadas;
    private JuegoVista vista;
    private boolean guardada ;

    /**
     * Construye una partida
     * 
     */
    public Partida(JuegoVista vista,int filas, int columnas) {
        miTablero = new Tablero(filas, columnas);
        this.jugadas = new ArrayList<Casilla>();
        colocarBarcos();
        miTablero.nuevoObservador(vista);
        
        guardada = false;
    }
    
    /**
     * 
     * Construye una parrtida con una vista ya creada
     */
      public Partida (int filas, int columnas) {
        miTablero = new Tablero(filas, columnas);
        this.jugadas = new ArrayList<Casilla>();
        colocarBarcos();
        
        guardada = false;
    }

    /**
     * Construye una partida de un fichero
     * 
     */
    public Partida( JuegoVista vista,Scanner scanner) throws IOException {
        jugadas = new ArrayList<>();
        int numJugadas = scanner.nextInt();
        
        for (int i = 0; i < numJugadas; i++) {
            jugadas.add(new Casilla(scanner));
            
        }
        miTablero = new Tablero(scanner);
        miTablero.nuevoObservador(vista);
        String texto = barcosRestantes();
        for(Casilla casilla: jugadas){
        vista.recuperarIConocasilla(casilla, miTablero);
        vista.actualizarTextoBarcosRestantes(texto);
        
        }
        
        guardada = true;
    }
    /** 
     * 
     * @devuelve el toString de barcos restantes  
     */
    public String barcosRestantes(){
       return  miTablero.barcosRestantes();
    }
    
    /**
     * @devuelve si una casilla pertenece a un barco 
     */ 
    public Barco casillaPerteneBarco(Casilla casilla){
        return miTablero.perteneceBarco(casilla);
    }
    /**
     * @devuelve si una casilla pertenece a un barco hundido
     */ 
    public boolean barcoHundido(Casilla casilla){
        return miTablero.barcoHundido(casilla);
    }
    /**
     * 
     * @devuelve si  partida ha sido guardada
     */
    public boolean guardada(){
        return guardada;
    }

    /**
     * Colocar un barco en el tablero
     * 
     */
    public void colocarBarco(String ID) {
        miTablero.colocarBarco(ID);
    }
    
    /**
     * Colocar Barcos de la Partida
     * 
     */
    public void colocarBarcos() { 
        colocarBarco(TipoBarco.ID_CRUCERO);
        colocarBarco(TipoBarco.ID_DESTRUCTOR);
        colocarBarco(TipoBarco.ID_FRAGATA);
        colocarBarco(TipoBarco.ID_PORTAAVION);
    }

    /**
     * Dispara a una posicion del tablero
     * 
     */
    public Partida.ResultadoDisparo dispararCasilla(Casilla casilla) {
        ResultadoDisparo resultado;

        if (!casilla.valida(miTablero.devolverFilas(),
                miTablero.devolverColumnas())) {
            return ResultadoDisparo.DISPARO_INVALIDO;
        }

        resultado = miTablero.disparar(casilla);
        jugadas.add(casilla);

        if (miTablero.esAdyacente(casilla)) {
            return ResultadoDisparo.ADYACENTE;
        }
        guardada = false;
        return resultado;
    }

    /**
     * toString()
     * 
     */
    public String toString() {
        String s = "";
        s = s + " " + " ";

        for (int k = 0; k < miTablero.devolverColumnas(); k++) {
            s = s + (char) (INICIO_COORDENADAS + k) + " ";
        }

        for (int i = 0; i < miTablero.devolverFilas(); i++) {
            s = s + '\n' + (char) (INICIO_COORDENADAS + i) + " ";
            for (int j = 0; j < miTablero.devolverColumnas(); j++) {

                boolean esBarco = false;
                boolean esJugada = false;

                for (Casilla jugada : jugadas) {
                    if (jugada.equals(new Casilla(i, j,
                            false))) {
                        esBarco = miTablero.esBarco(jugada);
                        esJugada = true;
                        break;
                    }
                }

                if (esJugada && esBarco) {
                    s = s + SIMBOLO_BARCO + " ";
                } else if (esJugada && !esBarco) {
                    s = s + SIMBOLO_AGUA + " ";
                } else {
                    s = s + "  " + " ";
                }
            }
        }
        s = s + '\n' + miTablero.barcosRestantes();
        return s;
    }

    /**
     * Guardar el estado de la partida en el fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.println(jugadas.size() + " ");
        for (Casilla jugada : jugadas) {
            jugada.guardar(pw);
        }
        pw.println();
        miTablero.guardar(pw);
        guardada = true;
    }

}

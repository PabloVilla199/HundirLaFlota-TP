/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Modelo;

import juegobarcosv1.Controlador.Partida;
import juegobarcosv1.Controlador.Partida.ResultadoDisparo;

import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Tablero de Hundir la flota
 * 
 */
public class Tablero {
    public static final int MAX_INTENTOS = 15;
    public static final char INICIO_COORDENADAS = 'A';
    public static final char SIMBOLO_BARCO = '*';
    public static final char SIMBOLO_AGUA = 'X';
    public static final int INCREMENTO_FILAS_COLUMNAS = 1;
    public static final int BARCO_TOCADO = 1;
    public static final int BARCO_ADYACENTE = 2;
    public static final int AGUA = 0;
    public static String DISPARO_ACERTADO = "disparo";
    public static String DISPARO_FALLADO = "agua";

    private int filas;
    private int columnas;
    private Map<String, List<Barco>> barcos;
    private PropertyChangeSupport observadores;


    /**
     * Construye el tablero de juego
     * 
     */
    public Tablero(int columnas, int filas) {
        this.columnas = columnas;
        this.filas = filas;
        this.barcos = new HashMap<>();
        observadores = new PropertyChangeSupport(this);

        barcos.put(TipoBarco.ID_CRUCERO, new ArrayList<>());
        barcos.put(TipoBarco.ID_DESTRUCTOR, new ArrayList<>());
        barcos.put(TipoBarco.ID_FRAGATA, new ArrayList<>());
        barcos.put(TipoBarco.ID_PORTAAVION, new ArrayList<>());

    }

    /**
     * Construye el tablero de juego de un fichero
     * 
     */
    public Tablero(Scanner scanner) throws IOException {
        observadores = new PropertyChangeSupport(this);
        
        scanner.nextLine();
        filas = scanner.nextInt(); 
        columnas = scanner.nextInt(); 
        int numTiposBarco = scanner.nextInt();
        
        this.barcos = new HashMap<>();
        for (int i = 0; i < numTiposBarco; i++) {
            
            String IDBarco = scanner.next();            
            scanner.nextLine();
            int numBarcos = scanner.nextInt();  
            for (int j = 0; j < numBarcos; j++) {
                
                Barco barco = new Barco(scanner); 
                barco = new Barco(IDBarco,barco.devolverCasillas());
                List<Barco> listaBarcos = barcos.getOrDefault(IDBarco,
                                                     new ArrayList<>());
                listaBarcos.add(barco);
                barcos.put(IDBarco, listaBarcos);
            }
        }
    }
    
    /**
     * Añade observador del tablero
     * 
     */
    public void nuevoObservador(PropertyChangeListener observador) {
        this.observadores.addPropertyChangeListener(observador);
    }
    
    /**
     * Devuelve las columnas del tablero
     * 
     */
    public int devolverColumnas() {
        return columnas;
    }

    /**
     * Devuelve las filas del tablero
     * 
     */
    public int devolverFilas() {
        return filas;
    }
    
    /**
     * Devuelve verdad si la casilla esta dentro del tablero
     * 
     */
    public boolean casillaValida(Casilla casilla) {
        return casilla.valida(filas, columnas);
    }

    
    /**
     * 
     * devuuelve si un posible barco se solapa con un barco del tablero
     */
    public boolean solapa(Barco barcoPosible) {
        for (List<Barco> barcos : barcos.values()) {
            for (Barco barco : barcos) {
                if (barcoPosible.solapa(barco)) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Devuelve FALSO si alguna posicion de un posible
     * barco se encuentra fuera del rango del tablero
     * en caso contrario devuelve VERDAD
     *
     */
    public boolean casillasValidas(Barco barcoPosible) {
        for (Casilla casillaBarco : barcoPosible.devolverCasillas()) {
            if (! casillaValida(casillaBarco)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 
     * Comprueba que las casillas de un posible barco son correctas 
     * (dentro de los limites) y que no se solapa con ningun otro barco 
     */
    public boolean barcoValido(Barco barcoPosible) {
        if (casillasValidas(barcoPosible) && ! solapa(barcoPosible)) {
            return true;
        }
        return false;
    }

    /**
     * Coloca un Barco de una clave especifica de manera aleatoria
     * 
     */
    public boolean colocarBarco(String ID) {
        TipoBarco tipoBarco = new TipoBarco(ID);
        boolean colocado = false;
        int intentos = 0;
        Barco barcoPosible;
        do {
            barcoPosible = new Barco(tipoBarco);

            if (barcoValido(barcoPosible)) {
                colocado = true;
            }
            intentos++;

        } while (!colocado && intentos < MAX_INTENTOS);

        if (colocado) {
            barcos.get(ID).add(barcoPosible);
            return true;
        }
        return false;
    }
    
    /**
     * Devuelve VERDAD si la casilla forma parte de un barco
     * 
     */
    public boolean esBarco(Casilla casilla) {
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if (barco.tocada(casilla)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 
     * Compruueba si un barco esta hundido
     * @return 
     */
    public boolean barcoHundido(Casilla casilla){
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if(barco.estaHundido()){
                    if (barco.tocada(casilla)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * Devuelve el barco al q pertenece la casilla 
     */
     public Barco perteneceBarco(Casilla casilla){
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if(barco.tocada(casilla)){
                    return barco;

                    }
                }
            }
            return null;
    }
     
    /**
     * Disparo en el tablero
     * 
     */
    public Partida.ResultadoDisparo disparar(Casilla casilla) {
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if (barco.disparar(casilla)) {
                    this.observadores.firePropertyChange(
                            DISPARO_ACERTADO, null, 
                                              new Tupla<>(casilla,
                              Partida.ResultadoDisparo.BARCO_TOCADO));
                    
                    return Partida.ResultadoDisparo.BARCO_TOCADO;
                }
            }
        }
        this.observadores.firePropertyChange(
                                 DISPARO_FALLADO, null,
                new Tupla<>(casilla,
                          Partida.ResultadoDisparo.DISPARO_FALLADO));
        
                return Partida.ResultadoDisparo.DISPARO_FALLADO;
    }
    
    /**
     * 
     * Devuelve el estado de la partida
     */
    public Partida.ResultadoDisparo estadoCasilla(Casilla casilla){ 
        
        if(esBarco(casilla) && casilla.tocada()){
            return Partida.ResultadoDisparo.BARCO_TOCADO;
        }
        else if (casilla.tocada()){
            return Partida.ResultadoDisparo.DISPARO_FALLADO;
        }
        else{
            return Partida.ResultadoDisparo.DISPARO_INVALIDO;
        }
    }


    /**
     * devuelve VERDAD si una casilla es adyacente a un Barco
     * 
     */
    public boolean esAdyacente(Casilla casilla) {
        int[][] casillasAdyacentes = { { 0, 1 }, { 1, 0 }, { 1, 1 },
                { 0, -1 }, { -1, 0 }, { -1, 1 },
                { 1, -1 }, { -1, -1 } };

        for (int i = 0; i < casillasAdyacentes.length; i++) {

            int[] casillaAdyacente = casillasAdyacentes[i];
            Casilla adyacente = new Casilla(filas + casillaAdyacente[0],
                    columnas + casillaAdyacente[1],
                    false);

            if (adyacente.equals(casilla) && !casilla.tocada()) {
                return true;
            }
        }
        return false;
    }

   
    /**
     * String que muestra los barcos que aun no han sido hundidos
     * 
     */
    public String barcosRestantes() {
        String tipoBarco = "";
        String s = "Barcos restantes:\n";

        for (Map.Entry<String, List<Barco>> entry : barcos.entrySet()) {
            tipoBarco = entry.getKey();
            s += '\n';
            s += tipoBarco + ":" + cuentaBarcosRestantes(tipoBarco) 
                 + '\n' + " ";
        }
        return s;
    }
    
    /**
     * 
     * Cuenta los barcos restantes por ID
     */
    public int cuentaBarcosRestantes(String ID) {
        int barcosHundidos = 0;
        if (barcos.containsKey(ID)) {
            for (Barco barco : barcos.get(ID)) {
                if (! barco.estaHundido()) {
                    barcosHundidos++;
                }
            }
        }

        return barcosHundidos;
    }

    /**
     * Guarda el tablero en un fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.println(filas);
        pw.println(columnas);
        
        pw.println(barcos.size());

        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                pw.println(barco.devolverTipo());
                pw.print( listaBarcos.size()+ " ");
                barco.guardar(pw);
            }
        }
    }

}
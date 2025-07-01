/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Icon;
import javax.swing.JPanel;

import juegobarcosv1.Modelo.Casilla;

/**
 * Vista Swing del tablero
 * 
 */
public class TableroVista extends JPanel {
    private static final int ALTURA_FILA = 20;
    private static final int ANCHURA_COLUMNA = 20;
    private CasillaVista casillas[][];
    private JuegoVista juegoVista;
    private boolean habilitado = false;

    public static final boolean RECIBIR_EVENTOS_RATON = true;
    public static final boolean NO_RECIBIR_EVENTOS_RATON = false;

    /**
     * Construye el panel del tablero de filas x columnas
     * 
     */
    TableroVista(JuegoVista juegoVista, int filas, int columnas,
            boolean recibeEventosRaton) {
        this.setEnabled(false);
        this.juegoVista = juegoVista;

        setLayout(new GridLayout(filas, columnas));

        crearCasillas(filas, columnas, recibeEventosRaton);

        this.setPreferredSize(new Dimension(filas * ALTURA_FILA,
                columnas * ANCHURA_COLUMNA));
    }

    /**
     * Crea casillas
     * 
     */
    private void crearCasillas(int filas, int columnas,
            boolean recibeEventosRaton) {
        casillas = new CasillaVista[filas][columnas];
        for (int fil = 0; fil < filas; fil++)
            for (int col = 0; col < columnas; col++) {
                casillas[fil][col] = new CasillaVista(juegoVista, 
                        new Casilla(fil, col,
                              recibeEventosRaton),
                        recibeEventosRaton);
                add(casillas[fil][col]);
            }
    }

    /**
     * Inicializa tablero
     * 
     */
    void inicializar() {
        for (int fil = 0; fil < casillas.length; fil++) {
            for (int col = 0; col < casillas[0].length; col++) {
                ponerIconoCasilla(new Casilla(fil, 
                         col, false), null);
            }
        }
    }

    /**
     * Devuelve el tamaño del tablero
     * 
     */
    Dimension dimensionCasilla() {
        return casillas[0][0].getSize();
    }

    /**
     * Habilita o deshabilita tablero vista
     * 
     */
    void habilitar(boolean habilitacion) {
        this.setEnabled(habilitacion);

        for (int fil = 0; fil < casillas.length; fil++) {
            for (int col = 0; col < casillas[0].length; col++) {
                casillas[fil][col].habilitar(habilitacion);
            }
        }
    }

    /**
     * Pone un icono en la casilla de la posición indicada
     * 
     */
    void ponerIconoCasilla(Casilla posicion, Icon icono) {
        casillas[posicion.devolverfila()][posicion.devolverColumna()]
                .ponerIcono(icono);
    }
}

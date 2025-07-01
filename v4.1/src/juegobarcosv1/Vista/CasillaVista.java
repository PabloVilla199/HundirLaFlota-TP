/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */
package juegobarcosv1.Vista;

import juegobarcosv1.Controlador.OyenteVista;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import juegobarcosv1.Modelo.Casilla;

/**
 * Vista de una casilla del tablero a partir de un JLabel
 * 
 */
public class CasillaVista extends JLabel {
    private Casilla posicion;
    private JuegoVista juegoVista;

    /**
     * Construye una vista con el icono indicado de la casilla en una posición
     * 
     */

    CasillaVista(JuegoVista juegoVista, Casilla posicion, Icon icono,
                 boolean recibeEventosRaton) {
        this.posicion = posicion;
        this.juegoVista = juegoVista;
        setIcon(icono);

        setEnabled(false);

        setHorizontalAlignment(SwingConstants.CENTER);
        // setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        if (recibeEventosRaton) {
            recibirEventosRaton();
        }
    }

    /**
     * Construye una vista sin icono de la casilla en una posición
     * 
     */
    CasillaVista(JuegoVista vista, Casilla casilla,
                 boolean recibeEventosRaton) {
      this(vista, casilla, null, recibeEventosRaton);
    }

    /**
     * Recibe los eventos de ratón
     * 
     */
    private void recibirEventosRaton() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    juegoVista.notificacion(OyenteVista.Evento.DISPARAR,
                                            posicion);
                }
            }
        });
    }

    /**
     * Habilita la casilla
     * 
     */
    void habilitar(boolean habilitacion) {
        setEnabled(habilitacion);
    }

    /**
     * Devuelve la posición de la casilla
     * 
     */
    Casilla devuelvePosicion() {
        return posicion;
    }

    /**
     * Pone icono
     * 
     */
    void ponerIcono(Icon icono) {
        setIcon(icono);
    }

    /**
     * Sobreescribe toString
     * 
     */
    @Override
    public String toString() {
        return posicion.toString();
    }
}

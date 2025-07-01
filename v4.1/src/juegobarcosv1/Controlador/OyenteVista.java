/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Controlador;

public interface OyenteVista {

    public enum Evento {
        NUEVA, ABRIR, GUARDAR, GUARDAR_COMO,
        ACTUALIZAR_TEXTO_BARCOS_RESTANTES,
        DISPARAR,SALIR,CAMBIAR_LENGUAJE
    }

    /**
     * Llamado para notificar un evento de la interfaz de usuario
     * 
     */
    public void eventoProducido(Evento evento, Object obj);
}

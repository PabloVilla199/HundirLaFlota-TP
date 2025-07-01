/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoBarco {
    public static final int LONGITUD_FRAGATA = 2;
    public static final int LONGITUD_PORTAAVION = 5;
    public static final int LONGITUD_CRUCERO = 3;
    public static final int LONGITUD_DESTRUCTOR = 4;

    public static final String ID_FRAGATA = "Fragata";
    public static final String ID_PORTAAVION = "Portaavion";
    public static final String ID_CRUCERO = "Crucero";
    public static final String ID_DESTRUCTOR = "Destructor";

    Map<String, List<Casilla>> forma = new HashMap<>();
    String ID = " ";

    public TipoBarco(String ID) {
        this.ID = ID;
        forma = new HashMap<>();

        forma.put(TipoBarco.ID_CRUCERO, new ArrayList<>());
        forma.put(TipoBarco.ID_DESTRUCTOR, new ArrayList<>());
        forma.put(TipoBarco.ID_FRAGATA, new ArrayList<>());
        forma.put(TipoBarco.ID_PORTAAVION, new ArrayList<>());

        forma.get(TipoBarco.ID_CRUCERO).addAll(generarCasillas(
                                        TipoBarco.LONGITUD_CRUCERO));
        forma.get(TipoBarco.ID_DESTRUCTOR).addAll(generarCasillas(
                                        TipoBarco.LONGITUD_DESTRUCTOR));
        forma.get(TipoBarco.ID_FRAGATA).addAll(generarCasillas(
                                        TipoBarco.LONGITUD_FRAGATA));
        forma.get(TipoBarco.ID_PORTAAVION).addAll(generarCasillas(
                                        TipoBarco.LONGITUD_PORTAAVION));

    }
    
    /**
     * 
     * Dado un tipo de barco se generan una forma de casillas  
     */
    private List<Casilla> generarCasillas(int longitud) {
        List<Casilla> casillas = new ArrayList<>();
        for (int i = 0; i < longitud; i++) {
            casillas.add(new Casilla(0, 0, false));
        }
        return casillas;
    }
    
    
    public List<Casilla> formaBarco(String ID) {
        List<Casilla> casillasBarco = new ArrayList<>();

        for (Map.Entry<String, List<Casilla>> entry : forma.entrySet()) {
            if (entry.getKey().equals(ID)) {
                casillasBarco = entry.getValue();
                break;
            }
        }
        return casillasBarco;
    }

}
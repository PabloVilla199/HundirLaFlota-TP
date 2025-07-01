
/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Controlador;

import juegobarcosv1.Controlador.OyenteVista.Evento;
import juegobarcosv1.Controlador.Partida.ResultadoDisparo;
import juegobarcosv1.Modelo.*;
import juegobarcosv1.Vista.JuegoVista;
import juegobarcosv1.Modelo.Casilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import juegobarcosv1.Vista.Localizacion;

/**
 * Juego Hundir la Flota
 * 
 */
public class HundirLaFlota implements OyenteVista {
    public static final int COLUMNAS = 9;
    public static final int FILAS = 9;
    public static final int BARCOS_MAX = 4;
    public static final String VERSION = "Hundir la flota 2.0";
    public static final String NUEVA_PARTIDA = "Nueva partida creada";
    public static final String PAIS = "country";
    public static final String LENGUAJE = "language";
    public static final String MENSAJE_EXITO = "Exitoso";
    public static final String MENSAJE_ERROR = 
            "Hay errores al guardar la partida";

    private static String ARG_DEBUG = "-d";
    private static boolean modoDebug = false;
    private static final String NOMBRE_ARCHIVO = "Version2.txt";
    private static final String FICHERO_CONFIG = "config.properties";
    private static String FICHERO_CONFIG_WRONG = 
       "Config file is wrong. Set default values";
    private static String COMENTARIO_CONFIG = 
            "country = ES|US, language = es|en";
    
    private Localizacion local; 
    private String  ficheroPartida = null;
    private Partida partida;
    private JuegoVista vista;
    private Properties configuracion;
    private String lenguaje;
    public String pais;


    public HundirLaFlota(String args[]) {
        procesarArgsMain(args); 
        leerConfiguracion();
     
        local = Localizacion.devolverInstancia(lenguaje, pais);
        vista =  JuegoVista.devolverInstancia(this, VERSION,
                                              FILAS, COLUMNAS,
                                              "español","españa");
    }
    

  /**
   *  Lee configuración
   * 
   */ 
    private void leerConfiguracion() {
        lenguaje = Locale.getDefault().getLanguage();
        pais = Locale.getDefault().getCountry();

        try {
            configuracion = new Properties();
            configuracion.load(new FileInputStream(FICHERO_CONFIG));

            lenguaje = configuracion.getProperty(LENGUAJE);
            pais = configuracion.getProperty(PAIS);
            
            if ((lenguaje == null) || (pais == null)) {
                lenguaje = Locale.getDefault().getLanguage();
                configuracion.setProperty(LENGUAJE, lenguaje);
                pais = Locale.getDefault().getCountry();
                configuracion.setProperty(PAIS, pais);
            }
        } catch (Exception e) {
            configuracion.setProperty(LENGUAJE, lenguaje);
            configuracion.setProperty(PAIS, pais);
        }
    }
    
    public void nuevaPartida(int filas, int columnas){
        guardarPartidaActual();
        ficheroPartida = null;
        vista.ponerTitulo(" ");
        vista.inicializarVista();
        partida = new Partida(vista,filas, columnas);
        vista.actualizarTextoBarcosRestantes(partida.barcosRestantes());
        vista.mensajeDialogo(NUEVA_PARTIDA);
        
        vista.habilitarEvento(Evento.GUARDAR, false);
        vista.habilitarEvento(Evento.DISPARAR, true);    
    }
    
    /**
     * Crear partida
     * 
     */
    public void crearPartida(int filas, int columnas) {
        partida = new Partida(vista,filas, columnas);
    }
    
    /**
     * Guarda partida actual
     * 
     */
    public void guardarPartida(String nombreArchivo) throws IOException {
        PrintWriter pw = new PrintWriter(new File(nombreArchivo));

        partida.guardar(pw);
        pw.close();

    }
    
     private void guardarPartida(){  
        if(ficheroPartida == null){
           ficheroPartida = vista.seleccionarFichero(
                                           JuegoVista.GUARDAR_FICHERO);  
        }
        try {
              guardarPartida(ficheroPartida);
              vista.mensajeDialogo(MENSAJE_EXITO);
           } catch (Exception e) {
                if(esModoDebug()){
                    e.printStackTrace();  
                    vista.mensajeDialogo(MENSAJE_ERROR);
                }
           }     
    }

    /**
     * Recupera una Partida de un Fichero
     * 
     */
    public void cargarPartida(String nombreArchivo) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(nombreArchivo));

        this.partida = new Partida(vista,scanner);
        scanner.close();

    }
    
    private void abrirPartida(){
        ficheroPartida = vista.seleccionarFichero(
                                            JuegoVista.ABRIR_FICHERO);
        if(ficheroPartida != null){
            try {   
                 cargarPartida(ficheroPartida);
                 vista.habilitarEvento(Evento.GUARDAR, false); 
                 vista.habilitarEvento(Evento.DISPARAR, true);
                }catch (IOException e) {
                    e.printStackTrace();
                } 
        }
    }
    
    private void guardarPartidaActual() {
        if ((partida != null) && (!partida.guardada())) {
            if (vista.mensajeConfirmacion(vista.CONFIRMACION_GUARDAR) 
                        == vista.OPCION_SI) {
                guardarPartida();
            }
        }
   }

    private void disparar(Casilla casilla) {
        Partida.ResultadoDisparo resultado;

        if (partida != null) {
            resultado = partida.dispararCasilla(casilla);

            if (resultado == Partida.ResultadoDisparo.BARCO_TOCADO) {
                vista.ponerIconoCasilla(casilla, resultado);
                vista.habilitarEvento(OyenteVista.Evento.DISPARAR,
                            true);
                vista.habilitarEvento(Evento.GUARDAR, true);
                vista.habilitarEvento(
                                Evento.ACTUALIZAR_TEXTO_BARCOS_RESTANTES,
                            true);
                if(partida.barcoHundido(casilla)){
                    Barco barcoHundido;
                    barcoHundido = partida.casillaPerteneBarco(casilla);
                    
                    for(Casilla CasillaBarco: barcoHundido.devolverCasillas()){
                        vista.ponerIconoCasilla(CasillaBarco,
                                        ResultadoDisparo.BARCO_HUNDIDO); 
                    }
                     String texto = partida.barcosRestantes();
                     vista.actualizarTextoBarcosRestantes(texto);
                
                }                
                
            } else if (resultado == Partida.ResultadoDisparo.DISPARO_FALLADO) {
                vista.ponerIconoCasilla(casilla, resultado);
                vista.habilitarEvento(OyenteVista.Evento.DISPARAR, 
                            true);
                vista.habilitarEvento(Evento.GUARDAR, true);

            }
        }
    }

    private void salir() {

        guardarPartidaActual();

        // guarda configuración
        try {
            FileOutputStream fichero = new FileOutputStream(FICHERO_CONFIG);
            configuracion.store(fichero, COMENTARIO_CONFIG);
            fichero.close();
        } catch (Exception e) {
            mensajeError(local.CONFIGURACION_NO_GUARDADA, e);
        }
        System.exit(0);
    }
    

    
    
       
    @Override
    public void eventoProducido(Evento evento, Object obj) {
        switch (evento) {
            case NUEVA:
                nuevaPartida(FILAS, COLUMNAS);
                break;

            case ABRIR:
                abrirPartida();
                break;

            case SALIR:
                salir();
                break;

            case GUARDAR:
                guardarPartida();
                break;
                
            case ACTUALIZAR_TEXTO_BARCOS_RESTANTES: 
                String texto = partida.barcosRestantes();
                vista.actualizarTextoBarcosRestantes(texto);
                break;
               
            case CAMBIAR_LENGUAJE:
                cambiarLenguaje((Tupla)obj);
                break;
            case DISPARAR:
                disparar((Casilla) obj);

                break;
        }
    }
    
    private void cambiarLenguaje(Tupla tupla) {  
        configuracion.setProperty(LENGUAJE, (String)tupla.a);
        configuracion.setProperty(PAIS, (String)tupla.b);
        System.out.println((tupla.a));
        System.out.println(tupla.b);
        salir();    
    }
    
    /**
     * Devuelve el resultado del disparo
     *
     */
    public ResultadoDisparo dispararCasilla(Casilla casilla) {
        return partida.dispararCasilla(casilla);

    }
    
    private void mensajeError(String mensaje, Exception e) {
        if (esModoDebug()) {
            e.printStackTrace();
        }
        vista.mensajeDialogo(mensaje);
    }

    /**
     * Devuelve verdadero si aplicación está en modo debug
     * o falso en caso contrario
     * 
     */
    public static boolean esModoDebug() {
        return modoDebug;
    }

    /**
     * Procesa argumentos de main
     * 
     */
    public static void procesarArgsMain(String[] args) {
        List<String> argumentos = new ArrayList<>(Arrays.asList(args));

        if (argumentos.contains(ARG_DEBUG)) {
            modoDebug = true;
        }
    }

    /**
     * toString()
     *
     */
    @Override
    public String toString() {
        return partida.toString() + "\n";
    }

    
    /**
     * 
     * Metodo main 
     */
    public static void main (String[] args){
        new HundirLaFlota(args);
    }

}

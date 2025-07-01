/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */

package juegobarcosv1.Vista;

import juegobarcosv1.Controlador.HundirLaFlota;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.UIManager;

/**
 * Localizacion recursos de la vista
 *
 */
public class Localizacion {

    private static Localizacion instancia = null; // es singleton
    private ResourceBundle recursos;

    private static final String FICHERO_LOCALIZACION = "localizacion";
    public static final String RUTA_RECURSOS = "/Vista/recursos/";

    public static String LENGUAJE_ESPANOL = "es";
    public static String PAIS_ESPANA = "ES";
    public static String LENGUAJE_INGLES = "en";
    public static String PAIS_USA = "US";

    /**
     * Identificadores de textos dependientes del idioma
     */
    
    /* JuegoVista */
    public static final String BOTON_NUEVO = "BOTON_NUEVO";
    public static final String BOTON_ABRIR = "BOTON_ABRIR";
    public static final String BOTON_GUARDAR = "BOTON_GUARDAR";
    public static final String BOTON_GUARDAR_COMO = "BOTON_GUARDAR_COMO";
    public static final String BOTON_SALIR = "BOTON_SALIR";
    public static final String BOTON_OPCIONES = "MENU_OPCIONES";
    public static final String BOTON_ACERCA_DE = "BOTON_ACERCA_DE";

    public static final String TITULO = "TITULO";
    public static final String MENU_OPCIONES = "MENU_OPCIONES";
    public static final String MENU_ITEM_NUEVA = "MENU_ITEM_NUEVA";
    public static final String ATAJO_MENU_ITEM_NUEVA = 
            "ATAJO_MENU_ITEM_NUEVA";
    public static final String MENU_ITEM_NUEVA_ONLINE =
            "MENU_ITEM_NUEVA_ONLINE";
    public static final String ATAJO_MENU_ITEM_NUEVA_ONLINE
            = "ATAJO_MENU_ITEM_NUEVA_ONLINE";
    public static final String MENU_ITEM_ABRIR = "MENU_ITEM_ABRIR";
    public static final String ATAJO_MENU_ITEM_ABRIR = 
            "ATAJO_MENU_ITEM_ABRIR";
    public static final String MENU_ITEM_GUARDAR = "MENU_ITEM_GUARDAR";
    public static final String ATAJO_MENU_ITEM_GUARDAR = 
            "ATAJO_MENU_ITEM_GUARDAR";
    public static final String MENU_ITEM_GUARDAR_COMO = 
            "MENU_ITEM_GUARDAR_COMO";
    public static final String ATAJO_MENU_ITEM_GUARDAR_COMO
            = "ATAJO_MENU_ITEM_GUARDAR_COMO";
    public static final String MENU_ITEM_FINALIZAR_ONLINE
            = "MENU_ITEM_FINALIZAR_ONLINE";
    public static final String ATAJO_MENU_ITEM_FINALIZAR_ONLINE
            = "ATAJO_MENU_ITEM_FINALIZAR_ONLINE";

    public static final String MENU_ITEM_OPCIONES = "MENU_ITEM_OPCIONES";
    public static final String MENU_LENGUAJE = "MENU_ITEM_LENGUAJE";
    public static final String MENU_ESPANOL = "MENU_ITEM_ESPANOL";
    public static final String MENU_INGLES = "MENU_ITEM_INGLES";

    public static final String ATAJO_MENU_ITEM_SALIR = 
            "ATAJO_MENU_ITEM_SALIR";
    public static final String MENU_AYUDA = "MENU_AYUDA";
    public static final String MENU_ITEM_DEBUG = "MENU_ITEM_DEBUG";
    public static final String ATAJO_ITEM_DEBUG = "ATAJO_ITEM_DEBUG";
    public static final String MENU_ITEM_ACERCA_DE = "MENU_ITEM_ACERCA_DE";
    public static final String ATAJO_ITEM_ACERCA_DE = "ATAJO_ITEM_ACERCA_DE";

    public static final String ESTADO_INICIAL = "ESTADO_INICIAL";
    public static final String FICHA_ONLINE = "FICHA_ONLINE";

    public static final String BOTON_ACEPTAR = "BOTON_ACEPTAR";
    public static final String BOTON_CANCELAR = "BOTON_CANCELAR";

    public static final String CONFIGURACION_NO_ENCONTRADA
            = "CONFIGURACION_NO_ENCONTRADA";
    //public static final String EXT_FICHERO_PARTIDA = ".txt";
    public static final String FILTRO_PARTIDAS = "FILTRO_PARTIDAS";
    public static final String TURNO = "TURNO";
    public static final String CONFIRMACION_GUARDAR = 
            "CONFIRMACION_GUARDAR";
    public static final String CONFIRMACION_LENGUAJE = 
            "CONFIRMACION_LENGUAJE";
    public static final String PARTIDA_FIN_TRES_EN_RAYA = 
            "PARTIDA_FIN_TRES_EN_RAYA";
    public static final String PARTIDA_FIN_EMPATE = "PARTIDA_FIN_EMPATE";
    public static final String PARTIDA_NO_GUARDADA = "PARTIDA_NO_GUARDADA";
    public static final String PARTIDA_NO_LEIDA = "PARTIDA_NO_LEIDA";
    public static final String FICHERO_PARTIDA_NO_ENCONTRADO
            = "FICHERO_PARTIDA_NO_ENCONTRADO";
    public static final String CONFIGURACION_NO_GUARDADA = 
            "CONFIGURACION_NO_GUARDADA";

    /* identificadores sistema */
    String[] sistema = {
        "OptionPane.cancelButtonText",
        "OptionPane.okButtonText",
        "OptionPane.noButtonText",
        "OptionPane.yesButtonText",
        "OptionPane.yesButtonText",
        "FileChooser.openDialogTitleText",
        "FileChooser.saveDialogTitleText",
        "FileChooser.lookInLabelText",
        "FileChooser.saveInLabelText",
        "FileChooser.openButtonText",
        "FileChooser.saveButtonText",
        "FileChooser.cancelButtonText",
        "FileChooser.fileNameLabelText",
        "FileChooser.filesOfTypeLabelText",
        "FileChooser.openButtonToolTipText",
        "FileChooser.cancelButtonToolTipText",
        "FileChooser.fileNameHeaderText",
        "FileChooser.upFolderToolTipText",
        "FileChooser.homeFolderToolTipText",
        "FileChooser.newFolderToolTipText",
        "FileChooser.listViewButtonToolTipText",
        "FileChooser.newFolderButtonText",
        "FileChooser.renameFileButtonText",
        "FileChooser.deleteFileButtonText",
        "FileChooser.filterLabelText",
        "FileChooser.detailsViewButtonToolTipText",
        "FileChooser.fileSizeHeaderText",
        "FileChooser.fileDateHeaderText",
        "FileChooser.acceptAllFileFilterText"};

    /**
     * Construye localizacion
     *
     */
    private Localizacion(String lenguaje, String pais) {
        try {
            Locale locale = new Locale(lenguaje, pais);
            System.out.println(JuegoVista.RUTA_RECURSOS.substring(1).
                    replace('/', '.')
                    + FICHERO_LOCALIZACION + locale);
            recursos = ResourceBundle
                    .getBundle(JuegoVista.RUTA_RECURSOS.substring(1).
                            replace('/', '.')
                            + FICHERO_LOCALIZACION, locale);

            // localiza textos sistema si no son los de defecto
            if (!locale.equals(Locale.getDefault())) {
                for (int i = 0; i < sistema.length; i++) {
                    UIManager.put(sistema[i],
                            recursos.getString(sistema[i]));
                }
            }
        } catch (MissingResourceException e) {
        }
    }

    /**
     * Devuelve la instancia de la localizacion
     *
     */
    public static synchronized Localizacion devolverInstancia(
            String lenguaje, String pais) {
        if (instancia == null) {
            instancia = new Localizacion(lenguaje, pais);
        }
        return instancia;
    }

    /**
     * Localiza recurso
     *
     */
    public String devuelve(String texto) {
        return recursos.getString(texto);
    }
}

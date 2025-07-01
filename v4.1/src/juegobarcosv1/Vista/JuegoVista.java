/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de calidad
 *  Alvaro Perez 870097 Responsable de funcionamiento
 * 30/01/24
 */
package juegobarcosv1.Vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.filechooser.FileNameExtensionFilter;

import juegobarcosv1.Modelo.Casilla;
import juegobarcosv1.Controlador.OyenteVista;
import juegobarcosv1.Controlador.Partida.ResultadoDisparo;
import juegobarcosv1.Controlador.Partida;
import juegobarcosv1.Modelo.Barco;
import juegobarcosv1.Modelo.Tablero;
import juegobarcosv1.Modelo.Tupla;

public class JuegoVista extends JFrame implements ActionListener, 
                                        PropertyChangeListener {
    private static JuegoVista instancia = null; // es singleton
    private OyenteVista oyenteVista;
    private String pais;
    private String lenguaje;
    private String version;
    private TableroVista tableroVista;
    private Tablero tableroModelo;
    private JLabel lineaEstado;
    private ImageIcon icono;
    private ImageIcon IconoBarco;
    private ImageIcon IconoAgua;
    private ImageIcon IconoBarcoHundido;
    private JMenuItem menuFicheroGuardar;
    private JMenuItem menuFicheroGuardarComo;
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JButton botoNuevo;
    private JButton botonInfo;
    private JButton botonSalir;
    private JButton botonOpciones;
    private JTextArea barcosTextArea; 
    private JRadioButtonMenuItem radioBotonEsp;
    private JRadioButtonMenuItem radioBotonIng;
    private Map<String, JRadioButtonMenuItem> botonesLenguaje = new HashMap<>();
   
    public static final int ABRIR_FICHERO = 0;
    public static final int GUARDAR_FICHERO = 1;
    public static final int OPCION_SI = JOptionPane.YES_OPTION;
    private static final int ANCHO_VENTANA = 25;
    private static final int LARGO_VENTANA = 25; 
    
    private Localizacion local;
     
    public static final String DISPARO_ACERTADO = " Has alcanzado un Barco";
    public static final String DISPARO_FALLADO = " Has Fallado el disparo";
    public static final String EXT_FICHERO_PARTIDA = ".txt";
    public static final String FILTRO_PARTIDAS = "Partidas";
    public static final String CONFIRMACION_GUARDAR = 
            "¿Quieres guardar la partida actual?";
    public static final String PARTIDA_FIN = "¡¡ Hundida la flota !!";
    public static final String PARTIDA_FIN_EMPATE = "¡¡ Empate !!";
    public static final String PARTIDA_NO_GUARDADA = 
            "No pudo guardarse partida";
    public static final String PARTIDA_NO_LEIDA = "No pudo leerse partida";
    public static final String FICHERO_NO_ENCONTRADO = "Fichero no encontrado";

    private static final String RUTA_ICONO_AGUA  = 
            "juegobarcosv1/Vista/recursos/Agua.jpg";
    private static final String RUTA_ICONO_BARCO  = 
            "juegobarcosv1/Vista/recursos/Barco.jpg";
    public static final String RUTA_RECURSOS = 
            "/juegobarcosv1/Vista/recursos/";
    private static final String RUTA_ICONO_BARCO_HUNDIDO  = 
            "juegobarcosv1/Vista/recursos/BarcoHundido.jpg";



 
    private static final int MARGEN_HORIZONTAL = 50;
    private static final int MARGEN_VERTICAL = 100;

    /**
     * Construye la vista del tablero de filas x columnas con el oyente para
     * eventos de la interfaz de usuario indicado
     * 
     */
    public JuegoVista(OyenteVista oyenteVista, String version,
                      int filas, int columnas,String lenguaje, String pais) {
        super(version);
        this.oyenteVista = oyenteVista;
        this.version = version;
        this.lenguaje = lenguaje;
        this.pais = pais;
        
        local = Localizacion.devolverInstancia(lenguaje, pais);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                oyenteVista.eventoProducido(OyenteVista.Evento.SALIR,
                                            null);
            }
        });
        
        crearElementosVentanaPrincipal( filas, columnas);  
        setVisible(true);  
    }
    
    public void crearElementosVentanaPrincipal(int filas, int columnas){
        ponerTitulo(" ");
        setLayout(new BorderLayout());

        JPanel panelNorte = new JPanel();
        JPanel panelEste = new JPanel();
        panelNorte.setLayout(new GridLayout(2, 1));

        creaBarraHerramientas(panelNorte);
        add(panelNorte, BorderLayout.NORTH);

        JPanel panelizq = new JPanel();
        creaTablero(panelizq, filas, columnas);
        add(panelizq, BorderLayout.WEST);
        
        BarcosRestantes(panelEste);
        add(panelEste, BorderLayout.EAST);

        ClassLoader classLoader = getClass().getClassLoader();
        Icon iconoAgua = new ImageIcon(classLoader.
                getResource(RUTA_ICONO_AGUA));
        Icon iconoBarco = new ImageIcon(classLoader.
                getResource(RUTA_ICONO_BARCO));
        Icon iconoBarcoHundido = new ImageIcon(classLoader.
                getResource(RUTA_ICONO_BARCO_HUNDIDO));

        Image imagenAgua = ((ImageIcon) iconoAgua).getImage()
                             .getScaledInstance(ANCHO_VENTANA, LARGO_VENTANA, 
                                                Image.SCALE_SMOOTH);
        Image imagenBarco = ((ImageIcon) iconoBarco).getImage()
                             .getScaledInstance(ANCHO_VENTANA, LARGO_VENTANA,
                                                Image.SCALE_SMOOTH);
        Image imagenBarcoHundido = ((ImageIcon) iconoBarcoHundido).getImage()
                             .getScaledInstance(ANCHO_VENTANA, LARGO_VENTANA,
                                                Image.SCALE_SMOOTH);

        IconoAgua = new ImageIcon(imagenAgua);
        IconoBarco = new ImageIcon(imagenBarco);
        IconoBarcoHundido = new ImageIcon(imagenBarcoHundido);

        setResizable(false);
        setSize((int) (tableroVista.dimensionCasilla().getWidth() *
                       columnas + MARGEN_HORIZONTAL),
                (int) (tableroVista.dimensionCasilla().getHeight() *
                        filas + MARGEN_VERTICAL));

        pack(); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    /**
     * Devuelve la instancia de la vista del tablero
     * 
     */
    public static synchronized JuegoVista devolverInstancia(
            OyenteVista oyenteIU, String version, int filas, int columnas,
            String lenguaje, String pais) {
        if (instancia == null){
            instancia = new JuegoVista(oyenteIU, version, filas,
                                       columnas,lenguaje, pais);           
        }     
        return instancia;
    }

    /**
     * Crea barra de herramientas
     * 
     */
    private JButton crearBoton(JToolBar barra, String command, Localizacion local, boolean enabled) {
        JButton boton = new JButton(command);
        boton.setText(local.devuelve(command));
        boton.setToolTipText(local.devuelve(command));
        
        boton.addActionListener(this);
        boton.setActionCommand(command);
        boton.setEnabled(enabled);
        
        barra.add(boton);
        barra.addSeparator();
        return boton;
    }

    private void creaBarraHerramientas(JPanel panelNorte) {
        JToolBar barra = new JToolBar();
        barra.setFloatable(false);

        botoNuevo = crearBoton(barra, local.BOTON_NUEVO, local, true);
        botonAbrir = crearBoton(barra, local.BOTON_ABRIR, local, true);
        botonGuardar = crearBoton(barra, local.BOTON_GUARDAR, local, false);
        botonOpciones = crearBoton(barra, local.MENU_OPCIONES, local, true);
        botonInfo = crearBoton(barra, local.BOTON_ACERCA_DE, local, true);
        botonSalir = crearBoton(barra, local.BOTON_SALIR, local, true);

        panelNorte.add(barra);
    }

    
    /**
     * Actualiza el texto de la barra de barcos restantes
     * 
     */
    
     private void BarcosRestantes(JPanel panelEste) {
       barcosTextArea = new JTextArea();
      barcosTextArea.setText("Barcos a Hundir: ");
      
       panelEste.add(barcosTextArea);

    }

    public void actualizarTextoBarcosRestantes(String texto) {
        barcosTextArea.setText(texto);
    }
    
     private void cambiarLenguaje(String nuevoLenguaje, String nuevoPais) {
        if (!lenguaje.equals(nuevoLenguaje)) {
            if (mensajeConfirmacion(local.devuelve(
                    Localizacion.CONFIRMACION_LENGUAJE)) == OPCION_SI) {
                System.out.println(nuevoLenguaje + nuevoPais);
                oyenteVista.eventoProducido(
                   OyenteVista.Evento.CAMBIAR_LENGUAJE,
                        new Tupla(nuevoLenguaje, nuevoPais));
            } 
            else {
                botonesLenguaje.get(lenguaje).setSelected(true);
            }
        }
    }



    /**
     * Crea la vista del tablero
     * 
     */
    private void creaTablero(JPanel panel, int numFilTab, int numColTab) {
        tableroVista = new TableroVista(this, numFilTab, numColTab,
                TableroVista.RECIBIR_EVENTOS_RATON);
        panel.add(tableroVista);
        habilitarEvento(OyenteVista.Evento.DISPARAR, true);
    }
    
    private void mostrarDialogoOpcionesIdioma() {
        JPanel panelRadioButtons = new JPanel();
        panelRadioButtons.setLayout(new GridLayout(2, 1));

        JRadioButton botonEspanol = new JRadioButton(
                local.devuelve(local.MENU_ESPANOL));
        JRadioButton botonIngles = new JRadioButton(
                local.devuelve(local.MENU_INGLES));

        ButtonGroup grupoIdiomas = new ButtonGroup();
        grupoIdiomas.add(botonEspanol);
        grupoIdiomas.add(botonIngles);

        if (lenguaje.equals(local.LENGUAJE_ESPANOL)) {
            botonEspanol.setSelected(true);
        } else if (lenguaje.equals(local.LENGUAJE_INGLES)) {
            botonIngles.setSelected(true);
        }

        panelRadioButtons.add(botonEspanol);
        panelRadioButtons.add(botonIngles);

    int opcionSeleccionada = JOptionPane.showOptionDialog(this,
        panelRadioButtons,
        local.devuelve(local.MENU_OPCIONES),
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        null,
        null);

    if (opcionSeleccionada == JOptionPane.OK_OPTION) {
        if (botonEspanol.isSelected()) {
            cambiarLenguaje(local.LENGUAJE_ESPANOL, 
                     local.PAIS_ESPANA);
        } else if (botonIngles.isSelected()) {
            cambiarLenguaje(local.LENGUAJE_INGLES, 
                     local.PAIS_USA);
        }
    }
}

    /**
     * Sobreescribe actionPerformed
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case Localizacion.BOTON_NUEVO:
                oyenteVista.eventoProducido(OyenteVista.Evento.NUEVA, 
                                         null);
                tableroVista.habilitar(true);
                inicializarVista();
                botonGuardar.setEnabled(true);
                break;

            case Localizacion.BOTON_ABRIR:
                oyenteVista.eventoProducido(OyenteVista.Evento.ABRIR, 
                                         null);
                break;

            case Localizacion.BOTON_GUARDAR:
                oyenteVista.eventoProducido(OyenteVista.Evento.GUARDAR, 
                                         null);
                break;

            case Localizacion.BOTON_GUARDAR_COMO:
                oyenteVista.eventoProducido(OyenteVista.
                                            Evento.GUARDAR_COMO, null);
                break;

            case Localizacion.BOTON_SALIR:
                oyenteVista.eventoProducido(OyenteVista.Evento.SALIR,
                                         null);
                break;
              
            case Localizacion.MENU_OPCIONES:
                    mostrarDialogoOpcionesIdioma();
                 break;
            case Localizacion.MENU_ESPANOL: 
                cambiarLenguaje(local.LENGUAJE_ESPANOL, 
                         local.PAIS_ESPANA);           
                 break;           

            case Localizacion.MENU_INGLES:
                cambiarLenguaje(local.LENGUAJE_INGLES,
                         local.PAIS_USA);        
                break;  

            case Localizacion.BOTON_ACERCA_DE:
                JOptionPane.showMessageDialog(this, version + "\n",
                                              local.BOTON_ACERCA_DE, 
                                              JOptionPane.INFORMATION_MESSAGE, 
                                              icono);
                break;
        }
    }

    /**
     * Selecciona fichero de partida
     * 
     */
    public String seleccionarFichero(int operacion) {
        String nombreFichero = null;
        int resultado = 0;

        JFileChooser dialogoSeleccionar = 
                new JFileChooser(new File("."));
        FileNameExtensionFilter filtro = 
                new FileNameExtensionFilter(FILTRO_PARTIDAS,
        EXT_FICHERO_PARTIDA.substring(1));

        dialogoSeleccionar.setFileFilter(filtro);

        if (operacion == ABRIR_FICHERO) {
            resultado = dialogoSeleccionar.showOpenDialog(this);
        } else {
            resultado = dialogoSeleccionar.showSaveDialog(this);
        }

        if (resultado == JFileChooser.APPROVE_OPTION) {
            nombreFichero = dialogoSeleccionar.getSelectedFile().getName();

            if (!nombreFichero.endsWith(EXT_FICHERO_PARTIDA) &&
                    (operacion == GUARDAR_FICHERO)) {
                nombreFichero = nombreFichero + EXT_FICHERO_PARTIDA;
            }

            ponerTitulo(nombreFichero);
        }

        return nombreFichero;
    }

    public void ponerTitulo(String nombreFichero) {
        if (nombreFichero.equals("")) {
            setTitle(version);
        } else {
            setTitle(nombreFichero + " - " + version);
        }
    }

    /**
     * Escribe mensaje con diálogo modal
     * 
     */
    public void mensajeDialogo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, 
                                 version,
                             JOptionPane.INFORMATION_MESSAGE,
                                  icono);
    }

    /**
     * Cuadro diálogo de confirmación acción
     * 
     */
    public int mensajeConfirmacion(String mensaje) {
        return JOptionPane.showConfirmDialog(this, mensaje,
                                        version,
                                     JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pone Icono del color indicado en la posición indicada
     * 
     */
    public void ponerIconoCasilla(Casilla posicion, ResultadoDisparo disparo){
        
        if (disparo == ResultadoDisparo.DISPARO_FALLADO) {

            tableroVista.ponerIconoCasilla(posicion, IconoAgua);
        } else if (disparo == ResultadoDisparo.BARCO_TOCADO) {

            tableroVista.ponerIconoCasilla(posicion, IconoBarco);
        } else if (disparo == ResultadoDisparo.BARCO_HUNDIDO ) {
            
            tableroVista.ponerIconoCasilla(posicion, IconoBarcoHundido);
        }
        else{
             tableroVista.ponerIconoCasilla(posicion, null);
        }
    }

    /**
     * Inicializa vista
     * 
     */
    public void inicializarVista() {
        tableroVista.inicializar();
    }

    /**
     * Recupera partida con el tablero indicado
     * 
     */
    public void recuperarIConocasilla( Casilla casilla,Tablero tableroModelo) {
        Barco barco = tableroModelo.perteneceBarco(casilla);
        
        if(barco == null){
            ponerIconoCasilla(casilla,
                      ResultadoDisparo.DISPARO_FALLADO);
        }else if (tableroModelo.barcoHundido(casilla)){
            ponerIconoCasilla(casilla,
                       ResultadoDisparo.BARCO_HUNDIDO);
        }
        else{
           ponerIconoCasilla(casilla,
                      ResultadoDisparo.BARCO_TOCADO);
        }
    }

    /**
     * Notificación de un evento de la interfaz de usuario
     * 
     */
    public void notificacion(OyenteVista.Evento evento, Object obj) {
        oyenteVista.eventoProducido(evento, obj);
    }

    /**
     * Sobreescribe propertyChange para recibir eventos del tablero observado
     * 
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Tupla tupla;
        
        if(evt.getPropertyName().equals(Tablero.DISPARO_ACERTADO)){
          tupla = (Tupla)evt.getNewValue();
          ponerIconoCasilla((Casilla)tupla.a, ((ResultadoDisparo)tupla.b));
        }
    }

    /**
     * Habilita o deshabilita evento vista
     * 
     */
    public void habilitarEvento(OyenteVista.Evento evento,
                                boolean habilitacion) {
        switch (evento) {
            case GUARDAR:
                botonGuardar.setEnabled(habilitacion);
                break;

            case DISPARAR:
                tableroVista.habilitar(habilitacion);
                break;
                  
        }
    }
    
    public void dispararEnCasilla(Casilla casilla, 
                                  Partida.ResultadoDisparo resultado) {
        switch (resultado) {
            case BARCO_TOCADO:
                ponerIconoCasilla(casilla, resultado);
                break;
            case DISPARO_FALLADO:
                ponerIconoCasilla(casilla, resultado);
                break;
            default:
                break;
        }
    }

}

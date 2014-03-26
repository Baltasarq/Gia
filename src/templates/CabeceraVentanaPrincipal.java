import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.FactoryConfigurationError;
import java.util.Date;

/**
 * Clase que implementa la ventana Principal de la aplicación.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class VentanaPrincipal
    extends JFrame
    implements ActionListener {

  //vector que almacena la clase una vez modificada
  Vector vClaseModificada = new Vector();
  //vector que contiene elementos de los atributos de la clase principal
  Vector vfilas = new Vector();
  Raiz cLista;
  Date d = new Date();
  String enlace = "";
  String consultar = "";
  boolean alta = false;
  boolean guardado = false;
  boolean ok = false;
  boolean cancel = false;

  //paneles
  JPanel panelTabla = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelBotLateral = new JPanel();
  JPanel panelContenedor = new JPanel();
  JPanel PanelIzquierda = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel pLateral = new JPanel();
  //botones
  JButton botonGuardar = new JButton();
  JButton botonCancelar = new JButton();
  //menu
  JMenuBar menuSuperior = new JMenuBar();
  JMenu menuArchivo = new JMenu();
  JMenu menuEditar = new JMenu();
  JMenuItem menuGuardar = new JMenuItem();
  JMenuItem menuSalir = new JMenuItem();
  //layouts
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();

package View;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;

/**
 * Ventana desde la que se busca el archivo xmi a abrir por la aplicación.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowOpenXMIFile
    extends JDialog
    implements ActionListener {

  String nombreXMI="";

  //Paneles
  JPanel principal = new JPanel();
  JPanel pBotones = new JPanel();

  //Botones
  JButton botonFinalizar = new JButton();
  JButton botonCancelar = new JButton();

  //Archivo XMI
  JLabel DirectorioTrabajo = new JLabel();
  JTextField fieldDirXMI = new JTextField();
  JButton BotonDirTrabajo = new JButton();
  JPanel PanelComentariosDirTrabajo = new JPanel();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  JPanel PanelDirTrabajo = new JPanel();
  ImageIcon imagen = new ImageIcon();

  static boolean datosAccesibles = false;
  static boolean botonFinalizarPulsado = false;

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();

  //Constructor de clase
  public WindowOpenXMIFile(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowOpenXMIFile() {
    this(null);
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    imagen = new ImageIcon(View.WindowOpenXMIFile.class.getResource(
        "Images/lateralrueda.png"));
    principal.setLayout(xYLayout1);
    this.setTitle("Importar XMI...");
    this.getContentPane().setLayout(xYLayout4);

    //Botones
    botonFinalizar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonFinalizar.setText("Aceptar");
    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonFinalizar.addActionListener(this);
    botonCancelar.addActionListener(this);

    DirectorioTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    DirectorioTrabajo.setText("Archivo XMI");
    BotonDirTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    BotonDirTrabajo.setText("...");
    BotonDirTrabajo.addActionListener(new
        Ventana_AbrirArchivo_BotonDirTrabajo_actionAdapter(this));
    BotonDirTrabajo.addActionListener(this);

    fieldDirXMI.setMinimumSize(new Dimension(11, 20));
    fieldDirXMI.setPreferredSize(new Dimension(130, 20));
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    PanelComentariosTrabajo.setFont(new java.awt.Font("MS Sans Serif", 0, 11));
    PanelComentariosTrabajo.setToolTipText("");
    PanelComentariosTrabajo.setEditable(false);
    PanelComentariosTrabajo.setText(
        "Seleccione el Archivo con formato XMI (XML Metadata Interchange) " +
        "versión 1.2 que desea abrir.\nEste archivo debe de haber sido generado " +
        "a partir de un diagrama de clases,  con una herramienta de diagramación " +
        "UML como ArgoUML.");
    PanelComentariosDirTrabajo.setBorder(BorderFactory.createEtchedBorder());
    PanelComentariosDirTrabajo.setLayout(xYLayout3);
    xYLayout4.setWidth(453);
    xYLayout4.setHeight(249);
    etiquetaImagen.setIcon(imagen);
    this.getContentPane().add(principal, new XYConstraints(2, 0, 452, 247));
    pBotones.add(botonFinalizar, null);
    pBotones.add(botonCancelar, null);
    principal.add(etiquetaImagen, new XYConstraints(6, 9, -1, -1));
    principal.add(PanelComentariosDirTrabajo,
                  new XYConstraints(157, 10, 288, 180));
    PanelDirTrabajo.add(DirectorioTrabajo, null);
    PanelDirTrabajo.add(fieldDirXMI, null);
    PanelDirTrabajo.add(BotonDirTrabajo, null);
    principal.add(pBotones, new XYConstraints(192, 203, 218, 27));
    PanelComentariosDirTrabajo.add(PanelComentariosTrabajo,
                                   new XYConstraints(13, 24, 261, -1));
    PanelComentariosDirTrabajo.add(PanelDirTrabajo,
                                   new XYConstraints(12, 125, -1, -1));
    setResizable(true);
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
      botonFinalizarPulsado = false;
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonFinalizar) {
      //Comprobamos si el fichero existe y está bien formado con el método openFile
      String error = Model.Controller.openFile(fieldDirXMI.getText());
      if (error != "") {
        JOptionPane.showMessageDialog(this, error, "Aviso",
                                      JOptionPane.WARNING_MESSAGE);
        datosAccesibles = false;
        botonFinalizarPulsado = false;
      }
      else {
        if(Model.Controller.isFile(fieldDirXMI.getText(),".xmi")){
          File f= new File(fieldDirXMI.getText());
          this.nombreXMI=f.getName();
          datosAccesibles = true;
          botonFinalizarPulsado = true;
          dispose();
        }
        else
        {
          JOptionPane.showMessageDialog(this, "El archivo debe de tener extensión xmi", "Error",
                                   JOptionPane.ERROR_MESSAGE);
     datosAccesibles = false;
     botonFinalizarPulsado = false;

        }
      }
    }
    if (e.getSource() == botonCancelar) {
      datosAccesibles = false;
      botonFinalizarPulsado = false;
      dispose();
    }
  }

  /**
   * Método que implementa las acciones del botón DirTrabajo.
   * @param e ActionEvent evento al que responde.
   */
  void BotonDirTrabajo_accion(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("xmi");
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowOpenXMIFile.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      fieldDirXMI.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class Ventana_AbrirArchivo_BotonDirTrabajo_actionAdapter
    implements java.awt.event.ActionListener {
  WindowOpenXMIFile vent;

  Ventana_AbrirArchivo_BotonDirTrabajo_actionAdapter(WindowOpenXMIFile v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.BotonDirTrabajo_accion(e);
  }

}

/**
 * Clase que hace que el filechooser muestre solo los archivos con la extensión
 * indicada.
 * @author Digna Rodríguez
 * @version 1.0
 */
class FilterExtension
    extends javax.swing.filechooser.FileFilter {
  String ext;
  public FilterExtension(String ext) {
    this.ext = ext;
  }

  public boolean accept(File dir) {
    String nombre = dir.getName();
    if (dir.isDirectory()) {
      return true;
    }
    return nombre.endsWith(ext);
  }

  public String getDescription() {
    return "*." + this.ext;
  }
}

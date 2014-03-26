package View;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Model.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;

/**
 * Clase que implementa la ventana del directorio en el que se almacenarán
 * los archivos *.java generados por la herramienta.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowChooseDirectory
    extends JDialog
    implements ActionListener {

  //Ruta en donde se almacenarán los archivos .java
  private static String rutaArchivos;

  //Paneles
  JPanel principal = new JPanel();
  JPanel pBotones = new JPanel();
  JPanel panelEspacioTrabajo = new JPanel();

  //Botones
  JButton botonGenerar = new JButton();
  JButton botonCancelar = new JButton();

  //Archivo XMI
  JLabel DirectorioTrabajo = new JLabel();
  JTextField DirTrabajoCasilla = new JTextField();
  JButton BotonDirTrabajo = new JButton();
  JPanel PanelComentariosDirTrabajo = new JPanel();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  JPanel PanelDirTrabajo = new JPanel();
  ImageIcon imagen = new ImageIcon();

  static boolean datosAccesibles = false;
  static boolean botonFinalizarPulsado = false;

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();

  //Constructor de clase
  public WindowChooseDirectory(Frame parent, String nom) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit(nom);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }



  /**
   * Método que cambia la ruta  en la que se almacenarán los archivos *.java.
   * @param path String nueva ruta.
   */
  public void setRuta(String path) {
    rutaArchivos = path;
  }

  /**
   * Método que devuelve la ruta en la que se almacenarán los archivos.
   * @return String ruta.
   */
  public static String getRuta() {
    return rutaArchivos;
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   * @param nom String nombre del proyecto.
   */
  private void jbInit(String nom) throws Exception {

    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource(
        "Images/lateraltubos.png"));
    principal.setLayout(xYLayout1);
    this.setTitle("Escoger directorio de trabajo");
    this.getContentPane().setLayout(xYLayout4);

    //Botones
    botonGenerar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonGenerar.setText("Generar");
    botonGenerar.addActionListener(this);

    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);
    File t= new File(Controller.defaultJavaPath);
    String s=  t.getAbsolutePath();
    DirTrabajoCasilla.setText(s+File.separator+ nom+File.separator);
    BotonDirTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    BotonDirTrabajo.setText("...");
    BotonDirTrabajo.addActionListener(new
                                      Ventana_AbrirDirectorio_BotonDirTrabajo_actionAdapter(this));
    BotonDirTrabajo.addActionListener(this);

    DirectorioTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    DirectorioTrabajo.setText("Directorio");


    DirTrabajoCasilla.setMinimumSize(new Dimension(11, 20));
    DirTrabajoCasilla.setPreferredSize(new Dimension(155, 20));
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    PanelComentariosTrabajo.setEditable(false);
    PanelComentariosTrabajo.setText(
        "Seleccione el directorio en el que quiere que se almacenen los documentos " +
        ".java generados.\nSi no se se selecciona ninguno se usará por defecto " +
        "la carpeta \"generated GiA Files\" situada en Mis Documentos." +
        "\nEl código se generará a partir del proyecto actual. ");
    panelEspacioTrabajo.setLayout(xYLayout2);
    PanelComentariosDirTrabajo.setBorder(BorderFactory.createEtchedBorder());
    PanelComentariosDirTrabajo.setLayout(xYLayout3);
    xYLayout4.setWidth(488);
    xYLayout4.setHeight(249);
    etiquetaImagen.setIcon(imagen);
    this.getContentPane().add(principal, new XYConstraints(0, 0, 485, -1));
    pBotones.add(botonGenerar, null);
    pBotones.add(botonCancelar, null);
    principal.add(etiquetaImagen, new XYConstraints(9, 2, 136, 244));
    panelEspacioTrabajo.add(PanelComentariosDirTrabajo,
                            new XYConstraints(3, 0, 321, 178));
    principal.add(panelEspacioTrabajo, new XYConstraints(149, 12, 333, -1));
    PanelDirTrabajo.add(DirectorioTrabajo, null);
    PanelDirTrabajo.add(DirTrabajoCasilla, null);
    PanelDirTrabajo.add(BotonDirTrabajo, null);
    panelEspacioTrabajo.add(pBotones, new XYConstraints(29, 189, 260, -1));
    PanelComentariosDirTrabajo.add(PanelComentariosTrabajo,
                                   new XYConstraints(4, 12, 309, 106));
    PanelComentariosDirTrabajo.add(PanelDirTrabajo,
                                    new XYConstraints(6, 131, 285, -1));

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
   * Método que cierra la ventana tras un suceso de botón.
   * @param e ActionEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonGenerar) {
      if (this.DirTrabajoCasilla.getText().compareTo("") != 0) {
        String error = "";
        try {
          this.rutaArchivos = DirTrabajoCasilla.getText();
          if(this.rutaArchivos.indexOf(" ")==-1){
            error = Model.Controller.createDirectory(DirTrabajoCasilla.getText());
            if (Model.Controller.validatePath(DirTrabajoCasilla.getText()) == false)
              error = "El directorio no es correcto, vuelva a introducirlo.";
            if (error.compareTo("") == 0) {
              Model.CodeGenerator cg = new Model.CodeGenerator(Model.Controller.
                  contenido, DirTrabajoCasilla.getText());
              cg.getPathCode();
            }
          }
          else
            error="La ruta no puede contener espacios";
        }
        catch (IOException ex) {
        }
        if (error != "") {
          JOptionPane.showMessageDialog(this, error, "Aviso",
                                        JOptionPane.WARNING_MESSAGE);
          datosAccesibles = false;
          botonFinalizarPulsado = false;
        }
        else {
          datosAccesibles = true;
          botonFinalizarPulsado = true;
          dispose();
        }
      }
      else {
        JOptionPane.showMessageDialog(this, "Debe de introducir una ruta",
                                      "Aviso",
                                      JOptionPane.WARNING_MESSAGE);
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
    FilterExtension filtro = new FilterExtension("Directorio");
    dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowChooseDirectory.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      DirTrabajoCasilla.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class Ventana_AbrirDirectorio_BotonDirTrabajo_actionAdapter
    implements java.awt.event.ActionListener {
  WindowChooseDirectory vent;

  Ventana_AbrirDirectorio_BotonDirTrabajo_actionAdapter(WindowChooseDirectory v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.BotonDirTrabajo_accion(e);
  }

}

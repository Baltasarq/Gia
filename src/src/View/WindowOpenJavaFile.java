package View;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;

/**
 * Ventana desde la que se busca el archivo Java a abrir por la aplicación.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowOpenJavaFile
    extends JDialog
    implements ActionListener {

  //Paneles
  JPanel principal = new JPanel();
  JPanel pBotones = new JPanel();

  //Botones
  JButton botonAceptar = new JButton();
  JButton botonCancelar = new JButton();

  //Archivo XMI
  JLabel DirectorioTrabajo = new JLabel();
  JTextField fieldName = new JTextField();
  JButton ButtonDirectory = new JButton();
  JPanel PanelComentariosDirTrabajo = new JPanel();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  JPanel PanelDirTrabajo = new JPanel();
  ImageIcon imagen = new ImageIcon();

  static boolean datosAccesibles = false;
  static boolean botonFinalizarPulsado = false;
  static boolean modelo = false;
  ButtonGroup group = new ButtonGroup();

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();
  JLabel labelDirectorio = new JLabel();
  JRadioButton radioModelo = new JRadioButton();
  JRadioButton radioVista = new JRadioButton();
  JPanel panelDirectorio = new JPanel();
  XYLayout xYLayout6 = new XYLayout();

  //Constructor de clase
  public WindowOpenJavaFile(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowOpenJavaFile() {
    this(null);
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception  en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    imagen = new ImageIcon(View.WindowOpenJavaFile.class.getResource(
        "Images/lateralrueda.png"));
    principal.setLayout(xYLayout1);
    this.setTitle("Abrir Fichero Java");
    this.getContentPane().setLayout(xYLayout4);

    //Botones
    botonAceptar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonAceptar.setText("Aceptar");
    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonAceptar.addActionListener(this);
    botonCancelar.addActionListener(this);

    DirectorioTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    DirectorioTrabajo.setText("Archivo Java");
    ButtonDirectory.setFont(new java.awt.Font("Dialog", 1, 11));
    ButtonDirectory.setText("...");
    ButtonDirectory.addActionListener(new ButtonOpenJavaFile_actionAdapter(this));
    ButtonDirectory.addActionListener(this);

    fieldName.setMinimumSize(new Dimension(11, 20));
    fieldName.setPreferredSize(new Dimension(130, 20));
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    PanelComentariosTrabajo.setFont(new java.awt.Font("MS Sans Serif", 0, 11));
    PanelComentariosTrabajo.setToolTipText("");
    PanelComentariosTrabajo.setEditable(false);
    PanelComentariosTrabajo.setText(
        "Seleccione el Archivo de código Java que se desee añadir al proyecto. " +
        "Coloquelo debajo del directorio correspondiente, marcando Modelo " +
        "o GUI.");
    PanelComentariosDirTrabajo.setBorder(BorderFactory.createEtchedBorder());
    PanelComentariosDirTrabajo.setLayout(xYLayout3);
    xYLayout4.setWidth(453);
    xYLayout4.setHeight(249);
    etiquetaImagen.setIcon(imagen);
    labelDirectorio.setText("Directorio");
    labelDirectorio.setFont(new java.awt.Font("Dialog", 1, 11));
    radioModelo.setSelected(true);
    radioModelo.setText("Modelo");
    radioVista.setText("GUI");
    group.add(radioVista);
    group.add(radioModelo);

    panelDirectorio.setLayout(xYLayout6);
    this.getContentPane().add(principal, new XYConstraints(2, 0, 452, 247));
    pBotones.add(botonAceptar, null);
    pBotones.add(botonCancelar, null);
    principal.add(etiquetaImagen, new XYConstraints(6, 9, -1, -1));
    principal.add(PanelComentariosDirTrabajo,
                  new XYConstraints(157, 10, 288, 180));
    PanelDirTrabajo.add(DirectorioTrabajo, null);
    PanelDirTrabajo.add(fieldName, null);
    PanelDirTrabajo.add(ButtonDirectory, null);
    PanelComentariosDirTrabajo.add(panelDirectorio,
                                   new XYConstraints(3, 124, 255, -1));
    panelDirectorio.add(radioModelo, new XYConstraints(84, 4, -1, -1));
    panelDirectorio.add(radioVista, new XYConstraints(150, 5, -1, -1));
    panelDirectorio.add(labelDirectorio, new XYConstraints(13, 9, -1, -1));
    PanelComentariosDirTrabajo.add(PanelComentariosTrabajo,
                                   new XYConstraints(9, 12, 261, -1));
    PanelComentariosDirTrabajo.add(PanelDirTrabajo,
                                   new XYConstraints(11, 84, -1, -1));
    principal.add(pBotones, new XYConstraints(192, 203, 218, 27));

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
    if (e.getSource() == botonAceptar) {
      if (fieldName.getText().compareTo("") != 0) {
        boolean exist = Model.Controller.validatePath(fieldName.getText());
        if (!exist) {
          JOptionPane.showMessageDialog(this,
                                        "El archivo introducido no existe",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
          datosAccesibles = false;
          botonFinalizarPulsado = false;
        }
        else {
          if (Model.Controller.isFile(fieldName.getText(), ".java")) {
            if (this.radioModelo.isSelected())
              this.modelo = true;
            else
              this.modelo = false;

            datosAccesibles = true;
            botonFinalizarPulsado = true;
            dispose();
          }
          else {
            JOptionPane.showMessageDialog(this,
                "El archivo debe de tener extensión java", "Error",
                                          JOptionPane.WARNING_MESSAGE);
            datosAccesibles = false;
            botonFinalizarPulsado = false;
          }
        }
      }
      else
        JOptionPane.showMessageDialog(this,
                                      "Debe de rellenar el campo con el nombre del fichero a añadir",
                                      "Mensaje informativo",
                                      JOptionPane.WARNING_MESSAGE);
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
  void ButtonDirectory_action(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("java");
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowOpenJavaFile.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      fieldName.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class ButtonOpenJavaFile_actionAdapter
    implements java.awt.event.ActionListener {
  WindowOpenJavaFile vent;

  ButtonOpenJavaFile_actionAdapter(WindowOpenJavaFile v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.ButtonDirectory_action(e);
  }

}

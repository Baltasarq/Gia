package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import Model.*;
import java.io.File;
import java.io.IOException;

/**
 * Panel desde el que se editan las preferencias del sistema. Podemos modificar
 * los directorios en los que se van a almacenar los ficheros.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowPreferences
    extends JDialog
    implements ActionListener {

  boolean botonOk = false;
  boolean botonCancel = false;
  JPanel panelPrincipal = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelSeleccion = new JPanel();
  JButton botonAceptar = new JButton();
  JButton botonCancelar = new JButton();
  ImageIcon imagen = new ImageIcon();
  XYLayout xYLayout1 = new XYLayout();

  XYLayout xYLayout3 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();
  XYLayout xYLayout2 = new XYLayout();
  JLabel jLabel1 = new JLabel();
  JTextField fieldWorkspace = new JTextField();
  JLabel jLabel3 = new JLabel();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  JTextField fieldJavaFiles = new JTextField();
  JButton botonWorkspace = new JButton();
  JButton botonJava = new JButton();
  XYLayout xYLayout4 = new XYLayout();

  public WindowPreferences(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowPreferences() {
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
    PanelComentariosTrabajo.setText(
        "Directorio donde se guardarán los archivos generados");
    PanelComentariosTrabajo.setEditable(false);
    PanelComentariosTrabajo.setDisabledTextColor(Color.lightGray);
    PanelComentariosTrabajo.setAlignmentY( (float) 0.5);
    PanelComentariosTrabajo.setOpaque(false);
    PanelComentariosTrabajo.setFont(new java.awt.Font("Dialog", 0, 11));
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    this.fieldJavaFiles.setText(Controller.defaultJavaPath);
    this.fieldWorkspace.setText(Controller.defaultSavePath);
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel3.setText("Modificar los directorios por defecto");
    jLabel1.setText("Directorio de trabajo");
    this.getContentPane().setLayout(xYLayout2);
    this.setResizable(false);
    this.setTitle("Preferencias");
    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource(
        "Images/lateralengranaje.png"));
    panelPrincipal.setLayout(xYLayout1);
    panelSeleccion.setLayout(xYLayout3);
    panelSeleccion.setBorder(BorderFactory.createEtchedBorder());
    botonAceptar.setText("Guardar");
    botonAceptar.addActionListener(this);
    etiquetaImagen.setIcon(imagen);
    xYLayout2.setWidth(478);
    xYLayout2.setHeight(254);
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);
    botonWorkspace.setFont(new java.awt.Font("Dialog", 1, 11));
    botonWorkspace.setText("...");
    botonWorkspace.addActionListener(new
                                     Window_OpenWorkspace_actionAdapter(this));
    botonJava.addActionListener(new
                                Window_OpenJavaDir_actionAdapter(this));
    botonJava.setText("...");
    botonJava.setFont(new java.awt.Font("Dialog", 1, 11));
    panelBotones.setLayout(xYLayout4);
    panelPrincipal.add(etiquetaImagen, new XYConstraints(9, 4, 136, 244));
    panelSeleccion.add(jLabel1, new XYConstraints(7, 47, -1, -1));
    panelSeleccion.add(fieldJavaFiles, new XYConstraints(115, 99, 157, -1));
    panelSeleccion.add(PanelComentariosTrabajo,
                       new XYConstraints(6, 80, 181, 46));
    panelSeleccion.add(fieldWorkspace, new XYConstraints(113, 44, 158, -1));
    panelSeleccion.add(botonWorkspace, new XYConstraints(277, 43, 33, -1));
    panelSeleccion.add(botonJava, new XYConstraints(276, 97, 33, -1));
    panelSeleccion.add(jLabel3, new XYConstraints(6, 6, 251, -1));
    panelPrincipal.add(panelBotones, new XYConstraints(217, 194, 184, -1));
    panelBotones.add(botonCancelar, new XYConstraints(93, 5, -1, -1));
    panelBotones.add(botonAceptar, new XYConstraints(5, 5, -1, -1));
    panelPrincipal.add(panelSeleccion, new XYConstraints(150, 17, 318, 164));
    this.getContentPane().add(panelPrincipal, new XYConstraints(0, 0, 476, -1));
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      botonCancel = true;
      this.dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      botonCancel = true;
      dispose();
    }
    if (e.getSource() == botonAceptar) {
      if (this.fieldJavaFiles.getText().compareTo("") == 0||
          this.fieldWorkspace.getText().compareTo("")==0) {
        String aviso =
            "Debe de rellenar todos los campos";
        JOptionPane.showMessageDialog(this, aviso, "Error",
                                      JOptionPane.ERROR_MESSAGE);

      }
      else {
        Controller.defaultJavaPath = this.fieldJavaFiles.getText();
        Controller.defaultSavePath = this.fieldWorkspace.getText();
        botonOk = true;
        this.dispose();
      }
    }
  }

  /**
   * Método que implementa las acciones del botón buscar directorio donde
   * se almacenarán los archivos java.
   * @param e ActionEvent evento al que responde.
   */
  public void dirJava_action(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("Directorio");
    dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowPreferences.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      this.fieldJavaFiles.setText(s);
    }
  }

  /**
   * Método que implementa las acciones del botón buscar directorio donde se
   * almacenarán los archivos guardados por el sistema.
   * @param e ActionEvent evento al que responde.
   */
  public void dirWorkspace_action(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("Directorio");
    dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowPreferences.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      this.fieldWorkspace.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class Window_OpenJavaDir_actionAdapter
    implements java.awt.event.ActionListener {
  WindowPreferences vent;

  Window_OpenJavaDir_actionAdapter(WindowPreferences v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.dirJava_action(e);
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class Window_OpenWorkspace_actionAdapter
    implements java.awt.event.ActionListener {
  WindowPreferences vent;

  Window_OpenWorkspace_actionAdapter(WindowPreferences v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.dirWorkspace_action(e);
  }
}

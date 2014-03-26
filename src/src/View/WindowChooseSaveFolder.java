package View;

import java.io.*;
import Model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/**
 * Clase que implementa la ventana de elección del nombre y el directorio en el
 * que se va a guardar el fichero el contenido del proyecto.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowChooseSaveFolder
    extends JDialog
    implements ActionListener {

  static boolean datosCompletados = false;
  static boolean botonAceptarPulsado = false;
  boolean nuevoProyecto=false;

  //Ruta en donde se almacenará el proyecto
  private static String rutaArchivo;
  private static String nomArchivo;

  //Paneles
  JPanel principal = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelNombre = new JPanel();
  JPanel panelDirectorio = new JPanel();
  JPanel PanelContendor = new JPanel();
  JTextPane panelComentarios = new JTextPane();

  //Botones
  JButton botonGuardar = new JButton();
  JButton botonCancelar = new JButton();
  JButton BotonDirectorio = new JButton();

  JLabel LabelNomArchivo = new JLabel();
  JLabel labelDirectorio = new JLabel();
  JLabel LabelImagen = new JLabel();
  JTextField fieldName = new JTextField();
  JTextField fieldDirectorio = new JTextField();

  ImageIcon imagen = new ImageIcon();

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();

  //Constructor de clase
  public WindowChooseSaveFolder(Frame parent, String t, boolean nuevo) {

    super(parent);
    this.nuevoProyecto=nuevo;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit(t);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Método que cambia la ruta en la que se almacenan los ficheros guardados.
   * @param path String nueva ruta.
   */
  public void setRuta(String path) {
    rutaArchivo = path;
  }

  /**
   * Método que obtiene la ruta en la que se almacenan los ficheros.
   * @return String ruta.
   */
  public static String getRuta() {
    return rutaArchivo;
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   * @param titulo String nombre del proyecto.
   */
  private void jbInit(String titulo) throws Exception {
    panelBotones.setLayout(xYLayout4);
    this.getContentPane().setLayout(xYLayout5);
    xYLayout5.setWidth(487);
    xYLayout5.setHeight(258);
    panelBotones.add(botonGuardar, new XYConstraints(69, 3, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(161, 4, -1, -1));
    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource(
        "Images/lateraltubos.png"));
    LabelImagen.setIcon(imagen);
    principal.setLayout(xYLayout1);
    this.setTitle(titulo);

    //Botones
    botonGuardar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonGuardar.setText("Guardar");
    botonGuardar.addActionListener(this);

    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);

    BotonDirectorio.setFont(new java.awt.Font("Dialog", 1, 11));
    BotonDirectorio.setText("...");
    BotonDirectorio.addActionListener(new window_openFolder_ActionAdapter(this));
    BotonDirectorio.addActionListener(this);

    LabelNomArchivo.setFont(new java.awt.Font("Dialog", 1, 11));
    LabelNomArchivo.setText("Nombre del Archivo");

    fieldDirectorio.setMinimumSize(new Dimension(11, 20));
    fieldDirectorio.setPreferredSize(new Dimension(130, 20));
    File f= new File(Controller.defaultSavePath);
    String s= f.getAbsolutePath();
    fieldDirectorio.setText(s);
    panelComentarios.setBackground(SystemColor.control);
    panelComentarios.setEditable(false);
    panelComentarios.setText(
        "Ecriba un nombre para el proyecto y seleccione el directorio en el que "+
        "quiere que se almacene el proyecto.\n" +
        "Se creará un fichero xml por cada clase del proyecto y se almacenarán "+
        "en un archivo comprimido zip.");
    PanelContendor.setBorder(BorderFactory.createEtchedBorder());
    PanelContendor.setLayout(xYLayout3);
    fieldName.setText("");
    labelDirectorio.setText("Directorio de trabajo");
    labelDirectorio.setFont(new java.awt.Font("Dialog", 1, 11));
    panelNombre.setLayout(xYLayout2);
    panelDirectorio.add(labelDirectorio, null);
    panelDirectorio.add(fieldDirectorio, null);
    panelDirectorio.add(BotonDirectorio, null);
    PanelContendor.add(panelComentarios, new XYConstraints(6, 25, 311, 72));
    PanelContendor.add(panelDirectorio, new XYConstraints(0, 131, 307, -1));
    principal.add(panelBotones, new XYConstraints(172, 211, 278, -1));
    PanelContendor.add(panelNombre, new XYConstraints(2, 102, 310, -1));
    this.getContentPane().add(principal, new XYConstraints(0, 0, 487, 249));
    panelNombre.add(fieldName, new XYConstraints(123, 5, 173, -1));
    panelNombre.add(LabelNomArchivo, new XYConstraints(6, 10, -1, -1));
    principal.add(LabelImagen, new XYConstraints(9, 6, 136, 244));
    principal.add(PanelContendor, new XYConstraints(153, 14, 324, 192));

    setResizable(true);
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
      botonAceptarPulsado = false;
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que cierra la ventana tras un suceso de botón.
   * @param e ActionEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    File f;
    if (e.getSource() == botonGuardar) {

      if ((this.fieldName.getText().compareTo("") != 0)&&
         (this.fieldDirectorio.getText().compareTo("")!=0)) {
       f = new File(fieldDirectorio.getText() + File.separator +
                                this.fieldName.getText() + ".zip");
       String error = "";

       //Almacenamos el nombre y la ruta donde se almacenará el proyecto.
       this.rutaArchivo = fieldDirectorio.getText();
       this.nomArchivo = this.fieldName.getText();
      if(Controller.validateFileName(this.nomArchivo)==true){
        if (!f.exists()) {
          if (!nuevoProyecto) {
            if (!Controller.contenido.existsClass(this.fieldName.getText())) {
     try {
              error = Model.Controller.createDirectory(fieldDirectorio.
                              getText());
                          if (Model.Controller.validatePath(fieldDirectorio.getText()) == false)
                            error =
                                "El directorio no es correcto, vuelva a introducirlo.";




                }
                catch (IOException ex) {
                }
              if(error.compareTo("")==0){
                  //directorio correcto
                  datosCompletados = true;
                  botonAceptarPulsado = true;
                  this.dispose();
                }
                else {

                  JOptionPane.showMessageDialog(this,
                      "El directorio no es correcto, vuelva a introducirlo",
                  "Aviso",
                      JOptionPane.WARNING_MESSAGE);
                  datosCompletados = false;
                  botonAceptarPulsado = false;
                }
              }
            else {
              JOptionPane.showMessageDialog(this, "El nombre del proyecto no puede ser igual al nombre de una clase existente en el sistema.",
                                            "Aviso",
                                            JOptionPane.WARNING_MESSAGE);
              datosCompletados = false;
              botonAceptarPulsado = false;
            }
          }//no es un nuevo proyecto
          else {

            try {
             error = Model.Controller.createDirectory(fieldDirectorio.
                             getText());
                         if (Model.Controller.validatePath(fieldDirectorio.getText()) == false)
                           error =
                               "El directorio no es correcto, vuelva a introducirlo.";
              }

              catch (IOException ex1) {
              }
            if(error.compareTo("")==0) {
                //directorio correcto
                datosCompletados = true;
                botonAceptarPulsado = true;
                this.dispose();
              }
              else {

                JOptionPane.showMessageDialog(this,
                    "El directorio no es correcto, vuelva a introducirlo",
                "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                datosCompletados = false;
                botonAceptarPulsado = false;
              }
          }
        }

        else {
          int value = JOptionPane.showConfirmDialog(
              this,
              " Ya existe un archivo con ese nombre ¿Desea sobreescribir el archivo?",
              "Mensaje de Confirmación",
              JOptionPane.YES_NO_OPTION);
          if (value == JOptionPane.YES_OPTION) {
            f.delete();

            datosCompletados = true;
            botonAceptarPulsado = true;
            dispose();
          }
        else{
          datosCompletados = false;
         botonAceptarPulsado=false;
        }
       }
      }
     else
     {
     JOptionPane.showMessageDialog(this, "El nombre del archivo no puede contener los caracteres \\ /\"*|?<>", "Aviso",
                                        JOptionPane.WARNING_MESSAGE);
          datosCompletados = false;
          botonAceptarPulsado = false;
        }
      }
      else {
        JOptionPane.showMessageDialog(this, "Debe de rellenar todos los campos", "Aviso",
                                        JOptionPane.ERROR_MESSAGE);
      }
    }





    if (e.getSource() == botonCancelar) {
      datosCompletados = false;
      botonAceptarPulsado = false;
      dispose();
    }
  }

  /**
   * Método que implementa las acciones del botón DirTrabajo.
   * @param e ActionEvent evento al que responde.
   */
  void BotonDirectorio_accion(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("Directorio");
    dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowChooseSaveFolder.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      fieldDirectorio.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class window_openFolder_ActionAdapter
    implements ActionListener {
  WindowChooseSaveFolder vent;

  window_openFolder_ActionAdapter(WindowChooseSaveFolder v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.BotonDirectorio_accion(e);
  }
}

package View;

import java.io.*;
import Model.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;

/**
 * Clase que implementa una ventana para abrir un proyecto de la
 * herramienta, en formato zip.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowOpenZipFile
    extends JDialog
    implements ActionListener {

  public String nombreRaiz = "";
  public String rutaProyecto="";

  //Paneles
  JPanel principal = new JPanel();
  JPanel panelButtons = new JPanel();
  JPanel panelContainer = new JPanel();

  //Botones
  JButton buttonValidate = new JButton();
  JButton bottonCancel = new JButton();

  //Archivo XMI
  JLabel labelName = new JLabel();
  JTextField fieldNameFile = new JTextField();
  JButton buttonSearchFile = new JButton();
  JPanel panelSup = new JPanel();
  JTextPane panelExplication = new JTextPane();
  JPanel PanelDirTrabajo = new JPanel();
  ImageIcon imagen = new ImageIcon();
  JLabel etiquetaImagen = new JLabel();

  static boolean datosAccesibles = false;
  static boolean botonFinalizarPulsado = false;

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();

  //Constructor de clase
  public WindowOpenZipFile(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowOpenZipFile() {
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
    imagen = new ImageIcon(View.WindowOpenZipFile.class.getResource(
        "Images/lateralrueda.png"));
    etiquetaImagen.setIcon(imagen);
    principal.setLayout(xYLayout1);
    this.setTitle("Abrir Archivo");
    this.getContentPane().setLayout(xYLayout4);

    //Botones
    buttonValidate.setFont(new java.awt.Font("Dialog", 1, 11));
    buttonValidate.setText("Validar");
    bottonCancel.setFont(new java.awt.Font("Dialog", 1, 11));
    bottonCancel.setText("Cancelar");
    buttonValidate.addActionListener(this);
    bottonCancel.addActionListener(this);

    labelName.setFont(new java.awt.Font("Dialog", 1, 11));
    labelName.setText("Archivo Zip");
    buttonSearchFile.setFont(new java.awt.Font("Dialog", 1, 11));
    buttonSearchFile.setText("...");
    buttonSearchFile.addActionListener(new
                                       WOpenXML_buttonSearchFile_actionAdapter(this));
    buttonSearchFile.addActionListener(this);

    File f1 = new File("temp");
    if (!f1.exists()) {
      f1.mkdir();
    }
    fieldNameFile.setMinimumSize(new Dimension(11, 20));
    fieldNameFile.setPreferredSize(new Dimension(130, 20));
    panelExplication.setBackground(SystemColor.control);
    panelExplication.setToolTipText("");
    panelExplication.setDisabledTextColor(UIManager.getColor(
        "CheckBox.background"));
    panelExplication.setEditable(false);
    panelExplication.setText(
        "Seleccione el Archivo Zip que desea abrir, este debe de haber sido " +
        "generado por la herramienta o tener una estructura similar.\nA continuación " +
        "se comprobará si los archivos está bien fomados y si son válidos.");
    panelContainer.setLayout(xYLayout2);
    panelSup.setBorder(BorderFactory.createEtchedBorder());
    panelSup.setLayout(xYLayout3);
    xYLayout4.setWidth(448);
    xYLayout4.setHeight(252);
    this.getContentPane().add(principal, new XYConstraints(0, 0, 444, 272));
    panelButtons.add(buttonValidate, null);
    panelButtons.add(bottonCancel, null);
    panelContainer.add(etiquetaImagen, new XYConstraints(7, 0, -1, -1));
    panelContainer.add(panelSup, new XYConstraints(162, 0, 276, 191));
    principal.add(panelContainer, new XYConstraints( -1, 6, 441, 237));
    PanelDirTrabajo.add(labelName, null);
    PanelDirTrabajo.add(fieldNameFile, null);
    PanelDirTrabajo.add(buttonSearchFile, null);
    panelContainer.add(panelButtons, new XYConstraints(167, 197, 256, -1));
    panelSup.add(panelExplication, new XYConstraints(2, 34, 267, 82));
    panelSup.add(PanelDirTrabajo, new XYConstraints(7, 124, -1, -1));

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
   * Método que lee el contenido del archivo manifest para saber cual es el
   * archivo raiz del sistema.
   * @return String
   */
  public String readManifest() {
    String toret = "";
    FileReader fr = null;
    try {
      fr = new FileReader("./temp/MANIFEST.txt");
      BufferedReader bf = new BufferedReader(fr);
      toret = bf.readLine();
    }
    catch (IOException ex1) { ex1.printStackTrace();
    }
   return toret;
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == buttonValidate) {

      //Comprobamos que el fichero zip existe
      String existeZip = "Debe introducir una ruta válida en el campo. \nEl archivo debe de tener extensión zip";
      boolean ev = Controller.validatePath(fieldNameFile.getText());
      if (ev == false)
        JOptionPane.showMessageDialog(this, existeZip, "Error",
                                      JOptionPane.WARNING_MESSAGE);
      else {
         if(Model.Controller.isFile(fieldNameFile.getText(),".zip")){
           //Descomprimir en temporal...
           Controller.contenido.unzip(fieldNameFile.getText());
           //buscamos el archivo raíz para ello abrimos manifest
           String nomRaiz = readManifest();
           this.nombreRaiz = nomRaiz;

           //validamos todos los xml contenidos en el zip
           String error = Model.Controller.validateAllXML("./temp/"+nomRaiz + ".xml");
           if (error != "") {
             error += "\n" + Model.Controller.errorBienFormado;
             JOptionPane.showMessageDialog(this, error, "Error",
                                           JOptionPane.ERROR_MESSAGE);
             datosAccesibles = false;
             botonFinalizarPulsado = false;
           }
           else {
             this.rutaProyecto=fieldNameFile.getText();
             datosAccesibles = true;
             botonFinalizarPulsado = true;
             dispose();
           }
         }
         else{
           JOptionPane.showMessageDialog(this, "El archivo debe de tener extensión zip", "Error",
                                   JOptionPane.WARNING_MESSAGE);
     datosAccesibles = false;
             botonFinalizarPulsado = false;
         }
      }
    }
    if (e.getSource() == bottonCancel) {
      datosAccesibles = false;
      botonFinalizarPulsado = false;
      dispose();
    }
  }

  /**
   * Método que implementa las acciones del botón buscar fichero.
   * @param e ActionEvent evento al que responde.
   */
  void buttonSearchFile_action(ActionEvent e) {
    JFileChooser dir = new JFileChooser();
    FilterExtension filtro = new FilterExtension("zip");
    dir.setFileFilter(filtro);
    dir.setDialogTitle("Seleccionar Archivo");
    int returnVal = dir.showOpenDialog(WindowOpenZipFile.this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File file = dir.getSelectedFile();
      String s = null;
      try {
        s = file.getCanonicalPath();
      }
      catch (IOException ex) {}
      fieldNameFile.setText(s);
    }
  }
}

/**
 * Clase que implementa las acciones del botón Abrir directorio.
 * @author Digna Rodríguez
 * @version 1.0
 */
class WOpenXML_buttonSearchFile_actionAdapter
    implements java.awt.event.ActionListener {
  WindowOpenZipFile vent;

  WOpenXML_buttonSearchFile_actionAdapter(WindowOpenZipFile v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.buttonSearchFile_action(e);
  }

}

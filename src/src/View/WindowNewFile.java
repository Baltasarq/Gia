package View;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import Model.Controller;

/**
 * Clase que implementa la ventana de elección del nombre y el directorio en el
 * que se va a guardar el fichero el contenido del proyecto.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowNewFile extends JDialog implements ActionListener{

  static boolean datosCompletados=false;
  static boolean botonOk=false;
  static boolean modelo= false;

  //Ruta en donde se almacenará el proyecto
  private static String nomArchivo;
  //Paneles
  JPanel principal = new JPanel();
  JPanel panelBotones=new JPanel();
  JPanel panelNombre = new JPanel();
  JPanel panelDirectorio = new JPanel();
  JPanel PanelContendor = new JPanel();
  JTextPane panelComentarios = new JTextPane();

  //Botones
  JButton botonGuardar = new JButton();
  JButton botonCancelar = new JButton();

  JLabel LabelNomArchivo = new JLabel();
  JLabel labelDirectorio = new JLabel();
  JLabel LabelImagen = new JLabel();
  JTextField fieldName = new JTextField();

  ImageIcon imagen = new ImageIcon();

  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  JRadioButton radioVista = new JRadioButton();
  JRadioButton radioModelo = new JRadioButton();
  XYLayout xYLayout6 = new XYLayout();
  ButtonGroup group = new ButtonGroup();


  //Constructor de clase
  public WindowNewFile(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  WindowNewFile() {
    this(null);
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception  en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception  {
    panelBotones.setLayout(xYLayout4);
    this.getContentPane().setLayout(xYLayout5);
    xYLayout5.setWidth(487);
    xYLayout5.setHeight(258);
    radioVista.setText("GUI");
    radioModelo.setSelected(true);
    radioModelo.setText("Modelo");
    panelDirectorio.setLayout(xYLayout6);
    panelComentarios.setSelectedTextColor(Color.white);
    panelBotones.add(botonGuardar, new XYConstraints(69, 3, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(161, 4, -1, -1));

    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource("Images/lateraltubos.png"));
    LabelImagen.setIcon(imagen);
    principal.setLayout(xYLayout1);
    this.setTitle("Nuevo Archivo Java");

    //Botones
    botonGuardar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonGuardar.setText("Guardar");
    botonGuardar.addActionListener(this);

    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);

    group.add(radioVista);
    group.add(radioModelo);

    LabelNomArchivo.setFont(new java.awt.Font("Dialog", 1, 11));
    LabelNomArchivo.setText("Nombre del Archivo");

    panelComentarios.setBackground(SystemColor.control);
    panelComentarios.setEditable(false);
    panelComentarios.setText("Rellene el nombre del archivo de código java a crear.\nColóquelo debajo " +
    "de la carpeta que le corresponda, marcando Modelo o GUI.");
    PanelContendor.setBorder(BorderFactory.createEtchedBorder());
    PanelContendor.setLayout(xYLayout3);
    fieldName.setText("");
    labelDirectorio.setText("Directorio");
    labelDirectorio.setFont(new java.awt.Font("Dialog", 1, 11));
    panelNombre.setLayout(xYLayout2);
    panelDirectorio.add(labelDirectorio, new XYConstraints(11, 8, -1, -1));
    panelDirectorio.add(radioModelo, new XYConstraints(84, 4, -1, -1));
    panelDirectorio.add(radioVista,  new XYConstraints(150, 5, -1, -1));
    PanelContendor.add(panelNombre,   new XYConstraints(4, 80, 310, -1));
    PanelContendor.add(panelComentarios, new XYConstraints(5, 14, 311, 49));
    PanelContendor.add(panelDirectorio, new XYConstraints(0, 112, 307, -1));
    principal.add(panelBotones, new XYConstraints(172, 211, 278, -1));
    this.getContentPane().add(principal,      new XYConstraints(0, 0, 487, 249));
    panelNombre.add(fieldName,    new XYConstraints(123, 5, 173, -1));
    panelNombre.add(LabelNomArchivo,     new XYConstraints(6, 10, -1, -1));
    principal.add(LabelImagen,   new XYConstraints(9, 6, 136, 244));
    principal.add(PanelContendor,        new XYConstraints(153, 13, 324, 180));

    setResizable(true);
  }

  /**
     * Método que cierra la ventana al salir.
     * @param e WindowEvent evento al que responde.
     */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
      botonOk=false;
    }
    super.processWindowEvent(e);
  }

  /**
    * Método que implementa los eventos de los botones aceptar y cancelar.
    * @param e WindowEvent evento al que responde.
    */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonGuardar) {
       if(this.fieldName.getText().compareTo("")!=0){
          if(Controller.validateFileName(this.fieldName.getText())==true){
              this.nomArchivo = this.fieldName.getText();
              if (this.radioModelo.isSelected())
                this.modelo = true;
              else
                this.modelo = false;

              datosCompletados = true;
              botonOk = true;
              dispose();
          }
          else{
            JOptionPane.showMessageDialog(this, "El nombre del archivo no puede contener los caracteres \\ /\"*|?<>", "Aviso",
                                               JOptionPane.WARNING_MESSAGE);
          datosCompletados = false;
          botonOk=false;
          }
      }
      else{
        JOptionPane.showMessageDialog(this, "Debe de dar un nombre al nuevo fichero.","Aviso",JOptionPane.WARNING_MESSAGE);
        datosCompletados = false;
        botonOk=false;
      }
    }
    if (e.getSource() == botonCancelar) {
      datosCompletados=false;
      botonOk=false;
      dispose();
    }
  }

 }






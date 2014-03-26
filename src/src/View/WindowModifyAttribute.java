package View;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;
import Model.Controller;

/**
 * Ventana desde la que se modifica un atributo de una clase.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowModifyAttribute
    extends JDialog
    implements ActionListener {

  static boolean botonModificarActivo = false;
  String nombreClase;
  String nombreAtbAnt = "";

  //Vector que contiene todos los datos de la clase a modificar
  Vector vAtb = new Vector();

  //Paneles
  JPanel principal = new JPanel();
  JPanel panelEspacioTrabajo = new JPanel();
  JPanel panelVisibilidad = new JPanel();
  JPanel panelBotones = new JPanel();

  //Botones
  JButton botonModificar = new JButton();
  JButton botonCancelar = new JButton();

  //Labels
  JLabel NombreAtb = new JLabel();
  JLabel visibilidad = new JLabel();
  JLabel estatico = new JLabel();
  JLabel etiquetaImagen = new JLabel();

  //Paneles
  JPanel PanelComentariosDirTrabajo = new JPanel();
  JPanel panelNombre = new JPanel();
  JPanel panelModificador = new JPanel();
  JPanel panelRadio = new JPanel();

  JTextField fieldAtbName = new JTextField();
  ButtonGroup group = new ButtonGroup();
  JCheckBox checkStatic = new JCheckBox("static");

  //Layouts
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  XYLayout xYLayout8 = new XYLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

  //RadioButtons
  JRadioButton radioPrivado = new JRadioButton();
  JRadioButton radioPublico = new JRadioButton();
  JRadioButton radioProtected = new JRadioButton();


  JComboBox comboVector = new JComboBox(Controller.vecTipo);
  XYLayout xYLayout4 = new XYLayout();
  JComboBox comboTipo = new JComboBox(Controller.vecTipo);
  JLabel tipo = new JLabel();
  JPanel panelTipo = new JPanel();

  //Constructor de clase
  public WindowModifyAttribute(Frame parent, String nomatbAnterior,String tit) {
    super(parent);
    this.nombreAtbAnt = nomatbAnterior;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit(tit);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit(String titulo) throws Exception {
    Controller.addTypes();

    principal.setLayout(xYLayout1);
    this.setTitle(titulo);
    this.getContentPane().setLayout(xYLayout8);

    ImageIcon imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/lateralengranaje.png"));
    etiquetaImagen.setIcon(imagen);

    comboTipo.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        modifyTypeAction(e);
      }
    }
    );

    comboVector.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        modifyTypeVectorAction(e);
      }
    }
    );

    //Botones
    botonModificar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonModificar.setText("Modificar");
    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonModificar.addActionListener(this);
    botonCancelar.addActionListener(this);

    //labels
    NombreAtb.setFont(new java.awt.Font("Dialog", 1, 11));
    NombreAtb.setText("Nombre Atributo");
    visibilidad.setFont(new java.awt.Font("Dialog", 1, 11));
    visibilidad.setText("Visibilidad");

    fieldAtbName.setPreferredSize(new Dimension(130, 20));
    fieldAtbName.setMinimumSize(new Dimension(11, 20));
    panelNombre.setLayout(xYLayout5);
    panelVisibilidad.setLayout(xYLayout6);
    panelBotones.setLayout(xYLayout7);

    panelEspacioTrabajo.setBorder(BorderFactory.createEtchedBorder());
    panelEspacioTrabajo.setLayout(xYLayout3);
    estatico.setText("Modificador");
    estatico.setFont(new java.awt.Font("Dialog", 1, 11));
    panelModificador.setLayout(xYLayout2);
    radioPrivado.setText("private");
    radioPublico.setSelected(true);
    radioPublico.setText("public");
    radioProtected.setOpaque(false);
    radioProtected.setText("protected");
    panelVisibilidad.setBorder(BorderFactory.createEtchedBorder());
    panelRadio.setLayout(verticalFlowLayout1);
    xYLayout8.setWidth(465);
    xYLayout8.setHeight(262);

    tipo.setText("Tipo de dato");
    tipo.setFont(new java.awt.Font("Dialog", 1, 11));
    panelTipo.setLayout(xYLayout4);
    panelVisibilidad.add(visibilidad, new XYConstraints(2, 5, -1, -1));
    panelVisibilidad.add(panelRadio, new XYConstraints(85, 0, -1, 87));
    panelRadio.add(radioPrivado, null);
    panelRadio.add(radioPublico, null);
    panelRadio.add(radioProtected, null);
    panelEspacioTrabajo.add(panelTipo, new XYConstraints(22, 134, 258, 35));
    panelTipo.add(tipo, new XYConstraints(4, 11, -1, -1));
    panelTipo.add(comboTipo, new XYConstraints(79, 6, 84, 23));
    panelTipo.add(comboVector, new XYConstraints(173, 6, 80, 23));
    panelEspacioTrabajo.add(panelNombre, new XYConstraints(12, 7, -1, -1));
    panelModificador.add(checkStatic, new XYConstraints(101, 6, -1, -1));
    panelModificador.add(estatico, new XYConstraints(5, 9, 82, -1));
    principal.add(panelBotones, new XYConstraints(179, 216, 239, -1));
    panelEspacioTrabajo.add(panelModificador,
                            new XYConstraints(27, 164, 209, -1));
    this.getContentPane().add(principal, new XYConstraints(0, 0, 478, 248));
    panelBotones.add(botonModificar, new XYConstraints(49, 2, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(143, 2, -1, -1));
    principal.add(etiquetaImagen, new XYConstraints(7, 9, -1, -1));
    principal.add(panelEspacioTrabajo, new XYConstraints(158, 8, 289, 200));
    panelNombre.add(NombreAtb, new XYConstraints(14, 8, -1, -1));
    panelNombre.add(fieldAtbName, new XYConstraints(120, 5, -1, -1));
    panelEspacioTrabajo.add(panelVisibilidad, new XYConstraints(24, 36, 239, 92));
    group.add(radioPrivado);
    group.add(radioPublico);
    group.add(radioProtected);
    if(titulo.compareTo("Añadir atributo")==0)
      this.botonModificar.setText("Aceptar");
    comboVector.setVisible(false);
    setResizable(true);
  }

  /**
   * El método completRecord comprueba si los datos de la clase se han rellenado.
   * @return boolean true si el registro se ha completado false en caso contrario.
   **/
  public boolean completRecord() {
    boolean res = true;
    if (this.fieldAtbName.getText().trim().compareTo("") == 0)
      res = false;
    return res;
  }

  /**
   * El método saveFields almacena el contenido de los datos recogidos
   * de los combobox y fields en el vector de la clase.
   **/
  public void saveFields() {
    vAtb.clear();
    vAtb.add(this.fieldAtbName.getText());
    vAtb.add( (String)this.comboTipo.getSelectedItem());

    if (this.radioPrivado.isSelected())
      vAtb.add("private");
    else {
      if (this.radioProtected.isSelected())
        vAtb.add("protected");
      else if (this.radioPublico.isSelected())
        vAtb.add("public");
    }
    if (this.checkStatic.isSelected())
      vAtb.add("true");
    else
      vAtb.add("false");

    if ( ( (String)this.comboTipo.getSelectedItem()).compareTo("Vector") == 0)
      vAtb.add( (String)this.comboVector.getSelectedItem());
    else
      vAtb.add("");

  }

  /**
   * El método fillFields carga en los combobox y fields los
   * datos de una clase para ser modificados.
   * @param v Vector que contiene los datos de la clase
   **/
  public void fillFields(Vector v) {
    if ( (v.size() == 5)) {
      this.fieldAtbName.setText(v.get(0).toString());
      this.comboTipo.setSelectedItem(v.get(1));
      if (v.get(2) == "public")this.radioPublico.setSelected(true);
      else if (v.get(2) == "private")this.radioPrivado.setSelected(true);
      else this.radioProtected.setSelected(true);
      if (v.get(3).toString().compareTo("true") == 0)
        this.checkStatic.setSelected(true);
      else
        this.checkStatic.setSelected(false);
      if (v.get(4) != "") {
        comboVector.setVisible(true);
        this.comboVector.setSelectedItem(v.get(4));
      }

    }
  }

  /**
   * Método que añade un nuevo comboBox si el tipo del atributo es un vector.
   * Esto sirve para introducir el tipo de los elementos del vector.
   * @param e ActionEvent evento de modificación del comboTypo
   */
  public void modifyTypeAction(ActionEvent e) {
    String tipo = (String)this.comboTipo.getSelectedItem();
    if (tipo.compareTo("Vector") == 0)
      this.comboVector.setVisible(true);
    else {
      if (Controller.existsClass(tipo)) {
        this.fieldAtbName.setText("$r_" + tipo);
        this.fieldAtbName.setEditable(false);
        this.comboVector.setVisible(false);
      }
      else {
        this.fieldAtbName.setEditable(true);
        this.comboVector.setVisible(false);
      }
    }
  }

  public void modifyTypeVectorAction(ActionEvent e) {
    String tipo = (String)this.comboVector.getSelectedItem();

    if (Controller.existsClass(tipo)) {
      this.fieldAtbName.setText("$v_" + tipo + "s");
      this.fieldAtbName.setEditable(false);
    }
    else
      this.fieldAtbName.setEditable(true);
  }

  /**
   * Método que comprueba si los atributos empiezan por '$v_' o '$r_' son
   * referencias a objetos.
   *
   * @param nombre String que se comprueba si es correcto o no.
   * @return boolean true si es correcto false en caso contrario.
   */
  public boolean correctName(String nombre) {
    boolean toret = false;
    String tres = nombre.substring(0, 3);
    if (tres.compareTo("$v_") == 0) {
      if ( ( (String)this.comboTipo.getSelectedItem()).compareTo("Vector") == 0) {
        if (! (Controller.existsClass( (String)this.comboVector.getSelectedItem())))
          toret = false;
        else
          toret = true;
      }
      else
        toret = false;
    }
    if (tres.compareTo("$r_") == 0) {
      if (Controller.existsClass( (String)this.comboTipo.getSelectedItem()))
        toret = true;
      else
        toret = false;
    }
    return toret;
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
      botonModificarActivo = false;
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      botonModificarActivo = false;
      this.dispose();
    }
    if (e.getSource() == botonModificar) {
      if (this.completRecord()) {
        if (this.fieldAtbName.getText().length() > 3) {

          if (this.nombreAtbAnt.compareTo(this.fieldAtbName.getText()) != 0) {
            if (!Controller.contenido.existsAtb(this.nombreClase,
                                                this.fieldAtbName.getText())) {
              String k = this.fieldAtbName.getText().substring(0, 1);
              if ( (k.compareTo("0") == 0) || (k.compareTo("1") == 0) ||
                  (k.compareTo("2") == 0) || (k.compareTo("3") == 0) ||
                  (k.compareTo("4") == 0) || (k.compareTo("5") == 0) ||
                  (k.compareTo("6") == 0) || (k.compareTo("7") == 0) ||
                  (k.compareTo("8") == 0) || (k.compareTo("9") == 0)) {
                JOptionPane.showMessageDialog(this,
                                              "La primera letra del nombre de un atributo no puede ser un número.",
                                              "Aviso",
                                              JOptionPane.WARNING_MESSAGE);
              }
              else {
                if (this.fieldAtbName.getText().indexOf(" ") != -1) {
                  JOptionPane.showMessageDialog(this,
                                                "El nombre de una clase no puede contener espacios en blanco",
                                                "Aviso",
                                                JOptionPane.WARNING_MESSAGE);
                }
                else {
                  String tres = this.fieldAtbName.getText().substring(0, 3);
                  if ( (tres.compareTo("$v_") == 0) ||
                      (tres.compareTo("$r_") == 0)) {
                    if (correctName(this.fieldAtbName.getText())) {
                      botonModificarActivo = true;
                      this.saveFields();
                      this.dispose();
                    }
                    else {
                      JOptionPane.showMessageDialog(this,
                          "El nombre del atributo no puede empezar por: '$v_' o '$r_' sino son referencias a objetos",
                          "Aviso",
                          JOptionPane.WARNING_MESSAGE);
                    }
                  }
                else{
                  botonModificarActivo = true;
                  this.saveFields();
                  this.dispose();
                }
                }
              }
            }
            else
              JOptionPane.showMessageDialog(this,
                                            "El nombre del atributo ya existe para esa clase.",
                                            "Aviso",
                                            JOptionPane.WARNING_MESSAGE);
          }
          else { //si no hemos cambiado el nombre del atributo.
            String tres = this.fieldAtbName.getText().substring(0, 3);
            if ( (tres.compareTo("$v_") == 0) || (tres.compareTo("$r_") == 0)) {
              if (correctName(this.fieldAtbName.getText())) {
                botonModificarActivo = true;
                this.saveFields();
                this.dispose();
              }
              else {
                JOptionPane.showMessageDialog(this,
                                              "El nombre del atributo no puede empezar por: '$v_' o '$r_' sino son referencias a objetos",
                                              "Aviso",
                                              JOptionPane.WARNING_MESSAGE);

              }
            }
            else{
                 botonModificarActivo = true;
                 this.saveFields();
                 this.dispose();
               }
          }
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "El nombre del atributo debe de tener más de 3 caracteres.",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }
      else
        JOptionPane.showMessageDialog(this,
                                      "Debe de rellenar todos los campos.",
                                      "Aviso", JOptionPane.WARNING_MESSAGE);
    }
  }
}

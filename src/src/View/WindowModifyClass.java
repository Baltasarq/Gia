package View;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import Model.*;
import java.io.File;

/**
 * Ventana desde la que se modifica una clase.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowModifyClass
    extends JDialog
    implements ActionListener {

  static boolean botonModificarActivo = false;
  Vector vClase = new Vector();

  String nombreAnterior = "";
  //Paneles
  JPanel principal = new JPanel();
  JPanel panelEspacioTrabajo = new JPanel();
  JPanel panelAbstacta = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel PanelComentariosDirTrabajo = new JPanel();
  JPanel panelNombre = new JPanel();
  JPanel panelRadio = new JPanel();
  JPanel panelVisibilidad = new JPanel();

  //Botones
  JButton botonModificar = new JButton();
  JButton botonCancelar = new JButton();

  //Labels
  JLabel NombreClase = new JLabel();
  JLabel abstracta = new JLabel();
  JLabel etiquetaImagen = new JLabel();
  JLabel visibilidad = new JLabel();

  JTextField fieldClassName = new JTextField();

  //Layouts
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout8 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();

  //RadioButtons
  JRadioButton radioPublico = new JRadioButton();
  JRadioButton radioProtected = new JRadioButton();
  JRadioButton radioPrivado = new JRadioButton();
  ButtonGroup group = new ButtonGroup();
  JCheckBox checkAbs = new JCheckBox();

  //Constructor de clase
  public WindowModifyClass(Frame parent, String nomAnt,String titulo) {
    super(parent);
    this.nombreAnterior = nomAnt;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit(titulo);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   * @param s String titulo de la ventana.
   * @throws Exception  en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit(String s) throws Exception {

    principal.setLayout(xYLayout1);
    this.setTitle(s);
    this.getContentPane().setLayout(xYLayout8);
    ImageIcon imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/lateralengranaje.png"));
    etiquetaImagen.setIcon(imagen);

    //Botones
    botonModificar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonModificar.setText("Modificar ");
    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonModificar.addActionListener(this);
    botonCancelar.addActionListener(this);

    //labels
    NombreClase.setFont(new java.awt.Font("Dialog", 1, 11));
    NombreClase.setText("Nombre Clase");
    abstracta.setFont(new java.awt.Font("Dialog", 1, 11));
    abstracta.setText("Abstracta");

    fieldClassName.setPreferredSize(new Dimension(130, 20));
    fieldClassName.setMinimumSize(new Dimension(11, 20));
    panelAbstacta.setLayout(xYLayout4);
    panelNombre.setLayout(xYLayout5);
    panelBotones.setLayout(xYLayout7);

    panelEspacioTrabajo.setBorder(BorderFactory.createEtchedBorder());
    panelEspacioTrabajo.setLayout(xYLayout2);
    xYLayout8.setWidth(430);
    xYLayout8.setHeight(247);
    radioPublico.setSelected(true);
    radioPublico.setText("public");
    panelRadio.setLayout(verticalFlowLayout1);
    radioProtected.setOpaque(true);
    radioProtected.setText("protected");
    group.add(radioPrivado);
    group.add(radioPublico);
    group.add(radioProtected);

    panelVisibilidad.setLayout(xYLayout6);
    panelVisibilidad.setBorder(BorderFactory.createEtchedBorder());
    radioPrivado.setText("private");
    visibilidad.setFont(new java.awt.Font("Dialog", 1, 11));
    visibilidad.setText("Visibilidad");
    checkAbs.setDoubleBuffered(false);
    checkAbs.setActionCommand("");
    checkAbs.setText("");
    panelAbstacta.add(abstracta, new XYConstraints(1, 6, -1, -1));
    panelAbstacta.add(checkAbs, new XYConstraints(66, 4, -1, -1));
    panelVisibilidad.add(visibilidad, new XYConstraints(2, 5, -1, -1));
    panelVisibilidad.add(panelRadio, new XYConstraints(85, 0, -1, 87));
    panelRadio.add(radioPrivado, null);
    panelRadio.add(radioPublico, null);
    panelRadio.add(radioProtected, null);
    principal.add(panelBotones, new XYConstraints(12, 189, 249, -1));
    panelEspacioTrabajo.add(panelNombre, new XYConstraints(0, 0, 243, 29));
    panelEspacioTrabajo.add(panelAbstacta, new XYConstraints(16, 136, 210, 27));
    panelBotones.add(botonCancelar, new XYConstraints(133, 4, -1, -1));
    panelBotones.add(botonModificar, new XYConstraints(29, 4, -1, -1));
    principal.add(panelEspacioTrabajo, new XYConstraints(6, 2, 255, 178));
    this.getContentPane().add(etiquetaImagen, new XYConstraints(7, 7, -1, -1));
    panelNombre.add(fieldClassName, new XYConstraints(103, 5, 133, -1));
    panelNombre.add(NombreClase, new XYConstraints(7, 8, -1, -1));
    panelEspacioTrabajo.add(panelVisibilidad, new XYConstraints(7, 36, 239, 95));
    this.getContentPane().add(principal, new XYConstraints(154, 8, 262, 224));
    if(s.compareTo("Añadir clase")==0)
      botonModificar.setText("Aceptar");
    setResizable(true);
  }

  /**
   * El método completRecord comprueba si los datos de la clase se han rellenado.
   * @return boolean true si el registro se ha completado false en caso contrario.
   **/
  public boolean completRecord() {
    boolean res = true;
    if (this.fieldClassName.getText().trim().compareTo("") == 0)
      res = false;
    return res;
  }

  /**
   * El método fillFields carga en los combobox y fields los
   * datos de una clase para ser modificados.
   * @param v Vector que contiene los datos de la clase
   **/
  public void fillFields(Vector v) {
    if (v.size() == 3) {
      this.fieldClassName.setText(v.get(0).toString());

      if (v.get(1) == "public")this.radioPublico.setSelected(true);
      else if (v.get(1) == "private")this.radioPrivado.setSelected(true);
      else this.radioProtected.setSelected(true);

      if (((String)v.get(2)).compareTo("true")==0)
        this.checkAbs.setSelected(true);
      else
        this.checkAbs.setSelected(false);
    }
  }

  /**
   * El método saveFields almacena el contenido de los datos recogidos
   * de los combobox y fields en el vector de la clase.
   **/
  public void saveFields() {
    this.vClase.clear();
    this.vClase.add(this.fieldClassName.getText());
    if (this.radioPrivado.isSelected())
      vClase.add("private");
    else {
      if (this.radioProtected.isSelected())
        vClase.add("protected");
      else if (this.radioPublico.isSelected())
        vClase.add("public");
    }
    if (this.checkAbs.isSelected())
      vClase.add("true");
    else
      vClase.add("false");


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
  * Método que cierra la ventana tras un suceso de botón.
  * @param e ActionEvent evento al que responde.
  */
 public void actionPerformed(ActionEvent e) {
   if (e.getSource() == botonCancelar) {
     botonModificarActivo = false;
     this.dispose();
   }
   if (e.getSource() == botonModificar) {
     if (this.completRecord()) {
       if (this.fieldClassName.getText().length() > 3) {
         if (MainWindow.nombreProyecto.compareTo(this.fieldClassName.getText()) !=
             0) {
           if (!Controller.contenido.existsClass(this.fieldClassName.getText())) {
             String k = this.fieldClassName.getText().substring(0, 1);
             if ( (k.compareTo("0") == 0) || (k.compareTo("1") == 0) ||
                 (k.compareTo("2") == 0) || (k.compareTo("3") == 0) ||
                 (k.compareTo("4") == 0) || (k.compareTo("5") == 0) ||
                 (k.compareTo("6") == 0) || (k.compareTo("7") == 0) ||
                 (k.compareTo("8") == 0) || (k.compareTo("9") == 0)) {
               JOptionPane.showMessageDialog(this,
                                             "La primera letra del nombre de una clase no puede ser un número.",
                                             "Aviso",
                                             JOptionPane.WARNING_MESSAGE);
             }
             else {
               if (this.fieldClassName.getText().indexOf(" ") != -1) {
                 JOptionPane.showMessageDialog(this,
                                               "El nombre de una clase no puede contener espacios en blanco",
                                               "Aviso",
                                               JOptionPane.WARNING_MESSAGE);
               }
               else {
                 botonModificarActivo = true;
                 this.saveFields();
                 this.dispose();
               }
             }
           }
           else
             JOptionPane.showMessageDialog(this,
                                           "El nombre de la clase ya existe en el sistema.",
                                           "Aviso",
                                           JOptionPane.WARNING_MESSAGE);
         }
         else
           JOptionPane.showMessageDialog(this,
               "El nombre de una clase no puede ser el mismo que el nombre del proyecto.",
                                         "Aviso", JOptionPane.WARNING_MESSAGE);
       }
       else
         JOptionPane.showMessageDialog(this,
             "El nombre de la clase debe de tener más de 3 caracteres.",
                                       "Aviso", JOptionPane.WARNING_MESSAGE);
     }
     else
       JOptionPane.showMessageDialog(this, "Debes rellenar todos los campos.",
                                     "Aviso", JOptionPane.WARNING_MESSAGE);
   }
 }
}


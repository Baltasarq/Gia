package View;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;
import Model.Controller;
import Model.UMLParameter;

/**
 * Ventana desde la que se modifica un método de una clase.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowModifyMethod
    extends JDialog
    implements ActionListener {

  static boolean botonModificarActivo = false;
  String nombreant = "";

  //Vector que contiene todos los datos del método a modificar
  Vector vOper = new Vector();
  Vector vparametros = new Vector();

  //Paneles
  JPanel principal = new JPanel();
  JPanel subpanel = new JPanel();
  JPanel panelVisibility = new JPanel();
  JPanel panelReturn = new JPanel();
  JPanel panelButtons = new JPanel();

  //Botones
  JButton buttonModify = new JButton();
  JButton buttonCancel = new JButton();

  //Labels
  JLabel NombreMet = new JLabel();
  JLabel tipoRetorno = new JLabel();
  JLabel visibilidad = new JLabel();
  JPanel panelName = new JPanel();

  JTextField fieldOperName = new JTextField();

  JComboBox comboTipo = new JComboBox(Controller.vecTipoReturn);
  ButtonGroup group = new ButtonGroup();

  //Layouts
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout4 = new XYLayout();
  XYLayout xYLayout5 = new XYLayout();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout7 = new XYLayout();
  JPanel panelMod = new JPanel();
  JLabel modificadores = new JLabel();
  JCheckBox checkStatic = new JCheckBox();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();
  JRadioButton radioPrivado = new JRadioButton();
  JRadioButton radioPublico = new JRadioButton();
  JRadioButton radioProtected = new JRadioButton();

  JPanel panelRadio = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JCheckBox checkAbstract = new JCheckBox();
  JPanel panelParameters = new JPanel();
  XYLayout xYLayout8 = new XYLayout();
  JPanel panelModificadores = new JPanel();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JButton buttonSiguiente = new JButton();
  JLabel etiquetaImagen = new JLabel();
  XYLayout xYLayout9 = new XYLayout();

  public WindowModifyMethod(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowModifyMethod() {
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
    principal.setLayout(xYLayout1);
    this.setTitle("Modificar un Método del sistema");
    this.getContentPane().setLayout(xYLayout9);
    ImageIcon imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/lateralengranaje.png"));
    etiquetaImagen.setIcon(imagen);

    //Botones
    buttonModify.setFont(new java.awt.Font("Dialog", 1, 11));
    buttonModify.setText("Aceptar");
    buttonCancel.setFont(new java.awt.Font("Dialog", 1, 11));
    buttonCancel.setText("Cancelar");
    buttonModify.addActionListener(this);
    buttonCancel.addActionListener(this);

    //labels
    NombreMet.setFont(new java.awt.Font("Dialog", 1, 11));
    NombreMet.setText("Nombre Método");
    tipoRetorno.setFont(new java.awt.Font("Dialog", 1, 11));
    tipoRetorno.setText("Tipo de retorno");
    visibilidad.setFont(new java.awt.Font("Dialog", 1, 11));
    visibilidad.setText("Visibilidad");

    fieldOperName.setPreferredSize(new Dimension(130, 20));
    fieldOperName.setMinimumSize(new Dimension(11, 20));
    panelReturn.setLayout(xYLayout4);
    panelName.setLayout(xYLayout5);
    panelVisibility.setLayout(xYLayout6);
    panelButtons.setLayout(xYLayout7);

    subpanel.setBorder(BorderFactory.createEtchedBorder());
    subpanel.setLayout(xYLayout3);
    modificadores.setText("Modificadores");
    modificadores.setFont(new java.awt.Font("Dialog", 1, 11));
    checkStatic.setText("static");
    panelMod.setLayout(xYLayout2);
    radioPrivado.setText("private");
    radioPublico.setSelected(true);
    radioPublico.setText("public");
    radioProtected.setOpaque(true);
    radioProtected.setText("protected");
    panelVisibility.setBorder(BorderFactory.createEtchedBorder());
    panelRadio.setLayout(verticalFlowLayout1);
    checkAbstract.setText("abstract");
    panelParameters.setLayout(xYLayout8);
    panelModificadores.setLayout(verticalFlowLayout2);
    buttonSiguiente.addActionListener(this);
    buttonSiguiente.setText("Modificar parámetros");
    buttonSiguiente.setFont(new java.awt.Font("Dialog", 1, 11));
    etiquetaImagen.setIcon(imagen);
    xYLayout9.setWidth(464);
    xYLayout9.setHeight(303);
    panelModificadores.add(checkStatic, null);
    panelModificadores.add(checkAbstract, null);
    subpanel.add(panelParameters, new XYConstraints(46, 268, -1, -1));
    subpanel.add(panelName, new XYConstraints(17, 3, -1, -1));
    panelVisibility.add(visibilidad, new XYConstraints(2, 5, -1, -1));
    panelVisibility.add(panelRadio, new XYConstraints(85, 0, -1, 87));
    panelRadio.add(radioPrivado, null);
    panelRadio.add(radioPublico, null);
    panelRadio.add(radioProtected, null);
    principal.add(panelButtons, new XYConstraints(174, 261, 279, -1));
    subpanel.add(panelReturn, new XYConstraints(32, 133, -1, -1));
    principal.add(etiquetaImagen, new XYConstraints(8, 12, -1, -1));
    panelReturn.add(comboTipo, new XYConstraints(123, 6, 93, 27));
    panelReturn.add(tipoRetorno, new XYConstraints(4, 11, 86, -1));
    subpanel.add(panelMod, new XYConstraints(41, 174, 209, 57));
    this.getContentPane().add(principal, new XYConstraints(0, 0, 471, 396));
    panelButtons.add(buttonModify, new XYConstraints(51, 3, -1, -1));
    panelButtons.add(buttonCancel, new XYConstraints(149, 3, -1, -1));
    principal.add(buttonSiguiente, new XYConstraints(14, 268, -1, -1));
    principal.add(subpanel, new XYConstraints(163, 11, 292, 242));
    panelName.add(NombreMet, new XYConstraints(9, 8, 94, -1));
    panelName.add(fieldOperName, new XYConstraints(120, 5, -1, -1));
    subpanel.add(panelVisibility, new XYConstraints(25, 34, 239, 95));
    group.add(radioPrivado);
    group.add(radioPublico);
    group.add(radioProtected);
    setResizable(true);
    panelMod.add(modificadores, new XYConstraints(5, 9, 82, -1));
    panelMod.add(panelModificadores, new XYConstraints(100, 4, -1, 63));
  }

  /**
   * El método completRecord comprueba si los datos de la clase se han rellenado.
   * @return boolean true si el registro se ha completado false en caso contrario.
   **/
  public boolean completRecord() {
    boolean res = true;
    if (this.fieldOperName.getText().trim().compareTo("") == 0)
      res = false;
    return res;
  }

  /**
   * El método saveFields almacena el contenido de los datos recogidos
   * de los combobox y fields en el vector de la clase.
   **/
  public void saveMethod() {

    vOper.clear();
    vOper.add(this.fieldOperName.getText());
    vOper.add( (String)this.comboTipo.getSelectedItem());

    if (this.radioPrivado.isSelected())
      vOper.add("private");
    else {
      if (this.radioProtected.isSelected())
        vOper.add("protected");
      else if (this.radioPublico.isSelected())
        vOper.add("public");
    }
    if (this.checkStatic.isSelected())
      vOper.add("true");
    else
      vOper.add("false");

    if (this.checkAbstract.isSelected())
      vOper.add("true");
    else
      vOper.add("false");
    System.out.println("despues de modificar::" + vOper);
  }

  /**
   * El método fillFields carga en los combobox y fields los
   * datos de un método para ser modificados.
   * @param v Vector que contiene los datos del método
   **/
  public void fillFields(Vector v) {
    if (v.size() == 5 || v.size() == 6) {
      this.fieldOperName.setText(v.get(0).toString());
      this.comboTipo.setSelectedItem(v.get(1));
      if (v.get(2) == "public")this.radioPublico.setSelected(true);
      else if (v.get(2) == "private")this.radioPrivado.setSelected(true);
      else this.radioProtected.setSelected(true);
      if (v.get(3) == "true")
        this.checkStatic.setSelected(true);
      else
        this.checkStatic.setSelected(false);
      if (v.get(4) == "true")
        this.checkAbstract.setSelected(true);
      else
        this.checkAbstract.setSelected(false);
    }
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
    if (e.getSource() == buttonCancel) {
      botonModificarActivo = false;
      this.dispose();
    }
    else {
      if (e.getSource() == buttonModify) {
        if (this.completRecord()) {
          String k = this.fieldOperName.getText().substring(0, 1);
          if ( (k.compareTo("0") == 0) || (k.compareTo("1") == 0) ||
              (k.compareTo("2") == 0) || (k.compareTo("3") == 0) ||
              (k.compareTo("4") == 0) || (k.compareTo("5") == 0) ||
              (k.compareTo("6") == 0) || (k.compareTo("7") == 0) ||
              (k.compareTo("8") == 0) || (k.compareTo("9") == 0)) {
            JOptionPane.showMessageDialog(this,
                                          "La primera letra del nombre de un método no puede ser un número.",
                                          "Aviso",
                                          JOptionPane.WARNING_MESSAGE);
          }
          else {
            if (this.fieldOperName.getText().indexOf(" ") != -1) {
              JOptionPane.showMessageDialog(this,
                                            "El nombre de un método no puede contener espacios en blanco",
                                            "Aviso",
                                            JOptionPane.WARNING_MESSAGE);
            }
            else {

              botonModificarActivo = true;
              this.saveMethod();
              this.dispose();
            }
          }
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "Debes rellenar todos los campos.",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }
      else if (e.getSource() == this.buttonSiguiente) {
        if (this.vparametros.size() > 0) {
          if (this.completRecord()) {
            //introducimos los parametros
            WindowModifyParameters v;
            v = new WindowModifyParameters(this.vparametros);
            v.setSize(600, 300);
            Dimension dlgSize = v.getPreferredSize();
            Dimension frmSize = getSize();
            Point loc = getLocation();
            v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                          (frmSize.height - dlgSize.height) / 2 + loc.y);
            v.setModal(true);
            v.pack();
            v.show();
            if (v.botonAceptarPulsado == true) {
              botonModificarActivo = true;
              this.saveMethod();

              this.vparametros = new Vector();
              for (int i = 0; i < v.vparam.size(); i++) {
                UMLParameter par = ( (UMLParameter) v.vparam.get(i));
                this.vparametros.add(par);
              }
              this.dispose();
            }
          }
          else
            JOptionPane.showMessageDialog(this,
                                          "Debes rellenar todos los campos.",
                                          "Aviso", JOptionPane.WARNING_MESSAGE);
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "No existen parámetros a modificar",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}

package View;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import com.borland.jbcl.layout.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import Model.*;

/**
 * Clase que implementa la ventana que permite añadir un método
 * a una clase del proyecto.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowAddMethod
    extends JDialog
    implements ActionListener {

  static boolean botonFinalizarActivo = false;
  static boolean botonAceptarActivo = false;

  //Vector que contiene todos los datos de la clase a añadir
  Vector vMethod = new Vector();
  Vector vPar = new Vector();
  int conta = 0;

  //Labels
  JLabel NombreMetodo = new JLabel();
  JPanel panelNombre = new JPanel();

  JTextField cajaNombre = new JTextField();

  //Layouts
  JPanel panelRetorno = new JPanel();
  JComboBox comboTipo = new JComboBox(Controller.vecTipoReturn);
  JLabel tipoRetorno = new JLabel();
  XYLayout xYLayout8 = new XYLayout();
  JPanel panelModificador = new JPanel();
  JCheckBox checkStatic = new JCheckBox();
  JLabel modificadores = new JLabel();
  JCheckBox chekAbstract = new JCheckBox();
  XYLayout xYLayout10 = new XYLayout();
  JTextField numParam = new JTextField();
  JPanel panelParametros = new JPanel();
  JLabel NumParam = new JLabel();
  ButtonGroup group = new ButtonGroup();
  JPanel panelPrincipal = new JPanel();
  JPanel panelCheck = new JPanel();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  JButton botonCancelar = new JButton();
  JPanel panelBotones = new JPanel();
  JButton botonSiguiente = new JButton();
  XYLayout xYLayout5 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();
  XYLayout xYLayout6 = new XYLayout();
  XYLayout xYLayout1 = new XYLayout();

  JPanel panelRadio = new JPanel();

  JPanel panelVisibilidad = new JPanel();
  JRadioButton radioPublico = new JRadioButton();
  JRadioButton radioProtected = new JRadioButton();
  JRadioButton radioPrivado = new JRadioButton();

  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel visibilidad = new JLabel();
  XYLayout xYLayout7 = new XYLayout();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  BoxLayout2 boxLayout22 = new BoxLayout2();

  //Constructor de clase
  public WindowAddMethod(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowAddMethod() {
    this(null);
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception lanza una excepción si no se puede pintar la ventana.
   */
  private void jbInit() throws Exception {
    numParam.setMinimumSize(new Dimension(11, 20));
    numParam.setPreferredSize(new Dimension(130, 20));
    numParam.setText("0");
    ImageIcon imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/lateralengranaje.png"));
    numParam.addMouseMotionListener(new WindowMouseMotion_NumParam(this));
    numParam.addActionListener(new WindowAction_NumParam(this));
    numParam.addMouseMotionListener(new WindowMouseMotion_NumParam(this));
    panelParametros.setLayout(xYLayout10);
    NumParam.setFont(new java.awt.Font("Dialog", 1, 11));
    NumParam.setText("Número de parámetros");
    this.setResizable(false);
    this.getContentPane().setLayout(xYLayout1);
    panelCheck.setLayout(verticalFlowLayout2);
    botonCancelar.setFont(new java.awt.Font("Dialog", 1, 11));
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);
    panelBotones.setLayout(xYLayout6);
    botonSiguiente.setFont(new java.awt.Font("Dialog", 1, 11));
    botonSiguiente.setText("Aceptar");
    botonSiguiente.addActionListener(this);
    panelRetorno.setPreferredSize(new Dimension(190, 19));
    panelPrincipal.setLayout(xYLayout5);
    etiquetaImagen.setIcon(imagen);
    xYLayout1.setWidth(443);
    xYLayout1.setHeight(302);
    radioPublico.setText("public");
    radioPublico.setSelected(true);
    panelRadio.setLayout(verticalFlowLayout1);
    radioProtected.setOpaque(true);
    radioProtected.setText("protected");
    panelVisibilidad.setLayout(xYLayout7);
    panelVisibilidad.setBorder(BorderFactory.createEtchedBorder());
    radioPrivado.setText("private");
    visibilidad.setFont(new java.awt.Font("Dialog", 1, 11));
    visibilidad.setText("Visibilidad");
    panelPrincipal.setBorder(BorderFactory.createEtchedBorder());
    NombreMetodo.setToolTipText("");
    panelCheck.add(checkStatic, null);
    panelCheck.add(chekAbstract, null);
    panelPrincipal.add(panelParametros, new XYConstraints(14, 209, 195, 26));
    panelPrincipal.add(panelNombre, new XYConstraints(12, 5, -1, -1));
    panelRetorno.add(tipoRetorno, null);
    panelRetorno.add(comboTipo, null);
    panelVisibilidad.add(visibilidad, new XYConstraints(2, 5, -1, -1));
    panelVisibilidad.add(panelRadio, new XYConstraints(85, 0, -1, 87));
    panelRadio.add(radioPrivado, null);
    panelRadio.add(radioPublico, null);
    panelRadio.add(radioProtected, null);
    panelPrincipal.add(panelModificador, new XYConstraints(11, 149, 183, 50));
    this.getContentPane().add(panelBotones, new XYConstraints(193, 265, 183, -1));
    panelBotones.add(botonSiguiente, new XYConstraints(2, 2, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(99, 2, -1, -1));
    this.getContentPane().add(etiquetaImagen, new XYConstraints(8, 10, -1, -1));
    this.getContentPane().add(panelPrincipal,
                              new XYConstraints(155, 12, 278, 245));
    panelNombre.add(NombreMetodo, null);
    panelNombre.add(cajaNombre, null);
    panelPrincipal.add(panelRetorno, new XYConstraints(10, 125, 251, 20));
    group.add(radioPrivado);
    group.add(radioPublico);
    group.add(radioProtected);

    this.setTitle("Añadir un método al sistema");
    //labels
    NombreMetodo.setFont(new java.awt.Font("Dialog", 1, 11));
    NombreMetodo.setText("  Nombre del Método   ");

    cajaNombre.setPreferredSize(new Dimension(130, 20));
    cajaNombre.setMinimumSize(new Dimension(11, 20));
    panelNombre.setLayout(boxLayout21);

    panelRetorno.setLayout(boxLayout22);
    tipoRetorno.setFont(new java.awt.Font("Dialog", 1, 11));
    tipoRetorno.setText(" Tipo de retorno       ");
    panelModificador.setLayout(xYLayout8);
    checkStatic.setText("static");
    modificadores.setText(" Modificadores");
    modificadores.setFont(new java.awt.Font("Dialog", 1, 11));
    chekAbstract.setText("abstract");

    panelParametros.add(NumParam, new XYConstraints(2, 2, 131, -1));
    panelParametros.add(numParam, new XYConstraints(148, 0, 35, -1));
    panelPrincipal.add(panelVisibilidad, new XYConstraints(15, 28, -1, -1));
    panelModificador.add(modificadores, new XYConstraints(2, 6, 85, -1));
    panelModificador.add(panelCheck, new XYConstraints(103, 0, -1, 59));
  }

  /**
   * El método completRecord comprueba si los datos del método se han rellenado.
   * @return boolean true si el registro se ha completado false en caso contrario.
   **/
  public boolean completRecord() {
    boolean res = true;
    if (this.cajaNombre.getText().trim().compareTo("") == 0)
      res = false;
    return res;
  }

  /**
   * El método saveClass almacena los datos del método en un vector.
   **/
  public void saveMethod() {
    vMethod.clear();
    vMethod.add(this.cajaNombre.getText());
    vMethod.add( (String)this.comboTipo.getSelectedItem());

    if (this.radioPrivado.isSelected())
      vMethod.add("private");
    else {
      if (this.radioProtected.isSelected())
        vMethod.add("protected");
      else if (this.radioPublico.isSelected())
        vMethod.add("public");
    }
    //static
    if (this.checkStatic.isSelected())
      vMethod.add("true");
    else
      vMethod.add("false");
      //abstract
    if (this.chekAbstract.isSelected())
      vMethod.add("true");
    else
      vMethod.add("false");

      //si tiene parametros
    int cont = Integer.parseInt(numParam.getText());
    if (cont != 0)
      vMethod.add(this.numParam.getText());

  }

  /**
   * Cambia la etiqueta del botón aceptar a siguiente si el método tiene
   * parámetros.
   * @param e ActionEvent evento al que responde.
   */
  public void addParameterAction(ActionEvent e) {
    if (numParam.getText().compareTo("") != 0) {
      String x = this.numParam.getText();
      if ( (x.compareTo("1") == 0) ||
          (x.compareTo("2") == 0) || (x.compareTo("3") == 0) ||
          (x.compareTo("4") == 0) || (x.compareTo("5") == 0) ||
          (x.compareTo("6") == 0) || (x.compareTo("7") == 0) ||
          (x.compareTo("8") == 0) || (x.compareTo("9") == 0)) {
        {
          this.conta = Integer.parseInt(numParam.getText());
          botonSiguiente.setText("Siguiente");
          panelPrincipal.updateUI();
          panelPrincipal.revalidate();
          panelPrincipal.revalidate();
          panelPrincipal.updateUI();
          this.setModal(true);
          this.pack();
          this.show();
        }
      }
    }
  }

  /**
   * Cambia la etiqueta del botón aceptar a siguiente si el método tiene
   * parámetros.
   * @param e MouseEvent evento de ratón al que responde.
   */
  public void addParameterMouse(MouseEvent e) {
    if (numParam.getText().compareTo("") != 0) {
      String x = this.numParam.getText();
      if ( (x.compareTo("1") == 0) ||
          (x.compareTo("2") == 0) || (x.compareTo("3") == 0) ||
          (x.compareTo("4") == 0) || (x.compareTo("5") == 0) ||
          (x.compareTo("6") == 0) || (x.compareTo("7") == 0) ||
          (x.compareTo("8") == 0) || (x.compareTo("9") == 0)) {
        {
          this.conta = Integer.parseInt(numParam.getText());
          botonSiguiente.setText("Siguiente");
          panelPrincipal.updateUI();
          panelPrincipal.revalidate();
          panelPrincipal.revalidate();
          panelPrincipal.updateUI();
          this.setModal(true);
          this.pack();
          this.show();
        }
      }
    }
  }

  /**
   * Cambia la etiqueta del botón aceptar a siguiente si el método tiene
   * parámetros.
   * @param e KeyEvent evento de teclado al que responde.
   */
  public void addParameterKey(KeyEvent e) {
    if (numParam.getText().compareTo("") != 0) {
      String x = this.numParam.getText();
      if ( (x.compareTo("1") == 0) ||
          (x.compareTo("2") == 0) || (x.compareTo("3") == 0) ||
          (x.compareTo("4") == 0) || (x.compareTo("5") == 0) ||
          (x.compareTo("6") == 0) || (x.compareTo("7") == 0) ||
          (x.compareTo("8") == 0) || (x.compareTo("9") == 0)) {
        {
          this.conta = Integer.parseInt(numParam.getText());
          botonSiguiente.setText("Siguiente");
          panelPrincipal.updateUI();
          panelPrincipal.revalidate();
          panelPrincipal.revalidate();
          panelPrincipal.updateUI();
          this.setModal(true);
          this.pack();
          this.show();
        }
      }
    }
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
      botonFinalizarActivo = false;
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que cierra la ventana tras un suceso de botón.
   * @param e ActionEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonCancelar) {
      botonAceptarActivo = false;
      this.dispose();
    }
    if (e.getSource() == botonSiguiente) {
      if (this.completRecord()) {
        String k = this.cajaNombre.getText().substring(0, 1);
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
          if (this.cajaNombre.getText().indexOf(" ") != -1) {
            JOptionPane.showMessageDialog(this,
                                          "El nombre de un método no puede contener espacios en blanco",
                                          "Aviso",
                                          JOptionPane.WARNING_MESSAGE);
          }
          else {
            String x = this.numParam.getText(); //comprobamos que  el número de parámetros es correcto.
            if ( (x.compareTo("0") == 0) || (x.compareTo("1") == 0) ||
                (x.compareTo("2") == 0) || (x.compareTo("3") == 0) ||
                (x.compareTo("4") == 0) || (x.compareTo("5") == 0) ||
                (x.compareTo("6") == 0) || (x.compareTo("7") == 0) ||
                (x.compareTo("8") == 0) || (x.compareTo("9") == 0)) {
              {
                if (x.compareTo("0") == 0) { // no hay parámetros
                  botonAceptarActivo = true;
                  this.saveMethod();
                  this.dispose();
                }
                else { // hay parámetros
                  WindowAddParameters v;
                  v = new WindowAddParameters(this.conta);
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
                    this.vPar = v.vparametros;
                    botonAceptarActivo = true;
                    this.saveMethod();
                    this.dispose();
                  }
                }
              }
            }
            else
              JOptionPane.showMessageDialog(this,
                                            "El número de parámetros debe de ser un dígito (0-9)",
                                            "Aviso",
                                            JOptionPane.WARNING_MESSAGE);
          }
        }
      }
      else
        JOptionPane.showMessageDialog(this,
                                      "Debe de rellenar todos los campos.",
                                      "Aviso", JOptionPane.WARNING_MESSAGE);

    }
  }
}

class WindowMouse_NumParam
    implements MouseListener, KeyListener {
  WindowAddMethod vent;

  WindowMouse_NumParam(WindowAddMethod v) {
    this.vent = v;
  }

  public void mouseClicked(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void mouseEntered(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void mouseExited(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void mousePressed(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void mouseReleased(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void keyReleased(KeyEvent e) {
    vent.addParameterKey(e);
  }

  public void keyTyped(KeyEvent e) {
    vent.addParameterKey(e);
  }

  public void keyPressed(KeyEvent e) {
    vent.addParameterKey(e);
  }

}

class WindowAction_NumParam
    implements ActionListener {
  WindowAddMethod vent;

  WindowAction_NumParam(WindowAddMethod v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.addParameterAction(e);
  }
}

class WindowMouseMotion_NumParam
    implements MouseMotionListener {
  WindowAddMethod vent;

  WindowMouseMotion_NumParam(WindowAddMethod v) {
    this.vent = v;
  }

  public void mouseDragged(MouseEvent e) {
    vent.addParameterMouse(e);
  }

  public void mouseMoved(MouseEvent e) {
    vent.addParameterMouse(e);
  }

}

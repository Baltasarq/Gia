package View;

import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import Model.UMLParameter;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import Model.Controller;

/**
 * Clase que implementa la ventana de añadir los parámetros a un método.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowAddOneParameter
    extends JDialog
    implements ActionListener {

  Vector otherParams = new Vector();
  boolean botonAceptarPulsado = false;
  boolean botonCancelarPulsado = false;
  JPanel panelBotones = new JPanel();
  JButton botonAceptar = new JButton();
  JButton botonCancelar = new JButton();
  UMLParameter par = new UMLParameter();
  int contador = 0;
  JPanel panelContenedor = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JPanel panelTitulo = new JPanel();
  JLabel labelNom = new JLabel();
  JLabel labelTipo = new JLabel();
  XYLayout xYLayout3 = new XYLayout();
  Border border1;
  JTextField fieldnombre = new JTextField();
  XYLayout xYLayout4 = new XYLayout();
  JPanel panelPar = new JPanel();
  JComboBox tipo;

  public WindowAddOneParameter(Vector v) {
    this.otherParams = new Vector();
    for (int i = 0; i < v.size(); i++)
      this.otherParams.addElement(v.get(i));

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(BorderFactory.
                                                 createEtchedBorder(Color.white,
        new Color(168, 168, 168)), BorderFactory.createEmptyBorder(1, 1, 0, 1));
    tipo = new JComboBox(Controller.vecTipo);
    this.setTitle("Añadir un Parámetros al método");
    botonAceptar.setText("Aceptar");
    botonCancelar.setText("Cancelar");
    panelContenedor.setLayout(xYLayout1);
    panelBotones.setLayout(xYLayout2);
    labelNom.setFont(new java.awt.Font("Dialog", 1, 12));
    labelNom.setText("Nombre Parámetro");
    labelTipo.setFont(new java.awt.Font("Dialog", 1, 12));
    labelTipo.setText("Tipo Parámetro");
    panelTitulo.setLayout(xYLayout3);
    panelTitulo.setFont(new java.awt.Font("Dialog", 1, 12));
    panelTitulo.setBorder(border1);
    panelPar.setLayout(xYLayout4);
    tipo.setFont(new java.awt.Font("Dialog", 0, 10));
    panelBotones.add(botonAceptar, new XYConstraints(40, 1, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(119, 0, -1, -1));
    panelContenedor.add(panelPar, new XYConstraints(17, 51, -1, -1));
    panelContenedor.add(panelBotones, new XYConstraints(22, 98, 227, -1));
    panelTitulo.add(labelNom, new XYConstraints(14, 5, -1, -1));
    panelTitulo.add(labelTipo, new XYConstraints(151, 7, -1, 15));
    panelTitulo.add(fieldnombre, new XYConstraints(11, 36, 125, 19));
    panelTitulo.add(tipo, new XYConstraints(153, 32, 101, 20));
    panelContenedor.add(panelTitulo, new XYConstraints(7, 9, 268, 80));
    this.getContentPane().add(panelContenedor, BorderLayout.CENTER);
    botonAceptar.addActionListener(this);
    botonCancelar.addActionListener(this);
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      botonCancelarPulsado = true;
      dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * El método completRecord comprueba si los datos del parámetro se han rellenado.
   * @return boolean true si el registro se ha completado false en caso contrario.
   **/
  public boolean completRecord() {
    boolean res = false;
    if (this.fieldnombre.getText() != "")
      res = true;
    return res;
  }

  /**
   * El método paramExists comprueba que los nombres de los parámetros son distintos.
   * @param nombre String nombre del parámetro.
   * @return boolean true si hay repetidos.
   **/
  public boolean paramExists(String nombre) {
    boolean repetidos = false;
    for (int i = 0; i < this.otherParams.size(); i++) {
      String nompar = (String)this.otherParams.get(i);
      if ( (nombre.compareTo(nompar) == 0)) {
        repetidos = true;
        return repetidos;
      }
    }
    return repetidos;
  }

  /**
   * Método que cierra la ventana tras un suceso de botón.
   * @param e ActionEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonCancelar) {
      botonAceptarPulsado = false;
      botonCancelarPulsado = true;
      this.dispose();
    }
    else if (e.getSource() == botonAceptar) {
      if (this.completRecord()) {
        String k = this.fieldnombre.getText().substring(0, 1);
        if ( (k.compareTo("0") == 0) || (k.compareTo("1") == 0) ||
            (k.compareTo("2") == 0) || (k.compareTo("3") == 0) ||
            (k.compareTo("4") == 0) || (k.compareTo("5") == 0) ||
            (k.compareTo("6") == 0) || (k.compareTo("7") == 0) ||
            (k.compareTo("8") == 0) || (k.compareTo("9") == 0)) {
          JOptionPane.showMessageDialog(this,
                                        "La primera letra del nombre de un parámetro no puede ser un número.",
                                        "Aviso",
                                        JOptionPane.WARNING_MESSAGE);
        }
        else {
          boolean repetido = paramExists(this.fieldnombre.getText());
          if (repetido == false) {
            String nompar = this.fieldnombre.getText();
            String tipopar = this.tipo.getSelectedItem().toString();
            this.par = new UMLParameter(nompar, tipopar);
            botonAceptarPulsado = true;
            this.dispose();
          }
          else
            JOptionPane.showMessageDialog(this,
                                          "Ya existe un parámetro con ese nombre.",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
      }

    else
      JOptionPane.showMessageDialog(this,
                                    "Debes rellenar el nombre del parámetro para continuar",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
    }
  }
}

package View;

import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.awt.event.*;
import Model.*;
import java.io.File;

/**
 * Panel que contiene un formulario para a�adir un nuevo par�metro a un m�todo.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class ParamPanel
    extends JPanel {
  JPanel panelPar = new JPanel();
  public JTextField fieldnombre = new JTextField();
  public JComboBox tipo;
  XYLayout xYLayout1 = new XYLayout();
  JButton botonBorrar = new JButton();
  XYLayout xYLayout2 = new XYLayout();
  ImageIcon iClose;

  public ParamPanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El m�todo jbInit se encarga de la inicializaci�n de componentes
   * de la interfaz.
   * @throws en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepci�n.
   */
  private void jbInit() throws Exception {
    iClose = new ImageIcon(MainWindow.class.getResource(
        "Images/close_mini.png"));
    botonBorrar.setBorderPainted(true);
    botonBorrar.setIcon(iClose);
    panelPar.setLayout(xYLayout1);
    tipo = new JComboBox(Controller.vecTipo);

    tipo.addActionListener(new ParamPanel_tipo_actionAdapter(this));
    botonBorrar.addActionListener(new ParamPanel_Delete_ActionAdapter(this));
    tipo.setFont(new java.awt.Font("Dialog", 0, 9));
    this.setLayout(xYLayout2);
    xYLayout2.setWidth(289);
    xYLayout2.setHeight(31);
    panelPar.add(fieldnombre, new XYConstraints(4, 2, 120, 19));
    this.add(botonBorrar, new XYConstraints(247, 7, 34, -1));
    this.add(tipo, new XYConstraints(136, 6, 101, 21));
    this.add(panelPar, new XYConstraints(3, 6, -1, -1));
  }

  /**
   * M�todo que oculta un par�metro de la lista de par�metros.
   * @param e ActionEvent
   */
  public void delete(ActionEvent e) {
    this.setVisible(false);
  }

  void tipo_actionPerformed(ActionEvent e) {
  }
}

/**
 * Clase que realiza la acci�n de cambiar el tipo de un par�metro del sistema.
 * @author Digna Rodr�guez
 * @version 1.0
 */
class ParamPanel_tipo_actionAdapter
    implements java.awt.event.ActionListener {
  ParamPanel adaptee;

  ParamPanel_tipo_actionAdapter(ParamPanel adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.tipo_actionPerformed(e);
  }
}

/**
 * Clase que realiza la acci�n de eliminar un par�metro de la ventana.
 * @author Digna Rodr�guez
 * @version 1.0
 */
class ParamPanel_Delete_ActionAdapter
    implements ActionListener {
  ParamPanel vent;
  ParamPanel_Delete_ActionAdapter(ParamPanel v) {
    this.vent = v;
  }

  public void actionPerformed(ActionEvent e) {
    vent.delete(e);
  }
}

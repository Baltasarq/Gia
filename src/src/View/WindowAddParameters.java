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

/**
 * Clase que implementa la ventana de añadir los parámetros a un método.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowAddParameters
    extends JDialog
    implements ActionListener {
  JPanel panelTabla = new JPanel();
  Vector vfilas = new Vector();
  boolean botonAceptarPulsado = false;
  boolean botonCancelarPulsado = false;
  JPanel panelBotones = new JPanel();
  JButton botonAceptar = new JButton();
  JButton botonCancelar = new JButton();
  Vector vparametros = new Vector();
  int contador = 0;
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel panelContenedor = new JPanel();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  JPanel panelTitulo = new JPanel();
  JLabel labelNom = new JLabel();
  JLabel labelTipo = new JLabel();
  XYLayout xYLayout3 = new XYLayout();
  Border border1;
  JScrollPane jScrollPane1 = new JScrollPane();
  XYLayout xYLayout4 = new XYLayout();

  public WindowAddParameters(int conta) {
    this.contador = conta;
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
    this.setTitle("Añadir Parámetros al método");
    this.getContentPane().setLayout(xYLayout4);
    panelTabla.setLayout(verticalFlowLayout1);
    botonAceptar.setText("Aceptar");
    botonCancelar.setText("Cancelar");
    panelTabla.setBorder(BorderFactory.createEtchedBorder());
    panelContenedor.setLayout(xYLayout1);
    panelBotones.setLayout(xYLayout2);
    labelNom.setFont(new java.awt.Font("Dialog", 1, 12));
    labelNom.setText("Nombre Parámetro");
    labelTipo.setFont(new java.awt.Font("Dialog", 1, 12));
    labelTipo.setText("Tipo Parámetro");
    panelTitulo.setLayout(xYLayout3);
    panelTitulo.setFont(new java.awt.Font("Dialog", 1, 12));
    panelTitulo.setBorder(border1);
    xYLayout4.setWidth(270);
    xYLayout4.setHeight(307);
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                              HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane1.setBorder(null);
    panelBotones.add(botonAceptar, new XYConstraints(40, 1, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(126, 1, -1, -1));
    this.getContentPane().add(panelContenedor, new XYConstraints(0, 0, 271, 304));
    panelTitulo.add(labelTipo, new XYConstraints(142, 6, -1, 15));
    panelTitulo.add(labelNom, new XYConstraints(8, 4, -1, -1));
    panelContenedor.add(panelBotones, new XYConstraints(7, 270, 247, -1));
    panelContenedor.add(jScrollPane1, new XYConstraints(8, 42, 252, 215));
    jScrollPane1.getViewport().add(panelTabla, null);
    panelContenedor.add(panelTitulo, new XYConstraints(8, 9, 253, 33));
    botonAceptar.addActionListener(this);
    botonCancelar.addActionListener(this);

    this.vfilas.clear();
    for (int i = 0; i < contador; i++) {
      ParamPanel aux = new ParamPanel();
      vfilas.add(aux);
    }

    panelTabla.removeAll();
    for (int i = 0; i < contador; i++)
      panelTabla.add( (ParamPanel) vfilas.get(i), null);

    this.panelTabla.updateUI();
    this.panelTabla.revalidate();
    panelContenedor.revalidate();
    panelContenedor.updateUI();
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
    boolean res = true;
    Component ve[] = panelTabla.getComponents();
    //sólo comprobamos que estén rellenados los visibles
    for (int i = 0; i < ve.length; i++) {
      if ( ( (ParamPanel) ve[i]).isVisible()) {
        String nompar = ( (ParamPanel) ve[i]).fieldnombre.getText();
        if (nompar.compareTo("") == 0)
          res = false;
      }
    }
    return res;
  }

  /**
   * El método checkParameterName comprueba que los nombres de los parámetros son distintos.
   * @return boolean true si hay repetidos.
   **/
  public boolean checkParameterNames() {
    boolean repetidos = false;
    Component v[] = panelTabla.getComponents();
    for (int i = 0; i < v.length; i++) {
      if ( ( (ParamPanel) v[i]).isVisible()) {
        String nompar = ( (ParamPanel) v[i]).fieldnombre.getText();
        Component v2[] = panelTabla.getComponents();
        for (int j = 0; j < v2.length; j++) {
          if ( ( (ParamPanel) v[i]).isVisible()) {
            String nompar2 = ( (ParamPanel) v2[j]).fieldnombre.getText();
            if ( (nompar.compareTo(nompar2) == 0) && (i != j)) {
              repetidos = true;
              return repetidos;
            }
          }
        }
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
        boolean repetidos = false;
        repetidos = checkParameterNames();
        if (!repetidos) {

          Component v[] = panelTabla.getComponents();
          for (int i = 0; i < v.length; i++) {
            if ( ( (ParamPanel) v[i]).isVisible()) {
              String nompar = ( (ParamPanel) v[i]).fieldnombre.getText();
              String tipopar = ( (ParamPanel) v[i]).tipo.getSelectedItem().
                  toString();
              UMLParameter par = new UMLParameter(nompar, tipopar);
              //añadimos el parametro al vector de parámetros.
              this.vparametros.add(par);
            }
            botonAceptarPulsado = true;
            this.dispose();
          }
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "Los nombres de los parámetros no pueden repetirse.",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }

      else
        JOptionPane.showMessageDialog(this,
                                      "Debe de rellenar todos los campos.",
                                      "Aviso", JOptionPane.WARNING_MESSAGE);
    }
  }
}

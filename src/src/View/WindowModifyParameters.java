package View;

import javax.swing.*;
import java.awt.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import Model.UMLParameter;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * Clase que implementa la ventana de modificar los parámetros a un método.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class WindowModifyParameters
    extends JDialog
    implements ActionListener {
  JPanel panelTabla = new JPanel();
  Vector vfilas = new Vector();
  Vector vparam = new Vector();
  boolean botonCrearActivo = false;
  int contador = 0;

  boolean botonAceptarPulsado = false;
  boolean botonCancelarPulsado = false;
  JPanel panelBotones = new JPanel();
  JButton botonAddParam = new JButton();
  JButton botonCancelar = new JButton();
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
  JButton botonModificar = new JButton();
  XYLayout xYLayout4 = new XYLayout();

  public WindowModifyParameters(Vector v) {

    this.vparam.clear();
    for (int i = 0; i < v.size(); i++) {
      UMLParameter par = ( (UMLParameter) v.get(i));
      vparam.add(par);
    }
    this.vparam = v;

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
   *
   * @throws Exception  en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    border1 = BorderFactory.createCompoundBorder(BorderFactory.
                                                 createEtchedBorder(Color.white,
        new Color(168, 168, 168)), BorderFactory.createEmptyBorder(1, 1, 0, 1));
    this.setTitle("Modificar Parámetros del método");
    this.getContentPane().setLayout(xYLayout4);

    panelTabla.setLayout(verticalFlowLayout1);
    botonAddParam.setText("Añadir parámetro");
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
    botonModificar.addActionListener(this);
    botonModificar.setText("Modificar");
    xYLayout4.setWidth(324);
    xYLayout4.setHeight(315);
    panelBotones.add(botonModificar, new XYConstraints(0, 0, -1, -1));
    panelBotones.add(botonAddParam, new XYConstraints(165, 0, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(86, 0, -1, -1));
    panelContenedor.add(jScrollPane1, new XYConstraints(7, 42, 308, 215));
    panelContenedor.add(panelTitulo, new XYConstraints(7, 8, 308, 33));
    jScrollPane1.getViewport().add(panelTabla, null);
    panelTitulo.add(labelNom, new XYConstraints(14, 5, -1, -1));
    panelTitulo.add(labelTipo, new XYConstraints(174, 6, -1, 15));
    panelContenedor.add(panelBotones, new XYConstraints(20, 268, 281, -1));
    this.getContentPane().add(panelContenedor, new XYConstraints(0, 0, -1, 298));
    botonAddParam.addActionListener(this);
    botonCancelar.addActionListener(this);

    this.vfilas.clear();
    for (int i = 0; i < this.vparam.size(); i++) {
      ParamPanel aux = new ParamPanel();
      aux.fieldnombre.setText( ( (UMLParameter)this.vparam.get(i)).getName());
      aux.tipo.setSelectedItem( ( (UMLParameter)this.vparam.get(i)).getType());
      vfilas.add(aux);
    }

    panelTabla.removeAll();
    for (int i = 0; i < this.vparam.size(); i++)
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
   * El método checkParameterName comprueba que los nombres de los
   * parámetros son distintos.
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
   * Método que añade un parámetro nuevo al panel de parámetros.
   * @param par UMLParameter
   */
  public void addParamToTable(UMLParameter par) {
    ParamPanel aux = new ParamPanel();
    aux.fieldnombre.setText(par.getName());
    aux.tipo.setSelectedItem(par.getType());
    vfilas.add(aux);
    panelTabla.add( (ParamPanel) vfilas.get(vfilas.size() - 1), null);

    this.panelTabla.updateUI();
    this.panelTabla.revalidate();
    panelContenedor.revalidate();
    panelContenedor.updateUI();
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonCancelar) {
      botonAceptarPulsado = false;
      botonCancelarPulsado = true;
      this.dispose();
    }
    else {
      if (e.getSource() == this.botonModificar) {
        if (this.completRecord()) {
          boolean repetidos = checkParameterNames();
          if (!repetidos) {
            //borramos los parámetros
            this.vparam.removeAllElements();
            Component v[] = panelTabla.getComponents();
            for (int i = 0; i < v.length; i++) {
              //si el parámetro está visible lo añadimos
              if ( ( (ParamPanel) v[i]).isVisible()) {
                String nompar = ( (ParamPanel) v[i]).fieldnombre.getText();
                String tipopar = ( (ParamPanel) v[i]).tipo.getSelectedItem().
                    toString();
                UMLParameter par = new UMLParameter(nompar, tipopar);
                //añadimos el parametro al vector de parámetros.
                this.vparam.add(par);
              }
              botonAceptarPulsado = true;
              this.dispose();
            }
          }
          else {
            JOptionPane.showMessageDialog(this,
                                          "Los nombres de los parámetros no pueden repetirse.",
                                          "Aviso", JOptionPane.WARNING_MESSAGE);
          }
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "Debes rellenar todos los campos.",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }
      else if (e.getSource() == this.botonAddParam) {
        if (this.completRecord()) {
          //almacenamos los nombres de los parámetros en un vector
          Vector nomParam = new Vector();
          Component ve[] = panelTabla.getComponents();
          for (int i = 0; i < ve.length; i++) {
            if ( ( (ParamPanel) ve[i]).isVisible()) {
              String nompar = ( (ParamPanel) ve[i]).fieldnombre.getText();
              nomParam.add(nompar);
            }
          }

          WindowAddOneParameter v;
          v = new WindowAddOneParameter(nomParam);
          v.setSize(600, 300);
          Dimension dlgSize = v.getPreferredSize();
          Dimension frmSize = getSize();
          Point loc = getLocation();
          v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
          v.setModal(true);
          v.pack();
          v.show();

          System.out.println(v.otherParams);
          if (v.botonAceptarPulsado == true) {
            botonCrearActivo = true;
            addParamToTable(v.par);
            this.vparam.add(v.par);
          }
        }
      }
    }
  }
}

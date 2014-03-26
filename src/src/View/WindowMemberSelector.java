package View;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.io.File;

/**
 * Ventana que permite seleccionar si el elemento que se va añadir a la clase
 * va a ser un método o un atributo.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowMemberSelector extends JDialog implements ActionListener {

  boolean botonSiguientePulsado = false;
  boolean botonCancelarPulsado = false;
  JPanel panelPrincipal = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelSeleccion = new JPanel();
  JButton botonSiguiente = new JButton();
  JButton botonCancelar = new JButton();
  ImageIcon imagen = new ImageIcon();
  XYLayout xYLayout1 = new XYLayout();
  JRadioButton radioMetodo = new JRadioButton();
  JRadioButton radioAtributo = new JRadioButton();
  // Group the radio buttons.
  ButtonGroup group = new ButtonGroup();
  XYLayout xYLayout3 = new XYLayout();
  JPanel panelRadio = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel etiquetaImagen = new JLabel();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  XYLayout xYLayout2 = new XYLayout();

  /**
   * Constructor en el que se crea el JDialog.
   *
   * @param parent Frame
   */
  public WindowMemberSelector(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowMemberSelector() {
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
    this.getContentPane().setLayout(xYLayout2);
    this.setResizable(false);
    this.setTitle("Selector de miembro");
    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource(
        "Images/lateralengranaje.png"));
    panelPrincipal.setLayout(xYLayout1);
    panelSeleccion.setLayout(xYLayout3);
    panelSeleccion.setBorder(BorderFactory.createEtchedBorder());
    botonSiguiente.setText("Siguiente");
    botonSiguiente.addActionListener(this);
    radioMetodo.setText("Método");
    radioAtributo.setSelected(true);
    radioAtributo.setText("Atributo");
    etiquetaImagen.setIcon(imagen);
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    PanelComentariosTrabajo.setFont(new java.awt.Font("Dialog", 1, 11));
    PanelComentariosTrabajo.setAlignmentY( (float) 0.5);
    PanelComentariosTrabajo.setDisabledTextColor(Color.lightGray);
    PanelComentariosTrabajo.setEditable(false);
    PanelComentariosTrabajo.setText(
        "Seleccione el tipo de miembro que desea añadir a la clase.");
    xYLayout2.setWidth(364);
    xYLayout2.setHeight(258);
    group.add(radioAtributo);
    group.add(radioMetodo);
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);
    panelRadio.setLayout(verticalFlowLayout1);
    panelRadio.add(radioAtributo, null);
    panelRadio.add(radioMetodo, null);
    panelPrincipal.add(panelBotones, new XYConstraints(155, 199, 184, -1));
    panelSeleccion.add(PanelComentariosTrabajo,
                       new XYConstraints(3, 17, 181, 46));
    panelSeleccion.add(panelRadio, new XYConstraints(33, 58, -1, 65));
    panelPrincipal.add(etiquetaImagen, new XYConstraints(9, 4, 136, 244));
    panelPrincipal.add(panelSeleccion, new XYConstraints(153, 18, 189, 164));
    panelBotones.add(botonSiguiente, null);
    panelBotones.add(botonCancelar, null);
    this.getContentPane().add(panelPrincipal, new XYConstraints(0, 0, -1, -1));
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      botonCancelarPulsado = true;
      this.dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      botonCancelarPulsado = true;
      this.dispose();
    }
    if (e.getSource() == botonSiguiente) {
      botonSiguientePulsado = true;
      this.dispose();
    }
  }
}








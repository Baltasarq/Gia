package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.io.File;

/**
 * JDialog en el que se pregunta si se desea eliminar los miembros seleccionados
 * del �rbol de clases.
 * @author Digna Rodr�guez.
 * @version 1.0
 */
public class WindowConfirmationDeleteAll
    extends JDialog
    implements ActionListener {

  boolean deleteAll = false;
  boolean tipoBorrarTodo = false;
  JPanel panelPrincipal = new JPanel();
  JPanel panelBotones = new JPanel();
  JButton botonAceptar = new JButton();
  ImageIcon imagen = new ImageIcon();
  JButton botonCancelar = new JButton();
  JTextPane PanelComentariosTrabajo = new JTextPane();
  XYLayout xYLayout2 = new XYLayout();
  JLabel etiquetaImagen = new JLabel();
  XYLayout xYLayout3 = new XYLayout();
  JPanel panelDcha = new JPanel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel panelImagen = new JPanel();

  public WindowConfirmationDeleteAll(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowConfirmationDeleteAll() {
    this(null);
  }

  /**
   * El m�todo jbInit se encarga de la inicializaci�n de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepci�n.
   */
  private void jbInit() throws Exception {

    imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/exclamation.png"));
    etiquetaImagen.setDebugGraphicsOptions(0);
    etiquetaImagen.setIcon(imagen);
    PanelComentariosTrabajo.setBackground(SystemColor.control);
    PanelComentariosTrabajo.setMinimumSize(new Dimension(6, 36));
    PanelComentariosTrabajo.setEditable(false);

    panelBotones.setLayout(xYLayout2);
    panelPrincipal.setBorder(BorderFactory.createEtchedBorder());
    this.getContentPane().setLayout(xYLayout3);
    xYLayout3.setWidth(343);
    xYLayout3.setHeight(166);
    this.setResizable(false);
    panelDcha.setLayout(verticalFlowLayout1);
    panelImagen.setOpaque(false);
    panelImagen.setBounds(new Rectangle(1, 24, 88, 102));
    panelDcha.setBounds(new Rectangle(90, 6, 219, 111));
    panelBotones.add(botonAceptar, new XYConstraints(25, 7, -1, -1));
    panelBotones.add(botonCancelar, new XYConstraints(120, 6, -1, -1));
    panelPrincipal.add(panelImagen, null);
    panelImagen.add(etiquetaImagen, null);
    panelPrincipal.add(panelDcha, null);
    panelDcha.add(PanelComentariosTrabajo, null);
    panelDcha.add(panelBotones, null);
    this.getContentPane().add(panelPrincipal,
                              new XYConstraints(12, 16, 313, 133));
    this.setTitle("Mensaje de confirmaci�n");
    panelPrincipal.setLayout(null);
    botonAceptar.setText("Aceptar");
    botonAceptar.addActionListener(this);
    botonCancelar.setText("Cancelar");
    botonCancelar.addActionListener(this);

    PanelComentariosTrabajo.setText(
        "\nSi pulsa Aceptar eliminar� todas las clases del proyecto.\n      " +
        "                �Desea continuar?");

  }

  /**
   * M�todo que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * M�todo que implementa los eventos de los botones aceptar y cancelar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      this.dispose();
    }
    if (e.getSource() == botonAceptar) {
      if (tipoBorrarTodo)
        deleteAll = true;
      this.dispose();
    }
  }

}

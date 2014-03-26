package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.io.File;

/**
 * Clase que muestra una ventana en la que se encuentra una descripci�n del
 * sistema,autor, fecha y versi�n.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class WindowAbout
    extends JDialog{

  JPanel panelPrincipal = new JPanel();
  JPanel panelSecundario = new JPanel();
  JPanel panelPrincipalBoton = new JPanel();
  JPanel panelSecundarioImagen = new JPanel();
  JPanel panelSecundarioEtiquetas = new JPanel();
  JButton botonAceptar = new JButton();
  JLabel etiquetaImagen = new JLabel();
  JLabel ProductoLabel = new JLabel();
  JLabel VersionLabel = new JLabel();
  JLabel copyrigLabel = new JLabel();
  JLabel descriptLabel1 = new JLabel();
  JLabel descriptLabel2 = new JLabel();
  JLabel descriptLabel3 = new JLabel();
  JLabel autorLabel = new JLabel();
  JLabel emailLabel = new JLabel();
  ImageIcon imagen = new ImageIcon();
  FlowLayout flowLayout1 = new FlowLayout();
  String product = "Herramienta de Generacion integral de aplicaciones";
  String version = "Versi�n:        1.0";
  String autor = "Autor:           Digna Rodr�guez Cudeiro";
  String email = "E-mail:           drcudeiro@correo.ei.uvigo.es";
  String comments1 = "Descripci�n:  Esta heraminenta permite generar c�digo Java a partir de ficheros XMI,";
  String comments2 = "                      modificar diagramas de clase y generar archivos XML para cada clase,  ";
  String comments3 = "                      adem�s de crear una interfaz b�sica para cada proyecto creado.";
  String copyright = "Copyright(c)  Universidad de Vigo 2008";
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  XYLayout xYLayout1 = new XYLayout();
  XYLayout xYLayout2 = new XYLayout();
  XYLayout xYLayout3 = new XYLayout();

  public WindowAbout(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowAbout() {
    this(null);
  }

  /**
   * El m�todo jbInit se encarga de la inicializaci�n de componentes
   * de la interfaz.
   * @throws en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepci�n.
   */
  private void jbInit() throws Exception {
    this.setSize(new Dimension(611, 267));
    this.getContentPane().setLayout(xYLayout3);
    imagen = new ImageIcon(View.MainWindow.class.getResource(
        "Images/lateralrosca.png"));
    etiquetaImagen.setMaximumSize(new Dimension(147, 230));
    etiquetaImagen.setPreferredSize(new Dimension(147, 230));
    etiquetaImagen.setIcon(imagen);
    this.setTitle("Acerca de");
    panelPrincipal.setLayout(xYLayout1);
    panelSecundario.setLayout(xYLayout2);
    panelPrincipalBoton.setLayout(flowLayout1);
    panelSecundarioImagen.setLayout(flowLayout1);
    panelSecundarioImagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
        10));
    ProductoLabel.setText("Herramienta de Generaci�n integral de aplicaciones");
    VersionLabel.setText(version);
    copyrigLabel.setText("Copyright(c)  Universidad de Vigo 2009");
    autorLabel.setText(autor);
    emailLabel.setText(email);
    descriptLabel1.setText(
        "Descripci�n:  Esta herraminenta permite generar c�digo .java a partir " +
        "de ficheros XMI,");
    descriptLabel2.setText(comments2);
    descriptLabel3.setText(comments3);
    panelSecundarioEtiquetas.setLayout(verticalFlowLayout1);
    panelSecundarioEtiquetas.setBorder(BorderFactory.createEtchedBorder());
    botonAceptar.setText("Aceptar");
    botonAceptar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    panelPrincipal.add(panelSecundarioEtiquetas,
                       new XYConstraints(164, 16, 433, 204));
    this.getContentPane().add(panelPrincipal, new XYConstraints(0, 0, -1, -1));
    panelSecundarioEtiquetas.add(ProductoLabel, null);
    panelSecundarioEtiquetas.add(VersionLabel, null);
    panelSecundarioEtiquetas.add(autorLabel, null);
    panelSecundarioEtiquetas.add(emailLabel, null);
    panelSecundarioEtiquetas.add(descriptLabel1, null);
    panelSecundarioEtiquetas.add(descriptLabel2, null);
    panelSecundarioEtiquetas.add(descriptLabel3, null);
    panelSecundarioEtiquetas.add(copyrigLabel, null);
    panelPrincipal.add(panelSecundario, new XYConstraints( -7, 0, 610, 261));
    panelSecundario.add(panelSecundarioImagen,
                        new XYConstraints(10, 0, 159, 222));
    panelSecundarioImagen.add(etiquetaImagen, null);
    panelSecundario.add(panelPrincipalBoton, new XYConstraints(343, 225, 93, -1));
    panelPrincipalBoton.add(botonAceptar, null);
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

}

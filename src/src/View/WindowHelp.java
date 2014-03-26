package View;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.text.html.*;
import com.borland.jbcl.layout.*;

/**
 * Ventana que muestra la ayuda en las dos partes de la aplicación. En la ayuda
 * se explican todas las funciones de los botones y menú de la interfaz.
 * @author Digna Rodriguez
 * @version 1.0
 */
public class WindowHelp
    extends JDialog {

  private final JPanel panelBotones = new JPanel();
  private final JScrollPane scrollPane = new JScrollPane();
  private final JTextPane textPane = new JTextPane();
  private final JButton cerrarButton = new JButton();
  protected HTMLDocument miDocumentoAyuda = new HTMLDocument();
  protected HTMLEditorKit htmlEditorKit = new HTMLEditorKit();

  private final JPanel panelPrincipal = new JPanel();
  XYLayout xYLayout1 = new XYLayout();

  /**
   * Constructor en el que se crea el JDialog.
   *
   * @param parent Frame
   * @param path String ruta del fichero de ayuda.
   */
  public WindowHelp(Frame parent, String path) {
    super(parent);
    setBounds(100, 100, 624, 571);
    try {
      jbInit(path);
    }
    catch (Throwable e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception  en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   * @param path String ruta el archivo de ayuda
   */
  private void jbInit(String path) throws Exception {
    setTitle("Ayuda");

    File file = new File(path);
    try {
      InputStream in = new FileInputStream(file);
      htmlEditorKit.read(in, miDocumentoAyuda, 0);
      in.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    getContentPane().add(panelPrincipal);
    panelPrincipal.setLayout(xYLayout1);
    panelPrincipal.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    textPane.setDocument(miDocumentoAyuda);
    textPane.setEditorKit(htmlEditorKit);
    textPane.setContentType("text/html");
    textPane.setDocument(miDocumentoAyuda);
    textPane.setEditable(false);

    panelPrincipal.add(scrollPane, new XYConstraints(9, 8, 560, 418));
    panelPrincipal.add(panelBotones, new XYConstraints(250, 430, -1, -1));
    panelBotones.add(cerrarButton);
    scrollPane.setViewportView(textPane);
    cerrarButton.setText("Cerrar");
    cerrarButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
  }

}

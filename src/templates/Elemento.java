import javax.swing.*;
import java.awt.*;

/**
 * Panel que contiene los datos(nombre,valor) de los atributos de una clase.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class Elemento
    extends JPanel {
  JPanel panel = new JPanel();
  JTextField texto = new JTextField();
  JLabel etiqueta = new JLabel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  /**
   * Constructor de la clase.
   * @param eti String que almacena el nombre del atributo
   * @param text String que almacena el dato.
   */
  public Elemento(String eti, String text) {
    etiqueta.setText(eti);
    texto.setText(text);
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
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    etiqueta.setBounds(new Rectangle(2, 2, 117, 20));
    texto.setBounds(new Rectangle(127, 1, 130, 20));
    panel.add(texto, null);
    panel.add(etiqueta, null);
    panel.setLayout(null);
    this.setRequestFocusEnabled(true);
    this.setLayout(gridBagLayout1);
    etiqueta.setBorder(BorderFactory.createEtchedBorder());
    etiqueta.setToolTipText("");
    this.add(panel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.BOTH,
                                           new Insets(5, 5, 14, 6), 266, 20));
  }
}

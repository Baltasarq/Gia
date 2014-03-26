import java.awt.*;

/**
 * Clase que carga la ventanaPrincipal del sistema.
 * <p>Copyright: Copyright (c) 2009</p>
 * @author Digna Rodr�guez Cudeiro
 * @version 1.0
 */
public class CargarVentanaPrincipal {

  boolean packFrame = false;
  /**
   * M�todo que crea la ventana principal del sistema.
   */
  public CargarVentanaPrincipal() {
    VentanaPrincipal frame = new VentanaPrincipal();
    //Validar marcos que tienen tama�os preestablecidos
    //Empaquetar marcos que cuentan con informaci�n de tama�o
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);
  }

}

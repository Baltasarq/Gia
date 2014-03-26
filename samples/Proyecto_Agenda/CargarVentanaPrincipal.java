import java.awt.*;

/**
 * Clase que carga la ventanaPrincipal del sistema.
 * <p>Copyright: Copyright (c) 2009</p>
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class CargarVentanaPrincipal {

  boolean packFrame = false;
  /**
   * Método que crea la ventana principal del sistema.
   */
  public CargarVentanaPrincipal() {
    VentanaPrincipal frame = new VentanaPrincipal();
    //Validar marcos que tienen tamaños preestablecidos
    //Empaquetar marcos que cuentan con información de tamaño
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

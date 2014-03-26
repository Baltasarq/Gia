package View;

import java.awt.*;

public class LoadMainWindow {

  boolean packFrame = false;

  /**
   * Método que se llama para construir la aplicación.
   * Validar y empaquetar marcos con tamaños preestablecidos.
   */
  public LoadMainWindow() {
    MainWindow frame = new MainWindow();
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Centrar la ventana
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

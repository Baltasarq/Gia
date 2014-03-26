package View;

import java.awt.*;

public class LoadMainWindow {

  boolean packFrame = false;

  /**
   * M�todo que se llama para construir la aplicaci�n.
   * Validar y empaquetar marcos con tama�os preestablecidos.
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

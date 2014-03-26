package View;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Panel contenedor de un panel de texto con el código de un archivo.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class PanelJavaCode extends JScrollPane{
  public JTextPane pCode;
  boolean javamodificado=false;


  /**
   * Constructor de un panel de código.
   * @param ptext JTextPane panel de texto a partir del cual se crea.
   */
  public PanelJavaCode(JTextPane ptext) {
     super(ptext);
     pCode=ptext;
     javamodificado=false;
     pCode.setText(ptext.getText());

     pCode.addKeyListener(
        new KeyListener() {
      public void actionPerformed(KeyEvent e) {
        panelModified(e);
      }

      /**
       * keyPressed
       *
       * @param e KeyEvent
       */
      public void keyPressed(KeyEvent e) {panelModified(e);
      }

      /**
       * keyReleased
       *
       * @param e KeyEvent
       */
      public void keyReleased(KeyEvent e) {panelModified(e);
      }

      /**
       * keyTyped
       *
       * @param e KeyEvent
       */
      public void keyTyped(KeyEvent e) {panelModified(e);
      }
    }
    );
  }

    public void      panelModified(KeyEvent e){
      this.javamodificado= true;
      }
}





package View;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Clase que sirve para asignar diferentes iconos a los elementos del �rbol
 * contenedor de los archivos de c�digo java y las im�genes.
 * @author Digna Rodr�guez Cudeiro
 * @version 1.0
 */
public class JavaTreeCellRender
    extends DefaultTreeCellRenderer {
  ImageIcon iJava;
  ImageIcon iImagen;
  ImageIcon iOpen;
  ImageIcon iClose;
  /**
   * Constructor de clase en el que se inicializan los iconos para los
   * diferentes tipos de nodos.
   */
  public JavaTreeCellRender() {
    iJava = new ImageIcon(JavaTreeCellRender.class.getResource("Images/java.png"));
    iOpen = new ImageIcon(JavaTreeCellRender.class.getResource("Images/open.png"));
    iClose = new ImageIcon(JavaTreeCellRender.class.getResource("Images/close.png"));
    iImagen = new ImageIcon(JavaTreeCellRender.class.getResource("Images/image.png"));
  }

  /**
   * M�todo que devuelve la informaci�n necesaria para dibujar un �rbol y sus
   * nodos.
   * @param tree JTree �rbol que se modifica.
   * @param value Object valor del nodo del �rbol.
   * @param selected boolean valor true si el nodo est� selecionado, false en
   * caso contrario.
   * @param expanded boolean valor true si el nodo est� expandido, false en
   * caso contrario.
   * @param leaf boolean valor true si el nodo es una hoja, false en caso
   * contrario.
   * @param row int fila del �rbol en la que est� el nodo.
   * @param hasFocus boolean Si el nodo tiene el foco.
   * @return Component contiene toda la informaci�n para poder dibujar el jtree.
   */
  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                boolean selected,
                                                boolean expanded, boolean leaf,
                                                int row, boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,
                                       row, hasFocus);

    DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
    String texto = (String) nodo.getUserObject();
    //comprobamos si es un m�todo o un atributo
    int i = 0;
    while (i < texto.length() && (texto.charAt(i) != '('))
      i++;
    if (nodo.getLevel() == 2) {
      if (nodo.getParent().toString().compareTo("Imagenes") == 0)
        setIcon(iImagen);
      else
        setIcon(iJava);
    }
    else if (nodo.getLevel() == 1) {
      setOpenIcon(iOpen);
      setClosedIcon(iClose);
    }
    return this;
  }
}

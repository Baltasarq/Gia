package View;

import javax.swing.tree.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Clase que sirve para asignar diferentes iconos a los elementos del árbol
 * contenedor de las clases,atributos y métodos del sistema.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class MTreeCellRender
    extends DefaultTreeCellRenderer {
  ImageIcon iAtb;
  ImageIcon iMet;
  ImageIcon iClass;
  ImageIcon iOpen;
  ImageIcon iClose;

  /**
   * Constructor de clase en el que se inicializan los iconos para los
   * diferentes tipos de nodos.
   */
  public MTreeCellRender() {
    iAtb = new ImageIcon(MTreeCellRender.class.getResource("Images/atributo.png"));
    iMet = new ImageIcon(MTreeCellRender.class.getResource("Images/metodo.png"));
    iClass = new ImageIcon(MTreeCellRender.class.getResource("Images/clase.png"));
    iOpen = new ImageIcon(MTreeCellRender.class.getResource("Images/open.png"));
    iClose = new ImageIcon(MTreeCellRender.class.getResource("Images/close.png"));
  }

  /**
   * Método que devuelve la información necesaria para dibujar un árbol y sus
   * nodos.
   * @param tree JTree árbol que se modifica.
   * @param value Object valor del nodo del árbol.
   * @param selected boolean valor true si el nodo está selecionado, false en
   * caso contrario.
   * @param expanded boolean valor true si el nodo está expandido, false en
   * caso contrario.
   * @param leaf boolean valor true si el nodo es una hoja, false en caso
   * contrario.
   * @param row int fila del árbol en la que está el nodo.
   * @param hasFocus boolean Si el nodo tiene el foco.
   * @return Component contiene toda la información para poder dibujar el jtree.
   */
  public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                boolean selected,
                                                boolean expanded, boolean leaf,
                                                int row, boolean hasFocus) {
    super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf,
                                       row, hasFocus);
    DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
    String texto = (String) nodo.getUserObject();
    //comprobamos si es un método o un atributo
    int i = 0;
    while (i < texto.length() && (texto.charAt(i) != '('))
      i++;
    if (nodo.getLevel() == 2) {
      if (i == texto.length())
        setIcon(iAtb);
      else
        setIcon(iMet);
    }
    else if (nodo.getLevel() == 1)
      setIcon(iClass);
    if (nodo.isRoot()) {
      setOpenIcon(iOpen);
      setClosedIcon(iClose);
    }
    return this;
  }
}

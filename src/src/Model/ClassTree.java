package Model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Clase que parte de un array de strings en la que el primer elemento del array
 * representa a un nodo raiz y el resto a las ramas de ese nodo.
 * @author Digna Rodriguez
 * @version 1.0
 */
public class ClassTree {
  DefaultMutableTreeNode r;
  public ClassTree(String datos[]) {
    r = new DefaultMutableTreeNode(datos[0]);
    for (int i = 1; i < datos.length; i++)
      r.add(new DefaultMutableTreeNode(datos[i]));
  }

  public DefaultMutableTreeNode node() {
    return (r);
  }
}

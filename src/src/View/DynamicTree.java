package View;

import Model.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.Enumeration;
import java.io.File;

/**
 * Clase que representa el árbol dinámico
 * contenedor de las clases, atributos y métodos del sistema.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class DynamicTree {
  protected DefaultMutableTreeNode rootNode;
  protected DefaultTreeModel treeModel;
  protected JTree tree;
  private Toolkit toolkit = Toolkit.getDefaultToolkit();

  /**
   * Método en el que se crea el arbol y se inicializan todos sus componentes.
   */
  public DynamicTree() {
    rootNode = new DefaultMutableTreeNode(
        "Contenedor de clases");
    treeModel = new DefaultTreeModel(rootNode);

    tree = new JTree(treeModel);
    tree.setEditable(false);
    tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    tree.setShowsRootHandles(true);
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
  ImageIcon iClose = new ImageIcon(MainWindow.class.getResource(
       "Images/close.png"));
    renderer.setLeafIcon(iClose);
    tree.setCellRenderer(renderer);
    renderer.setIcon(iClose);
  }

  /**
   * El método clear elimina todos los nodos del Jtree,
   * excepto en nodo raiz.
   */
  public void clear() {
    rootNode.removeAllChildren();
    treeModel.reload();
  }

  /**
   * El método modifyObject modifica el nombre del nodo seleccionado
   * del DynamicTree.
   * @param modificado String nombre del nuevo nodo.
   */
  public  void modifyObject(String modificado) {
    TreePath currentSelection = tree.getSelectionPath();
    if (currentSelection != null) {
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
          (currentSelection.getLastPathComponent());
      currentNode.setUserObject( (String) modificado);
    }
  }



  /**
   * El método removeNode elimina un nodo del DynamicTree.
   * @param ruta TreePath ruta del nodo a borrar.
   * @param nodo TreeNode nodo a borrar
   */
  public void removeNode(TreePath ruta, TreeNode nodo) {
    //nombre del nodo a eliminar
    String nom = ruta.getLastPathComponent().toString();

    if (nodo.toString().compareTo(nom) == 0) {
      /*si encontramos un nodo con el mismo nombre comprobamos que la ruta
       es la misma(que tengan el mismo padre)*/
      if (nodo.getParent().toString().compareTo(ruta.getPathComponent(1).
                                                toString()) == 0) {
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (nodo);
        treeModel.removeNodeFromParent(currentNode);
        //Lo borramos también del ClassContainer
        Controller.contenido.deleteMember(nodo.toString());
        Controller.addTypes();
        return;
      }
    }
    else {
      if (nodo.getChildCount() >= 0) {
        Enumeration e = nodo.children();
        while (e.hasMoreElements()) {
          TreeNode sig = (TreeNode) e.nextElement();
          removeNode(ruta, sig);
        }
      }
    }
  }

  /**
   * El método removeCurrentNode elimina el nodo seleccionado del Jtree.
   * Si no hay ningún nodo selecionado no borra ninguno.
   */
  public void removeCurrentNode() {
    String nodoborrado = "";
    String nombrepadre="";

    TreePath currentSelection = tree.getSelectionPath();
    if (currentSelection != null) {
      DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
          (currentSelection.getLastPathComponent());
      MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
      nombrepadre=parent.toString();
      if (parent != null) {
        treeModel.removeNodeFromParent(currentNode);
        nodoborrado = currentNode.getUserObject().toString();
      }
    }
    if (nodoborrado != "") {
      /*puede ser un atributo de tipo de otra clase por lo que hay
      que eliminar todas las relaciones*/
      if(!Controller.contenido.existsClass(nodoborrado))
      {
          if (!Controller.isMethod( nodoborrado)) {//es un atb
             String tipoclase = Controller.contenido.getType(nodoborrado);
             if(tipoclase.compareTo("")!=0)
               Controller.deleteAsociation(nombrepadre,tipoclase);
          }
      }

      //Lo borramos también del ClassContainer
      Controller.deleteAtbRel(nodoborrado);
      Controller.contenido.deleteMember(nodoborrado);
      Controller.addTypes();
      Controller.deleteAsociation(nodoborrado);
      Controller.contenido.printAllClasses();
    }
    else {
      toolkit.beep();
    }
  }

  /**
   * El método addObject añade un nuevo nodo al Jtree.
   * @param child Objeto que se añade.
   * @return DefaultMutableTreeNode
   */
  public DefaultMutableTreeNode addObject(Object child) {
    DefaultMutableTreeNode parentNode = null;
    TreePath parentPath = tree.getSelectionPath();
    if (parentPath == null) {
      parentNode = rootNode;
    }
    else {
      parentNode = (DefaultMutableTreeNode)
          (parentPath.getLastPathComponent());
    }
    return addObject(parentNode, child, true);
  }

  /**
   * El método addObject añade un nuevo nodo al Jtree.
   *
   * @param parent DefaultMutableTreeNode
   * @param child Object
   * @param shouldBeVisible boolean
   * @return DefaultMutableTreeNode
   */
  public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,
                                          Object child,
                                          boolean shouldBeVisible) {
    DefaultMutableTreeNode childNode =
        new DefaultMutableTreeNode(child);

    if (parent == null) {
      parent = rootNode;
    }

    treeModel.insertNodeInto(childNode, parent,
                             parent.getChildCount());

    //Hacemos visible el nuevo nodo
    if (shouldBeVisible) {
      tree.expandPath(new TreePath(parent.getPath()));
      tree.scrollPathToVisible(new TreePath(childNode.getPath()));
    }
    return childNode;
  }

  /**
   * El método setTree obtiene el nodo raiz de un jtree
   * y llama al método correspondiente para su expansión o colapso.
   * @param tree Arbol que queremos expandir o colapsar.
   * @param expanded Boolean que nos indica si deseamos expandir o colapsar
   * el árbol en profundidad.
   */
  public static void setTree(JTree tree, boolean expanded) {
    Object root = tree.getModel().getRoot();
    setTreeState(tree, new TreePath(root), expanded);
  }

  /**
   * El método setTreeState Colapsa o expande un arbol por completo.
   * @param tree Arbol que queremos expandir o colapsar.
   * @param path ruta del árbol.
   * @param expanded boolean que nos indica si expandir o colapsar el arbol.
   */
  public static void setTreeState(JTree tree, TreePath path, boolean expanded) {
    Object lastNode = path.getLastPathComponent();
    for (int i = 0; i < tree.getModel().getChildCount(lastNode); i++) {
      Object child = tree.getModel().getChild(lastNode, i);
      TreePath pathToChild = path.pathByAddingChild(child);
      setTreeState(tree, pathToChild, expanded);
    }
    if (expanded)
      tree.expandPath(path);
    else
      tree.collapsePath(path);
  }

}

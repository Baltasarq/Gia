package Model;

import java.util.*;

/**
 * Clase que representa una relación de herencia de un diagrama UML.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class UMLGeneralization {
  String id;
  public Vector vBeginEnd = new Vector(); //2 posiciones: nombres clases

  public UMLGeneralization() {
  }

  /**
   * Constructor de clase.
   * @param ini String  nombre de la clase padre de la relación.
   * @param fin String nombre de la clase hijo de la relación.
   */
  public UMLGeneralization(String ini, String fin) {
    Vector vnom = new Vector();
    vnom.add(ini);
    vnom.add(fin);
    copyAllMembers(vnom);
  }

  /**
   * Método que copia el contenido de un vector en el vector de nombres de
   * clase de la relación.
   * @param v1 Vector de Strings con los nombres inicio y fin de la clase.
   */
  public void copyAllMembers(Vector v1) {
    vBeginEnd.clear();

    for (int i = 0; i < v1.size(); i++) {
      String toCopy = (String) v1.get(i);
      this.vBeginEnd.add(i, toCopy);
    }
  }

  /**
   * Método que imprime por pantala el contenido de una relación de herencia.
   */
  public void print() {
    System.out.println("\n\nGeneralization: " + id + " ");
    System.out.print( (String) vBeginEnd.get(0) + " - ");
    System.out.print( (String) vBeginEnd.get(1));
    System.out.println();
  }
}

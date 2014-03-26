package Model;

import java.util.*;

/**
 * Clase que representa a una asociaci�n UML, est� compuesta por dos vectores
 * que contienen los nombres de las clases que componen la asociaci�n y su
 * multiplicidad.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class UMLAssociation {
  String id;
  public Vector vBeginEnd = new Vector(); //2 posiciones: nombres clases
  public Vector multiplicity = new Vector(); //2 posiciones: multiplicidad

  public UMLAssociation() {
  }

  /**
   * Constructor de clase.
   * @param ini String almacena el nombre de la clase de inicio de la relaci�n.
   * @param fin String almacena el nombre de la clase fin de la relaci�n.
   * @param mult1 String almacena la multiplicidad de la clase de inicio de
   * la relaci�n.
   * @param mult2 String almacena la multiplicidad de la clase de fin de la
   * relaci�n.
   */
  public UMLAssociation(String ini, String fin, String mult1, String mult2) {
    Vector vnom = new Vector();
    vnom.add(ini);
    vnom.add(fin);
    Vector vmult = new Vector();
    vmult.add(mult1);
    vmult.add(mult2);
    copyAllMembers(vnom, vmult);
  }

  /**
   * M�todo que copia as asociaciones que hay en un vector en otro.
   * @param v1 Vector del que se copian las asociaciones.
   * @param v2 Vector en el que se copian las asociaciones.
   */
  public void copyAllMembers(Vector v1, Vector v2) {
    vBeginEnd.clear();
    multiplicity.clear();
    for (int i = 0; i < v1.size(); i++) {
      String toCopy = (String) v1.get(i);
      this.vBeginEnd.add(i, toCopy);
    }
    for (int j = 0; j < v2.size(); j++) {
      String toCopy2 = (String) v2.get(j);
      this.multiplicity.add(j, toCopy2);
    }
  }

  /**
   * M�todo que imprime por pantalla una asociaci�n.
   */
  public void printAssociation() {
    System.out.println("\n\nAsociacion: " + id + " ");
    for (int i = 0; i < vBeginEnd.size(); i++)
      System.out.print( (String) vBeginEnd.get(i) + " ");
    System.out.println();
    for (int i = 0; i < multiplicity.size(); i++)
      System.out.print( (String) multiplicity.get(i) + " ");
    System.out.println();
  }
}

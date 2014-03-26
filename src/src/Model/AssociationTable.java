package Model;

import java.util.*;

/**
 * La clase AssociationTable contiene todas las asociaciones entre
 * clases del proyecto.
 * @author Digna Rodr�guez Cudeiro
 * @version 1.0
 * @deprecated Ning�n miembro de la clase es <i>deprecated</i>.
 */
public class AssociationTable {

  public Vector vAssoc = new Vector();

  public AssociationTable() {
  }

  /**
   * El m�todo deleteAllAsociations  elimina todas las asociaciones
   * de la AsociationTable.
   */
  public void deleteAllAsoc() {
    this.vAssoc.clear();
  }

  /**
   * Crea una relaci�n de asociaci�n 1 a 1 entre dos clases del sistema.
   * @param ini String identificador de la clase de inicio.
   * @param fin String idenficador de la clase de fin.
   */
  public void addAsoc1a1(String ini, String fin) {
    UMLAssociation nueva = new UMLAssociation(ini, fin, "1", "1");
    Controller.asoc.vAssoc.add(nueva);
    Controller.asoc.printAssociations();
  }

  /**
   * Crea una relaci�n de asociaci�n 1 a n entre dos clases del sistema.
   * @param ini String identificador de la clase de inicio.
   * @param fin String idenficador de la clase de fin.
   */
  public void addAsoc1an(String ini, String fin) {
    UMLAssociation nueva = new UMLAssociation(ini, fin, "1", "-1");
    Controller.asoc.vAssoc.add(nueva);
    Controller.asoc.printAssociations();
  }

  /**
   * Muestra la tabla de asociaciones por pantalla.
   */
  public void printAssociations() {
    System.out.println("===Tabla de Asociaciones===");
    for (int i = 0; i < vAssoc.size(); i++)
      ( (UMLAssociation) vAssoc.get(i)).printAssociation();
  }
}

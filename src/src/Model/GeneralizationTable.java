package Model;

import java.util.*;

/**
 * La clase GeneralizationTable contiene todas las relaciones de
 * herencia que existen entre las clases del proyecto.
 * @author Digna Rodr�guez Cudeiro
 * @version 1.0
 * @deprecated Ning�n miembro de la clase es <i>deprecated</i>.
 */
public class GeneralizationTable {

  public Vector vGen = new Vector();

  public GeneralizationTable() {
  }

  /**
   * El m�todo deleteAllGen  elimina todas las relaciones de
   * herencia de la GeneralizationTable.
   */
  public void deleteAllGen() {
    this.vGen.clear();
  }

  /**
   * A�ade una nueva relaci�n de herencia entre dos clases del sistema.
   * @param padre String clase que representa a la clase padre de la herencia.
   * @param hijo String clase que representa a la clase madre de la herencia.
   */
  public void addGeneralization(String padre, String hijo) {
    UMLGeneralization nueva = new UMLGeneralization(padre, hijo);
    Controller.gener.vGen.add(nueva);
    Controller.gener.printGenralizations();
  }

  /**
   * M�todo que imprime todas las relaciones de herencia existentes en el
   * sistema.
   */
  public void printGenralizations() {
    System.out.println("===Tabla de Herencias===");
    for (int i = 0; i < this.vGen.size(); i++)
      ( (UMLGeneralization)this.vGen.get(i)).print();
  }
}

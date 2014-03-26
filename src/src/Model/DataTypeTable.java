package Model;

import java.util.*;

/**
 * Clase en la que se almacenan en un vector todos los diferentes tipos de
 * datos de la aplicaci�n.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class DataTypeTable {

  protected Vector vTypes = new Vector();

  public DataTypeTable() {
  }

  /**
   * M�todo que busca un tipo de dato en la tabla de tipos de datos.
   * @param s String identificador que se busca en la DataTypeTable
   * @return String si encuentra el tipo de dato correspondiente al id lo
   * retorna en caso contrario devuelve la cadena vac�a.
   */
  public String searchType(String s) {
    String toret = "";
    for (int i = 0; i < vTypes.size(); i++)
      if ( ( (DataType) vTypes.get(i)).getId().compareTo(s) == 0) {
        toret = (String) ( (DataType) vTypes.get(i)).getType();
      }
    return toret;
  }

  /**
   * M�todo que imprime por pantalla todos los tipos de la apliaci�n.
   */
  public void printAllClasses() {
    System.out.println("===TABLA DE TIPOS DE DATOS===");
    for (int i = 0; i < vTypes.size(); i++)
      ( (DataType) vTypes.get(i)).printType();
  }
}

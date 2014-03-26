package Model;

/**
 * Clase que almacena pares
 * de strings  que se corresponden con el identificador que en el xmi se le
 * da al tipo de dato y el tipo de dato asociado a ese idenfificador.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class DataType {
  protected String type;
  protected String id;

  public DataType() {}

  /**
   * Devuelve el identificador de un DataType
   * @return String identificador;
   */
  public String getId() {
    return id;
  }

  /**
   * Devuelve el tipo de dato de un DataType
   * @return String
   */
  public String getType() {
    return type;
  }

  /**
   * Imprime por pantalla el objeto DataType
   */
  public void printType() {
    System.out.println(type + ":: " + id);

  }
}

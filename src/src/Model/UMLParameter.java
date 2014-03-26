package Model;

import java.util.*;
import java.io.*;

/**
 * Clase que representa al par�metro de un m�todo de una clase UML.
 * @author Digna Rodr�guez
 * @version 1.0
 */
public class UMLParameter {
  protected String name;
  protected String type;

  public UMLParameter() {
  }

  /**
   * Constructor de clase
   * @param n String nombre del par�metro.
   * @param t String tipo del par�metro.
   */
  public UMLParameter(String n, String t) {
    this.name = n;
    this.type = t;
  }

  /**
   * M�todo que permite modificar el nombre de un par�metro.
   * @param n String nuevo nombre del par�metro.
   */
  public void setName(String n) {
    this.name = n;
  }

  /**
   * M�todo que permite modificar el tipo de un par�metro.
   * @param t String nuevo tipo de un par�metro.
   */
  public void setType(String t) {
    this.type = t;
  }

  /**
   * M�todo que obtiene el nombre de un par�metro.
   * @return String nombre del par�metro.
   */
  public String getName() {
    return this.name;
  }

  /**
   * M�todo que devuelve el tipo de un par�metro.
   * @return String tipo del par�metro.
   */
  public String getType() {
    return this.type;
  }

  /**
   * M�todo que comprueba si el par�metro es un valor de retorno de un m�todo.
   * @return boolean true si es valor de retorno, false en caso contrario.
   */
  public boolean isReturnType() {
    if (name.compareTo("return") == 0)
      return true;
    else return false;
  }

  /**
   * M�todo que escribe el c�digo relativo a un par�metro en un archivo.
   * @param bw BufferedWriter stream en el que se escribe el c�digo.
   */
  public void writeParameter(BufferedWriter bw) {
    try {
      bw.write(type + " " + name);
    }
    catch (IOException ex) {
    }
  }

  /**
   * M�todo que escribe un par�metro en xml en un fichero.
   * @param bw BufferedWriter stream en el que se escribe.
   */
  public void writeParameterXML(BufferedWriter bw) {
    try {
      bw.write("       <parameter>\n");
      bw.write("             <name_par>" + this.name + "</name_par>\n");
      bw.write("             <type_par>" + this.type + "</type_par>\n");
      bw.write("       </parameter>\n");
    }
    catch (IOException ex) {
    }
  }

  /**
   * M�todo que imprime por pantalla el valor de un par�metro
   */
  public void printParameter() {
    System.out.print(type + " " + name);
  }

}

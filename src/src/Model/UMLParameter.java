package Model;

import java.util.*;
import java.io.*;

/**
 * Clase que representa al parámetro de un método de una clase UML.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class UMLParameter {
  protected String name;
  protected String type;

  public UMLParameter() {
  }

  /**
   * Constructor de clase
   * @param n String nombre del parámetro.
   * @param t String tipo del parámetro.
   */
  public UMLParameter(String n, String t) {
    this.name = n;
    this.type = t;
  }

  /**
   * Método que permite modificar el nombre de un parámetro.
   * @param n String nuevo nombre del parámetro.
   */
  public void setName(String n) {
    this.name = n;
  }

  /**
   * Método que permite modificar el tipo de un parámetro.
   * @param t String nuevo tipo de un parámetro.
   */
  public void setType(String t) {
    this.type = t;
  }

  /**
   * Método que obtiene el nombre de un parámetro.
   * @return String nombre del parámetro.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Método que devuelve el tipo de un parámetro.
   * @return String tipo del parámetro.
   */
  public String getType() {
    return this.type;
  }

  /**
   * Método que comprueba si el parámetro es un valor de retorno de un método.
   * @return boolean true si es valor de retorno, false en caso contrario.
   */
  public boolean isReturnType() {
    if (name.compareTo("return") == 0)
      return true;
    else return false;
  }

  /**
   * Método que escribe el código relativo a un parámetro en un archivo.
   * @param bw BufferedWriter stream en el que se escribe el código.
   */
  public void writeParameter(BufferedWriter bw) {
    try {
      bw.write(type + " " + name);
    }
    catch (IOException ex) {
    }
  }

  /**
   * Método que escribe un parámetro en xml en un fichero.
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
   * Método que imprime por pantalla el valor de un parámetro
   */
  public void printParameter() {
    System.out.print(type + " " + name);
  }

}

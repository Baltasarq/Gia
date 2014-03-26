import java.util.Vector;
import javax.xml.parsers.*;

/**
 * La clase Gestion realiza la acciones de alta, baja y modificación
 * sobre los datos contenidos en los xml de las clases del sistema que
 * heredan de la clase Raiz.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 * @deprecated Ningún miembro de la clase es <i>deprecated</i>.
 */
public class Gestion {
  /**
   * Añade los datos de un nuevo objeto de una clase al xml de esa clase.
   * @param v Vector que contiene objetos de tipo elemento que almacenan los
   * datos de la nueva clase a añadir.
   * @param x es de tipo Raiz y representa a la clase en la que se añade el
   * nuevo objeto.
   * @param enlace es un String que almacena el identificador de la clase x.
   * @return Vector contiene todos los objetos de la clase x que tengan como
   * ident el enlace, incluido el que acabamos de añadir.
   */
  public static Vector alta(Vector v, Raiz x, String enlace) {
    //Agregamos en la primera posición del vector el atributo enlace
    Vector nuevo = new Vector();
    nuevo.add(enlace);
    for (int i = 0; i < v.size(); i++)
      nuevo.add(v.get(i));
    Vector salida = new Vector();
    if (enlace == "")
      salida = x.leerXML();
    else
      salida = x.buscarenXML(enlace);
    salida.add(nuevo);

    //creamos el xml con todos los datos que tenía antes añadiendole el nuevo
    Vector leidocompleto = new Vector();
    leidocompleto = x.leerXML();
    leidocompleto.add(nuevo);
    try {
      x.crearXML(leidocompleto);
    }
    catch (ParserConfigurationException ex) {
    }
    catch (FactoryConfigurationError ex) {
    }
    return salida;
  }

  /**
   * Añade los datos de un nuevo objeto de la clase principal del sistema.
   * @param v Vector que contiene objetos de tipo elemento que almacenan los
   * datos de la clase principal.
   * @param x es de tipo Raiz y representa a la clase en la que se añade el
   * nuevo objeto.
   * @return Vector contiene todos los datos de los objetos de la clase x.
   */
  public static Vector altaPrincipal(Vector v, Raiz x) {
    Vector nuevo = new Vector();
    nuevo.add(""); //Agregamos el atributo enlace ""
    for (int i = 0; i < v.size(); i++)
      nuevo.add(v.get(i));
    Vector salida = new Vector();
    salida.add(nuevo);
    try {
      x.crearXML(salida);
    }
    catch (ParserConfigurationException ex) {
    }
    catch (FactoryConfigurationError ex) {
    }
    return salida;
  }

  /**
   * Elimina del xml los datos de la clase que se le pasa como parámetro cuyo
   * ident sea igual al que se le pasa como parámetro.
   * @param id String identificador del objeto.
   * @param x es de tipo Raiz y representa a la clase de la que se elimina el
   * elemento.
   * @return Vector contiene todos los datos de los objetos de la clase x.
   */
  public static Vector baja(String id, Raiz x) {
    Vector lista = new Vector();
    lista = x.leerXML();
    Vector salida = new Vector();
    for (int i = 0; i < lista.size(); i++) {
      Vector vclase = (Vector) (lista.get(i));
      if (! ( ( (String) vclase.get(1)).compareTo(id) == 0))
        salida.add(vclase);
      try {
        x.crearXML(salida);
      }
      catch (ParserConfigurationException ex) {}
      catch (FactoryConfigurationError ex) {}
    }
    return salida;
  }

  /**
   * Elimina todos los datos de diferentes clases en cascada. las clases estan
   * relacionadas mediante relaciones 1 a n o 1 a l.
   * @param id String identificador del objeto.
   * @param x es de tipo Raiz y representa a la clase de la que se parte en la
   * relación para eliminar los elementos.
   */
  public static void borradoCascada(String id, Raiz x) {
    Vector vAtb = new Vector();
    vAtb = x.getvAtb();
    for (int i = 0; i < vAtb.size(); i++) {
      String elementoactual = vAtb.get(i).toString();
      if (Gestion.esObjeto(elementoactual)) {
        String nombre = elementoactual.substring(3, elementoactual.length());
        x.getRelaciones1a1();
        Raiz r = x.getElementoRelacionado(nombre);
        borrarRelaciones(id, r);
      }
      else {
        if (Gestion.esVectorObjetos(elementoactual)) {
          String nombre = elementoactual.substring(3, elementoactual.length());
          x.getRelaciones1an();
          Raiz r = x.getElementoRelacionado(nombre);
          borrarRelaciones(id, r);
        }
      }
    }
  }

  /**
   * Método que borra todos los elementos de la clase Raiz que tengan como enlace
   * la cadena que se le pasa como parámetro.
   * @param enlace String que almacena el atributo enlace dentro del xml.
   * @param x es de tipo Raiz y representa a la clase sobre la que se eliminan
   * los datos.
   */
  public static void borrarRelaciones(String enlace, Raiz x) {
    Vector lista = new Vector();
    lista = x.leerXML();
    Vector salida = new Vector();
    for (int i = 0; i < lista.size(); i++) {
      Vector vclase = (Vector) (lista.get(i));
      if (! ( ( (String) vclase.get(0)).compareTo(enlace) == 0))
        salida.add(vclase);
      try {
        x.crearXML(salida);
      }
      catch (ParserConfigurationException ex) {}
      catch (FactoryConfigurationError ex) {}
    }
  }

  /**
   * Método que modifica los datos de un objeto de una clase contenidos en el
   * xml.
   * @param vmodificado Vector que contiene los nuevos datos de la clase ya
   * modificada.
   * @param x Raiz clase sobre la que se modificia el objeto.
   * @param enlace String almacena un identificador de relación de la clase
   * x con otra.
   * @return Vector contiene todos los objetos de la clase x que tengan como
   * ident el enlace, incluido el que acabamos de modificar.
   */
  public static Vector modificacion(Vector vmodificado, Raiz x, String enlace) {
    Vector nuevo = new Vector();
    Vector salida = new Vector();
    Vector completo = new Vector();
    Vector lista = new Vector();
    //al vector modificado le añadimos el enlace en primera posición
    nuevo.add(enlace);
    for (int i = 0; i < vmodificado.size(); i++)
      nuevo.add(vmodificado.get(i));
    lista = x.leerXML();
    for (int i = 0; i < lista.size(); i++) {
      //Sustituimos el contenido del vector.
      salida = (Vector) (lista.get(i));
      if ( ( (String) salida.get(1)).compareTo( (String) nuevo.get(1)) == 0)
        completo.add(i, nuevo);
      else
        completo.add(i, salida);
    }

    try {
      x.crearXML(completo);
    }
    catch (ParserConfigurationException ex) {}
    catch (FactoryConfigurationError ex) {}
    return completo;
  }

  /**
   * Método que comprueba si el string que se le pasa como parámetro es
   * un atributo de tipo objeto.
   * @param nombreatb String nombre de un atributo.
   * @return boolean true si es un objeto, false en caso contrario.
   */
  public static boolean esObjeto(String nombreatb) {
    boolean ret = false;
    if (nombreatb.substring(0, 3).compareTo("$r_") == 0)
      ret = true;
    return ret;
  }

  /**
   * Método que comprueba si el string que se le pasa como parámetro es
   * el identificador de un vector de objetos.
   * @param nombreatb String nombre de un atributo.
   * @return boolean true si es un objeto, false en caso contrario.
   */
  public static boolean esVectorObjetos(String nombreatb) {
    boolean ret = false;
    if (nombreatb.substring(0, 3).compareTo("$v_") == 0)
      ret = true;
    return ret;
  }
  
   /**
   * Método que devuelve le plural de una clase
   * @param nombre String  nombre de la clase
   * @return String "s" o "es" para mostrar el plural de una clase.
   */ 
  public static String getPlural(String nombre) {
    int tam = nombre.length() - 1;
    if ( (nombre.charAt(tam) == 'a') || (nombre.charAt(tam) == 'e') ||
        (nombre.charAt(tam) == 'i')
        || (nombre.charAt(tam) == 'o') || (nombre.charAt(tam) == 'u'))
      return "s";
   else
      return "es";
  }

}

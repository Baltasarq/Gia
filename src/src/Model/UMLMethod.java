package Model;

import java.util.*;
import java.io.*;

/**
 * Clase que representa a un método de una UMLClass.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class UMLMethod {
  protected String name;
  protected int visibility;
  protected boolean isAbstract;
  protected String typeReturn;
  protected boolean isstatic;

  public Vector parameters = new Vector();

  public UMLMethod() {
  }

  /**
   * Crea un UMLMethod.
   * @param nom El nombre del método.
   * @param ret El tipo de dato que retornará el método, void en caso de que
   * no retorne nada.
   * @param vis Visibilidad del método, puede ser pública, privada y abstracta.
   * @param sta Booleano que indica si el método es estático o no.
   * @param isAb Booleano que indica si el método es abstracto o no.
   * @param param Vector que almacena los parámetros del método.
   */
  public UMLMethod(String nom, String ret, int vis, boolean sta,
                   boolean isAb, Vector param) {
    name = nom;
    visibility = vis;
    isAbstract = isAb;
    typeReturn = ret;
    isstatic = sta;
    for(int i =0;i<param.size();i++)
    {
      UMLParameter par = (UMLParameter)param.get(i);
      this.parameters.add(par);
    }
  }

  /**
   * Crea un UMLMethod, sin parámetros.
   * @param nom El nombre del método.
   * @param ret El tipo de dato que retornará el método, void en caso de que
   * no retorne nada.
   * @param vis Visibilidad del método, puede ser pública, privada y abstracta.
   * @param sta Booleano que indica si el método es estático o no.
   * @param isAb Booleano que indica si el método es abstracto o no.
   */
  public UMLMethod(String nom, String ret, int vis, boolean sta,
                   boolean isAb) {
    name = nom;
    visibility = vis;
    isAbstract = isAb;
    typeReturn = ret;
    isstatic = sta;
  }

  /**
   * El método getName devuelve el nombre del método.
   * @return String nombre del método.
   */
  public String getName() {
    return name;
  }

  /**
   * El método getType devuelve el tipo de retorno del método.
   * @return String tipo de retorno del método.
   */
  public String getType() {
    return typeReturn;
  }

  /**
   * El método getVisibility devuelve la visibilidad del método.
   * @return String Visibilidad del método(public, private, protected).
   */
  public String getVisibility() {
    String vis = "";
    if (visibility == 1)
      vis = "public";
    else if (visibility == 2)
      vis = "private";
    else if (visibility == 3)
      vis = "protected";

    return vis;
  }

  /**
   * El método getStatic devuelve si el método es estático o no.
   * @return boolean Valor true si es estático, false en caso contrario.
   */
  public boolean getStatic() {
    return isstatic;
  }

  /**
   * El método getAbstract devuelve si el método es abstracto o no.
   * @return boolean Valor true si es abstracto, false en caso contrario.
   */
  public boolean getAbstract() {
    return isAbstract;
  }

  /**
   * El método getParameters devuelve un string con los parametros del
   * método separados por comas.
   * @return String Parametros del método.
   */
  public String getParameters() {
    String toret = "";
    for (int i = 0; i < this.parameters.size(); i++) {
      UMLParameter par = (UMLParameter)this.parameters.get(i);
      if (i != this.parameters.size() - 1)
        toret += par.getType() + " " + par.getName() + ",";
      else
        toret += par.getType() + " " + par.getName();
    }
    return toret;
  }

  /**
   * El método setType Permite cambiar el tipo de retorno del método.
   * @param n String nuevo tipo.
   */
  public void setType(String n) {
    typeReturn = n;
  }

  /**
   * El método setTypeReturn Cambia el tipo del tipo de retorno, por
   * un tipo que está almacenado en el vector de parámetros la primera vez.
   */
  public void setTypeReturn() {
    int i = 0;
    while (i < parameters.size()) {
      String npar = ( (UMLParameter) parameters.get(i)).name;
      String ntype = ( (UMLParameter) parameters.get(i)).type;
      if (npar.compareTo("return") == 0) {
        typeReturn = ntype;
        parameters.removeElementAt(i);
        i = parameters.size();
      }
      else
        i++;
    }
  }

  /**
   * El método setName Cambia el nombre del método.
   * @param n String que contiene el nuevo nombre del método.
   */
  public void setName(String n) {
    name = n;
  }

  /**
   * El método setStatic Cambia el modificador static del método.
   * @param s boolean que tiene el valor true si el método es static, false
   * en caso contrario.
   */
  public void setStatic(boolean s) {
    isstatic = s;
  }

/**
 * El método setAbstract Cambia el modificador abstract del método.
 * @param s Boolean que tiene el valor true si el método es abstracto, false
 * en caso contrario.
 */
public void setAbstract(boolean s) {
  isAbstract = s;
}


  /**
   * El método setVisibility Cambia la visibilidad del método.
   * @param i int que representa la visibilidad: 1=public, 2=private, 3=protected.
   */
  public void setVisibility(int i) {
    this.visibility = i;
  }

  /**
   * El método setParameters Cambia los parametros de un método.
   * @param par es un String que almacena los parametros separados por comas
   * ej:tipo1 nombre1, tipo2 nombre2...
   */
  public void setParameters(String par) {
    this.parameters.clear();

    StringTokenizer parametros = new StringTokenizer(par, ",");
    // mientras no se acaben los parámetros
    while (parametros.hasMoreTokens()) {
      UMLParameter param = new UMLParameter();
      StringTokenizer partesparametro = new StringTokenizer(parametros.
          nextToken(), " ");
      param.setType(partesparametro.nextToken());
      param.setName(partesparametro.nextToken());
      this.parameters.add(param);
    }
  }

  /**
   * El método copyParameters copia los UMLParameter almacenados en
   * un vector en el vector de parámetros de la clase UMLMethod.
   * @param vPar Vector que contiene los datos a copiar.
   */
  public void copyParameters(Vector vPar) {
    parameters.clear();
    for (int i = 0; i < vPar.size(); i++) {
      this.parameters.add(i, (UMLParameter) vPar.get(i));
    }
    this.setTypeReturn();
  }

  /**
   * El método writeMethod añade al fichero el método con la
   * estructura del lenguaje java.
   * @param bw BufferedWriter donde se va escribiendo la información.
   */
  public void writeMethod(BufferedWriter bw) {
  try {
      String toret = "";
    if(typeReturn.compareTo("void") != 0){
     if (typeReturn.compareTo("char") == 0) {
       toret = typeReturn + " x = '';";
       toret += "\nreturn x;";
     }
     else {
       if (typeReturn.compareTo("boolean") == 0) {
         toret = typeReturn + " x = true;";
         toret += "\nreturn x;";
       }
       else {
         if (typeReturn.compareTo("String") == 0) {
           toret = typeReturn + " x = \"\";";
           toret += "\nreturn x;";
         }
         else {
           if ( (typeReturn.compareTo("int") == 0) ||
               (typeReturn.compareTo("double") == 0) ||
               (typeReturn.compareTo("float") == 0) ||
               (typeReturn.compareTo("short") == 0) ||
               (typeReturn.compareTo("byte") == 0)) {
             toret = typeReturn + " x = 0;";
             toret += "\nreturn x;";
           }
           else {
           //  toret = typeReturn + " x = new " + typeReturn + "();";
             toret = "\nreturn null;";
           }
         }
       }
     }
   }

      if (isstatic == false)
        bw.write(getVisibility() + " " + typeReturn + " " + name + "(");
      else
        bw.write(getVisibility() + " static " + typeReturn + " " + name + "(");
      for (int i = 0; i < parameters.size(); i++) {
        ( (UMLParameter) parameters.get(i)).writeParameter(bw);
        if (i + 1 != parameters.size()) bw.write(", ");
      }
      bw.write(") {\n");
      bw.write(toret + "\n");
      bw.write("}\n");
    }
    catch (IOException ex) {
    }
  }

  /**
   * Crea la parte del xml referida a un método.
   * @param bw BufferedWriter donde escribiremos la información.
   */
  public void writeMethodXML(BufferedWriter bw) {
    try {
      bw.write("   <method>\n");
      bw.write("       <name_met>" + this.name + "</name_met>\n");
      bw.write("       <visib_met>" + this.visibility + "</visib_met>\n");
      if (this.isAbstract)
        bw.write("       <abstract_met>si</abstract_met>\n");
      else
        bw.write("       <abstract_met>no</abstract_met>\n");

      if (this.isstatic)
        bw.write("       <static_met>si</static_met>\n");
      else
        bw.write("       <static_met>no</static_met>\n");
       bw.write("       <return>" + this.typeReturn + "</return>\n");
       for (int i = 0; i < parameters.size(); i++) {
        ( (UMLParameter) parameters.get(i)).writeParameterXML(bw);
       }
      bw.write("   </method>\n");
    }
    catch (IOException ex) {
    }
  }

  /**
   * El método printOperation escribe por pantalla el método.
   */
  public void printOperation() {
    String vis = "";
    vis = getVisibility();
    System.out.print("\t " + vis + " " + typeReturn + " " + name + "(");

    for (int i = 0; i < parameters.size(); i++) {
      ( (UMLParameter) parameters.get(i)).printParameter();
      if (i + 1 != parameters.size()) System.out.print(", ");
    }
    System.out.println(")");
    if (isAbstract)
      System.out.println("\t Abstract: Sí");
    else
      System.out.println("\t Abstract: No");
  }
}

package Model;

import java.util.*;
import java.io.*;

/**
 * Clase que representa a un m�todo de una UMLClass.
 * @version 1.0
 * @author Digna Rodr�guez Cudeiro
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
   * @param nom El nombre del m�todo.
   * @param ret El tipo de dato que retornar� el m�todo, void en caso de que
   * no retorne nada.
   * @param vis Visibilidad del m�todo, puede ser p�blica, privada y abstracta.
   * @param sta Booleano que indica si el m�todo es est�tico o no.
   * @param isAb Booleano que indica si el m�todo es abstracto o no.
   * @param param Vector que almacena los par�metros del m�todo.
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
   * Crea un UMLMethod, sin par�metros.
   * @param nom El nombre del m�todo.
   * @param ret El tipo de dato que retornar� el m�todo, void en caso de que
   * no retorne nada.
   * @param vis Visibilidad del m�todo, puede ser p�blica, privada y abstracta.
   * @param sta Booleano que indica si el m�todo es est�tico o no.
   * @param isAb Booleano que indica si el m�todo es abstracto o no.
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
   * El m�todo getName devuelve el nombre del m�todo.
   * @return String nombre del m�todo.
   */
  public String getName() {
    return name;
  }

  /**
   * El m�todo getType devuelve el tipo de retorno del m�todo.
   * @return String tipo de retorno del m�todo.
   */
  public String getType() {
    return typeReturn;
  }

  /**
   * El m�todo getVisibility devuelve la visibilidad del m�todo.
   * @return String Visibilidad del m�todo(public, private, protected).
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
   * El m�todo getStatic devuelve si el m�todo es est�tico o no.
   * @return boolean Valor true si es est�tico, false en caso contrario.
   */
  public boolean getStatic() {
    return isstatic;
  }

  /**
   * El m�todo getAbstract devuelve si el m�todo es abstracto o no.
   * @return boolean Valor true si es abstracto, false en caso contrario.
   */
  public boolean getAbstract() {
    return isAbstract;
  }

  /**
   * El m�todo getParameters devuelve un string con los parametros del
   * m�todo separados por comas.
   * @return String Parametros del m�todo.
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
   * El m�todo setType Permite cambiar el tipo de retorno del m�todo.
   * @param n String nuevo tipo.
   */
  public void setType(String n) {
    typeReturn = n;
  }

  /**
   * El m�todo setTypeReturn Cambia el tipo del tipo de retorno, por
   * un tipo que est� almacenado en el vector de par�metros la primera vez.
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
   * El m�todo setName Cambia el nombre del m�todo.
   * @param n String que contiene el nuevo nombre del m�todo.
   */
  public void setName(String n) {
    name = n;
  }

  /**
   * El m�todo setStatic Cambia el modificador static del m�todo.
   * @param s boolean que tiene el valor true si el m�todo es static, false
   * en caso contrario.
   */
  public void setStatic(boolean s) {
    isstatic = s;
  }

/**
 * El m�todo setAbstract Cambia el modificador abstract del m�todo.
 * @param s Boolean que tiene el valor true si el m�todo es abstracto, false
 * en caso contrario.
 */
public void setAbstract(boolean s) {
  isAbstract = s;
}


  /**
   * El m�todo setVisibility Cambia la visibilidad del m�todo.
   * @param i int que representa la visibilidad: 1=public, 2=private, 3=protected.
   */
  public void setVisibility(int i) {
    this.visibility = i;
  }

  /**
   * El m�todo setParameters Cambia los parametros de un m�todo.
   * @param par es un String que almacena los parametros separados por comas
   * ej:tipo1 nombre1, tipo2 nombre2...
   */
  public void setParameters(String par) {
    this.parameters.clear();

    StringTokenizer parametros = new StringTokenizer(par, ",");
    // mientras no se acaben los par�metros
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
   * El m�todo copyParameters copia los UMLParameter almacenados en
   * un vector en el vector de par�metros de la clase UMLMethod.
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
   * El m�todo writeMethod a�ade al fichero el m�todo con la
   * estructura del lenguaje java.
   * @param bw BufferedWriter donde se va escribiendo la informaci�n.
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
   * Crea la parte del xml referida a un m�todo.
   * @param bw BufferedWriter donde escribiremos la informaci�n.
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
   * El m�todo printOperation escribe por pantalla el m�todo.
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
      System.out.println("\t Abstract: S�");
    else
      System.out.println("\t Abstract: No");
  }
}

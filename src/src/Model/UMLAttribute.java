package Model;

import java.io.*;
/**
 * Clase que representa un atributo UML, está compuesto por todas las
 * características propias de un atributo(nombre, tipo, visibilidad,es estático)
 * @author Digna Rodríguez
 * @version 1.0
 */
public class UMLAttribute {

  protected String name;
  protected int visibility;
  protected String type;
  protected boolean isstatic= false;
  protected String vectorType = "";//tipo de los datos contenidos en el vector


  public UMLAttribute() {}
  /**
   * Constructor de clase de un UMLAttribute.
   * @param nom String nombre del atributo.
   * @param tipo String tipo del atributo.
   * @param vis int visibilidad del atributo.
   * @param sta boolean vale true si el atributo es static false en caso
   * contrario.
   */
  public UMLAttribute(String nom, String tipo, int vis, boolean sta,String vtipo) {
    name = nom;
    type = tipo;
    visibility = vis;
    isstatic = sta;
    vectorType= vtipo;
  }

  /**
   * Método que obtienen el nombre del atributo.
   * @return String nombre del atributo.
   */
  public String getName() {
    return name;
  }

  /**
   * Método que devuelve el tipo del atributo.
   * @return String tipo del atributo.
   */
  public String getType(){
    return type;
  }
  /**
   * En caso de que el atributo sea de tipo Vector, se puede obtener el tipo de
   * los elementos del vector mediante este método.
   * @return String tipo de los elementos del vector.
   */
  public String getVectorType(){
    if (vectorType==null)
      return "";
    else
      return vectorType;
  }

  /**
   * Método que modifica el tipo de los elementos de un atributo de tipo Vector.
   * @param s String nuevo tipo.
   */
  public void setVectorType(String s)
  {vectorType=s;}
  public void setName(String n) {
    name = n;
  }
  /**
   * Método que obtiene la visibilidad de un atributo.
   * @return String identificador de la visibilidad.
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
   * Método que cambia la visibilidad de un atributo.
   * @param i int identificador de la visibilidad de un atributo.
   */
  public void setVisibility(int i) {
    this.visibility = i;
  }

  /**
   * Método que modifica el tipo de un atributo.
   * @param s String nuevo tipo.
   */
  public void setType(String s){
    this.type=s;
  }

  /**
   * Método que cambia el valor del modificador static de un atributo.
   * @param s boolean nuevo valor del modificador static.
   */
  public void setStatic(boolean s) {
    isstatic = s;
  }

  /**
   * Método que obtiene el valor del modificador static de un atributo.
   * @return boolean valor true si el atributo es static false en caso contrario.
   */
  public boolean getStatic() {
    return isstatic;
  }

  /**
   * Método  que genera código de los get/set de cada atributo.
   * @param bw BufferedWriter salida en la que se escriben los métodos.
   */
  public void generateSetGet(BufferedWriter bw) {
    try {
      //cambio de la primera letra a mayúscula
      char c = name.charAt(0);
      Character x = new Character(c);
      c = x.toUpperCase(c);
      String may = "";
      may = name;
      may = may.replace(may.charAt(0), c);

      //get
      bw.write("public " + type + " get" + may + "(");
      bw.write(") \n{ return " + name + "; }\n\n");
      //set
      bw.write("public void set" + may + "(" + type + " x");
      bw.write(")\n{ " + name + " = x; }\n\n");
    }
    catch (IOException ex) {
    }
  }

  /**
   * Método que genera código relativo a un atributo.
   * @param bw BufferedWriter salida en la que se escribe el código del
   * atributo.
   */
  public void writeAttribute(BufferedWriter bw) {

    try {
      if(type.compareTo("Vector")==0){
        bw.write(getVisibility() + " " + type + " " + name + " = new Vector();\n");
      }
      else{
        if (isstatic == false)
          bw.write(getVisibility() + " " + type + " " + name + ";\n");
        else
          bw.write(getVisibility() + " static " +type + " " + name + ";\n");
      }
    }
    catch (IOException ex) {
    }
  }

  /**
   * Método que escribe el xml de un atributo en un fichero
   * @param bw BufferedWriter canal de salida en el que se escribe.
   */
  public void writeAttributeXML(BufferedWriter bw){
    try {
      bw.write("   <attribute>\n");
      bw.write("       <name_atb>"+this.name+"</name_atb>\n");
      bw.write("       <visib_atb>"+this.visibility+"</visib_atb>\n");
      bw.write("       <type_atb>"+this.type+"</type_atb>\n");
      if(this.getVectorType().compareTo("")==0)
        bw.write("       <type_vector>null</type_vector>\n");
      else
        bw.write("       <type_vector>"+this.vectorType+"</type_vector>\n");
      if(this.isstatic)
        bw.write("       <static_atb>si</static_atb>\n");
      else
         bw.write("       <static_atb>no</static_atb>\n");
      bw.write("   </attribute>\n");
    }
    catch (IOException ex) {
    }
  }

  /**
   * Método que imprime por pantalla un atributo.
   */
  public void printAttribute() {
    String vis = "";
    vis = getVisibility();
    if(type.compareTo("Vector")==0)
     System.out.println("\t " + vis + "  "+ type  +" <"+ vectorType +"> "+ name);
    else
      System.out.println("\t " + vis + "  " + type + "  " + name);
  }
}

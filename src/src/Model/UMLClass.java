package Model;

import java.util.*;
import java.io.*;

/**
 * La clase UMLClass almacena los datos de una clase
 * UML:(visibilidad, nombre, métodos,atributos)
 * @author Digna Rodríguez Cudeiro
 * @version 1.0, octubre-2008
 * @deprecated Ningún miembro de la clase es <i>deprecated</i>.
 */
public class UMLClass {
  protected String name;
  protected boolean isAbstract;
  protected int visibility;

  public Vector vAttributes = new Vector();
  public Vector vOperations = new Vector();

  public UMLClass() {}

  /**
   * Constructor de clase
   * @param nom String nombre de la clase.
   * @param abs boolean valor true si la clase es abstracta, false en caso
   * contrario.
   * @param vis int representa la visibilidad de un atributo.
   */
  public UMLClass(String nom, boolean abs, int vis) {
    name = nom;
    isAbstract = abs;
    visibility = vis;
  }

  /**
   * El método getName devuelve el nombre de la clase.
   * @return String que contiene el nombre de la clase.
   **/
  public String getName() {
    return name;
  }

  /**
   * El método setVisibility Cambia la visibilidad de la clase.
   * @param i int que representa la visibilidad: 1=public, 2=private, 3=protected.
   */
  public void setVisibility(int i) {
    this.visibility = i;
  }

  /**
   * El método setAbstract Cambia el modificador abstract de la clase.
   * @param ab boolean representa si la clase es abstracta(true) o no(false).
   */
  public void setAbstract(boolean ab) {
    this.isAbstract = ab;
  }

  /**
   * El método isAbstract comprueba si la clase es abstracta.
   * @return boolean devuelve true si la clase es abstracta false
   * en caso contrario.
   **/
  public boolean isAbstract() {
    return this.isAbstract;
  }

  /**
   * El método copyAllMembers copia los vectores de atributos
   * y métodos que se le pasan como parámetro en los vectores miembro de la
   * clase.
   * @param vAts Vector que almacena objetos de tipo UMLAttribute que se
   * copiarán en el miembro vAttributes de la clase.
   * @param vOper Vector que almacena objetos de tipo UMLOperation que se
   * copiarán en el miembro vOperations de la clase.
   */
  public void copyAllMembers(Vector vAts, Vector vOper) {
    vAttributes.clear();
    vOperations.clear();
    for (int i = 0; i < vAts.size(); i++) {
      UMLAttribute toCopy = (UMLAttribute) vAts.get(i);
      this.vAttributes.add(i, toCopy);
    }
    for (int j = 0; j < vOper.size(); j++) {
      UMLMethod toCopy2 = (UMLMethod) vOper.get(j);
      this.vOperations.add(j, toCopy2);
    }
  }

  /**
   * El método loadGesXMLTemplate copia el contenido de la plantilla
   * plantillaCrearXML en el archivo .java correspondiente a la clase.
   * La plantilla contiene el método CrearXML.
   * @param bw BufferedWriter en el que se escribe el código de ese método.
   */
  public void loadGesXMLTemplate(BufferedWriter bw) {
    String s;
    FileReader fr = null;
    try {
      fr = new FileReader("."+File.separator+"templates"+File.separator+"template_GesXML.java");
      BufferedReader bf = new BufferedReader(fr);
      while ( (s = bf.readLine()) != null)
        bw.write(s + "\n");
    }
    catch (FileNotFoundException ex) {}
    catch (IOException ex1) {}
  }

  /**
   * Método que carga en el fichero xml la cabecera que es común a todos los
   * xml y es copiada de una plantilla.
   * @param bw BufferedWriter stream donde se escribe.
   */
  public void writeXMLHead(BufferedWriter bw) {
    String s;
    FileReader fr = null;
    try {
      fr = new FileReader("."+File.separator+"templates"+File.separator+"templateHeadXML.xml");
      BufferedReader bf = new BufferedReader(fr);
      while ( (s = bf.readLine()) != null)
        bw.write(s + "\n");
    }
    catch (FileNotFoundException ex) {}
    catch (IOException ex1) {}
  }

  /**
   * El método loadImportTemplate copia el contenido de la plantilla
   * plantillaImports en el archivo .java correspondiente a la clase.
   * La plantilla contiene todos los imports de las librerías.
   * @param bw BufferedWriter en el que se escriben los imports.
   */
  public void loadImportTemplate(BufferedWriter bw) {
    String s;
    FileReader fr = null;
    try {
      fr = new FileReader("."+File.separator+"templates"+File.separator+"template_Imports.java");
      BufferedReader bf = new BufferedReader(fr);
      while ( (s = bf.readLine()) != null)
        bw.write(s + "\n");
    }
    catch (FileNotFoundException ex) {}
    catch (IOException ex1) {}
  }

  /**
   * El método writeClass Genera el código propio de una clase
   * y lo almacena en un fichero.
   * @param f Fichero en el que se escribe la clase.
   */
  public void writeClass(File f) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));
      loadImportTemplate(bw);
      /*Recorremos la tabla de relaciones de herencia para saber si la clase
            extiende de otra*/
      String padre = "";
      int k = 0;
      while ( (k < Controller.gener.vGen.size())) {
        UMLGeneralization g = (UMLGeneralization) Controller.gener.vGen.get(k);
        if ( ( (String) g.vBeginEnd.get(0)).compareTo(name) == 0)
          padre = ( (String) g.vBeginEnd.get(1));
        k++;
      }
      if (padre.compareTo("") == 0)
        bw.write("\n" + getVisibility() + " class " + name +
                 " implements Raiz{\n\n");
      else
        bw.write("\n" + getVisibility() + " class " + name +
                 " extends " + padre + " implements Raiz {\n\n");

      bw.write("public Vector relaciones;\n");
      //Atributos de la clase y métodos set/get
      if (vAttributes.size() != 0) {
        for (int i = 0; i < vAttributes.size(); i++)
          ( (UMLAttribute) vAttributes.get(i)).writeAttribute(bw);
        bw.write("\n");
        for (int i = 0; i < vAttributes.size(); i++)
          ( (UMLAttribute) vAttributes.get(i)).generateSetGet(bw);
      }

      //constructor por defecto
      bw.write("\n" + "public " + name + " () {}" + "\n");
      //Método que devuelve el nombre de la clase
      bw.write("public String getClassName(){ return \"" + name +
               "\";}\n\n");

      //Método que devuelve un vector de strings (los tags del xml= los atb. el xml)
      Vector vRes = new Vector();
      for (int i = 0; i < vAttributes.size(); i++) {
        vRes.addElement( (String) ( (UMLAttribute) (vAttributes.get(i))).
                        getName());
      }

      bw.write(" public Vector getvAtb(){\n");
      bw.write("   Vector salida = new Vector();\n");
      bw.write("   salida.addElement(\"ident\");");
      String c = "";
      for (int j = 0; j < vRes.size(); j++) {
        c = (String) vRes.get(j);
        bw.write("  salida.addElement(\"" + c + "\");\n");
      }
      bw.write(" return salida;");
      bw.write("}\n\n");

      bw.write(" public Vector getRelaciones1a1() {\n");
      bw.write("   Vector salida = new Vector();\n");
      for (int i = 0; i < Controller.asoc.vAssoc.size(); i++) {
        UMLAssociation as = (UMLAssociation) Controller.asoc.vAssoc.get(i);

        String ini = (String) (as).vBeginEnd.get(0);
        String fin = (String) (as).vBeginEnd.get(1);
        if (ini.compareTo(name) == 0) { //relacion 1 a 1
          String multini = (String) (as.multiplicity.get(0));
          String multfin = (String) (as.multiplicity.get(1));
          if ( (multini.compareTo("1") == 0) && (multfin.compareTo("1") == 0)) {

            bw.write("   " + fin + " x" + fin + " = new " + fin + "();\n");
            bw.write("   salida.add( (Raiz) x" + fin + ");\n");
          }
        }
      }
      bw.write("   this.relaciones = salida;\n");
      bw.write("   return salida;\n");
      bw.write(" }\n");

      bw.write(" public Vector getRelaciones1an() {\n");
      bw.write("   Vector salida = new Vector();\n");
      for (int i = 0; i < Controller.asoc.vAssoc.size(); i++) {
        UMLAssociation as = (UMLAssociation) Controller.asoc.vAssoc.get(i);
        String ini = (String) (as).vBeginEnd.get(0);
        String fin = (String) (as).vBeginEnd.get(1);
        if (ini.compareTo(name) == 0) { //relacion 1 a n
          String multini = (String) (as.multiplicity.get(0));
          String multfin = (String) (as.multiplicity.get(1));
          // System.out.println("hay una rel::"+ini+"->"+fin);
          // System.out.println("hay una rel::"+multini+"->"+multfin);
          if ( (multini.compareTo("1") == 0) && (multfin.compareTo("-1") == 0)) {
            bw.write("   " + fin + " x" + fin + "= new " + fin + "();\n");
            bw.write("   salida.add( (Raiz) x" + fin + ");\n");
          }
        }
      }
      bw.write("   this.relaciones = salida;\n");
      bw.write("   return salida;\n");
      bw.write(" }\n");

      bw.write("public Raiz getElementoRelacionado(String n){\n");
      bw.write("  for (int i=0;i<this.relaciones.size();i++){\n");
      bw.write(
          "   String nom = ( (Raiz)this.relaciones.get(i)).getClassName();\n");
      bw.write("   if(nom.compareTo(n)==0)\n");
      bw.write("     return (Raiz)this.relaciones.get(i);\n");
      bw.write("   }\n");
      bw.write("  return null;\n");
      bw.write(" }\n");

      //Métodos de la clase
      if (vOperations.size() != 0) {
        for (int i = 0; i < vOperations.size(); i++)
          ( (UMLMethod) vOperations.get(i)).writeMethod(bw);
      }

      //Métodos de recuperación y escritura  y búsqueda en archivos xml
      this.loadGesXMLTemplate(bw);

      bw.write("}");
      bw.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Método que escribe en un fichero el xml relativo a una clase.
   * @param f File fichero en el que se escribe.
   */
  public void writeClassXML(File f) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(f));

      writeXMLHead(bw);
      bw.write("<nameclass>" + this.name + "</nameclass>\n");
      bw.write("<visibility>" + this.visibility + "</visibility>\n");
      if (this.isAbstract)
        bw.write("<abstract>si</abstract>\n");
      else
        bw.write("<abstract>no</abstract>\n");

      for (int i = 0; i < vAttributes.size(); i++) {
        ( (UMLAttribute) vAttributes.get(i)).writeAttributeXML(bw);
      }
      for (int i = 0; i < vOperations.size(); i++) {
        ( (UMLMethod) vOperations.get(i)).writeMethodXML(bw);
      }
      bw.write("</uml:class>\n");
      bw.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método getVisibility devuelve como un String la visibilidad de la
   * clase:(public, protected o private).
   * @return String que define la visibilidad de la clase.
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
   * El método printClass muestra por pantalla todos los miembros de la
   * clase */
  public void printClass() {
    System.out.println("\n\n---------CLASE: " + name + " --------");
    System.out.println("Visibilidad:" + this.getVisibility());
    System.out.print("Abstracta: ");
    if (isAbstract)
      System.out.println("Sí");
    else
      System.out.println("No");
    System.out.println("Atributos:");
    if (vAttributes.size() == 0) System.out.println("\tno tiene");
    else {
      for (int i = 0; i < vAttributes.size(); i++)
        ( (UMLAttribute) vAttributes.get(i)).printAttribute();
    }
    System.out.println("Métodos:");
    if (vOperations.size() == 0) System.out.println("\tno tiene");
    else {
      for (int i = 0; i < vOperations.size(); i++)
        ( (UMLMethod) vOperations.get(i)).printOperation();
    }
  }
}

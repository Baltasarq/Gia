package Model;

import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import View.*;
import View.LoadMainWindow;
import View.MainWindow;

/**
 * Clase que se encarga de leer el archivo xmi de entrada, tratando todos los
 * nodos del árbol xmi, creando a partir de esta lectura las estructuras del
 * modelo del proyecto.
 * @author Digna Rodriguez Cudeiro
 * @version 1.0
 */
public class ParserXMI {

  private String rutaXMI;
  private DataTypeTable tdt = new DataTypeTable();

  /**
   * Constructor de clase, inicializa el fichero xmi para su parseo.
   * @throws IOException lanza una excepción de entrada/salida en caso de que
   * no se pueda crear el fichero.
   */
  public ParserXMI() throws IOException {
    File fXMI = new File( (String) View.MainWindow.getXmiPath());
    setXMIPath(fXMI.getCanonicalPath());
    tdt = readTypes();
  }

  /**
   * Método que permite cambiar la ruta del fichero xmi.
   * @param path String ruta nueva.
   */
  public void setXMIPath(String path) {
    rutaXMI = path;
  }

  /**
   * Método que devuelve la ruta del archivo xmi.
   * @return String ruta del archivo xmi.
   */
  public String getXMIPath() {
    return rutaXMI;
  }

  /**
   * Método que lee el archivo xmi y devuelve un objeto de tipo ClassContainer
   * que contiene toda la infomación de las clases del sistema.
   * @return ClassContainer con todas las clases leidas del xmi.
   */
  public ClassContainer readXMI() {
    ClassContainer cc = new ClassContainer();
    Vector vAtb = new Vector();
    Vector vOper = new Vector();
    Vector vPar = new Vector();
    Vector vType = new Vector();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(rutaXMI));
      readXMLNode(cc, tdt, vAtb, vOper, vPar, vType, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return cc;
  }

  /**
   * es un método recursivo que se encarga de leer el contenido del fichero xmi
   * para ir creando el contenedor de clases del sistema.
   * @param clascont ClassContainer contenedor de clases del sistema que se va
   * creando en cada llamada al método.
   * @param tdt DataTypeTable tabla de tipos que se va creando en cada llamada
   * al método.
   * @param vatbs Vector que en cada iteración almacena los atributos de una
   * clase.
   * @param vopers Vector que en cada iteración almacena los métodos de una clase.
   * @param vpar Vector que en cada iteración almacena los parametros de un
   * método de una clase.
   * @param vx Vector que se va creando con los tipos de datos del sistema.
   * @param nodo Node del xmi que en cada iteración contiene la información a
   * parsear.
   */
  public void readXMLNode(ClassContainer clascont, DataTypeTable tdt,
                            Vector vatbs,
                            Vector vopers, Vector vpar, Vector vx,
                            Node nodo) {

    switch (nodo.getNodeType()) {
      /*
       * Nodo de tipo Raiz del documento
       */
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        readXMLNode(clascont, tdt, vatbs, vopers, vpar, vx,
                      doc.getDocumentElement());
        break;

        /*
         * Nodo de tipo Elemento
         */
      case Node.ELEMENT_NODE:

        //Lista de atributos del nodo
        NamedNodeMap ats = nodo.getAttributes();
        //lista de hijos del nodo
        NodeList hijos = nodo.getChildNodes();

        if (nodo.getNodeName() == "UML:Class") {
          if (ats.getLength() != 1) {
            UMLClass clase = new UMLClass();

            for (int i = 0; i < ats.getLength(); i++) {
              if (ats.item(i).getNodeName() == "name") {
                String nomClase = (String) ats.item(i).getNodeValue();
                clase.name = nomClase;
              }
              else {
                if (ats.item(i).getNodeName() == "visibility") {
                  String visi = (String) ats.item(i).getNodeValue();
                  clase.visibility = getVisibility(visi);
                }
                else
                if (ats.item(i).getNodeName() == "isAbstract") {
                  String abs = (String) ats.item(i).getNodeValue();
                  if (abs.compareTo("true") == 0)
                    clase.isAbstract = true;
                  else
                    clase.isAbstract = false;
                }
              }
            }
            /*vauxiAt,vAuxiOp en principio están vacío pero sirve para almacenar
              los hijos de una clase, es decir su atributos y operaciones*/
            if (clase.name != null) {
              Vector vauxiAt = new Vector();
              Vector vauxiOp = new Vector();
              Vector vauxiPar = new Vector();

              for (int i = 0; i < hijos.getLength(); i++)
                readXMLNode(clascont, tdt, vauxiAt, vauxiOp, vauxiPar, vx,
                              hijos.item(i));

                /*cuando salimos de este for los vectore vauxiAt y vauxiOp contienen
                             los atributos y operaciones de la clase*/
              clase.copyAllMembers(vauxiAt, vauxiOp);
              //añadimos una clase nueva y completa al contenedor.
              ( (ClassContainer) clascont).vClasses.addElement(clase);
            }
          }
          else {
            //Es una clase tipo de dato->ejemplo:String
            if (ats.item(0).getNodeName() == "xmi.idref") {
              String c = (String) ats.item(0).getNodeValue();
              vx.add(c);

            }

          }
        }
        else {
          if (nodo.getNodeName() == "UML:Attribute") {

            UMLAttribute atb = new UMLAttribute();
            //nos quedamos con los datos que nos interesan de attibute
            for (int i = 0; i < ats.getLength(); i++) {
              if (ats.item(i).getNodeName() == "name") {
                String nomAtributo = (String) ats.item(i).getNodeValue();
                atb.name = nomAtributo;
              }
              else if (ats.item(i).getNodeName() == "visibility") {
                String visi = (String) ats.item(i).getNodeValue();
                atb.visibility = getVisibility(visi);
              }
            }
            //vector en cuya primera posición almacenamos el tipo
            vx.clear();
            for (int i = 0; i < hijos.getLength(); i++) {
              readXMLNode(clascont, tdt, vatbs, vopers, vpar, vx,
                            hijos.item(i));
            }
            if (vx.size() != 0)
              atb.type = changeType( (String) vx.get(0), tdt);

              //añadimos el atributo al vector auxiliar de atributos
            vatbs.add( (UMLAttribute) atb);
          }
          else {
            if (nodo.getNodeName() == "UML:Operation") {
              UMLMethod oper = new UMLMethod();
              //nos quedamos con los datos que nos interesan del método
              for (int i = 0; i < ats.getLength(); i++) {
                if (ats.item(i).getNodeName() == "name") {
                  String nom = (String) ats.item(i).getNodeValue();
                  oper.name = nom;
                }
                else {
                  if (ats.item(i).getNodeName() == "visibility") {
                    String visi = (String) ats.item(i).getNodeValue();
                    oper.visibility = getVisibility(visi);
                  }
                  else if (ats.item(i).getNodeName() == "isAbstract") {
                    String abs = (String) ats.item(i).getNodeValue();
                    if (abs.compareTo("true") == 0)
                      oper.isAbstract = true;
                    else
                      oper.isAbstract = false;
                  }
                }
              }
              vpar.clear();
              //Almacenamos en vpar los parámetros de ese método
              for (int i = 0; i < hijos.getLength(); i++)
                readXMLNode(clascont, tdt, vatbs, vopers, vpar, vx,
                              hijos.item(i));
              oper.copyParameters(vpar);
              //añadimos el método al vector auxiliar de operaciones
              vopers.add( (UMLMethod) oper);
            }
            else {
              if (nodo.getNodeName() == "UML:Parameter") {
                UMLParameter par = new UMLParameter();
                for (int i = 0; i < ats.getLength(); i++) {
                  if (ats.item(i).getNodeName() == "name") {
                    String nom = (String) ats.item(i).getNodeValue();
                    par.name = nom;
                  }
                }

                //vector en cuya primera posición almacenamos el tipo
                vx.clear();
                for (int i = 0; i < hijos.getLength(); i++) {
                  readXMLNode(clascont, tdt, vatbs, vopers, vpar, vx,
                                hijos.item(i));
                }
                // System.out.println(vx);
                if (vx.size() != 0)
                  par.type = changeType( (String) vx.get(0), tdt);
                vpar.add( (UMLParameter) par);
              }
              else {
                if (nodo.getNodeName() == "UML:DataType") {
                  if (ats.getLength() == 1) {
                    if (ats.item(0).getNodeName() == "xmi.idref") {
                      String c = (String) ats.item(0).getNodeValue();
                      vx.add(c);
                    }
                  }
                }
                else {
                  if ( (nodo.getNodeName() ==
                        "UML:StructuralFeature.multiplicity") ||
                      (nodo.getNodeName() == "UML:Package")) {}
                  else {
                    //son los elementos distintos a uml:class y uml:attribute
                    for (int i = 0; i < hijos.getLength(); i++)
                      readXMLNode(clascont, tdt, vatbs, vopers, vpar, vx,
                                    hijos.item(i));
                  }
                } //association
              } //dataType
            } //parameter
          } //operation
        } //atribute
        break;

        /*
         * Nodo de tipo Texto
         */
      case Node.TEXT_NODE:
        String text = nodo.getNodeValue();
        break;
    } //switch
  }

  /**
   * Obtiene la visibilidad de una clase o de un miembro de una clase.
   * @param visi String que almacena la visibilidad.
   * @return int entero que representa el nivel de visibilidad.
   */
  public int getVisibility(String visi) {
    int toRet = 0;
    if (visi.compareTo("public") == 0)
      toRet = EnumVisibility.mPublic;
    else if (visi.compareTo("private") == 0)
      toRet = EnumVisibility.mPrivate;
    else if (visi.compareTo("protected") == 0)
      toRet = EnumVisibility.mProtected;
    return toRet;
  }

  /**
   * Método que permite cambiar el tipo de dato de un identificador.
   * @param s String identificador del DataType.
   * @param tdt DataTypeTable tabla que contiene todos los tipos de datos del
   * sistema.
   * @return String devuelve un string con el tipo de dato a modificar, en caso
   * de que no lo encuentre devuelve la cadena vacía.
   */
  public String changeType(String s, DataTypeTable tdt) {
    String tipo = "";
    String cmp = "";
    for (int i = 0; i < tdt.vTypes.size(); i++) {
      cmp = (String) ( (DataType) tdt.vTypes.get(i)).getId();

      if (s.compareTo(cmp) == 0)
        tipo = (String) ( (DataType) tdt.vTypes.get(i)).getType();
    }
    return tipo;
  }

  /**
   * Método que devuelve un objeto de tipo DataTypeTable que contiene toda la
   * infomación de los tipos de los parámetros y atributos de las clases del
   * sistema.
   * @return DataTypeTable contiene todos los tipos de datos del xmi.
   */
  public DataTypeTable readTypes() {
    DataTypeTable tdt = new DataTypeTable();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(rutaXMI));
      getAllTypes(tdt, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return tdt;
  }

  /**
   * Método recursivo que lee el archivo xmi y crea una DataTypeTable con todos
   * los tipos de datos del sistema.
   * @param tdt DataTypeTable tabla que se va creando.
   * @param nodo Node del xmi que se esta evaluando en cada iteración.
   */
  public void getAllTypes(DataTypeTable tdt,
                          Node nodo) {

    switch (nodo.getNodeType()) {
      /*
       * Nodo de tipo Raiz del documento
       */
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        getAllTypes(tdt, doc.getDocumentElement());
        break;

        /*
         * Nodo de tipo Elemento
         */
      case Node.ELEMENT_NODE:

        //Lista de atributos del nodo
        NamedNodeMap ats = nodo.getAttributes();
        //lista de hijos del nodo
        NodeList hijos = nodo.getChildNodes();

        if ( (nodo.getNodeName() == "UML:Class") ||
            (nodo.getNodeName() == "UML:DataType")) {
          DataType dt = new DataType();
          for (int i = 0; i < ats.getLength(); i++) {
            if (ats.item(i).getNodeName() == "xmi.id") {
              String iden = (String) ats.item(i).getNodeValue();
              dt.id = iden;
            }
            else {
              if (ats.item(i).getNodeName() == "name") {
                String t = (String) ats.item(i).getNodeValue();
                dt.type = t;
              }
            }
          }
          if (dt.type != null) {
            //Añadimos un nuevo dataType a la tabla
            tdt.vTypes.addElement(dt);
          }
        }
        else {
          for (int i = 0; i < hijos.getLength(); i++)
            getAllTypes(tdt, hijos.item(i));
        }
        break;
    } //switch
  }

  /**
   * Método que devuelve un objeto de tipo AssociationTable que contiene toda la
   * infomación de las relaciones entre las clases del sistema.
   * @return AssociationTable con todas las asociaciones del xmi.
   */
  public AssociationTable readAssociations() {
    AssociationTable ta = new AssociationTable();
    Vector vbe = new Vector();
    Vector vmult = new Vector();

    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(rutaXMI));
      getAllAssociations(ta, vbe, vmult, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return ta;
  }

  /**
   * Método recursivo que lee el archivo xmi y crea una AssociationTable con
   * todas las asociaciones existentes entre clases del sistema.
   * @param ta AssociationTable tabla de asociaciones que se va creando.
   * @param beEnd Vector que contiene los elementos inicio y fin de la asociación.
   * @param mult Vecto que contiene la multiplicidad de una asociación.
   * @param nodo Node del xmi que se esta evaluando en cada iteración.
   */
  public void getAllAssociations(AssociationTable ta, Vector beEnd, Vector mult,
                                 Node nodo) {

    switch (nodo.getNodeType()) {
      /*
       * Nodo de tipo Raiz del documento
       */
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        getAllAssociations(ta, beEnd, mult, doc.getDocumentElement());
        break;
        /*
         * Nodo de tipo Elemento
         */
      case Node.ELEMENT_NODE:

        //Lista de atributos del nodo
        NamedNodeMap ats = nodo.getAttributes();
        //lista de hijos del nodo
        NodeList hijos = nodo.getChildNodes();

        if (nodo.getNodeName() == "UML:Association") {
          UMLAssociation as = new UMLAssociation();
          for (int i = 0; i < ats.getLength(); i++) {
            if (ats.item(i).getNodeName() == "xmi.id") {
              String iden = (String) ats.item(i).getNodeValue();
              as.id = iden;
            }
          }
          beEnd.clear();
          mult.clear();
          for (int i = 0; i < hijos.getLength(); i++)
            getAllAssociations(ta, beEnd, mult, hijos.item(i));

          as.copyAllMembers(beEnd, mult);
          //Añadimos una nueva asociacion a la tabla
          ta.vAssoc.addElement(as);
        } //Association
        else {
          if (nodo.getNodeName() == "UML:Class") {

            for (int i = 0; i < ats.getLength(); i++) {
              if (ats.item(i).getNodeName() == "xmi.idref") {
                String iden = (String) ats.item(i).getNodeValue();
                beEnd.addElement(iden);
              }
            }
          }
          else {
            if (nodo.getNodeName() == "UML:MultiplicityRange") {
              for (int i = 0; i < ats.getLength(); i++) {
                if (ats.item(i).getNodeName() == "upper") {
                  String iden = (String) ats.item(i).getNodeValue();
                  mult.addElement(iden);
                }
              }
            }
            else {
              for (int i = 0; i < hijos.getLength(); i++)
                getAllAssociations(ta, beEnd, mult, hijos.item(i));
            }
          } //MultiplicityRange
        } //Class
        break;
    } //switch
  }

  /**
   * Método que devuelve un objeto de tipo GeneralizationTable que contiene
   * toda la información sobre las relaciones de herencia existentes entre
   * las clases del sistema.
   * @return GeneralizationTable con todas las relaciones de generalización del
   * xmi.
   */
  public GeneralizationTable readGeneralizations() {
    GeneralizationTable ta = new GeneralizationTable();
    Vector vbe = new Vector();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(rutaXMI));
      getAllGeneralizations(ta, vbe, doc);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return ta;
  }

  /**
   * Método recursivo que lee el archivo xmi y crea una GeneralizationTable con
   * todas las relaciones de herencia existentes entre clases del sistema.
   * @param ta GeneralizationTable tabla de relaciones de herencia
   * que se va creando.
   * @param beEnd Vector que contiene los elementos inicio y fin de la relación
   * de herencia.
   * @param nodo Node del xmi que se esta evaluando en cada iteración.
   */
  public void getAllGeneralizations(GeneralizationTable ta, Vector beEnd,
                                    Node nodo) {

    switch (nodo.getNodeType()) {
      /*
       * Nodo de tipo Raiz del documento
       */
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        getAllGeneralizations(ta, beEnd, doc.getDocumentElement());
        break;
        /*
         * Nodo de tipo Elemento
         */
      case Node.ELEMENT_NODE:

        //Lista de atributos del nodo
        NamedNodeMap ats = nodo.getAttributes();
        //lista de hijos del nodo
        NodeList hijos = nodo.getChildNodes();

        if (nodo.getNodeName() == "UML:Generalization") {
          UMLGeneralization as = new UMLGeneralization();
          for (int i = 0; i < ats.getLength(); i++) {
            if (ats.item(i).getNodeName() == "xmi.id") {
              String iden = (String) ats.item(i).getNodeValue();
              as.id = iden;
            }
          }
          beEnd.clear();
          for (int i = 0; i < hijos.getLength(); i++)
            getAllGeneralizations(ta, beEnd, hijos.item(i));

          as.copyAllMembers(beEnd);
          //Añadimos una nueva generalización a la tabla
          ta.vGen.addElement(as);
        } //Generalization
        else {
          if (nodo.getNodeName() == "UML:Class") {

            for (int i = 0; i < ats.getLength(); i++) {
              if (ats.item(i).getNodeName() == "xmi.idref") {
                String iden = (String) ats.item(i).getNodeValue();
                beEnd.addElement(iden);
              }
            }
          }
          else {
            for (int i = 0; i < hijos.getLength(); i++)
              getAllGeneralizations(ta, beEnd, hijos.item(i));
          }
        } //Class
        break;
    } //switch
  }

  /**
   * Método principal del sistema desde el que se llama al método que carga
   * la ventana principal.
   * @param args String[]
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException ex1) {
    }
    catch (IllegalAccessException ex1) {
    }
    catch (InstantiationException ex1) {
    }
    catch (ClassNotFoundException ex1) {
    }
    new LoadMainWindow();
  }

}

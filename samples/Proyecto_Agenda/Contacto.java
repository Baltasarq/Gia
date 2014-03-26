import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.*;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.*;

public class Contacto implements Raiz{

public Vector relaciones;
protected String nombre;
protected String mail;
protected String cumpleanos;
protected int telefonoFijo;
public int movil;
protected Direccion $r_Direccion;

public String getNombre() 
{ return nombre; }

public void setNombre(String x)
{ nombre = x; }

public String getMail() 
{ return mail; }

public void setMail(String x)
{ mail = x; }

public String getCumpleanos() 
{ return cumpleanos; }

public void setCumpleanos(String x)
{ cumpleanos = x; }

public int getTelefonoFijo() 
{ return telefonoFijo; }

public void setTelefonoFijo(int x)
{ telefonoFijo = x; }

public int getMovil() 
{ return movil; }

public void setMovil(int x)
{ movil = x; }

public Direccion get$r_Direccion() 
{ return $r_Direccion; }

public void set$r_Direccion(Direccion x)
{ $r_Direccion = x; }


public Contacto () {}
public String getClassName(){ return "Contacto";}

 public Vector getvAtb(){
   Vector salida = new Vector();
   salida.addElement("ident");  salida.addElement("nombre");
  salida.addElement("mail");
  salida.addElement("cumpleanos");
  salida.addElement("telefonoFijo");
  salida.addElement("movil");
  salida.addElement("$r_Direccion");
 return salida;}

 public Vector getRelaciones1a1() {
   Vector salida = new Vector();
   Direccion xDireccion = new Direccion();
   salida.add( (Raiz) xDireccion);
   this.relaciones = salida;
   return salida;
 }
 public Vector getRelaciones1an() {
   Vector salida = new Vector();
   this.relaciones = salida;
   return salida;
 }
public Raiz getElementoRelacionado(String n){
  for (int i=0;i<this.relaciones.size();i++){
   String nom = ( (Raiz)this.relaciones.get(i)).getClassName();
   if(nom.compareTo(n)==0)
     return (Raiz)this.relaciones.get(i);
   }
  return null;
 }
public void mostrarDatos() {

}
public boolean imprimir() {
boolean x = true;
return x;
}
  /**
    * Método que lee de un xml y carga los elementos del XML en un
    * vector de vectores
    * @return Vector que contiene los elementos leidos del xml.
    */
   public Vector leerXML() {
    Vector res = new Vector();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(VentanaPrincipal.ruta +"xml"+File.separator + this.getClassName() +
                                               ".xml"));
      tratarNodoXML(res, doc);
    }
    catch (Exception ex) {}
    //System.out.println(res);
    return res;
  }
  
  /**
   * Método que lee recursivamente del xml.
   * @param res Vector que se va cargando de los elementos al leer el xml.
   * @param nodo Node que se lee en cada llamada al método
   */
  public void tratarNodoXML(Vector res, Node nodo) {
    switch (nodo.getNodeType()) {

      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        tratarNodoXML(res, doc.getDocumentElement());
        break;

      case Node.ELEMENT_NODE:
        NamedNodeMap ats = nodo.getAttributes();
        NodeList hijos = nodo.getChildNodes();
        if (nodo.getNodeName() == this.getClassName()) {
          Vector aux = new Vector();
          for (int i = 0; i < ats.getLength(); i++)
             if (ats.item(i).getNodeName() == "enlace") {
               tratarNodoXML(aux, ats.item(i));
               aux.add(ats.item(i).getNodeValue());
             }
          for (int i = 0; i < hijos.getLength(); i++)
            tratarNodoXML(aux, hijos.item(i));
          res.add(aux);
        }
        else {
          for (int i = 0; i < hijos.getLength(); i++)
            tratarNodoXML(res, hijos.item(i));
          for (int i = 0; i < ats.getLength(); i++)
            tratarNodoXML(res, ats.item(i));
          if ( (ats.getLength() == 0) && (hijos.getLength() == 0))
            res.add("");
        }
        break;

      case Node.TEXT_NODE:
        String texto = nodo.getNodeValue().trim();
        if (!texto.equals("")) {
          res.add(texto);
        }
        break;
    }
  }
  
 /**
  * Método que compone un archivo con formato xml.
  * @param vObj Vector con los datos del cual se forma el xml.
  * @throws FactoryConfigurationError excepción si no se puede crear el xml.
  * @throws ParserConfigurationException excepción si no se puede crear el xml.
  */
 public void crearXML(Vector vObj) throws
     FactoryConfigurationError, ParserConfigurationException {
   

   DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
       newDocumentBuilder();
   Document doc = docBuilder.newDocument();

   Vector vAtb = new Vector();
   vAtb = getvAtb();
   Element root = doc.createElement("raiz");
   if (vObj.size() > 0) {
     for (int j = 0; j < vObj.size(); j++) {
       Vector vDatos = new Vector( (Vector) vObj.get(j));
       Element tagClase = doc.createElement(getClassName());
       tagClase.setAttribute("enlace",(String) vDatos.get(0));//añadimos un atributo al xml con el primer elemento del vector de datos
       //Creamos todos los hijos del nodo raíz
       for (int i = 0; i < vAtb.size(); i++) {
         if ( (! ( ( (String) vAtb.get(i)).substring(0, 3).compareTo("$v_") ==
                  0)) &&
             (! ( ( (String) vAtb.get(i)).substring(0, 3).compareTo("$r_") ==
                 0))) {

           Element tagAtb = doc.createElement( ( (String) vAtb.get(i))); 
           Text texto = doc.createTextNode( (String) vDatos.get(i+1));
           tagAtb.appendChild(texto);
           tagClase.appendChild(tagAtb);
           root.appendChild(tagClase);
         }
       }
     }
   }
   else {
     Element tagClase = doc.createElement(getClassName());
     tagClase.setAttribute("enlace","");
     //Generamos un nuevo id para el objeto de la clase     
    Date d=new Date();
    String identGenerado=getClassName().toString().substring(0,2)+"_"+ d.getTime();
    Element tagident = doc.createElement("ident"); 
    Text texident = doc.createTextNode( identGenerado);
    tagident.appendChild(texident);
    tagClase.appendChild(tagident);
    root.appendChild(tagClase);

       for (int i = 1; i < vAtb.size(); i++) {
       if ( (! ( ( (String) vAtb.get(i)).substring(0, 3).compareTo("$v_") == 0)) &&
           (! ( ( (String) vAtb.get(i)).substring(0, 3).compareTo("$r_") == 0))) {
         Element tagAtb = doc.createElement( ( (String) vAtb.get(i)));
         tagClase.appendChild(tagAtb);
         root.appendChild(tagClase);
       }
     }
   }
   doc.appendChild(root);

   Source source = new DOMSource(doc);

   // creación del fichero salida
   File fich = new File(VentanaPrincipal.ruta+"xml"+File.separator + getClassName() + ".xml");
   Result resultado = new StreamResult(fich);

   TransformerFactory factory = TransformerFactory.newInstance();
   Transformer transformer = null;
   try {
     transformer = factory.newTransformer();
   }
   catch (TransformerConfigurationException ex1) {
   }
   transformer.setOutputProperty(OutputKeys.INDENT, "yes");
   transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
   try {
     transformer.transform(source, resultado);
   }
   catch (TransformerException ex2) { ex2.printStackTrace();
   }
 }



  /**
   * Método que lee los elementos de un xml y busca un elemento cuya clave
   * sea igual a la que se le pasa como parámetro.
   * @param busqueda String identificador que se busca en el xml.
   * @return Vector que contiene los elementos encontrados del xml.
   */
  public Vector buscarenXML(String busqueda) {
    Vector res = new Vector();
    try {
      DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().
          newDocumentBuilder();
      Document doc = docBuilder.parse(new File(VentanaPrincipal.ruta+ "xml"+File.separator + this.getClassName() +
                                               ".xml"));
      buscarNodoXML(res, doc, busqueda);
    }
    catch (Exception ex) {}
    //System.out.println(res);
    return res;
  }
  /**
   * Método que recorre recursivamente los elementos del xml.
   * @param res Vector que se va cargando de los elementos al leer el xml.
   * @param nodo Node que se lee en cada llamada al método.
   * @param bus String identificador que se busca en el xml.
   */
  public void buscarNodoXML(Vector res, Node nodo, String bus) {
    switch (nodo.getNodeType()) {
      case Node.DOCUMENT_NODE:
        Document doc = (Document) nodo;
        buscarNodoXML(res, doc.getDocumentElement(), bus);
        break;

      case Node.ELEMENT_NODE:
        NamedNodeMap ats = nodo.getAttributes();
        NodeList hijos = nodo.getChildNodes();
        Vector aux = new Vector();
        if (nodo.getNodeName().compareTo(this.getClassName()) == 0) {
          if (ats.getNamedItem("enlace").getNodeValue().compareTo(bus) == 0){
              aux.add(ats.item(0).getNodeValue());
            for (int i = 0; i < hijos.getLength(); i++)
              buscarNodoXML(aux, hijos.item(i), bus);
            res.add(aux);
          }
        }
        else {
        for (int i = 0; i < hijos.getLength(); i++)
          buscarNodoXML(res, hijos.item(i),bus);
        if ( (ats.getLength() == 0) && (hijos.getLength() == 0))
          res.add("");
        }
        break;

      case Node.TEXT_NODE:
        String texto = nodo.getNodeValue().trim();
        if (!texto.equals("")) {
          res.add(texto);
        }
        break;
    }
  }

}
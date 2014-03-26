package Model;

import java.util.*;
import java.io.*;
import java.util.zip.*;

/**
 * Clase en la que se almacenarán las clases de nuestro diagrama de clases UML.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class ClassContainer {

  public static Vector vClasses = new Vector();

  public ClassContainer() {
  }

  /**
   * El método printAllClasses muestra por pantalla el contenido
   * de todo el ClassContainer.
   */
  public void printAllClasses() {
    for (int i = 0; i < vClasses.size(); i++)
      ( (UMLClass) vClasses.get(i)).printClass();
  }

  /****************************Añadir*************************************/
  /**
   * El método addClass añade una clase al ClassContainer.
   * @param vdatos Vector que contiene los datos de la clase que se quiere
   * añadir.
   */
  public void addClass(Vector vdatos) {
    if (vdatos.size() != 0) {
      boolean abs = true;
      int vis;
      if (vdatos.get(1) == "public") vis = 1;
      else if (vdatos.get(1) == "private") vis = 2;
      else vis = 3;
      if (vdatos.get(2) == "true") abs = true;
      else abs = false;
      UMLClass nueva = new UMLClass( (String) vdatos.get(0), abs, vis);
      this.vClasses.add(nueva);
    }
  }

  /**
   * El método addAtb añade una atributo a una clase del ClassContainer.
   * @param vAtb Vector que contiene los datos del atributo que se quiere añadir.
   * @param nclas String que almacena el nombre de la clase a la que se le
   * añaden el atributo.
   */
  public void addAtb(Vector vAtb, String nclas) {
    //el vector tendra tamaño 5 si el atributo es de tipo vector
    if ( (vAtb.size() == 4) || (vAtb.size() == 5)) {
      String nombre = "";
      int vis;
      boolean sta = false;
      for (int i = 0; i < this.vClasses.size(); i++) {
        nombre = ( (UMLClass) vClasses.get(i)).getName();

        if (nombre.compareTo(nclas) == 0) {
          if (vAtb.get(2) == "public") vis = 1;
          else {
            if (vAtb.get(2) == "private") vis = 2;
            else vis = 3;
          }
          if (vAtb.get(3) == "true")
            sta = true;
          UMLAttribute a;
          if ( (vAtb.size() == 4))
            a = new UMLAttribute( (String) vAtb.get(0),
                                 (String) vAtb.get(1), vis, sta, "");
          else
            a = new UMLAttribute( (String) vAtb.get(0),
                                 (String) vAtb.get(1), vis, sta,
                                 (String) vAtb.get(4));
          ( (UMLClass) vClasses.get(i)).vAttributes.add(a);
        }
      }
    }
  }

  /**
   * El método addMethod añade un método a una clase del ClassContainer.
   * @param vMet Vector que contiene los datos del método que se quiere añadir.
   * @param nclas String que almacena el nombre de la clase a la que se le
   * añaden el método
   * @param vpar Vector que contiene todos los parámetros del método.
   */
  public void addMethod(Vector vMet, Vector vpar, String nclas) {
    if (vMet.size() == 6 || vMet.size() == 5) {
      String nombre = "";
      int vis;
      boolean sta = false;
      boolean abs = false;
      for (int i = 0; i < this.vClasses.size(); i++) {
        nombre = ( (UMLClass) vClasses.get(i)).getName();

        if (nombre == nclas) {

          if (vMet.get(2) == "public") vis = 1;
          else {
            if (vMet.get(2) == "private") vis = 2;
            else vis = 3;
          }
          if (vMet.get(3) == "true")
            sta = true;
          if (vMet.get(4) == "true")
            abs = true;

          UMLMethod a;
          if (vMet.size() == 6) //tiene parámetros
            a = new UMLMethod( (String) vMet.get(0),
                              (String) vMet.get(1), vis, sta, abs,
                              vpar);
          else
            a = new UMLMethod( (String) vMet.get(0),
                              (String) vMet.get(1), vis, sta, abs);

          ( (UMLClass) vClasses.get(i)).vOperations.add(a);
        }
      }
    }
  }

  /*****************************Eliminar**************************************/
  /**
   * El método deleteMember elimina una atributo o método de
   * una clase del ClassContainer.
   * @param obj String que almacena el nombre del miembro que se desea
   * eliminar.
   */
  public static void deleteMember(String obj) {
    String nombre = "";
    boolean esclase = false;
    boolean borrado = false;
    for (int i = 0; i < vClasses.size(); i++) {
      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //Eliminamos una clase
      if (nombre.compareTo( (String) obj) == 0) {
        vClasses.remove(i);
        esclase = true;
        borrado = true;
      }
    }

    if (!esclase) {
      if (!Controller.isMethod( (String) obj)) { //es atb
        for (int i = 0; i < vClasses.size(); i++) {
          if (!borrado) {
            String nombreatb = "";
            for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vAttributes.size();
                 j++) {

              nombreatb = ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).
                           vAttributes.get(j)).getName();

              if (nombreatb.compareTo( (String) obj) == 0) {
                ( (UMLClass) vClasses.get(i)).vAttributes.remove(j);
                borrado = true;
              }
            }
          }
        }
      }
      else { //es método
        String sinpar = "";
        //Nos quedamos solo con el nombre del método
        sinpar = Controller.getOnlyName( (String) obj);
        for (int i = 0; i < vClasses.size(); i++) {
          if (!borrado) {
            String nombremet = "";

            for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vOperations.size();
                 j++) {
              nombremet = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                           vOperations.get(j)).getName();
              if (nombremet.compareTo(sinpar) == 0) {
                ( (UMLClass) vClasses.get(i)).vOperations.remove(j);
                borrado = true;
              }
            }
          }
        }
      }
    }
  }

  /**
   * Método que obtiene el tipo de un atributo o un vector si este es nombre de
   * alguna clase del sistema sino devuelve la cadena vacía.
   * @param nAtb nombre del atributo a buscar
   * @return String tipo del vector o atributo si es una referencia o
   * un vector de referencias.
   */
  public String getType(String nAtb) {
    String tiporel = "";

    for (int i = 0; i < vClasses.size(); i++) {
      for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vAttributes.size();
           j++) {
        UMLAttribute at = (UMLAttribute) ( (UMLClass) vClasses.get(i)).
            vAttributes.get(j);
        if (at.getName().compareTo(nAtb) == 0) {
          tiporel = at.getType();
          if (tiporel.compareTo("Vector") == 0)
            tiporel = at.getVectorType();
        }
      }
    }

    //comprobamos que es un tipo de clase
    if (Controller.contenido.existsClass(tiporel))
      return tiporel;
    else
      return "";
  }

  /**
   * El método deleteAllClases  elimina todas las clases
   * del ClassContainer.
   */
  public void deleteAllClases() {
    this.vClasses.clear();
  }

  /****************************** Modificar *********************************/
  /**
   * El método modifyClass modifica una clase existente en el ClassContainer.
   * @param nomAnt String que almacena el nombre de la clase que se desea modificar.
   * @param vdatos Vector que contiene los datos de la clase que se quiere modificar.
   */
  public void modifyClass(String nomAnt, Vector vdatos) {

    for (int i = 0; i < this.vClasses.size(); i++) {
      if ( ( (UMLClass) vClasses.get(i)).getName() == nomAnt) {
        ( (UMLClass) vClasses.get(i)).name = (String) vdatos.get(0);
        if (vdatos.get(1) == "public")
          ( (UMLClass) vClasses.get(i)).setVisibility(1);
        else {
          if (vdatos.get(1) == "private")
            ( (UMLClass) vClasses.get(i)).setVisibility(2);
          else
            ( (UMLClass) vClasses.get(i)).setVisibility(3);
        }
        if ( ( (String) vdatos.get(2)).compareTo("true") == 0)
          ( (UMLClass) vClasses.get(i)).setAbstract(true);
        else
          ( (UMLClass) vClasses.get(i)).setAbstract(false);
      }
    }
  }

  /**
   * El método modifyAttribute modifica un atributo de una clase
   * existente en el ClassContainer.
   * @param nomAnt String que almacena el nombre del atributo que se desea modificar.
   * @param nomClass String que almacena el nombre de la clase que tiene ese atributo.
   * @param vdatos Vector que contiene los datos del atributo que se quiere modificar.
   */
  public void modifyAttribute(String nomAnt, String nomClass, Vector vdatos) {
    String nombre;
    for (int i = 0; i < vClasses.size(); i++) {
      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //buscamos la clase que tiene ese atributo
      if (nombre == nomClass) {
        for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vAttributes.size();
             j++) {
          String nombreatb;
          nombreatb = ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).
                       vAttributes.get(j)).getName();
          //buscamos el atributo
          if (nombreatb.compareTo(nomAnt) == 0) {

            ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(j)).
                setName( (String) vdatos.get(0));
            ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(j)).
                setType( (String) vdatos.get(1));
            if (vdatos.get(2) == "public")
              ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(
                  j)).
                  setVisibility(1);
            else {
              if (vdatos.get(2) == "private")
                ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.
                 get(
                    j)).setVisibility(2);
              else
                ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.
                 get(
                    j)).setVisibility(3);
            }
            if (vdatos.get(3) == "true")
              ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(
                  j)).
                  setStatic(true);
            else
              ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(
                  j)).
                  setStatic(false);

            ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(
                j)).
                setStatic(false);

            ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).vAttributes.get(j)).
                setVectorType( (String) vdatos.get(4));
          }
        }
      }
    }
  }

  /**
   * El método modifyOperation modifica un método de una clase
   * existente en el ClassContainer.
   * @param nomAnt String que almacena el nombre del método que se desea modificar.
   * @param nomClass String que almacena el nombre de la clase que tiene ese método.
   * @param vdatos Vector que contiene los datos del método que se quiere modificar.
   * @param vpar Vector que contiene los parametos a modificar del método.
   */
  public void modifyOperation(String nomAnt, String nomClass, Vector vdatos,
                              Vector vpar) {
    String nombre;
    for (int i = 0; i < vClasses.size(); i++) {
      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //buscamos la clase que tiene ese método
      if (nombre == nomClass) {
        for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vOperations.size();
             j++) {
          String nombreOper;
          nombreOper = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                        vOperations.get(j)).getName();
          //buscamos el método
          if (nombreOper.compareTo(nomAnt) == 0) {

            ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                setName( (String) vdatos.get(0));
            ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                setType( (String) vdatos.get(1));

            //visibilidad
            if (vdatos.get(2) == "public")
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  setVisibility(1);
            else {
              if (vdatos.get(2) == "private")
                ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(
                    j)).setVisibility(2);
              else
                ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(
                    j)).setVisibility(3);
            }
            //static
            if (vdatos.get(3) == "true")
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  setStatic(true);
            else
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  setStatic(false);
            //abstract
            if (vdatos.get(4) == "true")
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  setAbstract(true);
            else
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  setAbstract(false);
            //parámetros
            if (vpar.size() > 0) {
              ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                  parameters.removeAllElements();
              for (int s = 0; s < vpar.size(); s++) {
                UMLParameter par = (UMLParameter) vpar.get(s);
                ( (UMLMethod) ( (UMLClass) vClasses.get(i)).vOperations.get(j)).
                    parameters.add(par);
              }
            }
          }
        }
      }
    }
  }

  /***************************** Consultar **********************************/
  /**
   * El método consultClass Carga los datos de una clase en un vector.
   * @param nclass es un String que almacena el nombre de la clase.
   * @return Vector vector que contiene todos los elementos de una clase.
   */
  public Vector consultClass(String nclass) {
    Vector res = new Vector();
    for (int i = 0; i < this.vClasses.size(); i++) {
      UMLClass clase = ( (UMLClass) vClasses.get(i));
      if (clase.getName() == nclass) {
        res.addElement(clase.getName());
        res.addElement(clase.getVisibility());
        if (clase.isAbstract == true)
          res.add("true");
        else
          res.add("false");
      }
    }
    return res;
  }

  /**
   * El método existsClass Comprueba si ya existe el nombre para esa clase.
   * @param nClass es un String que almacena el nombre de la clase.
   * @return boolean valor verdadero si ya existe la clase false
   * en caso contrario.
   */
  public boolean existsClass(String nClass) {
    UMLClass clase = new UMLClass();
    for (int i = 0; i < this.vClasses.size(); i++) {
      clase = ( (UMLClass) vClasses.get(i));
      if (clase != null) {
        if (clase.getName().compareTo(nClass) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * El método consultAttribute Carga los datos de un Atributo en
   * un vector.
   * @param nomClass es un String que almacena el nombre de la clase que tiene
   * ese atributo.
   * @param nomAtb es un String que almacena el nombre del atributo a consultar.
   * @return Vector vector que contiene todos los elementos de un atributo.
   */
  public Vector consultAttribute(String nomAtb, String nomClass) {

    Vector res = new Vector();
    String nombre;
    for (int i = 0; i < this.vClasses.size(); i++) {

      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //buscamos la clase que tiene ese atributo
      if (nombre == nomClass) {
        for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vAttributes.size();
             j++) {
          String nombreatb;
          nombreatb = ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).
                       vAttributes.get(j)).getName();

          if (nombreatb.compareTo(nomAtb) == 0) {
            UMLAttribute atb = ( (UMLAttribute) ( (UMLClass) vClasses.get(i)).
                                vAttributes.get(j));

            res.add(atb.getName());
            res.add(atb.getType());
            res.add(atb.getVisibility());
            if (atb.getStatic() == true)
              res.add("true");
            else
              res.add("false");

            if (atb.getType().compareTo("Vector") == 0)
              res.add(atb.getVectorType());
            else
              res.add("");

          }
        }
      }
    }
    System.out.println(res.toString());
    return res;
  }

  /**
   * El método existsAtb Comprueba si ya existe el nombre de atributo
   * para esa clase.
   * @param nomClass es un String que almacena el nombre de la clase.
   * @param nomAtb es un String que almacena el nombre del atributo de la clase.
   * @return boolean valor verdadero si ya existe ese atributo para esa clase
   * false en caso contrario.
   */
  public boolean existsAtb(String nomClass, String nomAtb) {

    for (int i = 0; i < this.vClasses.size(); i++) {
      UMLClass c = ( (UMLClass)this.vClasses.get(i));
      if (c.getName().compareTo(nomClass) == 0) {
        System.out.println(c.getName());
        for (int j = 0; j < c.vAttributes.size(); j++) {
          UMLAttribute at = ( (UMLAttribute) c.vAttributes.get(j));
          if (at.getName().compareTo(nomAtb) == 0)
            return true;
        }
      }
    }
    return false;
  }

  /**
   * El método consultOperation Carga los datos de un Método en
   * un vector.
   * @param nomClass es un String que almacena el nombre de la clase que tiene
   * ese método.
   * @param nomMet es un String que almacena el nombre del método a consultar.
   * @return Vector vector que contiene todos los elementos de un método.
   */
  public Vector consultOperation(String nomMet, String nomClass) {
    Vector res = new Vector();
    String nombre;
    for (int i = 0; i < this.vClasses.size(); i++) {

      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //buscamos la clase que tiene ese método
      if (nombre == nomClass) {
        for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vOperations.size();
             j++) {
          String nombremet;
          nombremet = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                       vOperations.get(j)).getName();

          if (nombremet.compareTo(nomMet) == 0) {
            UMLMethod oper = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                              vOperations.get(j));

            res.add(oper.getName());
            res.add(oper.getType());
            res.add(oper.getVisibility());
            if (oper.getStatic() == true)
              res.add("true");
            else
              res.add("false");

            if (oper.getAbstract() == true)
              res.add("true");
            else
              res.add("false");
            if (oper.getParameters().compareTo("") != 0)
              res.add(oper.getParameters());
          }
        }
      }
    }
    return res;
  }

  /**
   * El método consultParameters Carga los parámetros de un método
   * en un vector.
   * @param nomMet String nombre del método del cual se consultan los parámetros
   * @param nomClass String nombre de la clase que contiene el método.
   * @return Vector contenedor de UMLParameters.
   */
  public Vector consultParameters(String nomMet, String nomClass) {
    Vector res = new Vector();
    String nombre;
    for (int i = 0; i < this.vClasses.size(); i++) {

      nombre = ( (UMLClass) vClasses.get(i)).getName();
      //buscamos la clase que tiene ese método
      if (nombre == nomClass) {
        for (int j = 0; j < ( (UMLClass) vClasses.get(i)).vOperations.size();
             j++) {
          String nombremet;
          nombremet = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                       vOperations.get(j)).getName();

          if (nombremet.compareTo(nomMet) == 0) {
            UMLMethod oper = ( (UMLMethod) ( (UMLClass) vClasses.get(i)).
                              vOperations.get(j));

            for (int k = 0; k < oper.parameters.size(); k++) {
              UMLParameter par = (UMLParameter) oper.parameters.get(k);
              res.add(par);
            }
          }
        }
      }
    }
    return res;
  }

  /**
   * Descomprime un fichero
   * @param fichero String ruta del fichero comprimido.
   */
  public static void unzip(String fichero) {
    int BUFFER = 2048;
    try {
      BufferedOutputStream dest = null;
      System.out.println("comprimido: " + fichero);
      FileInputStream fis = new FileInputStream(fichero);
      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
      ZipEntry entry;
      while ( (entry = zis.getNextEntry()) != null) {
        System.out.println("Extracting: " + entry);
        int count;
        byte data[] = new byte[BUFFER];
        // Escribir los archivos en el disco
        FileOutputStream fos = new
            FileOutputStream(entry.getName());
        dest = new
            BufferedOutputStream(fos, BUFFER);
        while ( (count = zis.read(data, 0, BUFFER))
               != -1) {
          dest.write(data, 0, count);
        }
        dest.flush();
        dest.close();
      }
      zis.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Comprime un fichero.
   * @param filenames Vector que contiene los nombres del fichero a
   * comprimir.
   * @param ruta String que almacena el path donde se creará el zip.
   * @param nombreZip String que almacena el nombre del archivo zip.
   */
  public void zip(Vector filenames, String ruta, String nombreZip) {
    byte[] buf = new byte[1024];

    try {
      // Creamos el fichero principal ZIP
      String outFilename = ruta + File.separator + nombreZip + ".zip";
      ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
          outFilename));

      for (int i = 0; i < filenames.size(); i++) {
        System.out.println( (String) filenames.get(i).toString());
        FileInputStream in = new FileInputStream( (String) filenames.get(i).
                                                 toString());

        //Añadimos la entrada nueva al zip
        out.putNextEntry(new ZipEntry(filenames.get(i).toString()));

        //transferimos los bytes del fichero al zip
        int len;
        while ( (len = in.read(buf)) > 0) {
          out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
      }
      out.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Método que guarda el proyecto creado por la aplicación.
   * @param ruta String path en el que se almacena.
   * @param nombre String nombre que se le da al proyecto.
   */
  public void save(String ruta, String nombre) {

    //Creamos los ficheros a comprimir
    //fichero raiz
    String fraiz = createFileXMLProject(nombre);
    //Archivo manifest
    File temp = new File("./temp");
    if (!temp.exists())
      temp.mkdir();
    File fmanifest = new File("./temp/MANIFEST.txt");
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter(fmanifest));
      bw.write(nombre);
      bw.close();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    //ficheros hijos
    Vector vtodosFich = createXMLFile();
    vtodosFich.add(fraiz);
    vtodosFich.add(fmanifest.getAbsolutePath().toString());

    //Comprimimos todos los ficheros
    zip(vtodosFich, ruta, nombre);

    //borramos los ficheros descomprimidos
    if (temp.isDirectory()) {
      File[] ficheros = temp.listFiles();
      for (int i = 0; i < ficheros.length; i++) {
        // System.out.println("borrando..." + ( (File) ficheros[i]).getName());
        ( (File) ficheros[i]).delete();
      }
    }
  }

  /**
   * El método createXMLFile Crea los ficheros del proyecto
   * @return Vector que contiene los archivos xml.
   */
  public Vector createXMLFile() {
    Vector vFiles = new Vector();
    for (int i = 0; i < this.vClasses.size(); i++) {
      UMLClass tempClass = ( (UMLClass)this.vClasses.get(i));
      File fich = new File("./temp/"+  tempClass.getName() + ".xml");
      try {
        if (fich.exists()) fich.delete();
        if (!fich.exists()) {
          // A partir del objeto File creamos el fichero físicamente
          if (!fich.createNewFile())
            System.out.println("No ha podido ser creado el fichero");
          else {
            System.out.println("Creando el fichero:" +
                               fich.getAbsolutePath().toString());
            ( (UMLClass)this.vClasses.get(i)).writeClassXML(fich);
          }
        }
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
      vFiles.add(fich.getAbsolutePath());
    }
    return vFiles;
  }

  /**
   * El método createXMLProyect Crea el fichero raiz del proyecto
   * @param nomfich es un String que almacena el nombre del fichero raíz del
   * proyecto.
   * @return String devuelve el nombre del fichero creado.
   */
  public String createFileXMLProject(String nomfich) {
    File fich;
    fich = new File("./temp/" + nomfich +
                    ".xml");

    try {
      if (fich.exists()) fich.delete();
      if (!fich.exists()) {
        // A partir del objeto File creamos el fichero físicamente
        if (!fich.createNewFile())
          System.out.println("No ha podido ser creado el fichero");
        else {
          BufferedWriter bw = new BufferedWriter(new FileWriter(fich));

          bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
          bw.write("<project>\n");
          for (int i = 0; i < this.vClasses.size(); i++) {
            UMLClass tempClass = ( (UMLClass)this.vClasses.get(i));
            bw.write("   <file>" + tempClass.name + ".xml" +
                     "</file>\n");
          }
          bw.write("</project>");
          bw.close();
        }
      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return (fich.getAbsolutePath());
  }
}

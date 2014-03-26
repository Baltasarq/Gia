package Model;

import java.io.*;
import java.util.*;
import View.MainWindow;
/**
 * Clase que se encarga de generar el código relativo a todas las clases del
 * sistema.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class CodeGenerator {
  private String pathCode;
  /**
   * Constructor de clase.
   * @param cc ClassContainer contenedor de todas las clases del sistema
   * @param ruta String path en el que se alacenará el código generado.
   * @throws IOException lanza una excepción si no se puede generar el código.
   */
  public CodeGenerator(ClassContainer cc, String ruta) throws IOException {
    File dir = new File(ruta);
    dir.mkdir();
    setPathCode(dir.getCanonicalPath());
    generateAllClasses(cc);
  }
  /**
   * Método que cambia la ruta en la que se almacenará el código generado.
   * @param path String nueva ruta en la que se almacenara el código.
   */
  public void setPathCode(String path) {
    pathCode = path;
  }

  /**
   * Método que obtiene la ruta en la que se almacenará el código generado.
   * @return String ruta donde se situará el código.
   */
  public String getPathCode() {
    return pathCode;
  }

  /**
   * El método generateAllClasses crea un archivo .java por cada
   * clase que contendrá el código relativo a todas las clases.
   * @param cc es un ClassContainer que contiene la información relativa a
   * todas las clases.
   */
  public void generateAllClasses(ClassContainer cc) {

    for (int i = 0; i < cc.vClasses.size(); i++) {

      File dir = new File(this.pathCode);
      dir.mkdir();

      String nomClase = ( (UMLClass) cc.vClasses.get(i)).getName();
      File fich = new File(this.pathCode+ File.separator + nomClase + ".java");
      try {
        if (fich.exists()) fich.delete();
        if (!fich.exists()) {
          // A partir del objeto File creamos el fichero físicamente
          if (!fich.createNewFile())
            System.out.println("No ha podido crearse el fichero");
          else {
            ( (UMLClass) cc.vClasses.get(i)).writeClass(fich);
          }
        }
      }
      catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

  /**
   * Copia un solo archivo
   * @param src File directorio origen.
   * @param dst File directorio destino.
   * @throws IOException lanza una excepción sino se puede copiar con éxito el
   * archivo.
   */
  public static void copy(File src, File dst) throws IOException {
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);

    byte[] buf = new byte[1024];
    int len;
    while ( (len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
    in.close();
    out.close();
  }

  /**
   * Copia un directorio con todo y su contenido
   * @param srcDir directorio origen
   * @param dstDir directorio destino
   * @throws IOException
   */
  public void copyDirectory(File srcDir, File dstDir) throws IOException {
    if (srcDir.isDirectory()) {
      if (!dstDir.exists()) {
        dstDir.mkdir();
      }
      String[] children = srcDir.list();
      for (int i = 0; i < children.length; i++) {
        copyDirectory(new File(srcDir, children[i]),
                      new File(dstDir, children[i]));
      }
    }
    else {
      copy(srcDir, dstDir);
    }
  }

  /**
   * Método que genera todas las clases de la interfaz del sistema y las añade
   * al árbol de clases generadas.
   */
  public void generateGUI() {
    File dirVista = new File(this.pathCode);
    File dirImagenes = new File(this.pathCode + File.separator+ "Imagenes");
    dirVista.mkdir();
    dirImagenes.mkdir();

    try {
      copyDirectory(new File("."+File.separator+"Images"+File.separator), dirImagenes);
      copy(new File("."+File.separator+"templates"+File.separator+"CargarVentanaPrincipal.java"),
           new File(this.pathCode + File.separator+"CargarVentanaPrincipal.java"));
      copy(new File("."+File.separator+"templates"+File.separator+"Elemento.java"),
           new File(this.pathCode +File.separator+"Elemento.java"));
      copy(new File("."+File.separator+"templates"+File.separator+"VentanaAgregar.java"),
           new File(this.pathCode + File.separator+"VentanaAgregar.java"));
      copy(new File("."+File.separator+"templates"+File.separator+"VentanaGestion.java"),
           new File(this.pathCode +File.separator+"VentanaGestion.java"));
      copy(new File("."+File.separator+"templates"+File.separator+"VentanaModificar.java"),
           new File(this.pathCode +File.separator+"VentanaModificar.java"));
    }
    catch (IOException ex) {
    }
    //creamos VentanaPrincipal.java
    File fichPrincipal = new File(this.pathCode +File.separator+"VentanaPrincipal.java");
    try {
      if (fichPrincipal.exists()) fichPrincipal.delete();
      if (!fichPrincipal.exists()) {
        if (!fichPrincipal.createNewFile())
          System.out.println("No ha podido crearse  el fichero VentanaPrincipal.java");
        else {
          BufferedWriter bw = new BufferedWriter(new FileWriter(fichPrincipal));
          loadMainTemplate(bw);
          bw.close();
        }
      }
    }
    catch (IOException ioe) {
      ioe.printStackTrace();
    }
 }

  /**
   * Copia el código básico(plantilla) de la ventana principal en un archivo
   * @param bw Buffer de escritura en fichero.
   */
  public void loadMainTemplate(BufferedWriter bw) {
    String s;
    FileReader fr = null;
    try {
      fr = new FileReader("."+File.separator+"templates"+File.separator+"CabeceraVentanaPrincipal.java");
      BufferedReader bf = new BufferedReader(fr);
      while ( (s = bf.readLine()) != null)
        bw.write(s + "\n");
      bw.write("  "+Controller.mainClass +" cPrincipal = new "+Controller.mainClass+"();\n");
      String path= MainWindow.javaPath;
      path = path.replace('\\','/');
      bw.write(" static String ruta = \""+ path +"\";");

      fr = new FileReader("."+File.separator+"templates"+File.separator+"MetodosVentanaPrincipal.java");
      BufferedReader bf1 = new BufferedReader(fr);
      while ( (s = bf1.readLine()) != null)
         bw.write(s + "\n");
    }
    catch (FileNotFoundException ex) {}
    catch (IOException ex1) {}
  }
}

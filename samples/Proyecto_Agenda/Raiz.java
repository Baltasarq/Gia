import java.util.Vector;
import javax.xml.parsers.*;

/**
 * Representa el nivel de abstracción de cualquier clase generada
 * mediante el generador integral de aplicaciones.
 * @author Digna Rodriguez Cudeiro
 * @version 1.0
 */
public interface Raiz {

  public abstract String getClassName();
  public abstract Vector getvAtb();
  public abstract Vector leerXML();
  public abstract void crearXML(Vector vObj) throws
      FactoryConfigurationError, ParserConfigurationException;
  public abstract Vector buscarenXML(String busqueda);
  public abstract Raiz getElementoRelacionado(String n);
  public abstract Vector getRelaciones1a1();
  public abstract Vector getRelaciones1an();
}

package Model;
/**
 * Clase que representa los tipos de visibilidad que se pueden asociar a las
 * clases y a sus miembros en java.
 * @author Digna Rodr�guez.
 * @version 1.0
 */
public class EnumVisibility {
  // constructor privado, as� la clase no puede ser instanciable
  private EnumVisibility() {}
  public static final int mPublic = 1;
  public static final int mPrivate = 2;
  public static final int mProtected = 3;

}

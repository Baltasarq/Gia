import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.FactoryConfigurationError;
import java.util.Date;

/**
 * Clase que implementa la ventana Principal de la aplicación.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class VentanaPrincipal
    extends JFrame
    implements ActionListener {

  //vector que almacena la clase una vez modificada
  Vector vClaseModificada = new Vector();
  //vector que contiene elementos de los atributos de la clase principal
  Vector vfilas = new Vector();
  Raiz cLista;
  Date d = new Date();
  String enlace = "";
  String consultar = "";
  boolean alta = false;
  boolean guardado = false;
  boolean ok = false;
  boolean cancel = false;

  //paneles
  JPanel panelTabla = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelBotLateral = new JPanel();
  JPanel panelContenedor = new JPanel();
  JPanel PanelIzquierda = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel pLateral = new JPanel();
  //botones
  JButton botonGuardar = new JButton();
  JButton botonCancelar = new JButton();
  //menu
  JMenuBar menuSuperior = new JMenuBar();
  JMenu menuArchivo = new JMenu();
  JMenu menuEditar = new JMenu();
  JMenuItem menuGuardar = new JMenuItem();
  JMenuItem menuSalir = new JMenuItem();
  //layouts
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  BorderLayout borderLayout2 = new BorderLayout();
  Agenda cPrincipal = new Agenda();
 static String ruta = "C:/GiaJava/Proyecto_Agenda/";
  /**
   * Constructor de la ventana Principal
   */
  public VentanaPrincipal() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {

    this.setTitle("Gestión de " + cPrincipal.getClassName());
    this.setSize(new Dimension(507, 373));
    this.getContentPane().setLayout(gridLayout1);
    panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));

    ImageIcon img = new ImageIcon(VentanaPrincipal.class.getResource(
        "." + File.separator + "Imagenes" + File.separator + "base.png"));
    ImageIcon cerrar = new ImageIcon(VentanaPrincipal.class.getResource(
        "." + File.separator + "Imagenes" + File.separator + "salir.png"));
    ImageIcon guardar = new ImageIcon(VentanaPrincipal.class.getResource(
        "." + File.separator + "Imagenes" + File.separator + "guardar.png"));

    botonGuardar.setText("Guardar Cambios");
    botonCancelar.setText("  Salir");
    botonGuardar.setIcon(guardar);
    botonCancelar.setIcon(cerrar);

    panelContenedor.setLayout(null);
    panelBotones.setLayout(flowLayout1);
    panelBotLateral.setLayout(new BoxLayout(panelBotLateral, BoxLayout.Y_AXIS));
    jScrollPane1.setBorder(null);
    PanelIzquierda.setBorder(BorderFactory.createEtchedBorder());
    PanelIzquierda.setDebugGraphicsOptions(0);
    PanelIzquierda.setBounds(new Rectangle(13, 13, 289, 233));
    PanelIzquierda.setLayout(borderLayout1);
    panelBotones.setBounds(new Rectangle(16, 266, 280, 43));
    pLateral.setDebugGraphicsOptions(0);
    pLateral.setBounds(new Rectangle(317, 10, 172, 302));
    pLateral.setLayout(borderLayout2);
    panelBotLateral.setBorder(null);
    panelBotLateral.setDebugGraphicsOptions(0);
    jScrollPane2.setBorder(null);
    panelBotones.add(botonGuardar, null);
    panelBotones.add(botonCancelar, null);
    panelContenedor.add(pLateral, null);
    pLateral.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(panelBotLateral, null);
    panelContenedor.add(PanelIzquierda, null);
    PanelIzquierda.add(jScrollPane1, BorderLayout.CENTER);
    panelContenedor.add(panelBotones, null);
    jScrollPane1.getViewport().add(panelTabla, null);
    botonGuardar.addActionListener(this);
    botonCancelar.addActionListener(this);
    menuArchivo.setText("Archivo");
    menuEditar.setText("Editar");
    menuGuardar.setText("Guardar datos");
    menuSalir.setText("Salir");
    menuArchivo.add(menuGuardar);
    menuArchivo.add(menuSalir);
    menuSuperior.add(menuArchivo);
    menuSuperior.add(menuEditar);
    menuArchivo.add(menuSalir);
    this.setJMenuBar(menuSuperior);
    menuGuardar.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        guardar(e);
      }
    }
    );

    menuSalir.addActionListener(
        new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        salir(e);
      }
    }
    );

    Vector vAtb = new Vector();
    Vector vDatos = new Vector();
    File dir = new File(ruta + File.separator+"xml" + File.separator);
    if (!dir.exists()) dir.mkdirs();
    File fich1 = new File(ruta +File.separator+ "xml" + File.separator +
                          cPrincipal.getClassName() + ".xml");
    if (!fich1.exists()) {
      try {
        cPrincipal.crearXML(vDatos);
        alta = true;
      }
      catch (ParserConfigurationException ex) {
      }
      catch (FactoryConfigurationError ex) {
      }
    }
    /*Creamos un vector vfilas de elementos,con los datos  y los atb de la clase
      almacenados en el xml*/
    vAtb = cPrincipal.getvAtb();
    Vector vEncontrado = (Vector) cPrincipal.leerXML();

    if (vEncontrado.size() > 0) {
      vDatos = (Vector) cPrincipal.leerXML().get(0);
      if (vDatos.size() > 0) {
        this.enlace = vDatos.get(1).toString();

        for (int i = 1; i < vAtb.size(); i++) {
          Elemento el;
          if (! (Gestion.esVectorObjetos(vAtb.get(i).toString())) &&
              ! (Gestion.esObjeto( (vAtb.get(i).toString()))))
            el = new Elemento(vAtb.get(i).toString(),
                              vDatos.get(i + 1).toString());
          else
            el = new Elemento(vAtb.get(i).toString(), "");
          vfilas.add(el);
        }
      }
    }
    //visualizamos los elementos del vector vfilas en el panelTabla
    panelTabla.removeAll();
    for (int i = 0; i < this.vfilas.size(); i++) {
      if ( (!Gestion.esVectorObjetos( ( (Elemento) vfilas.get(i)).etiqueta.
                                     getText())) &&
          (!Gestion.esObjeto( ( (Elemento) vfilas.get(i)).etiqueta.getText()))) {
        panelTabla.add( (Elemento) vfilas.get(i), null);

      } //si es un vector o una referencia
      else {
        String elementoactual = ( (Elemento) vfilas.get(i)).etiqueta.getText();
        if (Gestion.esObjeto(elementoactual)) {
          consultar = elementoactual.substring(3, elementoactual.length());
          cPrincipal.getRelaciones1a1();
          final JMenuItem menuConsultar = new JMenuItem();
          menuConsultar.setText("Consultar " + consultar);
          menuEditar.add(menuConsultar);
          menuConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String s = menuConsultar.getText().substring(10,
                  menuConsultar.getText().length() - 1);
              Raiz r = cPrincipal.getElementoRelacionado(consultar);
              if (r != null)
                menuConsultar(e, r, enlace);
            }
          }
          );

          final JButton botonConsult = new JButton();
          botonConsult.setIcon(img);
          botonConsult.setText("Consultar " + consultar);
          botonConsult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String s = botonConsult.getText().substring(10,
                  botonConsult.getText().length() - 1);
              Raiz r = cPrincipal.getElementoRelacionado(consultar);
              if (r != null)
                menuConsultar(e, r, enlace);
            }
          }
          );
          panelBotLateral.add(botonConsult, null);
        }
        else {
          consultar = elementoactual.substring(3, elementoactual.length() - 1);
          final JButton botonGes = new JButton();
          botonGes.setIcon(img);
          botonGes.setText("Gestionar " + consultar + Gestion.getPlural(consultar));
          cPrincipal.getRelaciones1an();
          final JMenuItem menuGestionar = new JMenuItem();
          menuGestionar.setText("Gestionar " + consultar + Gestion.getPlural(consultar));
          menuEditar.add(menuGestionar);

          botonGes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String s = botonGes.getText().substring(10,
                  botonGes.getText().length() - 1);
              Raiz r = cPrincipal.getElementoRelacionado(s);
              if (r != null)
                menuGestionar(e, r);
            }
          }
          );
          menuGestionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              String s = menuGestionar.getText().substring(10,
                  menuGestionar.getText().length() - 1);
              Raiz r = cPrincipal.getElementoRelacionado(s);
              if (r != null)
                menuGestionar(e, r);
            }
          }
          );

          panelBotLateral.add(botonGes, null);
        }
      }
    }
    this.getContentPane().add(panelContenedor, null);
    this.panelTabla.updateUI();
    this.panelTabla.revalidate();
    panelContenedor.revalidate();
    panelContenedor.updateUI();
  }

  /**
   * método principal de la aplicación, punto de entrada de la aplicación.
   * @param args String[]
   */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (UnsupportedLookAndFeelException ex1) {}
    catch (IllegalAccessException ex1) {}
    catch (InstantiationException ex1) {}
    catch (ClassNotFoundException ex1) {}
    new CargarVentanaPrincipal();
  }

  /**
   * Método que instancia una nueva ventana para consultar/modificar un objeto
   * relacionado con la clase principal.
   * @param e ActionEvent evento al que responde el método.
   * @param x Raiz clase consultada.
   * @param enlace String que almacena el identificador que relaciona la clase
   * principal con la consultada.
   */
  public void menuConsultar(ActionEvent e, Raiz x, String enlace) {
    Vector vAtb = new Vector();
    Vector vDatos = new Vector();
    Vector ve = new Vector();
    if ( (alta == true && this.guardado == false) ||
        ( (alta == false) && (this.registroCompleto() == false))) {
      JOptionPane.showMessageDialog(this,
                                    "Antes de pasar a la edición de la siguiente ventana debe de rellenar y guardar los datos de esta",
                                    "Mensaje informativo",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      /*si no se ha hecho una alta ni una modificación de los datos de
       la clase principal el enlace se calcula de la siguiente forma*/
      Component vel[] = panelTabla.getComponents();
      this.enlace = ( (Elemento) vel[0]).texto.getText();
      vAtb = x.getvAtb();
      Vector vEncontrado = (Vector) x.buscarenXML(this.enlace);
      if (vEncontrado.size() > 0) {
        vDatos = (Vector) x.buscarenXML(this.enlace).get(0);

        if (vDatos.size() > 0) {
          for (int i = 0; i < vAtb.size(); i++) {
            Elemento el;
            if (! (Gestion.esVectorObjetos(vAtb.get(i).toString())) &&
                ! (Gestion.esObjeto( (vAtb.get(i).toString()))))
              el = new Elemento(vAtb.get(i).toString(),
                                vDatos.get(i + 1).toString());
            else
              el = new Elemento(vAtb.get(i).toString(), "");
            ve.add(el);
          }

          VentanaModificar v = new VentanaModificar(ve, x);
          v.setSize(600, 300);
          Dimension dlgSize = v.getPreferredSize();
          Dimension frmSize = getSize();
          Point loc = getLocation();
          v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
          v.setModal(true);
          v.pack();
          v.show();
        }
      }
      else { // sino existe lo creamos (abriendo uno vacío)
        for (int i = 0; i < vAtb.size(); i++) {
          Elemento el;
          if (! (Gestion.esVectorObjetos(vAtb.get(i).toString())) &&
              ! (Gestion.esObjeto( (vAtb.get(i).toString()))))
            el = new Elemento(vAtb.get(i).toString(), "");
          else
            el = new Elemento(vAtb.get(i + 1).toString(), "");
          ve.add(el);
        }
        VentanaAgregar v = new VentanaAgregar(ve, x);
        v.setSize(600, 300);
        Dimension dlgSize = v.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                      (frmSize.height - dlgSize.height) / 2 + loc.y);
        v.setModal(true);
        v.pack();
        v.show();
        if (v.ok) {
          Gestion.alta(v.vNuevaClase, x, enlace);
        }
      }
    }
  }

  /**
   * Método que instancia una nueva ventana para Gestionar una lista de objetos
   * relacionado con la clase principal.
   * @param e ActionEvent evento al que responde el método.
   * @param claseL Raiz clase cuyos elementos se gestionarán.
   */
  public void menuGestionar(ActionEvent e, Raiz claseL) {

    if ( (alta == true && this.guardado == false) ||
        ( (alta == false) && (this.registroCompleto() == false))) {
      JOptionPane.showMessageDialog(this,
                                    "Antes de pasar a la edición de la siguiente ventana debe de rellenar y guardar los datos de esta",
                                    "Mensaje informativo",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      VentanaGestion v = new VentanaGestion(cPrincipal, claseL, this.enlace);
      v.setSize(300, 300);
      Dimension dlgSize = v.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
      v.setModal(true);
      v.pack();
      v.show();
    }
  }

  /**
   * método que cierra la ventana para poder salir.
   * @param e WindowEvent evento al que responde el método
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel = true;
      dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que comprueba si se han completado todos los atributos de la clase.
   * @return boolean true si se han completado todos los campos, false en caso
   * contrario.
   */
  public boolean registroCompleto() {
    boolean res = true;
    Component ve[] = panelTabla.getComponents();
    for (int i = 0; i < ve.length; i++) {
      String dato = ( (Elemento) ve[i]).texto.getText();
      if (dato.compareTo("") == 0)
        res = false;
    }
    return res;
  }

  /**
   * Método que cierra la ventana principal.
   * @param e ActionEvent
   */
  public void salir(ActionEvent e) {
    ok = false;
    cancel = true;
    this.dispose();
  }

  /**
   * Método que guarda los datos de la clase principal.
   * @param e ActionEvent evento al que responde el método.
   */
  public void guardar(ActionEvent e) {
    if (this.registroCompleto()) {
      Component v[] = panelTabla.getComponents();
      vClaseModificada.clear();
      vClaseModificada.add(this.enlace);
      for (int i = 0; i < v.length; i++) {
        String dato = ( (Elemento) v[i]).texto.getText();
        this.vClaseModificada.add(dato);
      }
      if (this.alta = true)
        this.vfilas = Gestion.altaPrincipal(vClaseModificada, cPrincipal);
      else
        this.vfilas = Gestion.modificacion(vClaseModificada, cPrincipal,
                                           this.enlace);
      this.guardado = true;
      JOptionPane.showMessageDialog(this,
                                    "Se han guardado los datos.",
                                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }
    else
      JOptionPane.showMessageDialog(this,
                                    "Debes rellenar todos los campos.",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
  }

  /**
   * Método en el que se encuentran las acciones a realizar en respuesta a
   * un evento desde un campo de texto o un botón
   * @param e ActionEvent evento que dispara el método.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar)
      salir(e);

    else if (e.getSource() == botonGuardar)
      guardar(e);
  }
}

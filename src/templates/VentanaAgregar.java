import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Clase que implementa la ventana que permite añadir un objeto
 * a una clase del proyecto.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class VentanaAgregar
    extends JDialog
    implements ActionListener {
  boolean ok = false;
  boolean cancel = false;
  String enlace = "";
  Raiz cPrincipal;
  Vector vNuevaClase = new Vector();
  Vector vfilas = new Vector();
  String consultar = "";

  //paneles
  JPanel panelTabla = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelContenedor = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  //botones
  JButton botonAceptar = new JButton();
  JButton botonCancelar = new JButton();
  //layouts
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();

  /**
   * Constructor de la clase. En el se inicializan los elementos de la ventana.
   * @param vdatos Vector que contiene los datos de la clase.
   * @param x Raiz clase a la que se agrega el objeto.
   */
  public VentanaAgregar(Vector vdatos, Raiz x) {
    cPrincipal = x;
    Date d = new Date();
    String identGenerado = cPrincipal.toString().substring(0, 2) + "_" +
        d.getTime();
    this.enlace = identGenerado;
    vfilas.add(identGenerado);

    for (int i = 1; i < vdatos.size(); i++)
      vfilas.add(vdatos.get(i));
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * El método jbInit se encarga de la inicialización de componentes
   * de la interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    this.setTitle("Añadir " + cPrincipal.getClassName());
    this.getContentPane().setLayout(borderLayout2);
    panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
    panelTabla.setBorder(BorderFactory.createEtchedBorder());
    panelContenedor.setLayout(borderLayout1);
    panelContenedor.add(jScrollPane1, BorderLayout.CENTER);
    panelContenedor.add(panelBotones, BorderLayout.SOUTH);
    jScrollPane1.getViewport().add(panelTabla, null);
    botonAceptar.setText("Aceptar");
    botonCancelar.setText("Cancelar");
    botonAceptar.addActionListener(this);
    botonCancelar.addActionListener(this);
    panelBotones.setLayout(flowLayout1);
    panelBotones.add(botonAceptar, null);
    panelBotones.add(botonCancelar, null);
    panelTabla.removeAll();

    for (int i = 1; i < this.vfilas.size(); i++) {
      if ( (!Gestion.esVectorObjetos( ( (Elemento) vfilas.get(i)).etiqueta.
                                     getText())) &&
          (!Gestion.esObjeto( ( (Elemento) vfilas.get(i)).etiqueta.getText()))) {
        panelTabla.add( (Elemento) vfilas.get(i), null);
      }
      else {
        String elementoactual = ( (Elemento) vfilas.get(i)).etiqueta.getText();
        if (Gestion.esObjeto(elementoactual)) {
          consultar = elementoactual.substring(3, elementoactual.length());
          cPrincipal.getRelaciones1a1();
          JButton botonAlta = new JButton();
          this.botonAceptar.setVisible(false);
          botonAlta.setText("Crear " + consultar);
          botonAlta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Raiz r = cPrincipal.getElementoRelacionado(consultar);
              if (r != null)
                menuAlta(e, r);
            }
          }
          );
          panelBotones.add(botonAlta, null);
        }
        else {
          consultar = elementoactual.substring(3, elementoactual.length() - 1);
          JButton botonConsultar = new JButton();
          botonConsultar.setText("Gestionar " + consultar + Gestion.getPlural(consultar));
          cPrincipal.getRelaciones1an();
          botonConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Raiz r = cPrincipal.getElementoRelacionado(consultar);
              if (r != null)
                menuGestionar(e, cPrincipal, r);
            }
          }
          );
          panelBotones.add(botonConsultar, null);
        }
      }
    }
    this.getContentPane().add(panelContenedor, BorderLayout.CENTER);
    this.panelTabla.updateUI();
    this.panelTabla.revalidate();
    panelContenedor.revalidate();
    panelContenedor.updateUI();
  }

  /**
   * Metodo que guarda el contenido de la ventana actual y abre una nueva
   * ventana de gestion de una clase relacionada con la de la ventana actual.
   * @param e ActionEvent evento al que responde el método.
   * @param cPrincipal Raiz clase inicial de la relación.
   * @param cLista Raiz clase final de la relación.
   */
  public void menuGestionar(ActionEvent e, Raiz cPrincipal, Raiz cLista) {
    //primero guardamos lo que hay en la ventana principal
    if (this.registroCompleto()) {
      Component v[] = panelTabla.getComponents();
      vNuevaClase.clear();
      vNuevaClase.add(this.enlace);
      for (int i = 0; i < v.length; i++) {
        String dato = ( (Elemento) v[i]).texto.getText();
        vNuevaClase.add(dato);
      }
      ok = true;
      this.dispose();
    }
    else
      JOptionPane.showMessageDialog(this,
                                    "Debes rellenar todos los campos.",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
      /*Abrimos una nueva ventana que contienen los atributos de la clase
           relacionada*/
    VentanaGestion v = new VentanaGestion(cPrincipal, cLista,
                                          this.enlace);
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

  /**
   * Método que cierra la ventana actual.
   * @param e WindowEvent evento al que responde.
   */
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel = true;
      dispose();
    }
    super.processWindowEvent(e);
  }

  /**
   * Método que guarda el contenido de la ventana actual y abre una nueva
   * ventana para añadir datos de una clase relacionada con la de la ventana
   * actual.
   * @param e ActionEvent evento al que responde el método.
   * @param cPrincipal Raiz clase de la que vamos a añadir elementos.
   */
  public void menuAlta(ActionEvent e, Raiz cPrincipal) {
    //primero guardamos lo que hay en la ventana principal
    if (this.registroCompleto()) {
      Component v[] = panelTabla.getComponents();
      vNuevaClase.clear();
      vNuevaClase.add(this.enlace);
      for (int i = 0; i < v.length; i++) {
        String dato = ( (Elemento) v[i]).texto.getText();
        vNuevaClase.add(dato);
      }
      ok = true;
      this.dispose();
    }
    else
      JOptionPane.showMessageDialog(this,
                                    "Debes rellenar todos los campos.",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
      /*Abrimos una nueva ventana que contienen los atributos de la clase
           relacionada*/
    Vector ve = new Vector();
    Vector vAtb = cPrincipal.getvAtb();
    for (int i = 0; i < vAtb.size(); i++) {
      Elemento el = new Elemento(vAtb.get(i).toString(), "");
      ve.add(el);
    }
    VentanaAgregar v = new VentanaAgregar(ve, cPrincipal);
    v.setSize(600, 300);
    Dimension dlgSize = v.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    v.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                  (frmSize.height - dlgSize.height) / 2 + loc.y);
    v.setModal(true);
    v.pack();
    v.show();
    if (v.ok)
      Gestion.alta(v.vNuevaClase, cPrincipal,
                   this.enlace);
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
   * Método en el que se encuentran las acciones a realizar en respuesta a
   * un evento desde un campo de texto o un botón
   * @param e ActionEvent evento que dispara el método.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == botonCancelar) {
      ok = false;
      cancel = true;
      this.dispose();
    }
    else {
      if (e.getSource() == botonAceptar) {
        if (this.registroCompleto()) {
          Component v[] = panelTabla.getComponents();
          vNuevaClase.clear();
          vNuevaClase.add(this.enlace);
          for (int i = 0; i < v.length; i++) {
            String dato = ( (Elemento) v[i]).texto.getText();
            vNuevaClase.add(dato);
          }
          ok = true;
          this.dispose();
        }
        else
          JOptionPane.showMessageDialog(this,
                                        "Debes rellenar todos los campos.",
                                        "Aviso", JOptionPane.WARNING_MESSAGE);
      }
    }
  }
}

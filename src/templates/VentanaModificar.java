import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que implementa la ventana que permite modificar un objeto
 * de una clase del proyecto.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class VentanaModificar
    extends JDialog
    implements ActionListener {
  Raiz cPrincipal;
  Raiz cLista;
  String enlace = "";
  String consultar = "";
  Vector vClaseModificada = new Vector();
  Vector vfilas = new Vector();
  boolean ok = false;
  boolean cancel = false;

  //paneles
  JPanel panelTabla = new JPanel();
  JPanel panelContenedor = new JPanel();
  JPanel panelBotones = new JPanel();
  //botones
  JButton botonModificar = new JButton();
  JButton botonCancelar = new JButton();
  //layouts
  JScrollPane jScrollPane1 = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();

  /**
   * Constructor de clase.
   * @param vdatos Vector que contiene los datos ya modificados.
   * @param x Raiz clase a la que pertenece el objeto a modificar
   */
  public VentanaModificar(Vector vdatos, Raiz x) {
    cPrincipal = x;
    this.enlace = ( (Elemento) vdatos.get(0)).texto.getText();
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
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    this.setTitle("Modificar " + cPrincipal.getClassName());
    this.getContentPane().setLayout(borderLayout2);
    panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
    botonModificar.setText("Aceptar");
    botonCancelar.setText("Cancelar");
    panelTabla.setBorder(BorderFactory.createEtchedBorder());
    panelContenedor.setLayout(borderLayout1);
    panelBotones.setLayout(flowLayout1);
    panelBotones.add(botonModificar, null);
    panelBotones.add(botonCancelar, null);
    panelContenedor.add(jScrollPane1, BorderLayout.CENTER);
    panelContenedor.add(panelBotones, BorderLayout.SOUTH);
    jScrollPane1.getViewport().add(panelTabla, null);
    botonModificar.addActionListener(this);
    botonCancelar.addActionListener(this);

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
          JButton botonConsultar = new JButton();
          botonConsultar.setText("Modificar " + consultar);
          botonConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              Raiz r = cPrincipal.getElementoRelacionado(consultar);
              if (r != null)
                menuConsultar(e, r, enlace);
            }
          }
          );
          panelBotones.add(botonConsultar, null);
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
                menuGestionar(e, cPrincipal, r, enlace);
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
   * Método que instancia una nueva ventana para consultar/modificar un objeto
   * relacionado con la clase principal.
   * @param e ActionEvent evento al que responde el método.
   * @param x Raiz clase consultada.
   * @param enlace String que almacena la clave del objeto a
   * modificar/consultar.
   */
  public void menuConsultar(ActionEvent e, Raiz x, String enlace) {
    Vector vAtb = new Vector();

    Vector vDatos = new Vector();
    Vector ve = new Vector();
    vAtb = x.getvAtb();
    Vector vEncontrado = (Vector) x.buscarenXML(enlace);
    if (vEncontrado.size() > 0) {
      vDatos = (Vector) x.buscarenXML(enlace).get(0);
      if (vDatos.size() > 0) {

        for (int i = 0; i < vAtb.size(); i++) {
          Elemento el;
          if (! (Gestion.esVectorObjetos(vAtb.get(i).toString())) &&
              ! (Gestion.esObjeto( (vAtb.get(i).toString()))))
            el = new Elemento(vAtb.get(i).toString(),
                              vDatos.get(i + 1).toString());
          else
            el = new Elemento(vAtb.get(i + 1).toString(), "");
          ve.add(el);
        }
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
      if (v.ok) {
        Gestion.modificacion(v.vClaseModificada, x,
                             enlace);
      }
    }
    else { //si no existe lo creamos.
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

  /**
   * Metodo que abre una nueva ventana de gestion de una clase relacionada
   * con la de la ventana actual.
   * @param e ActionEvent evento al que responde el método.
   * @param claseP Raiz clase inicial de la relación.
   * @param claseL Raiz clase final de la relación.
   * @param enlace String que almacena el enlace con otras clases.
   */
  public void menuGestionar(ActionEvent e, Raiz claseP, Raiz claseL,
                            String enlace) {
    cPrincipal = claseP;
    cLista = claseL;
    VentanaGestion v = new VentanaGestion(cPrincipal, cLista, enlace);
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
    else if (e.getSource() == botonModificar) {

      if (this.registroCompleto()) {
        Component v[] = panelTabla.getComponents();

        vClaseModificada.clear();
        vClaseModificada.add(this.enlace);
        for (int i = 0; i < v.length; i++) {
          String dato = ( (Elemento) v[i]).texto.getText();
          this.vClaseModificada.add(dato);
        }
        ok = true;
        this.dispose();
      }
    }
    else
      JOptionPane.showMessageDialog(this,
                                    "Debes rellenar todos los campos.",
                                    "Aviso", JOptionPane.WARNING_MESSAGE);
  }
}

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Clase que implementa la ventana que permite visualizar una lista de n
 * elementos para su gestión.
 * @version 1.0
 * @author Digna Rodríguez Cudeiro
 */
public class VentanaGestion
    extends JDialog
    implements ActionListener {
  Raiz cPrincipal;
  Raiz cLista;

  String nomClasePrincipal;
  String nomClaseLista;
  String enlaceAnterior = "";

  Vector vAtbPrincipal = new Vector();
  Vector vAtbLista = new Vector();
  Vector vDatosLista = new Vector();
  Vector vDatosPrincipal = new Vector();
  //botones
  JButton botonAgregar = new JButton();
  JButton botonModificar = new JButton();
  JButton botonEliminar = new JButton();
  JButton botonEditar = new JButton();
  JButton botonSalir= new JButton();
  //paneles
  JPanel panelFondo = new JPanel();
  JPanel panelBotones = new JPanel();
  JPanel panelLista = new JPanel();
  //listas
  JList lista;
  JList listaAux;

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel labelAtb = new JLabel();
  TitledBorder titledBorder1;

  /**
   * Constructor de la clase.
   * @param claseP Raiz clase que representa la multiplicidad 1 en una relación
   * 1 a n.
   * @param claseL Raiz clase que representa la multiplicidad n en una relación
   * 1 a n, y cuyos n elementos aparecen en la ventanaGestion.
   * @param enlace String relación que une la clase 1 con la clase n.
   */
  public VentanaGestion(Raiz claseP, Raiz claseL, String enlace) {

    //Principal
    cPrincipal = claseP;
    enlaceAnterior = enlace;
    vDatosPrincipal = cPrincipal.leerXML();
    vAtbPrincipal = cPrincipal.getvAtb();
    nomClasePrincipal = cPrincipal.getClassName();

    //Lista
    if (enlace == "") {
      cLista = claseL;
      vAtbLista = cLista.getvAtb();
      vDatosLista = cLista.leerXML();
      nomClaseLista = cLista.getClassName();
    }
    else {
      cLista = claseL;
      vAtbLista = cLista.getvAtb();
      vDatosLista = cLista.buscarenXML(enlace);
      nomClaseLista = cLista.getClassName();
    }
    if (vDatosPrincipal.size() != 0)
      vDatosPrincipal = (Vector) vDatosPrincipal.get(0);
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

    titledBorder1 = new TitledBorder("");
    this.setSize(new Dimension(670, 475));
    this.getContentPane().setLayout(gridBagLayout1);
    this.setTitle("Gestionar " + nomClaseLista + "s de " + nomClasePrincipal);
    ImageIcon iAgregar = new ImageIcon(VentanaPrincipal.class.getResource(
        "."+File.separator+"Imagenes"+File.separator+"agregar.png"));
    ImageIcon iModificar = new ImageIcon(VentanaPrincipal.class.getResource(
        "."+File.separator+"Imagenes"+File.separator+"modificar.png"));
    ImageIcon iEliminar = new ImageIcon(VentanaPrincipal.class.getResource(
        "."+File.separator+"Imagenes"+File.separator+"eliminar.png"));
      ImageIcon iSalir= new ImageIcon(VentanaPrincipal.class.getResource(
        "."+File.separator+"Imagenes"+File.separator+"atras.png"));



    //en lista  tien el id y enlace.
    lista = new JList(vDatosLista);
    lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lista.setSelectedIndex(0);


  Vector vDatosListaAux=new Vector();
   for(int k=0;k<vDatosLista.size();k++){
     Vector nuevo= (Vector)vDatosLista.get(k);
     Vector cop=new Vector();
     for(int j=0;j<nuevo.size();j++){
       String s =(String) nuevo.get(j);
       cop.addElement(s);
     }
       vDatosListaAux.add(k, cop);
   }

    //eliminamos el enlace de cada linea
   for(int i =0 ;i<vDatosListaAux.size();i++){
     ((Vector)vDatosListaAux.get(i)).remove(0);
     ((Vector)vDatosListaAux.get(i)).remove(0);
   }

    listaAux= new JList(vDatosListaAux);
    listaAux.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listaAux.setSelectedIndex(0);
    JScrollPane listScrollPane = new JScrollPane(listaAux);




    botonAgregar.setText("Agregar");
    botonAgregar.setIcon(iAgregar);
    botonAgregar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        menuAgregar(e);
      }
    }
    );

    botonModificar.setIcon(iModificar);
    botonModificar.setText("Modificar");
    botonModificar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
       int sel= listaAux.getSelectedIndex();
       lista.setSelectedIndex(sel);
       Vector vdatos = (Vector) lista.getSelectedValue();
        menuModificar(e, nomClaseLista, vdatos, false);
      }
    }
    );

    botonEliminar.setText("Eliminar");
    botonEliminar.setIcon(iEliminar);
    botonEliminar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String id = null;
        if (listaAux.getSelectedValue() != null){
          int sel= listaAux.getSelectedIndex();
          lista.setSelectedIndex(sel);
          id = (String) ( (Vector) lista.getSelectedValue()).get(1);
        }
        menuEliminar(e, id);
      }
    }
    );

    botonSalir.setText("  Atrás");
    botonSalir.setIcon(iSalir);
    botonSalir.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e) {
       cerrar(e);
     }
   }
   );

    panelBotones.setLayout(new GridLayout(10, 0));
    panelLista.setBorder(BorderFactory.createEtchedBorder());
    labelAtb.setFont(new java.awt.Font("Dialog", 1, 11));
    labelAtb.setBorder(titledBorder1);
    Vector vLabel = new Vector();

    for (int i = 1; i < vAtbLista.size(); i++)
      if (! (Gestion.esVectorObjetos(vAtbLista.get(i).toString())) &&
          ! (Gestion.esObjeto( (vAtbLista.get(i).toString()))))
        vLabel.add(vAtbLista.get(i));
    labelAtb.setText(vLabel.toString());
    labelAtb.setBounds(new Rectangle(8, 10, 510, 23));
    listScrollPane.setMaximumSize(new Dimension(32767, 32767));
    listScrollPane.setPreferredSize(new Dimension(495, 415));
    panelBotones.add(botonAgregar, null);
    panelBotones.add(botonModificar, null);
    panelBotones.add(botonEliminar, null);
    panelBotones.add(botonSalir,null);
    panelFondo.add(labelAtb, null);
    panelBotones.setBounds(new Rectangle(525, 32, 131, 347));
    panelLista.setBounds(new Rectangle(8, 33, 510, 431));
    panelFondo.add(panelLista, null);
    panelLista.add(listScrollPane, null);
    panelFondo.add(panelBotones, null);
    panelFondo.setLayout(null);
    this.getContentPane().add(panelFondo,
                              new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 675, 475));
  }


  /**
   * Método que instancia una nueva ventana para añadir un nuevo objeto a la
   * lista de objetos de la ventana principal.
   * @param e ActionEvent evento al que responde el método.
   */
  public void menuAgregar(ActionEvent e) {
    Vector ve = new Vector();
    for (int i = 0; i < vAtbLista.size(); i++) {
      Elemento el = new Elemento(vAtbLista.get(i).toString(), "");
      ve.add(el);
    }
    VentanaAgregar v = new VentanaAgregar(ve, cLista);
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
      Vector completo = Gestion.alta(v.vNuevaClase, cLista, enlaceAnterior);
      actualizarListas(completo);
      this.panelLista.updateUI();
    }
  }

public void actualizarListas(Vector completo){


    //en lista  tien el id y enlace.
lista.removeAll();
lista.setListData(completo);


Vector vcompletoAux=new Vector();
for(int k=0;k<completo.size();k++){
Vector nuevo= (Vector)completo.get(k);
Vector cop=new Vector();
for(int j=0;j<nuevo.size();j++){
String s =(String) nuevo.get(j);
cop.addElement(s);
}
vcompletoAux.add(k, cop);
}

//eliminamos el enlace y el id de cada linea
for(int i =0 ;i<vcompletoAux.size();i++){
((Vector)vcompletoAux.get(i)).remove(0);
((Vector)vcompletoAux.get(i)).remove(0);
}
listaAux.removeAll();
listaAux.setListData(vcompletoAux);


}

  /**
   * Método que instancia una nueva ventana para modificar un objeto
   * seleccionado de la lista de objetos de la ventana principal.
   *
   * @param e ActionEvent evento al que responde el método.
   * @param nombre String
   * @param vdatos Vector
   * @param principal boolean
   */
  public void menuModificar(ActionEvent e, String nombre, Vector vdatos,
                            boolean principal) {

    if (vdatos == null)
      JOptionPane.showMessageDialog(this,
                                    "Debe de seleccionar un elemento a modificar",
                                    "Mensaje error",
                                    JOptionPane.ERROR_MESSAGE);
    else {
      Vector ve = new Vector();
      if (vdatos.size() != 0) {
        if (nombre.compareTo(this.nomClaseLista) == 0) {
          for (int i = 0; i < vAtbLista.size(); i++) {
            Elemento el;
            if (! (Gestion.esVectorObjetos(vAtbLista.get(i).toString())) &&
                ! (Gestion.esObjeto( (vAtbLista.get(i).toString()))))
              el = new Elemento(vAtbLista.get(i).toString(),
                                vdatos.get(i + 1).toString());
            else
              el = new Elemento(vAtbLista.get(i).toString(), "");
            ve.add(el);
          }
        }
        else {
          for (int i = 0; i < this.vAtbPrincipal.size(); i++) {
            Elemento el;
            if (! (Gestion.esVectorObjetos(vAtbPrincipal.get(i).toString())) &&
                ! (Gestion.esObjeto( (vAtbPrincipal.get(i).toString()))))
              el = new Elemento(vAtbPrincipal.get(i).toString(),
                                vdatos.get(i + 1).toString());
            else
              el = new Elemento(vAtbPrincipal.get(i).toString(), "");
            ve.add(el);
          }
        }
      }
      else {
        for (int i = 0; i < vAtbPrincipal.size(); i++) {
          Elemento el = new Elemento(vAtbPrincipal.get(i).toString(), "");
          ve.add(el);
        }
      }

      VentanaModificar v = new VentanaModificar(ve, this.cLista);
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
        Vector completo = new Vector();
        if (principal == false) {

          completo = Gestion.modificacion(v.vClaseModificada, cLista,
                                          this.enlaceAnterior);

          actualizarListas(completo);

        }
        else {
          completo = Gestion.modificacion(v.vClaseModificada, cPrincipal,
                                          this.enlaceAnterior);
          vDatosPrincipal = (Vector) cPrincipal.leerXML().get(0);
        }
      }
    }
  }

  /**
   * Da de baja el objeto seleccionado de la lista de objetos de la ventana
   * principal.
   *
   * @param e ActionEvent evento al que responde el método.
   * @param id String identificador del objeto a eliminar.
   */
  public void menuEliminar(ActionEvent e, String id) {
    if (id == null)
      JOptionPane.showMessageDialog(this,
                                    "Debe de seleccionar un elemento a modificar",
                                    "Mensaje error",
                                    JOptionPane.ERROR_MESSAGE);
    else {
      int n = JOptionPane.showConfirmDialog(
          this, "Seguro que desea eliminar el elemento seleccionado?",
          "Mensaje de confirmación",
          JOptionPane.YES_NO_OPTION);
      if (n == JOptionPane.YES_OPTION) {
        Vector completo = new Vector();
        //Comprobamos si hay más clases relacionadas con esta que borrar

        boolean borradosimple = borradoSimple();
        if (borradosimple){
          completo = Gestion.baja(id, cLista);
          System.out.println("borrado simple");
        }
        else {
          System.out.println("borrado en casacada");
          Gestion.borradoCascada(id, cLista);
          completo = Gestion.baja(id, cLista);
        }
        actualizarListas(completo);

      }
      else if (n == JOptionPane.NO_OPTION) {
      }
    }
  }
  /**
   * Método que comprueba si la clase está relacionada con otras para hacer su
   * borrado simple o en cascada.
   * @return boolean
   */
  public boolean borradoSimple(){
    for (int i = 0; i < vAtbLista.size(); i++) {
      if ( (Gestion.esVectorObjetos(vAtbLista.get(i).toString())) ||
          (Gestion.esObjeto( (vAtbLista.get(i).toString()))))
        return false;
    }
    return true;
  }


	/**
	 * Método que vuelve a la ventana principal, cerrando la ventana de gestión.
	 * @param e ActionEvent evento al que responde el método.
	 */
	  public void cerrar(ActionEvent e){
	  this.dispose();
	}


  /**
   * Método en el que se encuentran las acciones a realizar en respuesta a
   * un evento desde un campo de texto o un botón
   * @param e ActionEvent evento que dispara el método.
   */
  public void actionPerformed(ActionEvent e) {}

  //Modificado para poder salir cuando se cierra la ventana
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      dispose();
    }
    super.processWindowEvent(e);
  }

}

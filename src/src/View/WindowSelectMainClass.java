package View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import Model.*;
import java.util.Vector;
import java.io.File;

/**
 * Panel desde el que se puede elegir cual será la clase principal del sistema.
 * Esta clase será la clase que aparezca en la ventana principal de la aplicación
 * generada.
 * @author Digna Rodríguez
 * @version 1.0
 */
public class WindowSelectMainClass
    extends JDialog
    implements ActionListener {

  boolean botonOkPulsado = false;
  boolean botonCancelarPulsado = false;
  JPanel panelBotones = new JPanel();
  JPanel panelRadio = new JPanel();
  JButton botonAceptar = new JButton();
  ImageIcon imagen = new ImageIcon();
  Vector vRadioButtons = new Vector();
  // Group the radio buttons.
  ButtonGroup group = new ButtonGroup();

  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel etiquetaImagen = new JLabel();
  JTextPane panelTitulo = new JTextPane();
  JScrollPane jScrollPane1 = new JScrollPane();
  XYLayout xYLayout1 = new XYLayout();
  JPanel panelContenedor = new JPanel();
  XYLayout xYLayout2 = new XYLayout();

  public WindowSelectMainClass(Frame parent) {
    super(parent);
    setDefaultCloseOperation(0); // Desactiva el boton cerrar
   enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  WindowSelectMainClass() {
    this(null);
  }

  /**
   * Método que elige una clase como clase principal, basandose en que a ella
   * no llega ninguna asociación.
   * @return String nombre de la clase principal.
   */
  public String getMainClass(){
  /*recorremos todas las clases y para cada clase comprobamos si la clase
  no esta en el end de ninguna relación*/
  boolean principal=true;

  for(int j=0;j<Controller.contenido.vClasses.size();j++)
  {
    String nombre = ( (UMLClass) Controller.contenido.vClasses.get(j)).
        getName();
    principal= true;
    for (int i = 0; i < Controller.asoc.vAssoc.size(); i++) {
      UMLAssociation as = (UMLAssociation)Controller.asoc.vAssoc.get(i);
      if(((String)as.vBeginEnd.get(1)).compareTo(nombre)==0){
        principal = false;
      }
    }
    if(principal == true){
      return nombre;
    }
  }
  return "";
}

  /**
   * El método jbInit se encarga de la inicialización de componentes de la
   * interfaz.
   *
   * @throws Exception en caso de que los componentes de la interfaz no puedan cargarse
   * correctamente lanza una excepción.
   */
  private void jbInit() throws Exception {
    xYLayout1.setWidth(340);
    xYLayout1.setHeight(249);
    panelContenedor.setLayout(xYLayout2);
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.
                                            VERTICAL_SCROLLBAR_AS_NEEDED);
    //this.setUndecorated(true);

    panelBotones.add(botonAceptar, null);
    panelContenedor.add(etiquetaImagen, new XYConstraints(6, 8, -1, -1));
    panelContenedor.add(panelBotones, new XYConstraints(151, 204, 178, -1));
    panelContenedor.add(panelTitulo, new XYConstraints(150, 6, 195, -1));
    panelContenedor.add(jScrollPane1, new XYConstraints(154, 44, 172, 155));
    jScrollPane1.getViewport().add(panelRadio, null);
    this.getContentPane().add(panelContenedor, null);
    this.getContentPane().setLayout(xYLayout1);
    this.setResizable(false);
    this.setTitle("Selector de la clase principal del proyecto");
    imagen = new ImageIcon(View.WindowChooseDirectory.class.getResource(
        "Images/lateralengranaje.png"));
    botonAceptar.setText("Aceptar");
    botonAceptar.addActionListener(this);
    for (int i = 0; i < Controller.contenido.vClasses.size(); i++) {
      Model.UMLClass cl = ( (UMLClass) (Controller.contenido.vClasses.get(i)));
      String nCl = cl.getName();
      JRadioButton r = new JRadioButton();
      r.setText(nCl);
      vRadioButtons.add(r);
    }
    for (int i = 0; i < vRadioButtons.size(); i++) {
      group.add( (JRadioButton) vRadioButtons.get(i));
      panelRadio.add( (JRadioButton) vRadioButtons.get(i), null);
    }
    //si no se ha seleccionado previamente ninguna clase
    if (Controller.mainClass.compareTo("") == 0)
    {
      //( (JRadioButton) vRadioButtons.get(0)).setSelected(true);
      String g = getMainClass();
      if(g.compareTo("")==0)
        ( (JRadioButton) vRadioButtons.get(0)).setSelected(true);
      else
      {
       for (int i = 0; i < vRadioButtons.size(); i++) {
         JRadioButton r=(JRadioButton) vRadioButtons.get(i);
         if (r.getText().compareTo(g)==0)
          ((JRadioButton) vRadioButtons.get(i)).setSelected(true);
       }
      }
    }
    else { //si se ha seleccionado la marcamos
      for (int i = 0; i < vRadioButtons.size(); i++) {
        if (Controller.mainClass.compareTo( ( (JRadioButton) vRadioButtons.get(
            i)).getText()) == 0)
          ( (JRadioButton) vRadioButtons.get(i)).setSelected(true);
      }
    }

    etiquetaImagen.setIcon(imagen);
    panelTitulo.setBackground(SystemColor.control);
    panelTitulo.setFont(new java.awt.Font("Dialog", 1, 11));
    panelTitulo.setAlignmentY( (float) 0.5);
    panelTitulo.setDisabledTextColor(Color.lightGray);
    panelTitulo.setEditable(false);
    panelTitulo.setText("Seleccione la clase principal del proyecto actual:");
    panelRadio.setLayout(verticalFlowLayout1);
  }

  /**
   * Método que cierra la ventana al salir.
   * @param e WindowEvent evento al que responde.
   */
  /*protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      botonCancelarPulsado = true;
      this.dispose();
    }
    super.processWindowEvent(e);
  }*/

  /**
   * Método que implementa los eventos de los boton aceptar.
   * @param e WindowEvent evento al que responde.
   */
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == botonAceptar) {

      for (int i = 0; i < vRadioButtons.size(); i++) {
        if ( ( (JRadioButton) vRadioButtons.get(i)).isSelected())
          Controller.mainClass = ( (JRadioButton) vRadioButtons.get(i)).getText();
      }
      botonOkPulsado = true;
      this.dispose();
    }
  }
}

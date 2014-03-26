package Model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.swing.JEditorPane;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.View;
import javax.swing.text.html.HTMLDocument;
import javax.swing.JTextPane;

/**
 * La clase DocumentRender imprime objetos de tipo documento
 * (atributos de texto, incluyendo fuentes, color, iconos...)
 * calcula los saltos de l�nea, p�gina, y realiza
 * otros formateos.
 * @author Digna Rodr�guez Cudeiro
 * @version 1.0
 */
public class DocumentRender
    implements Printable {
  //contador de p�ginas
  protected int currentPage = -1;
  protected JEditorPane jeditorPane;

  //donde acaba la p�gina.
  protected double pageEndY = 0;

  //donde empieza la p�gina.
  protected double pageStartY = 0;

  //bolean que permite saber si las p�ginas son grandes para caber en una p�gina,sino se escalar�n.
  protected boolean scaleWidthToFit = true;
  protected PageFormat pFormat;
  protected PrinterJob pJob;

  /**
   * Constructor en el que se incializan las varialbes pFormat y pJob.
   */
  public DocumentRender() {
    pFormat = new PageFormat();
    pJob = PrinterJob.getPrinterJob();
  }

  /**
   * M�todo que obtiene el documento actual.
   * @return Document documento a imprimir
   */
  public Document getDocument() {
    if (jeditorPane != null)return jeditorPane.getDocument();
    else return null;
  }

  /**
   * M�todo que devuelve si se escalar� o no el documento
   * @return boolean true si el documento es m�s grande y se debe de escalar
   * false en caso contrario.
   */
  public boolean getScaleWidthToFit() {
    return scaleWidthToFit;
  }

  /**
   * M�todo que muestra la ventana de preferencias de impresi�n de p�gina.
   */
  public void pageDialog() {
    pFormat = pJob.pageDialog(pFormat);
  }

  public boolean pageDialogok() {
    pFormat = pJob.pageDialog(pFormat);
    return true;

  }

  /**
   * M�todo que realiza la impresi�n
   * @param graphics Graphics
   * @param pageFormat PageFormat
   * @param pageIndex int
   * @return int
   */
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
    double scale = 1.0;
    Graphics2D graphics2D;
    View rootView;
    graphics2D = (Graphics2D) graphics;
    //El JEditorPane se presenta utilizando el ancho de impresi�n de una p�gina
    jeditorPane.setSize( (int) pageFormat.getImageableWidth(),
                        Integer.MAX_VALUE);
    jeditorPane.validate();
    rootView = jeditorPane.getUI().getRootView(jeditorPane);
    //Se comprueba si se escala o no el objeto grafics2D
    if ( (scaleWidthToFit) && (jeditorPane.getMinimumSize().getWidth() >
                               pageFormat.getImageableWidth())) {
      scale = pageFormat.getImageableWidth() /
          jeditorPane.getMinimumSize().getWidth();
      graphics2D.scale(scale, scale);
    }
    // Recortamos el grafo2D
    graphics2D.setClip( (int) (pageFormat.getImageableX() / scale),
                       (int) (pageFormat.getImageableY() / scale),
                       (int) (pageFormat.getImageableWidth() / scale),
                       (int) (pageFormat.getImageableHeight() / scale));
    //  Se comprueba si hay que hacer una nueva p�gina.
    if (pageIndex > currentPage) {
      currentPage = pageIndex;
      pageStartY += pageEndY;
      pageEndY = graphics2D.getClipBounds().getHeight();
    }
    graphics2D.translate(graphics2D.getClipBounds().getX(),
                         graphics2D.getClipBounds().getY());

    Rectangle allocation = new Rectangle(0,
                                         (int) - pageStartY,
                                         (int) (jeditorPane.getMinimumSize().
                                                getWidth()),
                                         (int) (jeditorPane.getPreferredSize().
                                                getHeight()));

    if (printView(graphics2D, allocation, rootView)) {
      return Printable.PAGE_EXISTS;
    }
    else {
      pageStartY = 0;
      pageEndY = 0;
      currentPage = -1;
      return Printable.NO_SUCH_PAGE;
    }
  }

  /**
   * M�todo que imprime un HTMLDocument.
   * @param htmlDocument HTMLDocument que se imprime.
   */
  public void print(HTMLDocument htmlDocument) {
    setDocument(htmlDocument);
    printDialog();
  }

  /**
   * M�todo que imprime el contenido de un JEditorPane
   * @param jedPane JEditorPane
   */
  public void print(JEditorPane jedPane) {
    setDocument(jedPane);
    printDialog();
  }

  /**
   * M�todo qeu imprime el contenido de in JTextPane
   * @param jtexPane JTextPane
   */
  public void print(JTextPane jtexPane) {
    setDocument(jtexPane);
    printDialog();
  }

  /**
   * M�todo que imprime un documento de texto plano
   * @param plainDocument PlainDocument a imprimir.
   */
  public void print(PlainDocument plainDocument) {
    setDocument(plainDocument);
    printDialog();
  }

  /**
   * El m�todo printDialog abre un Dialog desde el que se permiten modificar las
   * preferencias de impresi�n.
   */
  protected void printDialog() {
    if (pJob.printDialog()) {
      pJob.setPrintable(this, pFormat);
      try {
        pJob.print();
      }
      catch (PrinterException printerException) {
        pageStartY = 0;
        pageEndY = 0;
        currentPage = -1;
        System.out.println("Error Printing Document");
      }
    }
  }

  /**
   * M�todo recursivo que itera a trav�s de una estructura de �rbol que va
   * recorriendo para crear la  p�gina que se imprimir�
   * @param graphics2D Graphics2D
   * @param allocation Shape
   * @param view View vista del �rbol en cada iteraci�n
   * @return boolean devuelve true si se puede pintar false en caso contrario.
   */
  protected boolean printView(Graphics2D graphics2D, Shape allocation,
                              View view) {
    boolean pageExists = false;
    Rectangle clipRectangle = graphics2D.getClipBounds();
    Shape childAllocation;
    View childView;
    //si existen p�ginas que imprimir
    if (view.getViewCount() > 0 &&
        !view.getElement().getName().equalsIgnoreCase("td")) {
      for (int i = 0; i < view.getViewCount(); i++) {
        childAllocation = view.getChildAllocation(i, allocation);
        if (childAllocation != null) {
          childView = view.getView(i);
          if (printView(graphics2D, childAllocation, childView)) {
            pageExists = true;
          }
        }
      }
    }
    else {

      if (allocation.getBounds().getMaxY() >= clipRectangle.getY()) {
        pageExists = true;

        if ( (allocation.getBounds().getHeight() > clipRectangle.getHeight()) &&
            (allocation.intersects(clipRectangle))) {
          view.paint(graphics2D, allocation);
        }
        else {

          if (allocation.getBounds().getY() >= clipRectangle.getY()) {
            if (allocation.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
              view.paint(graphics2D, allocation);
            }
            else {

              if (allocation.getBounds().getY() < pageEndY) {
                pageEndY = allocation.getBounds().getY();
              }
            }
          }
        }
      }
    }
    return pageExists;
  }

  /**
   * M�todo que cambia el tipo de contenido que se imprime.
   * @param type String nuevo tipo de contenido.
   */
  protected void setContentType(String type) {
    jeditorPane.setContentType(type);
  }

  /**
   * M�todo que cambia el  HTMLDocumente en un documento para imprimir.
   * @param htmlDocument HTMLDocument
   */
  public void setDocument(HTMLDocument htmlDocument) {
    jeditorPane = new JEditorPane();
    setDocument("text/html", htmlDocument);
  }

  /**
   * M�todo que cambia el Documento a imprimir por el contenido en un JEditorPane.
   * @param jedPane JEditorPane
   */
  public void setDocument(JEditorPane jedPane) {
    jeditorPane = new JEditorPane();
    setDocument(jedPane.getContentType(), jedPane.getDocument());
  }

  /**
   * M�todo que cambia el  PlainDocument en un documento para imprimir.
   * @param plainDocument PlainDocument
   */
  public void setDocument(PlainDocument plainDocument) {
    jeditorPane = new JEditorPane();
    setDocument("text/plain", plainDocument);
  }

  /**
   * M�todo que cambia el contenido y el tipo de un documento del JEditorPane.
   * @param type String nuevo tipo de contenido.
   * @param document Document nuevo documento
   */
  protected void setDocument(String type, Document document) {
    setContentType(type);
    jeditorPane.setDocument(document);
  }

}

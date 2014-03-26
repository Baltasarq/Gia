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
 * calcula los saltos de línea, página, y realiza
 * otros formateos.
 * @author Digna Rodríguez Cudeiro
 * @version 1.0
 */
public class DocumentRender
    implements Printable {
  //contador de páginas
  protected int currentPage = -1;
  protected JEditorPane jeditorPane;

  //donde acaba la página.
  protected double pageEndY = 0;

  //donde empieza la página.
  protected double pageStartY = 0;

  //bolean que permite saber si las páginas son grandes para caber en una página,sino se escalarán.
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
   * Método que obtiene el documento actual.
   * @return Document documento a imprimir
   */
  public Document getDocument() {
    if (jeditorPane != null)return jeditorPane.getDocument();
    else return null;
  }

  /**
   * Método que devuelve si se escalará o no el documento
   * @return boolean true si el documento es más grande y se debe de escalar
   * false en caso contrario.
   */
  public boolean getScaleWidthToFit() {
    return scaleWidthToFit;
  }

  /**
   * Método que muestra la ventana de preferencias de impresión de página.
   */
  public void pageDialog() {
    pFormat = pJob.pageDialog(pFormat);
  }

  public boolean pageDialogok() {
    pFormat = pJob.pageDialog(pFormat);
    return true;

  }

  /**
   * Método que realiza la impresión
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
    //El JEditorPane se presenta utilizando el ancho de impresión de una página
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
    //  Se comprueba si hay que hacer una nueva página.
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
   * Método que imprime un HTMLDocument.
   * @param htmlDocument HTMLDocument que se imprime.
   */
  public void print(HTMLDocument htmlDocument) {
    setDocument(htmlDocument);
    printDialog();
  }

  /**
   * Método que imprime el contenido de un JEditorPane
   * @param jedPane JEditorPane
   */
  public void print(JEditorPane jedPane) {
    setDocument(jedPane);
    printDialog();
  }

  /**
   * Método qeu imprime el contenido de in JTextPane
   * @param jtexPane JTextPane
   */
  public void print(JTextPane jtexPane) {
    setDocument(jtexPane);
    printDialog();
  }

  /**
   * Método que imprime un documento de texto plano
   * @param plainDocument PlainDocument a imprimir.
   */
  public void print(PlainDocument plainDocument) {
    setDocument(plainDocument);
    printDialog();
  }

  /**
   * El método printDialog abre un Dialog desde el que se permiten modificar las
   * preferencias de impresión.
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
   * Método recursivo que itera a través de una estructura de árbol que va
   * recorriendo para crear la  página que se imprimirá
   * @param graphics2D Graphics2D
   * @param allocation Shape
   * @param view View vista del árbol en cada iteración
   * @return boolean devuelve true si se puede pintar false en caso contrario.
   */
  protected boolean printView(Graphics2D graphics2D, Shape allocation,
                              View view) {
    boolean pageExists = false;
    Rectangle clipRectangle = graphics2D.getClipBounds();
    Shape childAllocation;
    View childView;
    //si existen páginas que imprimir
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
   * Método que cambia el tipo de contenido que se imprime.
   * @param type String nuevo tipo de contenido.
   */
  protected void setContentType(String type) {
    jeditorPane.setContentType(type);
  }

  /**
   * Método que cambia el  HTMLDocumente en un documento para imprimir.
   * @param htmlDocument HTMLDocument
   */
  public void setDocument(HTMLDocument htmlDocument) {
    jeditorPane = new JEditorPane();
    setDocument("text/html", htmlDocument);
  }

  /**
   * Método que cambia el Documento a imprimir por el contenido en un JEditorPane.
   * @param jedPane JEditorPane
   */
  public void setDocument(JEditorPane jedPane) {
    jeditorPane = new JEditorPane();
    setDocument(jedPane.getContentType(), jedPane.getDocument());
  }

  /**
   * Método que cambia el  PlainDocument en un documento para imprimir.
   * @param plainDocument PlainDocument
   */
  public void setDocument(PlainDocument plainDocument) {
    jeditorPane = new JEditorPane();
    setDocument("text/plain", plainDocument);
  }

  /**
   * Método que cambia el contenido y el tipo de un documento del JEditorPane.
   * @param type String nuevo tipo de contenido.
   * @param document Document nuevo documento
   */
  protected void setDocument(String type, Document document) {
    setContentType(type);
    jeditorPane.setDocument(document);
  }

}

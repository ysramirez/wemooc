package com.liferay.lms.servlet;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.lms.service.SCORMContentLocalServiceUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;

/**
 * Servlet implementation class SCORMFileServerServlet
 */

public class SCORMFileServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SCORMFileServerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private ServletContext application;
	private ServletConfig config=null;

  /** Procesa los métodos HTTP GET y POST.<br>
   *  Busca en la ruta que se le ha pedido el comienzo del directorio "contenidos" y sirve el fichero.
   */
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, java.io.IOException
  {
			String mime_type ;
			String patharchivo ;
			String uri ;
    try
    {
      String rutaDatos=SCORMContentLocalServiceUtil.getBaseDir();
      // Se comprueba que el usuario tiene permisos para acceder.
      // Damos acceso a todo el mundo al directorio "personalizacion",
      // para permitir mostrar a todos la pantalla de identificacion.
      uri = request.getRequestURI() ;
      uri = uri.substring(uri.indexOf("scorm/")+"scorm/".length()) ;
      patharchivo = rutaDatos+"/" + uri ;
      File archivo = new File(patharchivo) ;

      // Si el archivo existe y no es un directorio se sirve. Si no, no se hace nada.
      if (archivo.exists() && archivo.isFile())
      {
        
        //El content type siempre antes del printwriter
        mime_type= MimeTypesUtil.getContentType(archivo);
        response.setContentType(mime_type);

        java.io.OutputStream out=response.getOutputStream();

        FileInputStream fis=new FileInputStream(patharchivo);

        byte[] buffer =new byte[512];
        int i=0;

        while(fis.available()>0)
        {
          i=fis.read(buffer);
          if(i==512)	out.write(buffer);
          else out.write(buffer,0,i);
        }

        fis.close();
        out.flush();
        out.close();
      }
      else
      {
    	  java.io.OutputStream out=response.getOutputStream();
    	  out.write(uri.getBytes());
      }
    }
    catch(Exception e)
    {
      System.out.println("Error en el processRequest() de ServidorArchivos: "+e.getMessage());
    }
  }

  /**
   * Procesa el método HTTP GET.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, java.io.IOException
  {
      processRequest(request, response);
  }

  /**
   * Procesa el método HTTP POST.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  throws ServletException, java.io.IOException
  {
      processRequest(request, response);
  }
}

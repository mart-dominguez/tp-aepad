package isi.aepad.tp.web;

import java.io.IOException;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReportesServlet
 */
@WebServlet("/reportes")
public class ReportesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static long ID_JOB=-1;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();

		HttpSession ses =  request.getSession(true);
	     // Set refresh, autoload time as 5 seconds
		response.getWriter().append("<p> ID JOB "+ ID_JOB+"</p>");
		if(ID_JOB>-1) {
			response.getWriter().append("<p>Estado: "+jobOperator.getJobExecution(ID_JOB).getBatchStatus().toString()+"</p>");	
		}else {
			ID_JOB = jobOperator.start("reportejobs", props);
			response.getWriter().append("<p>Iniciado: "+ID_JOB+"</p>");	
		}
		response.setIntHeader("Refresh", 1);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

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

//		Properties props = new Properties();
//		props.setProperty("parameter1", "value1");
//		...
//		
//		<step id="productoreporter">
//		<listeners>
//			<listener ref="InfoItemProcessListeners" next="productoreporter" />
//		</listeners>
//		<chunk checkpoint-policy="item" item-count="10">
//			<reader ref="ProductoDataReader"></reader>
//			<processor ref="ProductoDataProcessor"></processor>
//			<writer ref="ProductoDataWriter"></writer>
//		</chunk>
//
//	</step>

		long execID = -1;
		HttpSession ses =  request.getSession(true);
		response.getWriter().append(" ID SESION "+ses.getId());
		if(ses.getAttribute("ID_JOB")!=null) {
			execID = Long.valueOf(ses.getAttribute("ID_JOB").toString()).longValue();			 
			response.getWriter().append("Estado: "+jobOperator.getJobExecution(execID).getBatchStatus().toString());		
		}else {
			execID = jobOperator.start("reportejobs", props);
			ses.setAttribute("ID_JOB", execID);
			response.getWriter().append("Iniciado: "+execID);		
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

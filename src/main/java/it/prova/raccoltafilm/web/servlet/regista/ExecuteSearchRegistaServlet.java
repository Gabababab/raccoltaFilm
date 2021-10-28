package it.prova.raccoltafilm.web.servlet.regista;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import it.prova.raccoltafilm.model.Regista;
import it.prova.raccoltafilm.model.Sesso;
import it.prova.raccoltafilm.service.MyServiceFactory;
import it.prova.raccoltafilm.utility.UtilityForm;

@WebServlet("/ExecuteSearchRegistaServlet")
public class ExecuteSearchRegistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String nomeParam = request.getParameter("nome");
		String cognomeParam = request.getParameter("cognome");
		String nicknameParam = request.getParameter("nickName");
		String dataPubblicazioneParam = request.getParameter("dataPubblicazione");
		String sessoParam=request.getParameter("sesso");
		
		UtilityForm.parseDateArrivoFromString(dataPubblicazioneParam);
		Sesso sessoParsed=StringUtils.isNotBlank(sessoParam) ? Sesso.valueOf(sessoParam):null;
		Regista example=new Regista(nomeParam, cognomeParam, nicknameParam, UtilityForm.parseDateArrivoFromString(dataPubblicazioneParam), sessoParsed);
				
		try {
			request.setAttribute("registi_list_attribute",
					MyServiceFactory.getRegistaServiceInstance().findByExample(example));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/regista/search.jsp").forward(request, response);
			return;
		}
		request.getRequestDispatcher("regista/list.jsp").forward(request, response);
	}

}

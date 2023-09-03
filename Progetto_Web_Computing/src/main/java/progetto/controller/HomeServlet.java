package progetto.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import progetto.persistenza.DBManager;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Recensione;
import progetto.persistenza.model.Utente;

@WebServlet("/")
public class HomeServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		List<Recensione> recensioni= DBManager.getInstance().getRecensioneDao().findAll();
			
		
		req.setAttribute("lista_recensioni", recensioni);
		 
		RequestDispatcher dispacher = req.getRequestDispatcher("views/index.html");
		
		dispacher.forward(req, resp);
		
		
	}
    
 }




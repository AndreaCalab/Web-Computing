package progetto.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.UtenteDao;
import progetto.persistenza.model.Utente;

@WebServlet("/doLogin")
public class LoginServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		
		UtenteDao udao = DBManager.getInstance().getUtenteDao();
		Utente utente = udao.findByPrimaryKey(username);
		boolean logged;
		if (utente == null) {
			logged = false;
		}else {
			if (password.equals(utente.getPassword())) {
				logged = true;
				HttpSession session=req.getSession();
				session = req.getSession();
				session.setAttribute("user", utente);
				session.setAttribute("sessionId", session.getId());
				Utente ut=(Utente)session.getAttribute("user");
				req.getServletContext().setAttribute(session.getId(),session);
			}else {
				logged = false;
			}
		}
		if (logged) {
			resp.sendRedirect("/");
		}else {
			resp.sendRedirect("/notAuthorized.html");
		}
		
	}
	
	
}

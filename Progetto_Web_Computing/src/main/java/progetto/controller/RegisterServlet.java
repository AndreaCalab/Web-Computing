package progetto.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.UtenteDao;
import progetto.persistenza.model.Utente;

@WebServlet("/doRegister")
public class RegisterServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email=req.getParameter("email");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String nome=req.getParameter("nome");
		String cognome=req.getParameter("cognome");
		
		
		UtenteDao udao = DBManager.getInstance().getUtenteDao();
		Utente utente = udao.findByPrimaryKey(username);
		boolean logged;
		if (utente != null) {
			logged = false;
		}else {
				logged = true;
				
				DBManager.getInstance().getUtenteDao().saveOrUpdate(new Utente(username, "cliente", email, password, nome, cognome, "false"));
				HttpSession session=req.getSession();
				session = req.getSession();
				session.setAttribute("user", new Utente(username, "cliente", email, password, nome, cognome, "false"));
				session.setAttribute("sessionId", session.getId());
				Utente ut=(Utente)session.getAttribute("user");
				req.getServletContext().setAttribute(session.getId(),session);
		}
		if (logged) {
			//RequestDispatcher dispacher = req.getRequestDispatcher("views/index.html");
			//dispacher.forward(req, resp);
			resp.sendRedirect("/");
		}else {
			resp.sendRedirect("/notAuthorized.html");
		}
		
	
	}

}

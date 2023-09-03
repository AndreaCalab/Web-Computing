package progetto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.annotation.RequestParameterMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import progetto.persistenza.DBManager;
import progetto.persistenza.model.Asta;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Utente;
import progetto.persistenza.model.Messaggio;

@RestController
@CrossOrigin("http://localhost:4200")
public class AdminRestController {
	
	public Utente getUtente(HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			HttpSession session=(HttpSession)req.getServletContext().getAttribute(sessionId);
			Utente ut=(Utente)session.getAttribute("user");
			return ut;
		}
		else 
			return null;
	}
	@PostMapping("/utenteTipo")
	public List<String> getUtenteTipo(HttpServletRequest req,@RequestBody String tipo){
		List<String> t=new ArrayList<String>();
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String[] type=tipo.split(":")[1].split(";");
		if (req.getServletContext().getAttribute(sessionId) != null) {
			Utente ut=getUtente(req);
			String admin=type[0].substring(1,type[0].length());
			String client=type[1].substring(0,type[1].length()-2);
			System.out.println(admin+" "+client);
			System.out.println("Utente:"+ut.toString());
			t.add(ut.getTipo());
			return t;
		}
		else
			return null;
	}
	
	@PostMapping("/users")
	public List<Utente> getClients(HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Utente> utentiAdmin=new ArrayList<Utente>();
			List<Utente> utenti=DBManager.getInstance().getUtenteDao().findAll();
			for(Utente u:utenti) {
				if(u.getTipo().equals("cliente"))
					utentiAdmin.add(u);
			}
			return utentiAdmin;
		}
		else
			return null;
	}
	//La stringa è composta dai parametri quali l'username e l'opzione.
	@PostMapping("/promoteOrBan")
	public List<Utente> promoteOrUpdateUser(HttpServletRequest req,@RequestBody String param)  {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		System.out.println(param);
		String [] params=param.split(":")[1].split(";");
		String username=params[0].substring(1, params[0].length());
		if (req.getServletContext().getAttribute(sessionId) != null) {
			System.out.println(DBManager.getInstance().getUtenteDao().findByPrimaryKey(username).toString());
			Utente ut=DBManager.getInstance().getUtenteDao().findByPrimaryKey(username);
			String opzione=params[1].substring(0,params[1].length()-2);
			System.out.println(opzione);
			 if(opzione.equals("Promuovi ad amministratore"))
				 ut.setTipo("amministratore");
			 
			 if(opzione.equals("Banna"))
				 ut.setBanned("true");
			 
			 if(opzione.equals("Rimuovi il ban"))
				 ut.setBanned("false");
			 
			 DBManager.getInstance().getUtenteDao().saveOrUpdate(ut);
			 System.out.println("Input:"+ut.toString());
			 System.out.println("Output:"+DBManager.getInstance().getUtenteDao().findByPrimaryKey(username));
			 List<Utente> utentiAdmin=new ArrayList<Utente>();
			List<Utente> utenti=DBManager.getInstance().getUtenteDao().findAll();
				for(Utente u:utenti) {
					if(u.getTipo().equals("cliente"))
						utentiAdmin.add(u);
				}
				return utentiAdmin;
			
			
		}
		else
			return null;
		 
		
	}
	
	@PostMapping("/utenti")
	public List<Utente> getUtentiList(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Utente> list=DBManager.getInstance().getUtenteDao().findAll();
			Utente ut=getUtente(req);
			list.remove(ut);
			return list;
		}
		else
			return null;
	}
	
	

}

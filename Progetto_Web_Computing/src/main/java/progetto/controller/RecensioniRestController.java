package progetto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.RecensioneDao;
import progetto.persistenza.model.Recensione;
import progetto.persistenza.model.Utente;
import progetto.persistenza.model.Immobile;

@RestController
@CrossOrigin("http://localhost:4200")
public class RecensioniRestController {
	
	
	public String generateId() {
		Integer i=1;
		String pid="id"+i;
		Recensione r=DBManager.getInstance().getRecensioneDao().findByPrimaryKey(pid);
		if(!r.equals(null)) {
			while(!r.equals(null)) {
				i++;
				pid="id"+i;
			}
		}
		return pid;
	}
	
	
	
	@PostMapping("/dammiRecensioni")
	public List<Recensione> getRecensioni(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Recensione> rec=DBManager.getInstance().getRecensioneDao().findAll();
			return rec;
		}
		else
			return null;
	}
	
	
	@PostMapping("/addReview")
	public List<Recensione> addRecensione(@RequestBody Recensione rec,HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		Utente utente=getUtente(req);
		rec.setUtente(utente.getUsername());
		if (req.getServletContext().getAttribute(sessionId) != null) {
		if(rec.getDescrizione().equals(""))
			return DBManager.getInstance().getRecensioneDao().findAll();
		else {
			Integer i=1;
			String pid="id"+i;
			if(DBManager.getInstance().getRecensioneDao().findByPrimaryKey(pid)==null) {
				for(int l=2;l<10000;l++) {
					pid="id"+l;
					if(DBManager.getInstance().getRecensioneDao().findByPrimaryKey(pid)==null)
						break;
				}
			}
			rec.setId(pid);
			System.out.println("Output:"+rec.toString());
			boolean exist=false;
			for(Recensione re:DBManager.getInstance().getRecensioneDao().findAll()) {
				if(rec.getUtente().equals(re.getUtente()) && rec.getImmobile().equals(re.getImmobile()))
					exist=true;
			}
			if(!exist)
			DBManager.getInstance().getRecensioneDao().saveOrUpdate(rec);
			return DBManager.getInstance().getRecensioneDao().findAll();
		}
		
		}
		
		else
			return null;
	}
	
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
	

}

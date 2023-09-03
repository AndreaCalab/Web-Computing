package progetto.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import progetto.persistenza.DBManager;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Messaggio;
import progetto.persistenza.model.Utente;

@RestController
@CrossOrigin("http://localhost:4200")
public class MessaggiController {
	
	public String getUsername(String nome) {
		List<Utente> ut=DBManager.getInstance().getUtenteDao().findAll();
		String n="";
		for(Utente u:ut) {
			if((u.getNome()+" "+u.getCognome()).equals(nome))
				n=u.getUsername();
		}
		return n;
	}
	
	
	public String generateId() {
		Integer i=1;
	    String id="id"+i.toString();
	    Messaggio immobile=DBManager.getInstance().getMessagioDao().findByPrimaryKey(id);
	    if(immobile!=null) {
 		   while(immobile!=null) {
 			   i++;
 		       id="id"+i.toString();
 		      immobile=DBManager.getInstance().getMessagioDao().findByPrimaryKey(id);
 		   }
 		   return id;
	    }
	    else
	    	return id;
	    
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
	
	@PostMapping("/messaggi")
	public List<Messaggio> getMessagiClienti(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Messaggio> mess=DBManager.getInstance().getMessagioDao().findAll();
			List<Messaggio> sms=new ArrayList<Messaggio>();
			String username=getUtente(req).getUsername();
			for(Messaggio m:mess) {
				if(username.equals(m.getDestinatario()))
					sms.add(m);
			}
			return sms;
		}
		else
			return null;
		
	}
	
	@PostMapping("/addMessaggio")
	public List<Messaggio> addMessaggio(@RequestBody Messaggio m,HttpServletRequest req){
		System.out.println("Collegato!");
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			m.setId(generateId());
			m.setMittente(getUtente(req).getNome()+" "+getUtente(req).getCognome());
			m.setDestinatario(getUsername(m.getDestinatario()));
			DBManager.getInstance().getMessagioDao().saveOrUpdate(m);
			List<Messaggio> sms=DBManager.getInstance().getMessagioDao().findAll();
			return sms;
		}
		else
			return null;
		
	}
	
	@PostMapping("/answer")
	public List<Messaggio> rispondiMessagio(@RequestBody String risposta,HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			System.out.println("Risposta:"+risposta);
			String [] risp=risposta.split(":")[1].split(";");
			Messaggio m=new Messaggio();
			m.setId(generateId());
			m.setMittente(getUtente(req).getNome()+" "+getUtente(req).getCognome());
			m.setDestinatario(getUsername(risp[1]));
			System.out.println(risp[0].substring(1,risp[0].length()));
			m.setOggetto(DBManager.getInstance().getMessagioDao().findByPrimaryKey(risp[0].substring(1,risp[0].length())).getOggetto());
			if(risp[2].equals("Rispondi"))
			m.setOperazione("Nessuna");
			else
			m.setOperazione("Vendita");
			m.setMessaggio(risp[risp.length-1]);
			DBManager.getInstance().getMessagioDao().saveOrUpdate(m);
			//Si cambia proprietario dell'immobile una volta venduto
			if(m.getOperazione().equals("Vendita")) {
			Immobile imm=new Immobile();
			for(Immobile i:DBManager.getInstance().getImmobileDao().findAll()) {
				if(i.getDescrizione().equals(m.getOggetto().split(";")[1])) {
					imm=i;
					Utente u=DBManager.getInstance().getUtenteDao().findByPrimaryKey(m.getDestinatario());
					imm.setProprietario(u.getNome()+" "+u.getCognome());
				}
				}
			DBManager.getInstance().getImmobileDao().saveOrUpdate(imm);
			Messaggio mes=DBManager.getInstance().getMessagioDao().findByPrimaryKey(risp[0].substring(1,risp[0].length()));
			DBManager.getInstance().getMessagioDao().delete(mes);
			}
			List<Messaggio> mes=DBManager.getInstance().getMessagioDao().findAll();
			List<Messaggio> lm=new ArrayList<Messaggio>();
			for(Messaggio me:mes) {
				if(me.getDestinatario().equals(m.getMittente()))
					lm.add(me);
			}
			return lm;
		}
		else
			return null;
	}

}

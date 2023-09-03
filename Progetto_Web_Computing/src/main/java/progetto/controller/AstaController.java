package progetto.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import progetto.persistenza.DBManager;
import progetto.persistenza.model.Asta;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Utente;

@RestController
@CrossOrigin("http://localhost:4200")
public class AstaController {
	
	
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
	
	
	@PostMapping("/aste")
	public List<Asta> getAste(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
			return aste;
		}
		else
			return null;
	}
	
	@PostMapping("/addAsta")
	public List<Asta> addAsta(HttpServletRequest req,@RequestBody Asta a) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String anno=a.getData_di_scadenza().substring(0,4);
		String mese=a.getData_di_scadenza().substring(5,7);
		String giorno=a.getData_di_scadenza().substring(8,10);
		a.setData_di_scadenza(giorno+"/"+mese+"/"+anno);
		List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
		if (req.getServletContext().getAttribute(sessionId) != null) {
			if(DBManager.getInstance().getAstaDao().findByPrimaryKeys(a.getCodice())==null) {
				if(DBManager.getInstance().getAstaDao().findByParam(a.getImmobile())==null)
					DBManager.getInstance().getAstaDao().saveOrUpdate(a);
			}
		}	
		return aste;
	}
	
	//Si prendono le aste che non sono dell'utente che ha fatto il login.
	@PostMapping("/asteUtenti")
	public List<Asta> getAsteUtenti(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
			List<Asta> asteUtenti=new ArrayList<Asta>();
			Utente ut=getUtente(req);
			List<Immobile> imm=DBManager.getInstance().getImmobileDao().findAll();
			for(Asta a:aste) {
				for(Immobile i:imm) {
				if(a.getImmobile().equals(i.getDescrizione()) && !i.getProprietario().equals(ut.getNome()+" "+ut.getCognome()))
					asteUtenti.add(a);
				}
			}
			return asteUtenti;
		}
		else
			return null;
	}
	//Si prendono le aste che sono dell'utente che ha fatto il login.
	
	
	@PostMapping("/asteUtente")
	public List<Asta> getAsteUtente(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
			List<Asta> asteUtenti=new ArrayList<Asta>();
			Utente ut=getUtente(req);
			List<Immobile> imm=DBManager.getInstance().getImmobileDao().findAll();
			for(Asta a:aste) {
				for(Immobile i:imm) {
				if(a.getImmobile().equals(i.getDescrizione()) && i.getProprietario().equals(ut.getNome()+" "+ut.getCognome()))
					asteUtenti.add(a);
				}
			}
			return asteUtenti;
		}
		else
			return null;
	}
	
	@PostMapping("/aggiorna")
	public List<Asta> UpdateOfferta(@RequestBody String offerta,HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String[] param=offerta.split(":")[1].split(",");
		String codice=param[0].substring(1,param[0].length());
		Integer off=Integer.parseInt(param[1].substring(0,param[1].length()-2));
		if (req.getServletContext().getAttribute(sessionId) != null) {
			Asta a=DBManager.getInstance().getAstaDao().findByPrimaryKeys(codice);
			Utente ut=getUtente(req);
			if(a.getOfferta_attuale()<off) {
			a.setVincitore(ut.getNome()+" "+ut.getCognome());
			a.setOfferta_attuale(off);
			}
			DBManager.getInstance().getAstaDao().saveOrUpdate(a);
		}
		List<Asta> aste=getAsteUtenti(req);
		return aste;
		
	}
	
	
	@PostMapping("/termina")
	public List<Asta> terminaAsta(@RequestBody String cod,HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String codice=cod.split(":")[1].substring(1,cod.split(":")[1].length()-2);
		Asta a=DBManager.getInstance().getAstaDao().findByPrimaryKeys(codice);
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Asta> aste=getAsteUtente(req);
			System.out.println(codice);
			List<Immobile> imm=DBManager.getInstance().getImmobileDao().findAll();
			if(!a.getVincitore().equals(null)) {
				System.out.println(a.getVincitore());
				for(Immobile i:imm) {
					if(i.getDescrizione().equals(a.getImmobile())) {
						i.setProprietario(a.getVincitore());
						DBManager.getInstance().getImmobileDao().saveOrUpdate(i);
					}
				}
			}
			aste.remove(a);
			DBManager.getInstance().getAstaDao().delete(a);
			return aste;
		}
		else
			return null;
	}

}

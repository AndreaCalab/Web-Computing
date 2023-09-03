package progetto.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import javax.enterprise.inject.Any;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.hateoas.InputType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.annotation.RequestScope;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONStyle;
import net.minidev.json.parser.JSONParser;
import progetto.persistenza.DBManager;
import progetto.persistenza.model.Asta;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Messaggio;
import progetto.persistenza.model.Recensione;
import progetto.persistenza.model.Utente;

@RestController
@CrossOrigin("http://localhost:4200")
public class ImmobileRestController {
	
	
	public String generateId() {
		Integer i=1;
	    String id="id"+i.toString();
	    Immobile immobile=DBManager.getInstance().getImmobileDao().findByPrimaryKey(id);
	    if(immobile!=null) {
 		   while(immobile!=null) {
 			   i++;
 		       id="id"+i.toString();
 		      immobile=DBManager.getInstance().getImmobileDao().findByPrimaryKey(id);
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
	
	@PostMapping("/searchImmobile")
	public List<Immobile> getImmobiliSearchList(HttpServletRequest req,@RequestBody String filtro){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
		List<Immobile> immobili= DBManager.getInstance().getImmobileDao().findAll();
		List<Immobile> res=new ArrayList<Immobile>();
		String[] param=filtro.split(":")[1].split(" ");
		String tipo=param[0].substring(1,param[0].length());
		String operazione=param[1].substring(0, param[1].length()-2);
		System.out.println(tipo+" "+operazione);
		if(operazione.equals("acquisto"))
			res=getImmobiliUtenti(req);
		if(operazione.equals("vendita"))
			res=getImmobiliUtente(req);
		List<Immobile> res2=new ArrayList<Immobile>();
		for(Immobile i:res) {
			if(i.getCategoria().equals(tipo))
				res2.add(i);
		}
		
		return res2;
		}
		else
			return null;
	}
	
	@PostMapping("/immobili")
	public List<Immobile> getImmobili(HttpServletRequest req) {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Immobile> immobili = DBManager.getInstance().getImmobileDao().findAll();
			return immobili;
		}
		else
	     return null;
		
	     
		
	}
	@PostMapping("/immobiliUtente")
	public List<Immobile> getImmobiliUtente(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Immobile> immobili = DBManager.getInstance().getImmobileDao().findAll();
			List<Immobile> immUtente=new ArrayList<Immobile>();
			Utente ut=getUtente(req);
			for(Immobile i:immobili) {
				if(i.getProprietario().equals(ut.getNome()+" "+ut.getCognome()))
					immUtente.add(i);
			}
			return immUtente;
		}
		else
			return null;
	}
	@PostMapping("/immobiliUtenti")
	public List<Immobile> getImmobiliUtenti(HttpServletRequest req){
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if (req.getServletContext().getAttribute(sessionId) != null) {
			List<Immobile> immobili = DBManager.getInstance().getImmobileDao().findAll();
			List<Immobile> imm=new ArrayList<Immobile>();
			Utente ut=getUtente(req);
			for(Immobile i:immobili) {
				if(!i.getProprietario().equals(ut.getNome()+" "+ut.getCognome()))
					imm.add(i);
			}
			return imm;
		}
		else
			return null;
	}
	
	
	@PostMapping(value="/annuncio")
	public List<Immobile> addAnnuncio(@RequestBody Immobile immobile,HttpServletRequest req,HttpServletResponse resp) throws Exception {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		if(req.getServletContext().getAttribute(sessionId) != null) {
		Integer i=1;
       String id="id"+i.toString();
       Utente prop=getUtente(req);
       if(immobile.getCategoria().equals("") || immobile.getDescrizione().equals("") || immobile.getPrezzo().toString().isEmpty() || immobile.getMetri_quadri().toString().isEmpty() || immobile.getPosizione().equals("")) {
    	   return null;
       }
       else {
    	//Impostazione di un nuovo id nel caso viene trovato un immobile con quello corrente
    	immobile.setId(generateId());
    	immobile.setProprietario(prop.getNome()+" "+prop.getCognome());
    	DBManager.getInstance().getImmobileDao().saveOrUpdate(immobile);
    	return DBManager.getInstance().getImmobileDao().findAll();
       }
      }
		else
		return null;
	}
	//Modifica annuncio e oggetto dell'asta(se c'è)
	@PostMapping("/updateAnnuncio")
	public List<Immobile> updateAnnuncio(@RequestBody Immobile imm,HttpServletRequest req,HttpServletResponse resp) throws IOException{
		System.out.println("Collegato!");
		Boolean mod=false;
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		Utente ut=getUtente(req);
		List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
		List<Immobile> immList=DBManager.getInstance().getImmobileDao().findAll();
		List<Immobile> immUtente=new ArrayList<Immobile>();
		if(req.getServletContext().getAttribute(sessionId) != null) {
		Immobile imm2=DBManager.getInstance().getImmobileDao().findByPrimaryKey(imm.getId());
		for(Asta a:aste) {
			if(a.getImmobile().equals(imm2.getDescrizione())) {
				if(!imm.getDescrizione().equals(null) && !imm.getDescrizione().equals(imm2.getDescrizione())) {
					a.setImmobile(imm.getDescrizione());
					DBManager.getInstance().getAstaDao().saveOrUpdate(a);
				}
					
			}
		}
		Immobile imm3=new Immobile();
		imm3=imm;
		if(imm.getDescrizione().equals(""))
			imm3.setDescrizione(imm2.getDescrizione());
		if(imm.getMetri_quadri().equals(0))
			imm3.setMetri_quadri(imm2.getMetri_quadri());
		if(imm.getPosizione().equals(""))
			imm3.setPosizione(imm2.getPosizione());
		if(imm.getPrezzo().equals(0))
			imm3.setPrezzo(imm2.getPrezzo());
		if(ut.getTipo().equals("cliente"))
		imm3.setProprietario(ut.getNome()+" "+ut.getCognome());
		else
		imm3.setProprietario(imm2.getProprietario());
		for(Immobile i:immList) {
			if(i.getProprietario().equals(ut.getNome()+" "+ut.getCognome()))
				immUtente.add(i);
			else
				System.out.println(i.getProprietario()+" Proprietario:"+ut.getNome()+" "+ut.getCognome());
		}
		DBManager.getInstance().getImmobileDao().saveOrUpdate(imm3);
		}
		
		return immUtente;
	}
	
	
	
	//In questa funzione non si elimina solamente l'immobile,ma anche tutto ciò che è stato creato a riguardo.
	@PostMapping("/deleteAnnuncio")
	public Boolean deleteAnnuncio(@RequestBody String id,HttpServletRequest req) {
		System.out.println("Collegato!");
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String subid=id.substring(7,10);
		if(req.getServletContext().getAttribute(sessionId) != null) {
			Immobile imm=DBManager.getInstance().getImmobileDao().findByPrimaryKey(subid);
			imm.setId(subid);
			List<Recensione> rec=DBManager.getInstance().getRecensioneDao().findAll();
			List<Asta> aste=DBManager.getInstance().getAstaDao().findAll();
			List<Messaggio> sms=DBManager.getInstance().getMessagioDao().findAll();
			for(Recensione r:rec) {
				if(r.getImmobile().split(";")[0].equals(imm.getDescrizione()))
					DBManager.getInstance().getRecensioneDao().delete(r);
			}
			for(Asta a:aste) {
				if(a.getImmobile().equals(imm.getDescrizione()))
					DBManager.getInstance().getAstaDao().delete(a);
			}
			for(Messaggio m:sms) {
				if(m.getOggetto().split(";")[1].equals(imm.getDescrizione()))
					DBManager.getInstance().getMessagioDao().delete(m);
			}
			
			DBManager.getInstance().getImmobileDao().delete(imm);
			return true;
		}
		else
			return false;
		
	}
	
	@PostMapping(value="/filter")
	public List<Immobile> getImmobiliOrder(@RequestBody String filtro,HttpServletRequest req,Model model,HttpServletResponse resp) throws Exception {
		String [] sessionIdParam = req.getQueryString().split("&")[0].split("=");
		String sessionId = sessionIdParam[1];
		String param=filtro.split(":")[1];
		String[] p=param.split("}");
		String pio=p[0].substring(1, p[0].length()-1);
		if (req.getServletContext().getAttribute(sessionId) != null) {
		List<Immobile> immobili=DBManager.getInstance().getImmobileDao().findAll();
		System.out.println("Parametro:"+pio);
			Comparator<Immobile> orderByPrice= 
			(Immobile o1,Immobile o2)->o1.getPrezzo().compareTo(o2.getPrezzo());
			Comparator<Immobile> orderByMq= 
				(Immobile o1,Immobile o2)->o1.getMetri_quadri().compareTo(o2.getMetri_quadri());
		if(pio.equals("Prezzo"))
			Collections.sort(immobili,orderByPrice);
		else if(pio.equals("Metri quadri"))
			Collections.sort(immobili, orderByMq);
		
		
		return immobili;
		}
		else
			return null;
	}
	
	
	
	

}

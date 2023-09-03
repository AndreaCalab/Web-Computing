package progetto.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import progetto.persistenza.model.Utente;

@RestController
@CrossOrigin("http://localhost:4200")
public class Auth {
	@GetMapping("/checkAuth")
	public Boolean isAuth(HttpServletRequest req,String jsessionid) {
		System.out.println(jsessionid);
		HttpSession session = (HttpSession) req.getServletContext().getAttribute(jsessionid);
		if (session != null) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
}


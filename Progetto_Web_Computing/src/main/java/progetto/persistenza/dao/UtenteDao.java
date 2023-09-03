package progetto.persistenza.dao;

import java.util.List;

import progetto.persistenza.model.Utente;

public interface UtenteDao {
	
	public List<Utente> findAll();
	
	public Utente findByPrimaryKey(String username);
	
	public void saveOrUpdate(Utente utente);
	
	public void delete(Utente utente);
	

}

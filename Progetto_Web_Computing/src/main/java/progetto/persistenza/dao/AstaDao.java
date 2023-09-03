package progetto.persistenza.dao;

import java.util.List;

import progetto.persistenza.model.Asta;

public interface AstaDao {
	
	public List<Asta> findAll();
	
	public Asta findByPrimaryKeys(String codice);
	
	public Asta findByParam(String immobile);
	
	public void saveOrUpdate(Asta a);
	
	public void delete(Asta a);

}

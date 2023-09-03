package progetto.persistenza.dao;

import java.util.List;

import progetto.persistenza.model.Recensione;

public interface RecensioneDao {
	public List<Recensione> findAll();
	
	public Recensione findByPrimaryKey(String id);
	
	public void saveOrUpdate(Recensione recensione);
	
	public void delete(Recensione recensione);
}

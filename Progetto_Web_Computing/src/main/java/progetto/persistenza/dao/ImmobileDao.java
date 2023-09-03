package progetto.persistenza.dao;

import progetto.persistenza.model.Immobile;
import java.util.List;

public interface ImmobileDao {
		
	public List<Immobile> findAll();
	
	public Immobile findByPrimaryKey(String id);
	
	public void saveOrUpdate(Immobile immobile);
	
	public void delete(Immobile immobile);
	
	
	
}

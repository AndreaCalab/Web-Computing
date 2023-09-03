package progetto.persistenza.dao;

import java.util.List;

import progetto.persistenza.model.Messaggio;

public interface MessaggioDao {
		
		public List<Messaggio> findAll();
		
		public Messaggio findByPrimaryKey(String id);
		
		public void saveOrUpdate(Messaggio m);
		
		public void delete(Messaggio m); 
}

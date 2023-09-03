package progetto.persistenza.dao.postgres;
import java.sql.Connection;
import org.springframework.context.annotation.Bean;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.RecensioneDao;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Recensione;


public class RecensioneDaoPostgres implements RecensioneDao{
	Connection conn;
	
	public RecensioneDaoPostgres(Connection c) {
		this.conn=c;
		
	}

	@Override
	public List<Recensione> findAll() {
		List<Recensione> recensioni = new ArrayList<Recensione>();
		
		String query="select * from progetto.recensione";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				Recensione recensione;
					recensione=new Recensione();
					recensione.setId(rs.getString("id"));
					recensione.setUtente(rs.getString("utente"));
					recensione.setImmobile(rs.getString("immobile"));
					recensione.setDescrizione(rs.getString("descrizione"));
					recensioni.add(recensione);
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recensioni;
	}

	@Override
	public Recensione findByPrimaryKey(String id)  {
		Recensione recensione=null;
		String query="SELECT * FROM progetto.recensione where id=?";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				if(recensione==null)
					recensione=new Recensione();
				recensione.setId(rs.getString("id"));
				recensione.setUtente(rs.getString("utente"));
				recensione.setImmobile(rs.getString("immobile"));
				recensione.setDescrizione(rs.getString("descrizione"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recensione;
	}

	@Override
	public void saveOrUpdate(Recensione recensione) {
		if(DBManager.getInstance().getRecensioneDao().findByPrimaryKey(recensione.getId())!=null) {
			String upQuery="UPDATE progetto.recensione SET utente=?,immobile=?,descrizione=? where id=?";
			PreparedStatement ps;
			
			try {
				ps=conn.prepareStatement(upQuery);
				ps.setString(1, recensione.getUtente());
				ps.setString(2, recensione.getImmobile());
				ps.setString(3, recensione.getDescrizione());
				ps.setString(4, recensione.getId());
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			String saveQuery="INSERT INTO progetto.recensione VALUES(?, ?, ?, ?)";
			PreparedStatement ps;
			
			try {
				ps=conn.prepareStatement(saveQuery);
				ps.setString(1, recensione.getId());
				ps.setString(2, recensione.getUtente());
				ps.setString(3, recensione.getImmobile());
				ps.setString(4, recensione.getDescrizione());
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Recensione recensione) {
		String query="DELETE FROM progetto.recensione WHERE id=?";
		PreparedStatement ps;
		
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1, recensione.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

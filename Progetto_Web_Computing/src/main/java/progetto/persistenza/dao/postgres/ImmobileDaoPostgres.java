package progetto.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.ImmobileDao;
import progetto.persistenza.model.Immobile;


public class ImmobileDaoPostgres implements ImmobileDao {
	Connection conn;
	
	public ImmobileDaoPostgres(Connection c) {
		this.conn=c;
	}

	@Override
	public List<Immobile> findAll() {
		List<Immobile> immobili = new ArrayList<Immobile>();
		
		String query = "SELECT * FROM progetto.immobile";
			Statement st;
			ResultSet rs;
			try {
				st = conn.createStatement();
				rs = st.executeQuery(query);
			
			while (rs.next()) {
				Immobile immobile;
					immobile=new Immobile();
					immobile.setId(rs.getString("id"));
					immobile.setCategoria(rs.getString("categoria"));
					immobile.setDescrizione(rs.getString("descrizione"));
					immobile.setPrezzo(rs.getInt("prezzo"));
					immobile.setMetri_quadri(rs.getInt("metri_quadri"));
					immobile.setPosizione(rs.getString("posizione"));
					immobile.setProprietario(rs.getString("proprietario"));
					immobili.add(immobile);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		return immobili;
	}

	@Override
	public Immobile findByPrimaryKey(String id) {
		Immobile immobile=null;
		String query = "SELECT * FROM progetto.immobile WHERE id=?";
		try {
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, id);
	
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
				immobile=new Immobile();
				immobile.setCategoria(rs.getString("categoria"));
				immobile.setDescrizione(rs.getString("descrizione"));
				immobile.setPrezzo(rs.getInt("prezzo"));
				immobile.setPosizione(rs.getString("posizione"));
				immobile.setMetri_quadri(rs.getInt("metri_quadri"));
				immobile.setProprietario(rs.getString("proprietario"));
			}
				
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return immobile;
	}

	@Override
	public void saveOrUpdate(Immobile immobile) {
		if(DBManager.getInstance().getImmobileDao().findByPrimaryKey(immobile.getId())!=null) {
			String updateQuery="UPDATE progetto.immobile SET categoria=?,descrizione=?,prezzo=?,metri_quadri=?,posizione=?,proprietario=?"
					+"WHERE id=?";
			PreparedStatement ps;
			try {
				ps=conn.prepareStatement(updateQuery);
				ps.setString(1, immobile.getCategoria());
				ps.setString(2, immobile.getDescrizione());
				ps.setInt(3, immobile.getPrezzo());
				ps.setInt(4, immobile.getMetri_quadri());
				ps.setString(5, immobile.getPosizione());
				ps.setString(6, immobile.getProprietario());
				ps.setString(7, immobile.getId());
	
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			String saveQuery="INSERT INTO progetto.immobile VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps;
			try {
				ps=conn.prepareStatement(saveQuery);
				ps.setString(1, immobile.getCategoria());
				ps.setString(2, immobile.getDescrizione());
				ps.setInt(3, immobile.getPrezzo());
				ps.setInt(4, immobile.getMetri_quadri());
				ps.setString(5, immobile.getPosizione());
				ps.setString(6, immobile.getId());
				ps.setString(7, immobile.getProprietario());
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Immobile immobile) {
		String query="DELETE FROM progetto.immobile WHERE id=?";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1, immobile.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

package progetto.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.AstaDao;
import progetto.persistenza.model.Asta;
import progetto.persistenza.model.Immobile;

public class AstaDaoPostgres implements AstaDao{
	
	Connection conn;
	
	public AstaDaoPostgres(Connection c) {
		this.conn=c;
	}

	@Override
	public List<Asta> findAll() {
		List<Asta> aste=new ArrayList<Asta>();
		String query="SELECT * FROM progetto.asta";
		Statement st;
		ResultSet rs;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(query);
		
		while (rs.next()) {
			Asta a=new Asta();
			a.setImmobile(rs.getString("immobile"));
			a.setCodice(rs.getString("codice"));
			a.setData_di_scadenza(rs.getString("data_di_scadenza"));
			a.setOfferta_attuale(rs.getInt("offerta_attuale"));
			a.setOfferta_di_base(rs.getInt("offerta_di_base"));
			a.setStato_asta(rs.getString("stato_asta"));
			a.setVincitore(rs.getString("vincitore"));
			aste.add(a);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aste;
	}

	@Override
	public Asta findByPrimaryKeys(String codice) {
		Asta asta=null;
		String query = "SELECT * FROM progetto.asta WHERE codice=?";
		try {
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, codice);
		
	
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
				asta=new Asta();
				asta.setImmobile(rs.getString("immobile"));
				asta.setCodice(rs.getString("codice"));
				asta.setData_di_scadenza(rs.getString("data_di_scadenza"));
				asta.setOfferta_attuale(rs.getInt("offerta_attuale"));
				asta.setOfferta_di_base(rs.getInt("offerta_di_base"));
				asta.setStato_asta(rs.getString("stato_asta"));
				asta.setVincitore(rs.getString("vincitore"));
			}
				
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return asta;
	}

	@Override
	public void saveOrUpdate(Asta a) {
		if(DBManager.getInstance().getAstaDao().findByPrimaryKeys(a.getCodice())!=null) {
			String updateQuery="UPDATE progetto.asta SET immobile=?,offerta_di_base=?,offerta_attuale=?,stato_asta=?,vincitore=?,data_di_scadenza=?"
					+"WHERE codice=?";
			PreparedStatement ps;
			try {
				ps=conn.prepareStatement(updateQuery);
				ps.setString(1, a.getImmobile());
				ps.setInt(2, a.getOfferta_di_base());;
				ps.setInt(3, a.getOfferta_attuale());
				ps.setString(4, a.getStato_asta());
				ps.setString(5, a.getVincitore());
				ps.setString(6, a.getData_di_scadenza());
				ps.setString(7,a.getCodice());
	
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			String saveQuery="INSERT INTO progetto.asta VALUES(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps;
			try {
				ps=conn.prepareStatement(saveQuery);
				ps.setString(1, a.getImmobile());
				ps.setString(2, a.getCodice());
				ps.setInt(3, a.getOfferta_di_base());;
				ps.setInt(4, a.getOfferta_attuale());
				ps.setString(5, a.getStato_asta());
				ps.setString(6, a.getVincitore());
				ps.setString(7, a.getData_di_scadenza());
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Asta a) {
		String query="DELETE FROM progetto.asta WHERE codice=?";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1, a.getCodice());
			ps.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Asta findByParam(String immobile) {
		Asta asta=null;
		String query = "SELECT * FROM progetto.asta WHERE immobile=?";
		try {
		PreparedStatement st = conn.prepareStatement(query);
		st.setString(1, immobile);
		
	
		ResultSet rs = st.executeQuery();
		while(rs.next()) {
				asta=new Asta();
				asta.setImmobile(rs.getString("immobile"));
				asta.setCodice(rs.getString("codice"));
				asta.setData_di_scadenza(rs.getString("data_di_scadenza"));
				asta.setOfferta_attuale(rs.getInt("offerta_attuale"));
				asta.setOfferta_di_base(rs.getInt("offerta_di_base"));
				asta.setStato_asta(rs.getString("stato_asta"));
				asta.setVincitore(rs.getString("vincitore"));
			}
				
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return asta;
	}

}

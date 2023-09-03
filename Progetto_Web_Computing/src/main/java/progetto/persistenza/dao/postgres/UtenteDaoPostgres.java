package progetto.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.UtenteDao;
import progetto.persistenza.model.Utente;

public class UtenteDaoPostgres implements UtenteDao{
	Connection conn;
	
	public UtenteDaoPostgres(Connection c) {
		this.conn=c;
	}

	@Override
	public List<Utente> findAll() {
		List<Utente> utenti = new ArrayList<Utente>();
		String query = "select * from progetto.utente";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			while (rs.next()) {
				Utente utente = new Utente();
				utente.setTipo(rs.getString("tipo"));
				utente.setPassword(rs.getString("password"));
				utente.setEmail(rs.getString("email"));
				utente.setUsername(rs.getString("username"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setBanned(rs.getString("banned"));

				utenti.add(utente);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utenti;
	}

	@Override
	public Utente findByPrimaryKey(String username) {
		Utente utente = null;
		String query = "select * from progetto.utente where username = ?";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				utente = new Utente();
				utente.setUsername(rs.getString("username"));
				utente.setPassword(rs.getString("password"));
				utente.setEmail(rs.getString("email"));
				utente.setTipo(rs.getString("tipo"));
				utente.setNome(rs.getString("nome"));
				utente.setCognome(rs.getString("cognome"));
				utente.setBanned(rs.getString("banned"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utente;
	}

	@Override
	public void saveOrUpdate(Utente utente) {
		if (DBManager.getInstance().getUtenteDao().findByPrimaryKey(utente.getUsername())== null) {
			String insertStr = "INSERT INTO progetto.utente VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(insertStr);
				st.setString(1, utente.getTipo());
				st.setString(2, utente.getEmail());
				st.setString(3, utente.getPassword());
				st.setString(4, utente.getUsername());
				st.setString(5, utente.getNome());
				st.setString(6, utente.getCognome());
				st.setString(7, utente.getBanned());
				
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			String updateStr = "UPDATE progetto.utente SET tipo=?,email=?,password=?,nome=?,cognome=?,banned=? WHERE username=?";
			
			PreparedStatement st;
			try {
				st = conn.prepareStatement(updateStr);
			
				st.setString(1, utente.getTipo());
				st.setString(2, utente.getEmail());
				st.setString(3, utente.getPassword());
				st.setString(4, utente.getNome());
				st.setString(5, utente.getCognome()); 
				st.setString(6, utente.getBanned());
				st.setString(7, utente.getUsername());
				st.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void delete(Utente utente) {
		String query="DELETE FROM progetto.utente WHERE username=?";
		PreparedStatement ps;
		try {
			ps=conn.prepareStatement(query);
			ps.setString(1, utente.getUsername());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

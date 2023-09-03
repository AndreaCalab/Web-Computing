package progetto.persistenza.dao.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import progetto.persistenza.DBManager;
import progetto.persistenza.dao.MessaggioDao;
import progetto.persistenza.model.Immobile;
import progetto.persistenza.model.Messaggio;

public class MessaggioDaoPostgres implements MessaggioDao{
	
	Connection con;
	
	public MessaggioDaoPostgres(Connection c) {
		this.con=c;
		
	}

	@Override
	public List<Messaggio> findAll() {
		List<Messaggio> sms=new ArrayList<Messaggio>();
		String query="SELECT * FROM progetto.messaggio";
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Messaggio m=new Messaggio();
				m.setId(rs.getString("id"));
				m.setMittente(rs.getString("mittente"));
				m.setDestinatario(rs.getString("destinatario"));
				m.setOggetto(rs.getString("oggetto"));
				m.setOperazione(rs.getString("operazione"));
				m.setMessaggio(rs.getString("messaggio"));
				sms.add(m);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return sms;
	}

	@Override
	public Messaggio findByPrimaryKey(String id) {
		Messaggio m =null;
		String query="SELECT * FROM progetto.messaggio WHERE id=?";
		try {
			PreparedStatement st = con.prepareStatement(query);
			st.setString(1, id);
		
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				m=new Messaggio();
				m.setId(rs.getString("id"));
				m.setMittente(rs.getString("mittente"));
				m.setDestinatario(rs.getString("destinatario"));
				m.setOggetto(rs.getString("oggetto"));
				m.setOperazione(rs.getString("operazione"));
				m.setMessaggio(rs.getString("messaggio"));
				}
					
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		return m;
	}

	@Override
	public void saveOrUpdate(Messaggio m) {
		if(DBManager.getInstance().getMessagioDao().findByPrimaryKey(m.getId())!=null) {
			String upQuery="UPDATE progetto.messaggio SET mittente=?,destinatario=?,oggetto=?,operazione=?,messaggio=?"+
					"WHERE id=?";
			PreparedStatement ps;
			try {
				ps=con.prepareStatement(upQuery);
				ps.setString(1, m.getMittente());
				ps.setString(2, m.getDestinatario());
				ps.setString(3, m.getOggetto());
				ps.setString(4, m.getOperazione());
				ps.setString(5, m.getMessaggio());
				ps.setString(6, m.getId());
				
				ps.executeUpdate();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		else {
			String query="INSERT INTO progetto.messaggio VALUES( ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps;
			try {
				ps=con.prepareStatement(query);
				ps.setString(1, m.getId());
				ps.setString(2, m.getMittente());
				ps.setString(3, m.getDestinatario());
				ps.setString(4, m.getOggetto());
				ps.setString(5, m.getOperazione());
				ps.setString(6, m.getMessaggio());
				ps.executeUpdate();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

	@Override
	public void delete(Messaggio m) {
		String query="DELETE FROM progetto.messaggio WHERE id=?";
		PreparedStatement ps;
		try {
			ps=con.prepareStatement(query);
			ps.setString(1, m.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	

}

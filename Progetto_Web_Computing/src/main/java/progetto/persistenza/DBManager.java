package progetto.persistenza;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import progetto.persistenza.dao.AstaDao;
import progetto.persistenza.dao.ImmobileDao;
import progetto.persistenza.dao.MessaggioDao;
import progetto.persistenza.dao.RecensioneDao;
import progetto.persistenza.dao.UtenteDao;
import progetto.persistenza.dao.postgres.AstaDaoPostgres;
import progetto.persistenza.dao.postgres.ImmobileDaoPostgres;
import progetto.persistenza.dao.postgres.MessaggioDaoPostgres;
import progetto.persistenza.dao.postgres.RecensioneDaoPostgres;
import progetto.persistenza.dao.postgres.UtenteDaoPostgres;
import progetto.persistenza.model.Messaggio;
import progetto.persistenza.model.Recensione;

public class DBManager {
	private static DBManager instance = null;
	Connection conn=null;
	
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
	}
	
	public Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
	public UtenteDao getUtenteDao()   {
		return new UtenteDaoPostgres(getConnection());
	}
	public ImmobileDao getImmobileDao()  {
		return new ImmobileDaoPostgres(getConnection());
	}
	
	public RecensioneDao getRecensioneDao()  {
		return new RecensioneDaoPostgres(getConnection());
	}
	
	public AstaDao getAstaDao() {
		return new AstaDaoPostgres(getConnection());
	}
	
	public MessaggioDao getMessagioDao() {
		return new MessaggioDaoPostgres(getConnection());
	}
	
}

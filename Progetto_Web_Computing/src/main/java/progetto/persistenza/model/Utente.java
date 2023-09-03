package progetto.persistenza.model;

public class Utente {
		String username;
		String tipo;
		String email;
		String password;
		String nome,cognome;
		String banned="false";
		
		
		public Utente() {
			username=null;
			tipo=null;
			email=null;
			password=null;
			nome=null;
			cognome=null;
			banned="false";
		}
		
		
		public Utente(String username, String tipo, String email, String password, String nome, String cognome,
				String banned) {
			super();
			this.username = username;
			this.tipo = tipo;
			this.email = email;
			this.password = password;
			this.nome = nome;
			this.cognome = cognome;
			this.banned = banned;
		}



		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}


		public String getNome() {
			return nome;
		}


		public void setNome(String nome) {
			this.nome = nome;
		}


		public String getCognome() {
			return cognome;
		}


		public void setCognome(String cognome) {
			this.cognome = cognome;
		}


		public String getBanned() {
			return banned;
		}


		public void setBanned(String banned) {
			this.banned = banned;
		}


		@Override
		public String toString() {
			return this.tipo+" "+this.nome+" "+this.cognome+" "+this.email+" "+this.password+" "+this.username+" "+this.banned;
		}
		
		
		
}

package progetto.persistenza.model;

public class Messaggio {
		String id;
		String mittente;
		String destinatario;
		String oggetto;
		String operazione;
		String messaggio;
		
		public Messaggio() {}
		
		
		
		public Messaggio(String id, String mittente, String destinatario, String oggetto, String operazione,
				String messaggio) {
			super();
			this.id = id;
			this.mittente = mittente;
			this.destinatario = destinatario;
			this.oggetto = oggetto;
			this.operazione = operazione;
			this.messaggio = messaggio;
		}



		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}


		public String getMittente() {
			return mittente;
		}
		public void setMittente(String mittente) {
			this.mittente = mittente;
		}
		public String getDestinatario() {
			return destinatario;
		}
		public void setDestinatario(String destinatario) {
			this.destinatario = destinatario;
		}
		public String getOggetto() {
			return oggetto;
		}
		public void setOggetto(String oggetto) {
			this.oggetto = oggetto;
		}
		public String getOperazione() {
			return operazione;
		}
		public void setOperazione(String operazione) {
			this.operazione = operazione;
		}



		public String getMessaggio() {
			return messaggio;
		}



		public void setMessaggio(String messaggio) {
			this.messaggio = messaggio;
		}
		
		
}

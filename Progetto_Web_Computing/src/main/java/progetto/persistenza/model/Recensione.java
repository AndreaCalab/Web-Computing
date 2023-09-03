package progetto.persistenza.model;

public class Recensione {
	
		String id;
		String utente;
		String immobile;
		String descrizione;
		
		public Recensione() {}
		
		public Recensione(String i,String u,String im,String descr) {
			this.id=i;
			this.utente=u;
			this.immobile=im;
			this.descrizione=descr;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getUtente() {
			return utente;
		}
		public void setUtente(String utente) {
			this.utente = utente;
		}
		public String getImmobile() {
			return immobile;
		}
		public void setImmobile(String immobile) {
			this.immobile = immobile;
		}
		public String getDescrizione() {
			return descrizione;
		}
		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
		
		@Override
		public String toString() {
			return this.id+" "+this.utente+" "+this.immobile+" "+this.descrizione;
		}
		public boolean equal(Recensione r) {
			return r.id==this.id;
		}
		
}

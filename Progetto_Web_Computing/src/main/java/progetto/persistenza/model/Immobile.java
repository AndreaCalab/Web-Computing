package progetto.persistenza.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Immobile {
		String id;
		String categoria,descrizione,posizione;
		Integer prezzo;
		Integer metri_quadri;
		String proprietario;
		List<Recensione> recensioni=new ArrayList<Recensione>();
		
		public Immobile() {}
		
		public Immobile(String id, String categoria, String descrizione, String posizione, Integer prezzo,
				Integer metri_quadri, String proprietario) {
			super();
			this.id = id;
			this.categoria = categoria;
			this.descrizione = descrizione;
			this.posizione = posizione;
			this.prezzo = prezzo;
			this.metri_quadri = metri_quadri;
			this.proprietario = proprietario;
			
		}
		
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCategoria() {
			return categoria;
		}

		public void setCategoria(String categoria) {
			this.categoria = categoria;
		}

		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}

		public String getPosizione() {
			return posizione;
		}

		public void setPosizione(String posizione) {
			this.posizione = posizione;
		}

		public Integer getPrezzo() {
			return prezzo;
		}

		public void setPrezzo(Integer prezzo) {
			this.prezzo = prezzo;
		}

		public Integer getMetri_quadri() {
			return metri_quadri;
		}

		public void setMetri_quadri(Integer metri_quadri) {
			this.metri_quadri = metri_quadri;
		}

		public String getProprietario() {
			return proprietario;
		}

		public void setProprietario(String proprietario) {
			this.proprietario = proprietario;
		}
		
		public List<Recensione> getRecImmobile(List<Recensione> l){
			for(Recensione i:l) {
				if(i.getImmobile().equals(this.descrizione))
					this.recensioni.add(i);
			}
			return this.recensioni;
		}
		
		public void setRecensioni(List<Recensione> recensioni) {
			this.recensioni = recensioni;
		}

		@Override
		public String toString() {
			return this.id+" "+this.categoria+" "+this.descrizione+" "+this.prezzo+" "+this.metri_quadri+" "+this.posizione;
		}
		
}

package progetto.persistenza.model;

public class Asta {
	
	String codice;
	String immobile;
	Integer offerta_di_base;
	Integer offerta_attuale;
	String stato_asta;
	String vincitore;
	String data_di_scadenza;
	
	public Asta() {}
	
	public Asta(String codice, String immobile, Integer offerta_di_base, Integer offerta_attuale, String stato_asta,
			String vincitore, String data_di_scadenza) {
		super();
		this.codice = codice;
		this.immobile = immobile;
		this.offerta_di_base = offerta_di_base;
		this.offerta_attuale = offerta_attuale;
		this.stato_asta = stato_asta;
		this.vincitore = vincitore;
		this.data_di_scadenza = data_di_scadenza;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getImmobile() {
		return immobile;
	}

	public void setImmobile(String immobile) {
		this.immobile = immobile;
	}

	public Integer getOfferta_di_base() {
		return offerta_di_base;
	}

	public void setOfferta_di_base(Integer offerta_di_base) {
		this.offerta_di_base = offerta_di_base;
	}

	public Integer getOfferta_attuale() {
		return offerta_attuale;
	}

	public void setOfferta_attuale(Integer offerta_attuale) {
		this.offerta_attuale = offerta_attuale;
	}

	public String getStato_asta() {
		return stato_asta;
	}

	public void setStato_asta(String stato_asta) {
		this.stato_asta = stato_asta;
	}

	public String getVincitore() {
		return vincitore;
	}

	public void setVincitore(String vincitore) {
		this.vincitore = vincitore;
	}

	public String getData_di_scadenza() {
		return data_di_scadenza;
	}

	public void setData_di_scadenza(String data_di_scadenza) {
		this.data_di_scadenza = data_di_scadenza;
	}
	
	

}

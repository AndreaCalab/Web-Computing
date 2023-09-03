
function validateLogin(username, password, e){
	if (username.value == ""){
		alert("Inserisci username")
		e.preventDefault();
	}
	
}


function Annuncio(categoria,descrizione,prezzo,metri_quadri,posizione,proprietario){
	this.categoria=categoria;
	this.descrizione=descrizione;
	this.prezzo=prezzo;
	this.metri_quadri=metri_quadri;
	this.posizione=posizione;
	this.proprietario=proprietario;
	this.fullAnnuncio=function(){
		return this.categoria+','+this.descrizione+','+this.prezzo=prezzo+','+this.metri_quadri+','+this.posizione;
	};
}


function validateAnnuncio(categoria,descrizione,prezzo,metri_quadri,posizione,proprietario,e){
	var recensione=new Annuncio(categoria,descrizione,prezzo,metri_quadri,posizione);
	if(categoria.value=="" || descrizione.value=="" || prezzo.value=="" || metri_quadri.value=="" || posizione.value=="" || priprietario.value==""){
		alert("Inserire Annuncio");
		e.preventDefault();
	}
}

var butEnter=document.getElementById("Invia");
butEnter.addEventListener("click", function(event){
	
};


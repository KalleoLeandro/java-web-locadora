/**
 * Arquivo responsável por formatar as máscaras de input
 */
 
function masc_rg(objeto) {
	var retorno = "";
	var padrao = /\D/g;
	var numero;
	
	numero = objeto.value.replace(padrao, "");
	
	parte1 = numero.substr(0,2);
	if (parte1.length > 0) {
		retorno = parte1;
	}
	
	parte2 = numero.substr(2,3);
	if (parte2.length > 0) {
		retorno += "." + parte2;
	}
	
	parte3 = numero.substr(5,3);
	if (parte3.length > 0) {
		retorno += "." + parte3;
	}
	
	padrao = /[^0-9Xx]/g;
	numero = objeto.value.replace(padrao, "");
	parte4 = numero.substr(8,1);
	if (parte4.length > 0) {
		retorno += "-" + parte4;
	}
	
	objeto.value = retorno;
}





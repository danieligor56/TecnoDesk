package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class DocumentUtils {
	
	public String documentFormatter(String doc) {
	    if (doc == null || doc.isEmpty()) {
	        throw new IllegalArgumentException("O documento não pode ser nulo ou vazio");
	    }
	    if (doc.length() == 11) {
	        // Formatar CPF no padrão xxx.xxx.xxx-xx
	        return doc.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	    } else if (doc.length() == 14) {
	        // Formatar CNPJ no padrão xx.xxx.xxx/xxxx-xx
	        return doc.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	    } else {
	        throw new IllegalArgumentException("O documento deve ter 11 (CPF) ou 14 (CNPJ) dígitos");
	    }
	}


}

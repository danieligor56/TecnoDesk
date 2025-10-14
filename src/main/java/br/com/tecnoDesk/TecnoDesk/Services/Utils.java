package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import exception.BadRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;

@Service
public class Utils {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public Object callNextId(long codEmpresa,long codRefTabela){
		return entityManager
				.createNativeQuery("SELECT fn_proximo_sequencial(:codEmpresa, :codRefTabela)")
				.setParameter("codEmpresa", codEmpresa)
				.setParameter("codRefTabela", codRefTabela)
				.getSingleResult();	   
	}
	
 


	
}

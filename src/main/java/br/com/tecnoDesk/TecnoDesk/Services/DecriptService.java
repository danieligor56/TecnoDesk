package br.com.tecnoDesk.TecnoDesk.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tecnoDesk.TecnoDesk.Component.EncryptionUtil;

@Service
public class DecriptService {
	
	@Autowired
	EncryptionUtil secUtil;
	
	public Long decriptCodEmp(String codEmp) throws Exception {
		String decriptedCodEmp = secUtil.decrypt(codEmp);
		return Long.valueOf(decriptedCodEmp);
	}
	
}

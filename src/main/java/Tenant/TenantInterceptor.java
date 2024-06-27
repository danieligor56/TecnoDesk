package Tenant;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor implements HandlerInterceptor {
	
	
	
	/*
	 * @Override public boolean preHandle(HttpServletRequest request,
	 * HttpServletResponse response,Object handler)
	 * 
	 * throws Exception {
	 * 
	 * String codEmpresa = request.getHeader("CodEmpresa");
	 * 
	 * if(codEmpresa != null) {
	 * 
	 * request.setAttribute("codEmpresa", codEmpresa); }
	 * 
	 * return true;
	 * 
	 * }
	 */
	 
	
}

package br.com.tecnoDesk.TecnoDesk.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.tecnoDesk.TecnoDesk.Entities.Produtos;

@Repository
public interface ProdutoRepository extends JpaRepository<Produtos, Long> {

			@Query(
					value = "select * from produtos p  where p.codigo_empresa = :CodEmp",
					nativeQuery = true
					)
			List<Produtos> listarProdutos(long CodEmp);
			
}

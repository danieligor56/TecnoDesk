import { Cliente } from "./Cliente";
import { Colaborador } from "./Colaborador";
import { Empresa } from "./Empresa";

export interface Os_entrada{

  empresa: Empresa;
  cliente: Cliente;
  colaborador: Colaborador;
  aparelhos: Number;
  descricaoModelo: string;
  checkList: string;
  reclamacaoCliente: string;
  laudoChamado: string;
  statusOS: Number;

}
	
	
import { Cliente } from "./Cliente";
import { Colaborador } from "./Colaborador";
import { Empresa } from "./Empresa";

export interface Os_entrada{
  
  id: number;
  numOs: number;
  empresa: Empresa;
  cliente: Cliente;
  colaborador: Colaborador;
  tecnico_responsavel:Colaborador;
  dataAbertura: string;
  aparelhos: number;
  descricaoModelo: string;
  checkList: string;
  reclamacaoCliente: string;
  initTest: string;
  statusOS: string;
  prioridadeOS:string;
  marcaAparelho: string;
  snAparelho: string;

}
	
	
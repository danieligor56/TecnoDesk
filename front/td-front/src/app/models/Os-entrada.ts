import { Cliente } from "./Cliente";
import { Colaborador } from "./Colaborador";
import { Empresa } from "./Empresa";

export interface Os_entrada{
  
  id: number;
  numOs: number;
  empresa: Empresa;
  cliente: Cliente;
  colaborador: Colaborador;
  dataAbertura: string;
  aparelhos: number;
  descricaoModelo: string;
  checkList: string;
  reclamacaoCliente: string;
  initTest: string;
  statusOS: number;
  prioridadeos:Number;
  marcaAparelho: string;
  snAparelho: string;

}
	
	
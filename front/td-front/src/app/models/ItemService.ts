import { Empresa } from "./Empresa";

export interface ItemService{
    
	id:string;
    empresa:Empresa;
    nomeServico:string;
    descServico:string;
    valorServicoHora:number;
    valorServicoUnidade:number;

}
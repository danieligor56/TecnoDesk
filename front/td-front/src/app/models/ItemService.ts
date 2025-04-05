import { Empresa } from "./Empresa";

export interface ItemService{
    
	id:string;
    empresa:Empresa;
    nomeServico:string;
    descricaoServico:string;
    valorServicoHora:number;
    valorServicoUnidade:number;

}
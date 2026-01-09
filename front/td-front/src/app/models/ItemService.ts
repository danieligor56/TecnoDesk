import { NumberSymbol } from "@angular/common";
import { Empresa } from "./Empresa";

export interface ItemService{
    
	id?:string;
    sequencial?:number;
    empresa?:Empresa;
    nomeServico:string;
    descricaoServico:string;
    valorServicoHora:number;
    valorServicoUnidade:number;

}
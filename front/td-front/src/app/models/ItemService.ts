import { Empresa } from "./Empresa";

export interface ItemService{
    
	id:BigInt
	empresa:Empresa
    nomeServico:string
    descServico:string
    valorServico:number
    custoServico:number

}
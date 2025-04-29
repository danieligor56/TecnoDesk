import { Empresa } from "./Empresa";

export interface OrcamentoItem{

    id?:bigint;
    empresa:Empresa;
    codOrcamento:number,
    codigoItem:number;
    nomeServicoAvulso:string,
    descricaoServicoAvulso:string,
    valorUnidadeAvulso?:number;
    valorHoraAvulso?:number;
    isAvulso:boolean
   
}


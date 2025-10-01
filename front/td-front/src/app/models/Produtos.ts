import { Empresa } from "./Empresa";

export interface Produtos {
    id: number;
    empresa: Empresa;
    nome: string; 
    descricao?: string;
    marca:string;
    preco: number;
    precoCusto:number; 
    quantidadeEstoque: number;
    categoria?: string;
    unidadeMedida?: string; 
}
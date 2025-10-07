import { Empresa } from "./Empresa";

export interface Produtos {
    id: number;
    empresa: Empresa;
    nome: string; 
    descricao?: string;
    marca:string;
    codigo_barras?: number
    preco: number;
    precoCusto:number; 
    quantidadeEstoque: number;
    categoria?: string;
    unidadeMedida?: string; 
    produtoAtivo: boolean;
}
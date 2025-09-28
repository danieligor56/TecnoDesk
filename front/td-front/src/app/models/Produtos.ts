import { Empresa } from "./Empresa";

export interface Produtos {
    id: number;
    empresa: Empresa;
    nome: string; 
    descricao?: string;
    preco: number; 
    quantidadeEstoque: number;
    categoria?: string;
    unidadeMedida?: string; 
}
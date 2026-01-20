import { Empresa } from "./Empresa";
import { CategoriaProduto } from "../services/categoria-produto.service";
import { UnidadeMedida } from "../services/unidade-medida.service";

export interface Produtos {
    id?: number;
    sequencial?: number;
    empresa?: Empresa;
    nome: string;
    descricao?: string;
    marca: string;
    codigo_barras?: number
    preco: number;
    precoCusto: number;
    quantidadeEstoque: number;
    categoria?: CategoriaProduto;
    unidadeMedida?: UnidadeMedida;
    produtoAtivo: boolean;
}
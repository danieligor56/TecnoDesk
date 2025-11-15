import { Empresa } from "./Empresa";
import { Produtos } from "./Produtos";

export interface ControleEstoque {
    codigoItem?: number;
    empresa?: Empresa;
    produto?: Produtos;
    estoqueAtual: number;
    estoqueMinimo: number;
    estoqueMaximo?: number;
    observacao?: string;
    ativo: boolean;
}


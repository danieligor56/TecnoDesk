import { Empresa } from "./Empresa";
import { Produtos } from "./Produtos";

export enum TipoMovimentacao {
    ENTRADA = 'ENTRADA',
    SAIDA = 'SAIDA',
    AJUSTE = 'AJUSTE'
}

export interface MovimentacaoEstoque {
    id?: number;
    empresa?: Empresa;
    produto?: Produtos;
    tipoMovimentacao: TipoMovimentacao;
    quantidade: number;
    estoqueAnterior: number;
    estoqueAtual: number;
    dataMovimentacao?: string;
    observacao?: string;
    usuarioResponsavel?: string;
    origemMovimentacao?: string;
}


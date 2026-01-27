import { Os_entrada } from "./Os-entrada";

export interface HistoricoOS {
    id?: number;
    os?: Os_entrada;
    descricao: string;
    dataAlteracao: string;
    responsavel: string;
}

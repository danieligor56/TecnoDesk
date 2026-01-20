import { Cliente } from "./Cliente";
import { Colaborador } from "./Colaborador";
import { Empresa } from "./Empresa";

export interface Os_Mecanica {
  id?: number;
  sequencial?: number;
  empresa: Empresa;
  cliente: Cliente;
  colaborador: Colaborador;
  tecnico_responsavel?: Colaborador;
  dataAbertura: string;
  // Vehicle data
  placa: string;
  marca: string;
  modelo: string;
  ano: string;
  cor: string;
  logo?: string;
  combustivel?: string;
  // OS data
  kmEntrada?: number;
  diagnosticoInicial?: string;
  prioridadeOS: string;
  statusOS?: string;
  laudoTecnico?: string;
}

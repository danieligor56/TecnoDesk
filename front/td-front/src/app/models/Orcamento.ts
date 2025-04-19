import { EmailValidator } from "@angular/forms";
import { Empresa } from "./Empresa";
import { Os_entrada } from "./Os-entrada";
import { OrcamentoItem } from "./OrcamentoItem";

export interface Orcamento{
    
    id:number;
    empresa:Empresa;
    oSentrada:Os_entrada;
    itens:OrcamentoItem[];
    statusOR:number
	
}
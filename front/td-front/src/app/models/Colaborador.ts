import { Ocupacao } from "../enuns/Ocupacao";
import { Empresa } from "./Empresa";

export interface Colaborador{

    id?:any;
	empresa:Empresa;
	nome:string;
	documento:string;
    ocupacao:Ocupacao[];
	email:string;
	cel1:string;
	estado:string;
	bairro:string;
	cidade:string;	
	logradouro:string;	
	numero:number;
	obs:string;
	cep:String;
	// ********* //
    atvReg:boolean;

}
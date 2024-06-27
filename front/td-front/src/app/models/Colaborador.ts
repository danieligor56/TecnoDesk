import { Empresa } from "./Empresa";

export interface Colaborador{

    id?:any;
	empresa:Empresa;
	nome:string;
	documento:any
    ocupacao:any;
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
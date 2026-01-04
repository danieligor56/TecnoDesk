import { Empresa } from "./Empresa";

export interface Cliente{

	id?:number;
	sequencial?:number;
	empresa:Empresa;
    nome:string;
	email:string;
	documento:string;
	cel1:string;
	cel2:string;

	//BLOCO ENDEREÇO// 
    cep:string;
	estado:string;
    cidade:string;
	logradouro:string;
	numero:string;
	obs:string;
		// *** // 
	atvReg:boolean;
}	
	


	
	
	

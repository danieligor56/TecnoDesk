export interface Cliente{

	id?:bigint;
    nome:string;
	email:string;
	documento:string;
	tel:string;
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

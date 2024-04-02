export interface Colaborador{

    id?:any;
	nome:string;
    //DOCUMNTO VAI FICAR any ENQUANTO COLOCO A FUNÇÃO PARA TRATAR
	documento:any
    //Vai ficar como ANY até a importação. 
    ocupacao:any;
	email:string;
    //Vai ficar como ANY até a importação.
	tel1:any;
	//Vai ficar como ANY até a importação.
	cel1:any;
	
	// BLOCO ENDEREÇO//
	estado:string;
	cidade:string;	
	logradouro:string;	
	numero:number;
	obs:string;
	// ********* //
	
    atvReg:boolean;

 

}
import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { MatRadioModule } from '@angular/material/radio';
import Validation from 'src/app/validators/validatorEmail';
import { CdkNestedTreeNode } from '@angular/cdk/tree';
import { EmpresaService } from 'src/app/services/empresa.service';
import { ToastrService } from 'ngx-toastr';
import { CepService } from 'src/app/services/autoCep.service';



@Component({
  selector: 'app-registro-inicial',
  templateUrl: './registro-inicial.component.html',
  styleUrls: ['./registro-inicial.component.css']
})
export class RegistroInicialComponent implements OnInit {
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  checked = false;
  indeterminate = false;
  labelPosition: 'before' | 'after' = 'after';
  disabled = false;
  isCpf: boolean = false;
  isCnpj: boolean = true;
  isEmailMacth:boolean = false;
  habilitarBotao:boolean = false;
 

  constructor(
    private _formBuilder: FormBuilder,
    private empresaService: EmpresaService,
    private toast: ToastrService,
    private servCep:CepService
     
  ) { }

  ngOnInit(): void {

     this.firstFormGroup = this._formBuilder.group({
      firstCtrl:['', Validators.required],
      razaoSocial:['',Validators.required],
      nomEmpresa:['',Validators.required],
      docEmpresa:['',Validators.required],
      mail:['',[Validators.required,Validators.email]],
      confirmEmail:['',[Validators.required,Validators.email]],
      cel:['',Validators.required],
      tel:[''],
      site:['']

    },{
       validators: [Validation.match('mail','confirmEmail')]
    });

    this.secondFormGroup = this._formBuilder.group({
      cep: ['', Validators.required],
      logra: ['',Validators.required],
      num:['',Validators.required],
      comp:['',Validators.required],
      bairro:['',Validators.required],
      municipio:['',Validators.required],
      uf:['',Validators.required]
      
    });

    this.thirdFormGroup = this._formBuilder.group({
      nomeCompleto: ['', Validators.required],
      mail: ['',[Validators.required,Validators.email]],
      confirmEmail:['',[Validators.required,Validators.email]],
      pass: ['',Validators.required],
      passConfirm: ['',Validators.required]

    },
  {
       validators: [Validation.match('mail','confirmEmail')]
       
    });
    
    
  }

  validarEmpresaExistenteDoc(){ 
    debugger;
     const docExists = this.empresaService.checkCompanyExistsByDoc(this.firstFormGroup.get('docEmpresa').value).subscribe( response =>
     {
        if(response){
          this.firstFormGroup.get('docEmpresa').setValue('')
       this.toast.error("Atenção, já existe uma empresa cadastrada com este documento");
       
      }
     }
     )
      

  }

  validarPrimeiroPasso():boolean{

    const nomEmpresa = this.firstFormGroup.get('nomEmpresa').valid;
    const docEmpresa = this.firstFormGroup.get('docEmpresa').valid;
    const mail = this.firstFormGroup.get('mail').valid;
    const cel = this.firstFormGroup.get('cel').valid;
    
    const validaMails = this.firstFormGroup.get('mail').value;
    const confirEmail = this.firstFormGroup.get('confirmEmail').value

    if(nomEmpresa && docEmpresa && mail && cel && confirEmail == validaMails){
        return true
    }
        return false
  }

  validarSegundoPasso():boolean{

    const cep = this.secondFormGroup.get('cep').valid;
    const logra = this.secondFormGroup.get('logra').valid;
    const num = this.secondFormGroup.get('num').valid;
    const bairro = this.secondFormGroup.get('bairro').valid;
    const municipio = this.secondFormGroup.get('municipio').valid;
    const uf = this.secondFormGroup.get('uf').valid;

    if(cep && logra && num && bairro && municipio && uf ){
        return true
    }
        return false

  }

   autoCep(cep:String){
    debugger;
    this.servCep.buscaCep(cep).subscribe(
      (data)=>{
        this.secondFormGroup.patchValue({
          logra: data.logradouro,
          municipio: data.localidade,
          uf: data.uf,
          bairro: data.bairro
        })
      }
    )
  }


  checkedCpf(){
    
    this.isCnpj = false
  }

  checkedCnpj(){
    this.isCpf = false;
    
  }

  






}

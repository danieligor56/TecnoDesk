import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { MatRadioModule } from '@angular/material/radio';
import Validation from 'src/app/validators/validatorEmail';
import { CdkNestedTreeNode } from '@angular/cdk/tree';
import { EmpresaService } from 'src/app/services/empresa.service';
import { ToastrService } from 'ngx-toastr';
import { UtilsService } from 'src/app/services/UtilsService.service';
import { RegistroInicialService } from 'src/app/services/registro-inicial.service';
import { EmpresaUsuarioDTO } from 'src/app/models/EmpresaUsuarioDTO';
import { MatStepper } from '@angular/material/stepper';



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
  documentType: 'cpf' | 'cnpj' = 'cnpj';
  isEmailMacth: boolean = false;
  habilitarBotao: boolean = false;


  constructor(
    private _formBuilder: FormBuilder,
    private empresaService: EmpresaService,
    private toast: ToastrService,
    private servCep: UtilsService,
    private registroService: RegistroInicialService

  ) { }

  ngOnInit(): void {

    this.firstFormGroup = this._formBuilder.group({
      razaoSocial: [''],
      nomEmpresa: ['', Validators.required],
      docEmpresa: ['', Validators.required],
      mail: ['', [Validators.required, Validators.email]],
      confirmEmail: ['', [Validators.required, Validators.email]],
      cel: ['', Validators.required],
      tel: [''],
      site: [''],
      segmento: ['', Validators.required]

    }, {
      validators: [Validation.match('mail', 'confirmEmail')]
    });

    // Set initial validators based on default isCnpj = true
    this.firstFormGroup.get('razaoSocial').setValidators([Validators.required]);
    this.firstFormGroup.get('razaoSocial').updateValueAndValidity();

    this.secondFormGroup = this._formBuilder.group({
      cep: ['', Validators.required],
      logra: ['', Validators.required],
      num: ['', Validators.required],
      comp: [''],
      bairro: ['', Validators.required],
      municipio: ['', Validators.required],
      uf: ['', Validators.required]

    });

    this.thirdFormGroup = this._formBuilder.group({
      nomeCompleto: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      confirmEmail: ['', [Validators.required, Validators.email]],
      pass: ['', Validators.required],
      passConfirm: ['', Validators.required]

    },
      {
        validators: [Validation.match('email', 'confirmEmail'), Validation.match('pass', 'passConfirm')]

      });


  }

  validarEmpresaExistenteDoc() {
    debugger;
    const docExists = this.empresaService.checkCompanyExistsByDoc(this.firstFormGroup.get('docEmpresa').value).subscribe(response => {
      if (response) {
        this.firstFormGroup.get('docEmpresa').setValue('')
        this.toast.error("Atenção, já existe uma empresa cadastrada com este documento");

      }
    }
    )


  }

  validarPrimeiroPasso(): boolean {

    const nomEmpresa = this.firstFormGroup.get('nomEmpresa').valid;
    const docEmpresa = this.firstFormGroup.get('docEmpresa').valid;
    const mail = this.firstFormGroup.get('mail').valid;
    const cel = this.firstFormGroup.get('cel').valid;
    const segmento = this.firstFormGroup.get('segmento').valid;
    const razaoSocial = this.documentType === 'cnpj' ? this.firstFormGroup.get('razaoSocial').valid : true;

    const validaMails = this.firstFormGroup.get('mail').value;
    const confirEmail = this.firstFormGroup.get('confirmEmail').value

    if (nomEmpresa && docEmpresa && mail && cel && segmento && confirEmail == validaMails && razaoSocial) {
      return true
    }
    return false
  }

  validarSegundoPasso(): boolean {

    const cep = this.secondFormGroup.get('cep').valid;
    const logra = this.secondFormGroup.get('logra').valid;
    const num = this.secondFormGroup.get('num').valid;
    const bairro = this.secondFormGroup.get('bairro').valid;
    const municipio = this.secondFormGroup.get('municipio').valid;
    const uf = this.secondFormGroup.get('uf').valid;

    if (cep && logra && num && bairro && municipio && uf) {
      return true
    }
    return false

  }

  autoCep(cep: String) {
    debugger;
    this.servCep.buscaCep(cep).subscribe(
      (data) => {
        this.secondFormGroup.patchValue({
          logra: data.logradouro,
          municipio: data.localidade,
          uf: data.uf,
          bairro: data.bairro
        })
      }
    )
  }


  onDocumentTypeChange() {
    this.updateValidators();
  }

  updateValidators() {
    if (this.documentType === 'cpf') {
      this.firstFormGroup.get('razaoSocial').clearValidators();
    } else {
      this.firstFormGroup.get('razaoSocial').setValidators([Validators.required]);
    }
    this.firstFormGroup.get('razaoSocial').updateValueAndValidity();
  }

  submitRegistro(stepper: MatStepper) {
    if (this.firstFormGroup.valid && this.secondFormGroup.valid && this.thirdFormGroup.valid) {
      // Sanitize tel field to remove mask characters if empty
      const telValue = this.firstFormGroup.get('tel').value;
      const sanitizedTel = telValue && telValue.trim().replace(/[^0-9]/g, '').length > 0 ? telValue : '';

      const dto: EmpresaUsuarioDTO = {
        razaoSocial: this.firstFormGroup.get('razaoSocial').value || '',
        nomEmpresa: this.firstFormGroup.get('nomEmpresa').value,
        docEmpresa: this.firstFormGroup.get('docEmpresa').value,
        mail: this.firstFormGroup.get('mail').value,
        cel: this.firstFormGroup.get('cel').value,
        tel: sanitizedTel,
        site: this.firstFormGroup.get('site').value || '',
        segmento: parseInt(this.firstFormGroup.get('segmento').value),
        cep: this.secondFormGroup.get('cep').value,
        logra: this.secondFormGroup.get('logra').value,
        num: parseInt(this.secondFormGroup.get('num').value),
        comp: this.secondFormGroup.get('comp').value || '',
        bairro: this.secondFormGroup.get('bairro').value,
        municipio: this.secondFormGroup.get('municipio').value,
        uf: this.secondFormGroup.get('uf').value,
        nomeCompleto: this.thirdFormGroup.get('nomeCompleto').value,
        email: this.thirdFormGroup.get('email').value,
        pass: this.thirdFormGroup.get('pass').value
      };

      this.registroService.registro(dto).subscribe(
        response => {
          this.toast.success('Registro realizado com sucesso!');
          stepper.next();
        },
        error => {
          this.toast.error('Erro ao registrar. Tente novamente.');
        }
      );
    } else {
      this.toast.error('Verifique os dados e tente novamente.');
    }
  }








}

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Cliente } from 'src/app/models/Cliente';
import { Colaborador } from 'src/app/models/Colaborador';
import { Os_Mecanica } from 'src/app/models/Os-mecanica';
import { ClienteService } from 'src/app/services/cliente.service';
import { ColaboradorService } from 'src/app/services/colaborador.service';
import { OsService } from 'src/app/services/os.service';
import { UtilsService } from 'src/app/services/UtilsService.service';
import { ApiPlacaResponseDTO } from 'src/app/DTO/ApiPlacaResponseDTO ';

@Component({
  selector: 'app-os-mecanica-entrada',
  templateUrl: './os-mecanica-entrada.component.html',
  styleUrls: ['./os-mecanica-entrada.component.css']
})
export class OsMecanicaEntradaComponent implements OnInit {

  clienteForm: FormGroup;
  veiculoForm: FormGroup;
  osForm: FormGroup;

  colaboradores: Colaborador[] = [];
  isLoadingPlaca = false;
  placaError = false;

  prioridades = [
    { value: 'NORMAL', label: '📌 Normal' },
    { value: 'URGENCIA', label: '🔥 Urgente' },
    { value: 'GARANTIA', label: '🔄 Garantia' },
    { value: 'PRIORITARIA', label: '⏱️ Prioritária' }
  ];

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private colaboradorService: ColaboradorService,
    private osService: OsService,
    private utilsService: UtilsService,
    private toast: ToastrService,
    private router: Router,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.initializeForms();
    this.loadTecnicos();
  }

  private initializeForms(): void {
    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      telefone: ['', Validators.required]
    });

    this.veiculoForm = this.fb.group({
      placa: ['', [Validators.required, Validators.pattern(/^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$/)]],
      marca: [''],
      modelo: [''],
      ano: [''],
      cor: ['']
    });

    this.osForm = this.fb.group({
      kmEntrada: [''],
      diagnosticoInicial: [''],
      prioridadeOS: ['NORMAL', Validators.required],
      tecnico_responsavel: [null]
    });

    // Watch for placa changes to auto-fill vehicle data
    this.veiculoForm.get('placa')?.valueChanges.subscribe(placa => {
      if (this.veiculoForm.get('placa')?.valid && placa) {
        this.buscarDadosVeiculo(placa);
      }
    });
  }

  private loadTecnicos(): void {
    this.colaboradorService.listarTecnicos().subscribe({
      next: (response) => {
        this.colaboradores = response;
      },
      error: () => {
        this.toast.error('Erro ao carregar técnicos');
      }
    });
  }

  buscarDadosVeiculo(placa: string): void {
    this.isLoadingPlaca = true;
    this.placaError = false;

    this.utilsService.pegarDadosCarro(placa).subscribe({
      next: (dados: ApiPlacaResponseDTO) => {
        this.veiculoForm.patchValue({
          marca: dados.marca,
          modelo: dados.modelo,
          ano: dados.ano,
          cor: dados.cor
        });
        this.isLoadingPlaca = false;
      },
      error: () => {
        this.placaError = true;
        this.isLoadingPlaca = false;
        this.toast.warning('Placa não encontrada ou serviço indisponível');
      }
    });
  }

  salvarOS(): void {
    if (this.isFormValid()) {
      const clienteData = this.createClienteData();
      const osData = this.createOSData();

      // First save client, then create OS
      this.clienteService.create(clienteData).subscribe({
        next: (cliente) => {
          osData.cliente = cliente;
          this.createOS(osData);
        },
        error: () => {
          this.toast.error('Erro ao salvar cliente');
        }
      });
    } else {
      this.toast.warning('Preencha todos os campos obrigatórios');
    }
  }

  private createClienteData(): Cliente {
    return {
      empresa: {
        id: sessionStorage.getItem('CompGrpIndent') || '',
        razaoSocial: '',
        nomEmpresa: '',
        docEmpresa: '',
        mail: '',
        cel: '',
        tel: '',
        site: '',
        segmento: 0,
        cep: '',
        logra: '',
        num: 0,
        comp: '',
        bairro: '',
        municipio: '',
        uf: ''
      },
      nome: this.clienteForm.value.nome,
      cel1: this.clienteForm.value.telefone,
      cel2: '',
      documento: '',
      email: '',
      cep: '',
      estado: '',
      cidade: '',
      logradouro: '',
      numero: '',
      obs: '',
      atvReg: false
      // cadastroCompleto will be set to false in backend
    };
  }

  private createOSData(): Os_Mecanica {
    const tecnicoId = this.osForm.value.tecnico_responsavel;
    const tecnico = tecnicoId ? this.colaboradores.find(c => c.id === tecnicoId) : null;

    return {
      empresa: {
        id: sessionStorage.getItem('CompGrpIndent') || '',
        razaoSocial: '',
        nomEmpresa: '',
        docEmpresa: '',
        mail: '',
        cel: '',
        tel: '',
        site: '',
        segmento: 0,
        cep: '',
        logra: '',
        num: 0,
        comp: '',
        bairro: '',
        municipio: '',
        uf: ''
      },
      cliente: null, // Will be set after client creation
      colaborador: null, // Current user - should be set in service
      tecnico_responsavel: tecnico,
      dataAbertura: new Date().toISOString(),
      // Vehicle data
      placa: this.veiculoForm.value.placa,
      marca: this.veiculoForm.value.marca,
      modelo: this.veiculoForm.value.modelo,
      ano: this.veiculoForm.value.ano,
      cor: this.veiculoForm.value.cor,
      // OS data
      kmEntrada: this.osForm.value.kmEntrada,
      diagnosticoInicial: this.osForm.value.diagnosticoInicial,
      prioridadeOS: this.osForm.value.prioridadeOS,
      statusOS: 'NOVO'
    };
  }

  private createOS(osData: Os_Mecanica): void {
    // Assuming there's a method in OsService for creating mechanical OS
    // For now, we'll use the existing create method or need to add one
    this.osService.createOsMecanica(osData).subscribe({
      next: (response) => {
        this.toast.success('OS Mecânica criada com sucesso!');
        this.resetForm();
        // Navigate to OS list or show success dialog
        this.router.navigate(['/os/list']);
      },
      error: () => {
        this.toast.error('Erro ao criar OS Mecânica');
      }
    });
  }

  isFormValid(): boolean {
    return this.clienteForm.valid && this.veiculoForm.valid && this.osForm.valid;
  }

  private resetForm(): void {
    this.clienteForm.reset();
    this.veiculoForm.reset();
    this.osForm.reset({ prioridadeOS: 'NORMAL' });
  }

  voltar(): void {
    this.router.navigate(['/os/list']);
  }
}

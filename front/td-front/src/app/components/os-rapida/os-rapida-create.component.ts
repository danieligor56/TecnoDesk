import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OsRapida } from 'src/app/models/OsRapida';
import { OsRapidaService } from 'src/app/services/os-rapida.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-os-rapida-create',
  templateUrl: './os-rapida-create.component.html',
  styleUrls: ['./os-rapida-create.component.css']
})
export class OsRapidaCreateComponent implements OnInit {
  osRapidaForm: FormGroup;
  isEditing: boolean = false;
  editingId: number | null = null;
  pageTitle: string = 'Nova OS Rápida';
  submitButtonText: string = 'Criar OS Rápida';

  constructor(
    private fb: FormBuilder,
    private osRapidaService: OsRapidaService,
    private router: Router,
    private route: ActivatedRoute,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.checkIfEditing();
  }

  initForm(): void {
    this.osRapidaForm = this.fb.group({
      clienteNome: ['', [Validators.required, Validators.minLength(2)]],
      clienteTelefone: [''],
      equipamentoServico: ['', [Validators.required, Validators.minLength(3)]],
      problemaRelatado: ['', [Validators.required, Validators.minLength(5)]],
      valorEstimado: [''],
      prazoCombinado: [''],
      observacoes: ['']
    });
  }

  checkIfEditing(): void {
    this.route.params.subscribe(params => {
      if (params['id']) {
        this.isEditing = true;
        this.editingId = +params['id'];
        this.pageTitle = 'Editar OS Rápida';
        this.submitButtonText = 'Atualizar OS Rápida';
        this.loadOsRapidaData(this.editingId);
      }
    });
  }

  loadOsRapidaData(id: number): void {
    this.osRapidaService.buscarOsRapidaPorId(id).subscribe({
      next: (osRapida) => {
        this.osRapidaForm.patchValue({
          clienteNome: osRapida.clienteNome,
          clienteTelefone: osRapida.clienteTelefone || '',
          equipamentoServico: osRapida.equipamentoServico,
          problemaRelatado: osRapida.problemaRelatado,
          valorEstimado: osRapida.valorEstimado || '',
          prazoCombinado: osRapida.prazoCombinado || '',
          observacoes: osRapida.observacoes || ''
        });
      },
      error: (error) => {
        this.toast.error('Erro ao carregar dados da OS Rápida');
        this.router.navigate(['/os-rapida/list']);
      }
    });
  }

  onSubmit(): void {
    if (this.osRapidaForm.valid) {
      const osRapida: OsRapida = this.osRapidaForm.value;

      if (this.isEditing && this.editingId) {
        this.osRapidaService.atualizarOsRapida(this.editingId, osRapida).subscribe({
          next: (response) => {
            this.toast.success('OS Rápida atualizada com sucesso!');
            this.router.navigate(['/os-rapida/list']);
          },
          error: (error) => {
            this.toast.error('Erro ao atualizar OS Rápida: ' + error.message);
          }
        });
      } else {
        this.osRapidaService.criarOsRapida(osRapida).subscribe({
          next: (response) => {
            this.toast.success('OS Rápida criada com sucesso!');
            this.router.navigate(['/os-rapida/list']);
          },
          error: (error) => {
            this.toast.error('Erro ao criar OS Rápida: ' + error.message);
          }
        });
      }
    } else {
      this.toast.warning('Por favor, preencha todos os campos obrigatórios.');
    }
  }

  onCancel(): void {
    this.router.navigate(['/os-rapida/list']);
  }

  get f() {
    return this.osRapidaForm.controls;
  }
}

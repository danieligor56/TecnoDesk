import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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

  constructor(
    private fb: FormBuilder,
    private osRapidaService: OsRapidaService,
    private router: Router,
    private toast: ToastrService
  ) { }

  ngOnInit(): void {
    this.initForm();
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

  onSubmit(): void {
    if (this.osRapidaForm.valid) {
      const osRapida: OsRapida = this.osRapidaForm.value;

      this.osRapidaService.criarOsRapida(osRapida).subscribe({
        next: (response) => {
          this.toast.success('OS Rápida criada com sucesso!');
          this.router.navigate(['/os-rapida/list']);
        },
        error: (error) => {
          this.toast.error('Erro ao criar OS Rápida: ' + error.message);
        }
      });
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

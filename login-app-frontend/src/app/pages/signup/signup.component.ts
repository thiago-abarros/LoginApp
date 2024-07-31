import { Component } from '@angular/core';
import { DefaultLoginLayoutComponent } from '../../components/default-login-layout/default-login-layout.component';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PrimaryInputComponent } from '../../components/primary-input/primary-input.component';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    DefaultLoginLayoutComponent,
    ReactiveFormsModule,
    PrimaryInputComponent
  ],
  providers: [
    LoginService,
    ToastrService
  ],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})

export class SignupComponent {
  SignupForm!: FormGroup

  constructor(
    private router: Router,
    private loginService: LoginService,
    private toastService: ToastrService
  ){
    this.SignupForm = new FormGroup({
      name: new FormControl('', [Validators.required, Validators.minLength(3)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      passwordConfirm: new FormControl('', [Validators.required, Validators.minLength(6)])
    })
  }

  submit() {
    this.loginService.signup(this.SignupForm.value.name, this.SignupForm.value.email, this.SignupForm.value.password)
      .subscribe({
        next: () => this.toastService.success("Cadastro realizado com sucesso!"),
        error: () => this.toastService.error("Email ou senha inválidos!")
      })
  }

  navigate() {
    this.router.navigate(["login"])
  }
}

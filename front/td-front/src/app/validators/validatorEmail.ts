import { AbstractControl, ValidatorFn } from '@angular/forms';

export default class Validation {
  static match(controlMail: string, checkControlMail: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(controlMail);
      const checkControl = controls.get(checkControlMail);

      if (checkControl.errors && !checkControl.errors.matching) {
        return null;
      }

      if (control.value !== checkControl.value) {
        controls.get(checkControlMail).setErrors({ matching: true });
        return { matching: true };
      } else {
        return null;
      }
    };
  }
}
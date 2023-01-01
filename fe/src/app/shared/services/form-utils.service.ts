import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }
  getInvalid(form: FormGroup, ...args: string[]): boolean {
    return form.get(args)?.touched && (form.get(args)?.errors && Object.keys(form.get(args)).length > 0);
  }
}

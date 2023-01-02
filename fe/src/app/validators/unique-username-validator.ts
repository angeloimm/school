import { AbstractControl, AsyncValidator, AsyncValidatorFn, ValidationErrors } from "@angular/forms";
import { map, Observable } from "rxjs";
import { CONST } from "../shared/constants";
import {InitDbServiceService} from '../shared/services/api/init-db-service.service';

export class UniqueUsernameValidator{
    private static readonly USERNAME_DUPLICATED = { usernameDuplicated: true };
    private static readonly USERNAME_NOT_DUPLICATED = null;
    
    static validateUsername(initDb: InitDbServiceService): AsyncValidatorFn {
        return (control:AbstractControl):Observable<ValidationErrors> =>{
            const username = control.value;
        const finalUrl = CONST.VALIDATE_USERNAME_URL.endsWith("/")?CONST.VALIDATE_USERNAME_URL+username:CONST.VALIDATE_USERNAME_URL+"/"+username;
        return initDb.get(finalUrl).pipe(map((response)=>response.payload?UniqueUsernameValidator.USERNAME_NOT_DUPLICATED:UniqueUsernameValidator.USERNAME_DUPLICATED));
        }
    }
}
export interface Utente{
    id?:string;
    nome?:string;
    cognome?:string;
    codiceFiscale?:string;
    dataNascita?:Date;
    indirizzo?:string;
    username?:string;
    password?:string;
    tipoUtente?:string;
    attivo?:boolean;
    sesso?:string;
}

export interface Sesso{
    name?:string;
    code?:string;
}
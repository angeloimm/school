export class CONST {
    private static API_BASE_URL:string = "";
    private static PUBLIC_BASE_URL:string = this.API_BASE_URL+"/public";
    private static PROTECTED_BASE_URL:string = this.API_BASE_URL+"/protected";
    public static INIT_DB_URL:string = this.PUBLIC_BASE_URL+"/init";
    public static VALIDATE_USERNAME_URL:string = this.PUBLIC_BASE_URL+"/validation/username";
    public static INIT_DB_SAVE_ADMIN_URL:string = this.INIT_DB_URL+"/admin";
    public static USERS_PROTECTED_URL:string=this.PROTECTED_BASE_URL+"/utenti";
    public static LOGIN_APP_URL:string="/login";
    public static LOGOUT_APP_URL:string=this.PUBLIC_BASE_URL+"/logout";
    public static USR_STORAGE_KEY :string = "CURRENT_USER_VALUE";
}

export class ROUTE_PATH{
    public static APP_INIT_ROUTE = "init/applicazione";
    public static APP_LOGIN_ROUTE = "app/login";
    public static APP_GENERIC_ERROR_ROUTE = "app/generic-error";
    public static APP_HP_AMMINISTRATORE_ROUTE = "app/hp/amministratore";
    public static APP_HP_UTENTE_ROUTE = "app/hp/utente";
}

export class TIPO_UTENTE_VALUES{
    public static AMMINISTRATORE = "AMMINISTRATORE";
    public static STUDENTE = "STUDENTE";
    public static DOCENTE = "DOCENTE";
}
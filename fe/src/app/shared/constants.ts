export class CONST {
    private static API_BASE_URL:string = "/api";
    private static PUBLIC_BASE_URL:string = this.API_BASE_URL+"/public";
    private static PROTECTED_BASE_URL:string = this.API_BASE_URL+"/protected";
    public static INIT_DB_URL:string = this.PUBLIC_BASE_URL+"/init";
    public static VALIDATE_USERNAME_URL:string = this.PUBLIC_BASE_URL+"/validation/username";
    public static INIT_DB_SAVE_ADMIN_URL:string = this.INIT_DB_URL+"/admin";
    public static USERS_PROTECTED_URL:string=this.PROTECTED_BASE_URL+"/utenti";
}
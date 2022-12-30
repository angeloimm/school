export class ErrorDetail{
    field!: string;
    source!: string;
    code!: string;
    message!: string;
}

export class ApiError{
    timestamp!: Date;
    path!: string;
    details!: ErrorDetail[];
}

export class ApiResponse<T>{
    error!: boolean;
    payload!: T;
    errori!: ApiError;
}
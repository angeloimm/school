export interface ErrorDetail{
    field?: string;
    source?: string;
    code?: string;
    message?: string;
}

export interface ApiError{
    timestamp?: Date;
    path?: string;
    details?: ErrorDetail[];
}

export interface ApiResponse<T>{
    error?: boolean;
    payload?: T;
    errori?: ApiError;
}

export interface PagedApiResponse<T> extends ApiResponse<T>{
    totalRecords?:number;
}
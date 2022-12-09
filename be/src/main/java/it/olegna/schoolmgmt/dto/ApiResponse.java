package it.olegna.schoolmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiResponse<T> {
    private boolean error;
    private T payload;
    private ApiError errori;

}

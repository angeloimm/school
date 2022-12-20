package it.olegna.schoolmgmt.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiError {
    @Builder.Default
    private Date timestamp = new Date();
    private String path;
    // La struttura dei sottoerrori può variare a seconda del tipo di errore
    private List<ErrorDetail> details;
}

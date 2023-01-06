package it.olegna.schoolmgmt.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public class PagedApiResponse<T> extends  ApiResponse<T>{
    private Long totalRecords;

}

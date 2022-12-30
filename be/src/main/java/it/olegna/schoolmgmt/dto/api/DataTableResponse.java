package it.olegna.schoolmgmt.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class DataTableResponse<T> {
    private Long draw;
    private Long recordsTotal;
    private Long recordsFiltered;
    private T payload;
}

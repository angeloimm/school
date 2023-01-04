package it.olegna.schoolmgmt.dto.ricerca.utente;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "first",
    "rows",
    "sortField",
    "sortOrder",
    "filters",
    "globalFilter"
})
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Event {

    @JsonProperty("first")
    private Integer first;
    @JsonProperty("rows")
    private Integer rows;
    @JsonProperty("sortField")
    private String sortField;
    @JsonProperty("sortOrder")
    private Integer sortOrder;
    @JsonProperty("filters")
    private Filters filters;
    @JsonProperty("globalFilter")
    private Object globalFilter;

}

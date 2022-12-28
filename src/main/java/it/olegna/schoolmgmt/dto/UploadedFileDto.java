package it.olegna.schoolmgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UploadedFileDto implements Serializable {

	// Valorizzati sempre
	private String name;
	private long size;
	private String id;
	// Valorizzati solo se upload OK
	private String url;
	private String thumbnailUrl;
	private String deleteUrl;
	private String deleteType;
	private String contentType;

	// Valorizzato solo in caso di errore
	private String error;
}
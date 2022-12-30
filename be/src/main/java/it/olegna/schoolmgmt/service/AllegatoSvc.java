package it.olegna.schoolmgmt.service;

import it.olegna.schoolmgmt.dto.AllegatoDto;
import it.olegna.schoolmgmt.dto.UploadedFileDto;

import java.util.UUID;

public interface AllegatoSvc {

    UploadedFileDto saveAllegato(AllegatoDto allegatoDto);

    void deleteById(UUID idAllegato);
}

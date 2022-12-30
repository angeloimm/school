package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.AllegatoDto;
import it.olegna.schoolmgmt.dto.UploadedFileDto;
import it.olegna.schoolmgmt.mapper.AllegatoMapper;
import it.olegna.schoolmgmt.persistence.model.Allegato;
import it.olegna.schoolmgmt.persistence.repository.AllegatoRepository;
import it.olegna.schoolmgmt.service.AllegatoSvc;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class AllegatoSvcImpl implements AllegatoSvc {
    @Autowired
    private AllegatoRepository repository;
    @Autowired
    private AllegatoMapper mapper;

    @Override
    @Transactional(readOnly = false)
    public UploadedFileDto saveAllegato(AllegatoDto allegatoDto) {

        try {
            Allegato toSave = mapper.toEntity(allegatoDto);
            toSave.setContenutoFile((BlobProxy.generateProxy(allegatoDto.getMultipartFile().getInputStream(), allegatoDto.getMultipartFile().getSize())));
            toSave = this.repository.save(toSave);
            return UploadedFileDto.builder()
                    .id(toSave.getId().toString())
                    .contentType(allegatoDto.getMultipartFile().getContentType())
                    .name(allegatoDto.getMultipartFile().getOriginalFilename())
                    .size(allegatoDto.getMultipartFile().getSize())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(UUID idAllegato) {
        this.repository.deleteById(idAllegato);
    }
}

package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.dto.MateriaTableDto;
import it.olegna.schoolmgmt.mapper.MateriaMapper;
import it.olegna.schoolmgmt.persistence.model.Materia;
import it.olegna.schoolmgmt.persistence.model.Materia_;
import it.olegna.schoolmgmt.persistence.repository.MateriaRepository;
import it.olegna.schoolmgmt.service.MateriaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MateriaSvcImpl implements MateriaSvc {
    @Autowired
    private MateriaRepository repository;
    @Autowired
    private MateriaMapper mapper;

    @Override
    public MateriaDto createModificaMateria(MateriaDto materia) {
        log.trace("Salvo materia {}", materia);
        return mapper.toDto(repository.save(mapper.toEntity(materia)));
    }

    @Override
    public Optional<MateriaDto> findMateriaById(UUID id) {
        log.info("Ricrco materia con ID {}", id);
        Optional<Materia> result = this.repository.findById(id);
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDto(result.get()));
    }

    @Override
    public void cancellaMateria(UUID idMateria) {
        log.info("Cancellazione materia con ID {}", idMateria);
        repository.deleteById(idMateria);
    }

    @Override
    public Page<MateriaTableDto> recuperaMaterie(String nome, Pageable pageable) {
        Page<MateriaTableDto> materie = null;
        if (!StringUtils.hasText(nome)) {
            Page<Materia> materieP = repository.findAll(pageable);
            long totalElements = materieP.getTotalElements();
            materie = new PageImpl<MateriaTableDto>(toMaterieTable(materieP.getContent()),pageable, totalElements);
        }else {
            materie = repository.findByNomeMateriaStartsWithIgnoreCase(nome, pageable);
        }
        return materie;
    }
    private List<MateriaTableDto> toMaterieTable(List<Materia> entities){
        if( entities != null && !entities.isEmpty() ) {
            List<MateriaTableDto> result = new ArrayList<>(entities.size());
            entities.forEach( e -> {
                result.add(MateriaTableDto.builder()
                                .id(e.getId())
                                .nomeMateria(e.getNomeMateria())
                        .build());
            });
        }
        return  Collections.emptyList();

    }
}
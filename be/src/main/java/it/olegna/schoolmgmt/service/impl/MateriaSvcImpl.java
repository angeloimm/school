package it.olegna.schoolmgmt.service.impl;

import it.olegna.schoolmgmt.dto.MateriaDto;
import it.olegna.schoolmgmt.mapper.MateriaMapper;
import it.olegna.schoolmgmt.persistence.model.Materia;
import it.olegna.schoolmgmt.persistence.model.Materia_;
import it.olegna.schoolmgmt.persistence.repository.MateriaRepository;
import it.olegna.schoolmgmt.service.MateriaSvc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public Optional<MateriaDto> findMateriaById(String id) {
        log.info("Ricrco materia con ID {}", id);
        Optional<Materia> result = this.repository.findById(id);
        return result.isEmpty() ? Optional.empty() : Optional.of(mapper.toDto(result.get()));
    }

    @Override
    public void cancellaMateria(String idMateria) {
        log.info("Cancellazione materia con ID {}", idMateria);
        repository.deleteById(idMateria);
    }

    @Override
    public List<MateriaDto> recuperaMaterie(String nome) {
        if (!StringUtils.hasText(nome)) {
            return mapper.toDtos(repository.findAll(Sort.by(Sort.Order.asc(Materia_.NOME_MATERIA))));
        }

        Optional<List<Materia>> p = repository.findByNomeMateriaStartsWithIgnoreCase(nome, Sort.by(Sort.Order.asc(Materia_.NOME_MATERIA)));
        if(p.isEmpty()) {
            return  Collections.emptyList();
        }
        return mapper.toDtos(p.get());
    }
}
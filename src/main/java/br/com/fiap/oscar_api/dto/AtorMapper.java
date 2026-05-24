package br.com.fiap.oscar_api.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.fiap.oscar_api.model.Ator;

@Component
public class AtorMapper {
    
    private final ModelMapper modelMapper = new ModelMapper();

    public Ator toModel (AtorCreateRequest dto) {
        return modelMapper.map(dto, Ator.class);
    }

    public AtorResponse toDto (Ator entity) {
        return modelMapper.map(entity, AtorResponse.class);
    }

    public Ator toModel (Long id, AtorUpdateRequest dto) {
        Ator ator = modelMapper.map(dto, Ator.class);
        ator.setId(id);
        return ator;
    }
}

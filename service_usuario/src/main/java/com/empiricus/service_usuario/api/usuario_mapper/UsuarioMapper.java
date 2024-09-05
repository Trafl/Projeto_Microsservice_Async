package com.empiricus.service_usuario.api.usuario_mapper;


import com.empiricus.service_usuario.domain.dto.UsuarioDTOInput;
import com.empiricus.service_usuario.domain.dto.UsuarioDTOOutput;
import com.empiricus.service_usuario.domain.dto.UsuarioUpdateDTOInput;
import com.empiricus.service_usuario.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {

    private final ModelMapper mapper;

    public UsuarioDTOOutput toDTO (Usuario usuario){
        return mapper.map(usuario, UsuarioDTOOutput.class);
    }

    public Usuario toModel (UsuarioDTOInput input){
        return mapper.map(input, Usuario.class);
    }

    public Usuario toModelUpdate (UsuarioUpdateDTOInput input){
        return mapper.map(input, Usuario.class);
    }

    public Page<UsuarioDTOOutput> toPageDto (Page<Usuario> usuarios){
        return usuarios.map(usuario -> mapper.map(usuario, UsuarioDTOOutput.class));
    }
}

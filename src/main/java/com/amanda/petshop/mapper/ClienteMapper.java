package com.amanda.petshop.mapper;

import com.amanda.petshop.dto.ClienteDTO;
import com.amanda.petshop.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface ClienteMapper {


    @Mapping(source = "usuario.cpf", target = "usuarioCpf")
    ClienteDTO toDTO(Cliente cliente);


    @Mapping(target = "usuario.cpf", source = "usuarioCpf")
    Cliente toEntity(ClienteDTO clienteDTO);

}

package com.projects.notas4int.bankservce.mappers;

import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.models.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client convertClientDTOtoClientModel(RequestRegisterDTO request);
}

package com.projects.notas4int.bankservce.mappers;

import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.models.BankAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount convertClientDTOtoBankAccountModel(RequestRegisterDTO requestClientDTO);
}

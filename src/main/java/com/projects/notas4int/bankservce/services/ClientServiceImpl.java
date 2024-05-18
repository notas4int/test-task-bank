package com.projects.notas4int.bankservce.services;

import com.projects.notas4int.bankservce.DTOs.RequestClientDTO;
import com.projects.notas4int.bankservce.mappers.BankAccountMapper;
import com.projects.notas4int.bankservce.mappers.ClientMapper;
import com.projects.notas4int.bankservce.models.BankAccount;
import com.projects.notas4int.bankservce.models.Client;
import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import com.projects.notas4int.bankservce.repositories.ClientRepository;
import com.projects.notas4int.bankservce.security.exceptions.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankAccountMapper bankAccountMapper;
    private final ClientMapper clientMapper;

    @Transactional
    public void saveClient(RequestClientDTO requestClientDTO) {
        if (clientRepository.existsByPhoneOrEmailOrLogin(requestClientDTO.getEmail(), requestClientDTO.getPhone(), requestClientDTO.getLogin())) {
            throw new ClientExistsException("Client already exists");
        }

        Client client = clientMapper.convertClientDTOtoClientModel(requestClientDTO);
        BankAccount bankAccount = bankAccountMapper.convertClientDTOtoBankAccountModel(requestClientDTO);
        client.setBankAccount(bankAccount);
        bankAccount.setClient(client);

        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void saveClientInfo(String phone, String email) {
        // TODO: 18.05.2024 изменить получение аккаунта через аутентификацию через логин
        Client client = bankAccountRepository.findById(1L)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + "login" + "' not found"))
                .getClient();

        if (phone != null) {
            if (client.getPhone() == null) {
                if (Pattern.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", phone))
                    if (!clientRepository.existsClientByPhone(phone))
                        client.setPhone(phone);
                    else
                        throw new PhoneNumberAlreadyTakenException("The phone number is already occupied");
                else
                    throw new IncorrectEnteredPhoneNumberException("The phone number was entered incorrectly");
            } else
                throw new PhoneAlreadyExistsException("Phone number already exists");
        } else if (email != null) {
            if (client.getEmail() == null) {
                if (Pattern.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$", email))
                    if (!clientRepository.existsClientByEmail(email))
                        client.setEmail(email);
                    else
                        throw new EmailAddressAlreadyTakenException("The email address is already occupied");
                else
                    throw new IncorrectEnteredEmailAddressException("The e-mail address was entered incorrectly");
            } else
                throw new EmailAlreadyExistsException("Email address already exists");
        }

        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void updateClientInfo(String phone, String email) {
        // TODO: 18.05.2024 изменить получение аккаунта через аутентификацию через логин
        Client client = bankAccountRepository.findById(1L)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + "login" + "' not found"))
                .getClient();

        if (phone != null) {
            if (client.getPhone() != null) {
                if (!client.getPhone().equals(phone)) {
                    if (Pattern.matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$", phone))
                        if (!clientRepository.existsClientByPhone(phone))
                            client.setPhone(phone);
                        else
                            throw new PhoneNumberAlreadyTakenException("The phone number is already occupied");
                    else
                        throw new IncorrectEnteredPhoneNumberException("The phone number was entered incorrectly");
                } else
                    throw new OldAndNewPhoneIdenticalException("The old and new phone numbers cannot be identical");
            } else
                throw new PhoneNumberEmptyException("Phone number empty");
        } else if (email != null) {
            if (client.getEmail() != null) {
                if (!client.getEmail().equals(email)) {
                    if (Pattern.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+$", email))
                        if (!clientRepository.existsClientByEmail(email))
                            client.setEmail(email);
                        else
                            throw new EmailAddressAlreadyTakenException("The email address is already occupied");
                    else
                        throw new IncorrectEnteredEmailAddressException("The e-mail address was entered incorrectly");
                } else
                    throw new OldAndNewEmailIdenticalException("The old and new email addresses cannot be identical");
            } else
                throw new EmailAddressEmptyException("Email address empty");
        }

        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void removeClientInfo(boolean phone, boolean email) {
        // TODO: 18.05.2024 изменить получение аккаунта через аутентификацию через логин
        Client client = bankAccountRepository.findById(1L)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + "login" + "' not found"))
                .getClient();

        if (phone) {
            if (client.getPhone() != null) {
                if (client.getEmail() != null)
                    client.setPhone(null);
                else
                    throw new LastClientConnectedWayRemoveException("It is not possible to delete the last communication method. " +
                            "You must specify either a phone number or an e-mail address");
            } else
                throw new PhoneNumberEmptyException("Phone number empty");
        } else {
            if (client.getEmail() != null) {
                if (client.getPhone() != null)
                    client.setEmail(null);
                else
                    throw new LastClientConnectedWayRemoveException("It is not possible to delete the last communication method. " +
                            "You must specify either a phone number or an e-mail address");
            } else
                throw new EmailAddressEmptyException("Email address empty");
        }

        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void transferMoneyByLogin(String login, double amountOfFunds) {
        BankAccount senderBankAccount = bankAccountRepository.findBankAccountByLogin("asksddjk").orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + "asksddjk" + "' not found"));
        double senderBalance = senderBankAccount.getBalance();
        if (senderBalance - amountOfFunds < 0)
            throw new insufficientFundsInBalanceException("There are not enough funds on the balance sheet to make a transfer");

        BankAccount recipientBankAccount = bankAccountRepository.findBankAccountByLogin(login).orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + login + "' not found"));
        double recipientBalance = recipientBankAccount.getBalance();

        double senderBalanceAfterTransfer = senderBalance - amountOfFunds;
        double recipientBalanceAfterTransfer = recipientBalance + amountOfFunds;

        senderBankAccount.setBalance(senderBalanceAfterTransfer);
        recipientBankAccount.setBalance(recipientBalanceAfterTransfer);

        bankAccountRepository.save(senderBankAccount);
        bankAccountRepository.save(recipientBankAccount);
    }
}

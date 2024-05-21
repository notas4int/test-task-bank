package com.projects.notas4int.bankservce.services.impls;

import com.projects.notas4int.bankservce.DTOs.RequestRegisterDTO;
import com.projects.notas4int.bankservce.mappers.ClientMapper;
import com.projects.notas4int.bankservce.models.BankAccount;
import com.projects.notas4int.bankservce.models.Client;
import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import com.projects.notas4int.bankservce.repositories.ClientRepository;
import com.projects.notas4int.bankservce.security.exceptions.*;
import com.projects.notas4int.bankservce.services.ClientService;
import com.projects.notas4int.bankservce.utils.AsyncUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final BankAccountRepository bankAccountRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final AsyncUtil scheduledTasksService;

    @SneakyThrows
    @Transactional
    public BankAccount saveClient(RequestRegisterDTO request) {
        if (clientRepository.existsByPhoneOrEmailOrLogin(request.getEmail(), request.getPhone(), request.getLogin())) {
            throw new ClientExistsException("Client already exists");
        }

        Client client = clientMapper.convertClientDTOtoClientModel(request);
        BankAccount bankAccount = BankAccount.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .balance(request.getBalance())
                .build();

        client.setBankAccount(bankAccount);
        bankAccount.setClient(client);
        clientRepository.save(client);
        scheduledTasksService.incBalanceAfterDepositByLogin(request.getLogin(), request.getBalance());

        return bankAccount;
    }

    @Override
    @Transactional
    @PreAuthorize("#userDetails.getUsername() == authentication.principal.username")
    public void saveClientInfo(String phone, String email, UserDetails userDetails) {
        Client client = findClientByLogin(userDetails.getUsername());

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

    @Transactional
    @PreAuthorize("#userDetails.getUsername() == authentication.principal.username")
    public void updateClientInfo(String phone, String email, UserDetails userDetails) {
        Client client = findClientByLogin(userDetails.getUsername());

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

    @Transactional
    @PreAuthorize("#userDetails.getUsername() == authentication.principal.username")
    public void removeClientInfo(boolean phone, boolean email, UserDetails userDetails) {
        Client client = findClientByLogin(userDetails.getUsername());

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

    @Transactional
    @PreAuthorize("#userDetails.getUsername() == authentication.principal.username")
    public void transferMoneyByLogin(String login, double amountOfFunds, UserDetails userDetails) {
        BankAccount senderBankAccount = bankAccountRepository.findBankAccountByLogin(userDetails.getUsername()).orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + "asksddjk" + "' not found"));

        double senderBalance = senderBankAccount.getBalance();
        if (senderBalance - amountOfFunds < 0)
            throw new InsufficientFundsInBalanceException("There are not enough funds on the balance sheet to make a transfer");

        BankAccount recipientBankAccount = bankAccountRepository.findBankAccountByLogin(login).orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + login + "' not found"));
        double recipientBalance = recipientBankAccount.getBalance();

        double senderBalanceAfterTransfer = senderBalance - amountOfFunds;
        double recipientBalanceAfterTransfer = recipientBalance + amountOfFunds;

        senderBankAccount.setBalance(senderBalanceAfterTransfer);
        recipientBankAccount.setBalance(recipientBalanceAfterTransfer);

        bankAccountRepository.save(senderBankAccount);
        bankAccountRepository.save(recipientBankAccount);
    }

    private Client findClientByLogin(String login) {
        return bankAccountRepository.findBankAccountByLogin(login)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account with login '" + login + "' not found"))
                .getClient();
    }
}

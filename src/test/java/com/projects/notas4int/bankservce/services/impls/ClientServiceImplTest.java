package com.projects.notas4int.bankservce.services.impls;

import com.projects.notas4int.bankservce.models.BankAccount;
import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import com.projects.notas4int.bankservce.security.exceptions.BankAccountNotFoundException;
import com.projects.notas4int.bankservce.security.exceptions.InsufficientFundsInBalanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ClientServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private UserDetails userDetails;
    private BankAccount senderBankAccount;
    private BankAccount recipientBankAccount;

    @BeforeEach
    void setUp() {
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("sender");

        senderBankAccount = BankAccount.builder()
                .login("sender")
                .balance(200.0)
                .password("ldkjfhdjkf")
                .build();
        recipientBankAccount = BankAccount.builder()
                .login("recipient")
                .balance(50.0)
                .password("ldkjfasdhdjkf")
                .build();
    }

    @Test
    void transferMoneyByLogin_SuccessfulTransfer() {
        String recipientLogin = "recipient";
        double transferAmount = 100.0;

        when(bankAccountRepository.findBankAccountByLogin(senderBankAccount.getLogin())).thenReturn(Optional.of(senderBankAccount));
        when(bankAccountRepository.findBankAccountByLogin(recipientLogin)).thenReturn(Optional.of(recipientBankAccount));

        clientService.transferMoneyByLogin(recipientLogin, transferAmount, userDetails);

        assertEquals(100.0, senderBankAccount.getBalance());
        assertEquals(150.0, recipientBankAccount.getBalance());

        verify(bankAccountRepository).save(senderBankAccount);
        verify(bankAccountRepository).save(recipientBankAccount);
    }

    @Test
    void transferMoneyByLogin_InsufficientFunds() {
        String recipientLogin = "recipient";
        double transferAmount = 300.0;

        when(bankAccountRepository.findBankAccountByLogin(senderBankAccount.getLogin())).thenReturn(Optional.of(senderBankAccount));
        when(bankAccountRepository.findBankAccountByLogin(recipientLogin)).thenReturn(Optional.of(recipientBankAccount));

        assertThrows(InsufficientFundsInBalanceException.class,
                () -> clientService.transferMoneyByLogin(recipientLogin, transferAmount, userDetails));

        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void transferMoneyByLogin_SenderNotFound() {
        String recipientLogin = "recipient";
        double transferAmount = 100.0;

        when(bankAccountRepository.findBankAccountByLogin(senderBankAccount.getLogin())).thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> clientService.transferMoneyByLogin(recipientLogin, transferAmount, userDetails));

        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }

    @Test
    void transferMoneyByLogin_RecipientNotFound() {
        String recipientLogin = "recipient";
        double transferAmount = 100.0;

        BankAccount senderBankAccount = BankAccount.builder()
                .login("sender")
                .balance(200.0)
                .password("ldkjfhdjkf")
                .build();

        when(bankAccountRepository.findBankAccountByLogin(senderBankAccount.getLogin())).thenReturn(Optional.of(senderBankAccount));
        when(bankAccountRepository.findBankAccountByLogin(recipientLogin)).thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class,
                () -> clientService.transferMoneyByLogin(recipientLogin, transferAmount, userDetails));

        verify(bankAccountRepository, never()).save(any(BankAccount.class));
    }
}
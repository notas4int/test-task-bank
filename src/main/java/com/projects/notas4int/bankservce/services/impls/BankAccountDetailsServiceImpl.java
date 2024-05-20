package com.projects.notas4int.bankservce.services.impls;

import com.projects.notas4int.bankservce.models.BankAccountDetails;
import com.projects.notas4int.bankservce.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountDetailsServiceImpl implements UserDetailsService {
    private final BankAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var account = repository.findBankAccountByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new BankAccountDetails(account);
    }
}

package org.bob.moneyplanner.java.spring.repository;

import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public Account findAccountByEmail(String email);
}

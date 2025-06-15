package com.mamta.accounts.repository;

import com.mamta.accounts.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByMobileNumberAndActiveSw(String mobileNumber, boolean active);

}

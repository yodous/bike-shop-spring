package com.example.repository;

import com.example.model.AccountActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivationTokenRepository extends JpaRepository<AccountActivationToken, Integer> {
}

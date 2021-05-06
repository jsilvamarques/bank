package br.com.impacta.bank.account.repository;

import br.com.impacta.bank.account.domain.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

    List<BankTransaction> findByAccountId(Long accountId);

}

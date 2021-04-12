package br.com.impacta.bank.account.repository;

import br.com.impacta.bank.account.domain.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

}

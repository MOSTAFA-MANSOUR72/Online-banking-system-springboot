package bank.management.system.repository;

import bank.management.system.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByAccountNumber(String accountNumber);
    List<Transaction> findAllByAccountNumberAndCreatedAtIsBetween(String accountNumber, LocalDate starting, LocalDate ending);
}

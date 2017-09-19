package crafting.code.bankkata;

import java.util.List;

public class AccountService {

    private TransactionRepository transactionRepository;
    private StatementPrinter statementPrinter;

    public AccountService(TransactionRepository transactionRepository,
                          StatementPrinter statementPrinter) {
        this.transactionRepository = transactionRepository;
        this.statementPrinter = statementPrinter;
    }

    public void deposit(int amount){
        transactionRepository.createDeposit(amount);
    }

    public void withdraw(int amount) {
        transactionRepository.createWithdraw(amount);
    }

    public void printStatement() {
        List<Transaction> transactions = transactionRepository.allTransactions();
        statementPrinter.format(transactions);
    }
}

package crafting.code.bankkata;

import java.util.List;

public interface TransactionRepository {
    void createDeposit(int amount);

    void createWithdraw(int amount);

    List<Transaction> allTransactions();
}

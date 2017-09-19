package crafting.code.bankkata;

import java.util.ArrayList;
import java.util.List;

public class LocalTransactionRepository implements TransactionRepository {

    private List<Transaction> transactions = new ArrayList<>();

    private final Clock clock;

    public LocalTransactionRepository(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void createDeposit(int amount) {
        transactions.add(new Transaction(amount, clock.now()));
    }

    @Override
    public void createWithdraw(int amount) {
        transactions.add(new Transaction(-amount, clock.now()));
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }
}

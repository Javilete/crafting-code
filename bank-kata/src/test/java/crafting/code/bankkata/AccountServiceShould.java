package crafting.code.bankkata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceShould {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private StatementPrinter statementPrinter;

    private AccountService accountService;

    @Before
    public void setUp() throws Exception {
        accountService = new AccountService(transactionRepository, statementPrinter);
    }

    @Test
    public void save_a_deposit_transaction() {
        accountService.deposit(100);

        verify(transactionRepository).createDeposit(100);
    }

    @Test
    public void save_a_withdraw_transaction() {
        accountService.withdraw(100);

        verify(transactionRepository).createWithdraw(100);
    }

    @Test
    public void print_all_transactions() {
        List<Transaction> transactions = Arrays.asList(new Transaction(100, "10/04/2014"));
        when(transactionRepository.allTransactions()).thenReturn(transactions);

        accountService.printStatement();

        verify(statementPrinter).format(transactions);
    }
}

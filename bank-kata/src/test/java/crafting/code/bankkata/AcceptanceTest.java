package crafting.code.bankkata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcceptanceTest {

    @Mock
    private Console console;

    @Mock
    private Clock clock;

    private AccountService accountService;
    private StatementPrinter statementPrinter;
    private TransactionRepository transactionRepository;

    @Before
    public void setUp() throws Exception {
        statementPrinter = new StatementPrinter(console);
        transactionRepository = new LocalTransactionRepository(clock);
        accountService = new AccountService(transactionRepository, statementPrinter);
    }

    @Test
    public void print_statements_after_several_transactions() {
        String statements = "DATE | AMOUNT | BALANCE\n" +
                "10/04/2014 | 500.00 | 1400.00\n" +
                "02/04/2014 | -100.00 | 900.00\n" +
                "01/04/2014 | 1000.00 | 1000.00";

        when(clock.now()).thenReturn("01/04/2014", "02/04/2014", "10/04/2014");

        accountService.deposit(1000);
        accountService.withdraw(100);
        accountService.deposit(500);
        accountService.printStatement();

        verify(console).print(statements);
    }
}

package crafting.code.bankkata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StatementPrinterShould {

    @Mock
    private Console console;

    private StatementPrinter formatter;

    @Before
    public void setUp() throws Exception {
        formatter = new StatementPrinter(console);
    }

    @Test
    public void format_a_list_of_transactions() {
        String formattedTransactions = "DATE | AMOUNT | BALANCE\n" +
                "10/04/2014 | 500.00 | 1400.00\n" +
                "02/04/2014 | -100.00 | 900.00\n" +
                "01/04/2014 | 1000.00 | 1000.00";


        formatter.format(Arrays.asList(
                new Transaction(1000, "01/04/2014"),
                new Transaction(-100, "02/04/2014"),
                new Transaction(500, "10/04/2014")));

        verify(console).print(formattedTransactions);
    }
}

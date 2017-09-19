package crafting.code.bankkata;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.Collections.reverse;
import static java.util.stream.Collectors.toList;

public class StatementPrinter {

    private static final String HEADER = "DATE | AMOUNT | BALANCE\n";
    private static final String SEPARATOR = " | ";
    private Console console;

    public StatementPrinter(Console console) {
        this.console = console;
    }

    public void format(List<Transaction> transactions) {
        List<String> formattedTransactions = printStatements(transactions);
        reverse(formattedTransactions);

        console.print(HEADER +
                formattedTransactions.stream().collect(Collectors.joining("\n")));
    }

    private List<String> printStatements(List<Transaction> transactions) {
        AtomicInteger runningBalance = new AtomicInteger(0);
        return transactions
                .stream()
                .map(transaction -> printLine(runningBalance, transaction))
                .collect(toList());
    }

    private String printLine(AtomicInteger runningBalance, Transaction transaction) {
        return transaction.getDate() + SEPARATOR +
                format(transaction.getAmount()) + SEPARATOR +
                format(runningBalance.addAndGet(transaction.getAmount()));

    }

    private String format(int amount) {
        return new DecimalFormat("#.00")
                .format(amount);
    }
}

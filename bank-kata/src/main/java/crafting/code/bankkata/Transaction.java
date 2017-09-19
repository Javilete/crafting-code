package crafting.code.bankkata;

public class Transaction {

    private int amount;
    private String date;

    public Transaction(int amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

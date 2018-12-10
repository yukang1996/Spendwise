package my.edu.um.fsktm.spendwise;

public class TransactionRow {
    public String date;
    public String transaction_type;
    public String category;
    public double amount;
    public String notes;

    public TransactionRow (String date, String transaction_type, String category, double amount
    ,String notes){
        this.date = date;
        this.transaction_type = transaction_type;
        this.category = category;
        this.amount = amount;
        this.notes = notes;
    }
}

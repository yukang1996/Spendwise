package my.edu.um.fsktm.spendwise;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TransactionRow implements Parcelable {
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

    public TransactionRow (Parcel parcel){
        this.date = parcel.readString();
        this.transaction_type = parcel.readString();
        this.category = parcel.readString();
        this.amount = parcel.readDouble();
        this.notes = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.date);
        parcel.writeString(this.transaction_type);
        parcel.writeString(this.category);
        parcel.writeDouble(this.amount);
        parcel.writeString(this.notes);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<TransactionRow> CREATOR = new Creator<TransactionRow>() {

        @Override
        public TransactionRow createFromParcel(Parcel source) {
            return new TransactionRow(source);
        }

        @Override
        public TransactionRow[] newArray(int size) {
            return new TransactionRow[size];
        }

    };
}

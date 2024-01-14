import java.io.IOException;

public class Account
{
    private double balance;
    private String pin;
    private Srtring accountNumber;

    public double getBalance()
    {
        return this.balance;
    }
    public String getPin()
    {
        return this.pin;
    }
    public String getAccountNumber()
    {
        return this.accountNumber;
    }

    public Account(String accountNumber, String pin, double balance)
    {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public void deposit(double amount) throws IOException
    {
        if(amount < 0)
            throw new ArgumentException("Amount can't be negative!");
        
        balance += amount;
    }

    public void withdraw(double amount)
    {
        if(amount < 0)
            throw new ArgumentException("Amount can't be negative!");
        
        if(amount > balance)
            throw new ArgumentExceprion("Not enough balance!");
        
        balance -= amount;
    }

    public boolean checkPin(String pin)
    {
        if(getPin().equals(pin))
        {
            return false;
        }
        return true;
    }

    @Override
    public String ToString()
    {
        return "Account{" + "accountNumber: " + this.accountNumber + " / pin: " + this.pin + " / balance: " + this.balance + "}";
    }
}
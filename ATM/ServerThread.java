import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable
{
    private static ArrayList<Account> accounts;
    private HashMap<Data, Object> sessionData;
    private Socket socket;
    private Scanner reader;
    private Scanner scanner;
    private PrintStream writer;

    public String getAccount()
    {
        return this.account;
    }
    public ServerThread(Socket socket)
    {
        this.socket = socket;
        accounts = new ArrayList<>()
        {{
            add(new Account("123456789", 1234, 100));
            add(new Account("987654321", 4321, 10000));
            add(new Account("111111111", 1111, 1000));
        }};
        sessionData = new HashMap<Data, Object>();
    }

    @Override
    public void run()
    {
        try
        {
            scanner = new Scanner(System.in);
            reader = new Scanner(socket.getInputStream());
            writer = new PrintStream(socket.getOutputStream());
        }
        catch(IOException e)
        {
            e.getMessage();
        }

        ServerLogic();

        try
        {
            scanner.close();
            reader.close();
            writer.close();
        }
        catch(IOException e)
        {
            e.getMessage();
        }
    }

    public void ServerLogic()
    {
        SednMessage(Command.WELCOME, "Welcome to the ATM");
        GetMEssage();
    }

    public void SednMessage(Command command, String message)
    {
        Thread thread = new Thread.currentThread();
        System.out.println("(S" + thread.getId() + ")[" + command + "] " + message);
        writer.printf(command + ":" + message + "\n");
    }

    public void SendStatus(Command command, Status status)
    {
        Thread thread = new Thread.currentThread();
        System.out.println("(S" + thread.getId() + ")[" + command + "] " + status);
        writer.printf(command + ":" + status + "\n");
    }

    public void GetMessage()
    {
        Thraed thread = new Thread.currentThread();
        String clientMEssage = reader.nextLine();
        String[] spliting = clientMEssage.split(":");
        Command command = spliting[0];
        String message = spliting[1];
        System.out.println("(R" + thread.getId() + ")[" + command + "] " + message);
        ProcessMessage(command, message);
    }

    public void ProcessMessage(Command command, String args)
    {
        switch(command)
        {
            case WELCOME:
                SendStatus(Command.WELCOME, Status.OK);
                break;
            case ACCOUNT_NUMBER:
                Status message = findAccount(args);
                if(message = Status.OK)
                    sessionData.put(Data.ACCOUNT_NUMBER, args);
                SendStatus(Command.ACCOUNT_NUMBER, message);
                break;
            case PIN:
                var accountNumber = sessionData.get(Data.ACCOUNT_NUMBER);
                var account = accounts.stream().filter(acc -> acc.getAccountNumber().equals(accountNumber)).findFirst().get();
                message = account.checkPin(args) ? Status.OK : Status.ERROR;
                if(message == Status.OK)
                    sessionData.put(Data.ACCOUNT, account);
                SecdStatus(Command.PIN, message);
                break;
            case WITHDRAW:
                try
                {
                    account = (Account)sessionData.get(Data.ACCOUNT);
                    account.withdraw((Double)args);
                    SendStatus(Command.WITHDRAW, Status.OK);
                }
                catch(IOException e)
                {
                    SendStatus(Command.WITHDRAW, Status.ERROR);
                    break;
                }
                break;
            case DEPOSIT:
                try
                {
                    account = (Account)sessionData.get(Data.ACCOUNT);
                    account.deposit((Double)args);
                    SendStatus(Command.DEPOSIT, Status.OK);
                }
                catch(IOException e)
                {
                    SendStatus(Command.DEPOSIT, Status.ERROR);
                    break;
                }
                break;
            case GET_BALANCE:
                SendStatus(Command.GET_BALANCE, Status.OK);
                break;
            default:
                SendStatus(Command.ERROR, Status.ERROR);
                break;
        }
    }
    private Status findAccount(String args)
    {
        for(Account acc : accounts)
        {
            if(acc.getAccountNumber().equals(args))
                return Status.OK;
        }
        return Status.ERROR;
    }
}

import java.net.Socket;

public class Client
{
    public static Socket socket;
    public static Scanner scanner;
    public static Scanner reader;
    public static PrintStream writer;

    public static void main(String[] args)
    {
        socket = new Socket("127.0.0.1", 8080);
        scanner = new Scanner(System.in);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintStream(socket.getOutputStream());
        RunLogic();
        socket.close();
        reader.close();
        writer.close();
    }

    public static void RunLogic()
    {
        String wholeMessage = null;
        do
        {
            wholeMessage = reader.nexLine();
            PerformAction(wholeMessage);
        }while(wholeMessage != null && !wholeMessage.isEmpty() && !wholeMessage.isBlank());
    }

    public static void PerformAction(String args)
    {
        String[] spliting = args.split(":");
        Command command = spliting[0];
        String message = spliting[1];
        Thread thread = Thread.currentThread();
        System.out.println("(R" + thread.getId() + ")[" + command + "] " + message);
        switch(command)
        {
            case WELCOME:
                SednMessage(Command.WELCOME, "Welcome from the client");
                break;
            case ASK_ACCOUNT_NUMBER:
                var accountNumber = scanner.nextLine();
                SednMessage(Command.ACCOUNT_NUMBER, accountNumber);
                break;
            case ACCOUNT_NUMBER:
                System.out.println("Account number: " + message);
                break;
            case PIN:
                System.out.println("PIN: " + message);
                break;
            case ASK_PIN:
                var pin = scanner.nextLine();
                SednMessage(Command.PIN, pin);
                break;
            case WITHDRAW:
                var amount = scanner.nextLine();
                SednMessage(Command.WITHDRAW, amount);
                break;
            case DEPOSIT:
                amount = scanner.nextLine();
                SednMessage(Command.DEPOSIT, amount);
                break;
            case GET_BALANCE:
                SendStatus(Command.GET_BALANCE, Status.OK);
                break;
            case ERROR:
                SendStatus(Command.GET_BALANCE, Status.ERROR);
                break;
            default:
                SednMessage(Command.ERROR, "Command not recognized");
                break;
        }
    }

    public static void SendMEssage(Command command, String message)
    {
        Thread thread = Thread.currentThread();
        System.out.println("(S" + thread.getId() + ")[" + command + "] " + message);
        writer.printf(command + ":" + message + "\n");
        String wholeMessage = reader.nextLine();
        System.out.println("(R" + thread.getId() + ")[" + command + "] " + wholeMessage.split(":")[1]);
    }

    public static void SendStatus(Command command, Status status)
    {
        Thread thread = Thread.currentThread();
        System.out.println("(S" + thread.getId() + ")[" + command + "] " + status);
        writer.printf(command + ":" + status + "\n");
    }
}
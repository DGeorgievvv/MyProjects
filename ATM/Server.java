import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class Server
{
    public static Scanner scanner;
    public static PrintStream writer;

    public static void main(Sring[] args)
    {
        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService executor = new Executor.newFixedThreadPool(3);
        while(true)
        {
            Socket socket = serverSocket.accept();
            executor.execute(new ServerThread(socket));
        }
    }
}
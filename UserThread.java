import java.net.Socket;

public class UserThread implements Runnable
{
    UniversitySystem universitySystem;
    private Socket clientSocket;
    
    public UserThread(Socket clientSocket, UniversitySystem universitySystem)
    {
        this.clientSocket = clientSocket;
        this.universitySystem = universitySystem;
    }

    @Override
    public void run()
    {
        //get username and password from client
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(clientSocket.getInputStream());
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }

        String credentials = scanner.nextLine();
        System.out.println("Credentials received, can start processing.");
        String userName = credentials.split(" ")[0];
        String password = credentials.split(" ")[1];

        //using socket to receive messages
        try
        {
            universitySystem.loginUser(userName, password, clientSocket);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}

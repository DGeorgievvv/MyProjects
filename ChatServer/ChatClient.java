import java.io.*;
import java.net.*;

public class ChatClient
{
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            BufferedReader consoleReader = new BufferedReader(new inputStreamReader(System.in));

            System.out.println("Свързване към сървъра. МОже да започнете ра пишете събщения: ");

            Thread readThread = new Thread(() -> {
                try
                {
                    String serverResponse;
                    while((serverResponse = reader.readLine()) != null)
                    {
                        System.out.println("От сървъра: " + serverResponse);
                    }
                }
                catch(IOException e)
                {
                    System.out.println("Връзката със сървъра е прекъсната!");
                }
            });
            readThread.start();

            String userInput;
            while((userInput = consoleReader.readLine()) != null)
            {
                writer.println(userInput);
            }

            socket.close();
        }
        catch(IOException e)
        {
            System.out.println("Грешка при врузка със сървъра: " + e.getMEssage());
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.*;

public class MultiThreadChatServer
{
    private static final int PORT = 8080;
    private static final Set<PrintWriter> writers = new HashSet<>();
    
    public static void main(String[] args)
    {
        try(ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("Сървърът е стартиран на порт: " + PORT);

            while(true)
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Нов клиент е свързан.");
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                writers.add(writer);
                Thread clienThread = new Thread(new ClientHandler(clientSocket));
                clienThread.start();
            }
        }
        catch(IOException e)
        {
            System.out.println("Грешка в сървъра: " + e.getMessage());
        }
    }

    private class ClientHandler implements Runnable
    {
        private final Socket clientSocket;
        private final BufferedReader reader;

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
            try
            {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }
            catch(IOException e)
            {
                System.out.println("Грешка при създаване на входящ поток за клиента: " + e.getMessage());
            }
        }

        public void run()
        {
            PrintWriter clientWriter;
            try
            {
                clientWriter = new PrintWriter(clientSocket.getOutputStream());
                writers.add(clientWriter);
                String message;
                while((message = reader.readline()) != null)
                {
                    System.out.println("Получено съобщение: " + message);
                    broadcast(message);
                }
            }
            catch(IOException e)
            {
                System.out.println("Грешка при комуникация с клиент: " + e.getMessage());
            }
            finally
            {
                writers.remove(clientWriter);
                try
                {
                    clientSoccket.close();
                }
                catch(IOException e)
                {
                    System.out.println("Грешка при затваряне на връзката с клиента: " + e.getMessage());
                }
            }
        }

        private void broadcast(String message)
        {
            for(PrintWriter writer : writers)
            {
                writer.println(message);
            }
        }
    } 
}

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server2 {
    private static final String MASTER_ADDRESS = "localhost";
    private static final int MASTER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(MASTER_ADDRESS, MASTER_PORT)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int localTime = new Random().nextInt(100);
            System.out.println("Tiempo del servidor antes de ajuste: " + localTime);

            writer.println(localTime);

            int adjustment = Integer.parseInt(reader.readLine());
            localTime += adjustment;

            System.out.println("Ajuste recibido: " + adjustment);
            System.out.println("Tiempo sincronizado: " + localTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

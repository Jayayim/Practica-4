import java.io.*;
import java.net.*;
import java.util.Random;

public class Cliente {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int localTime = new Random().nextInt(100);
            System.out.println(" Tiempo del cliente antes de ajuste: " + localTime);

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

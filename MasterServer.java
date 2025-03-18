import java.io.*;
import java.net.*;
import java.util.*;

public class MasterServer {
    private static final int PORT = 5000;
    private static List<Socket> servers = new ArrayList<>();
    private static List<Socket> clients = new ArrayList<>();
    private static List<Integer> times = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor Maestro esperando servidores y clientes...");

            // Aceptamos 2 servidores y 3 clientes
            for (int i = 0; i < 2; i++) {
                Socket server = serverSocket.accept();
                servers.add(server);
                System.out.println("Servidor conectado: " + server.getInetAddress());
            }

            for (int i = 0; i < 3; i++) {
                Socket client = serverSocket.accept();
                clients.add(client);
                System.out.println("Cliente conectado: " + client.getInetAddress());
            }

            // Recibir tiempos de servidores y clientes
            receiveTimes(servers, "Servidor");
            receiveTimes(clients, "Cliente");

            // Calcular tiempo promedio
            int masterTime = new Random().nextInt(100); // Tiempo del Maestro
            System.out.println("⏳ Tiempo del Maestro: " + masterTime);
            int averageTime = (masterTime + times.stream().mapToInt(Integer::intValue).sum()) / (times.size() + 1);

            // Enviar ajustes de tiempo
            sendAdjustments(servers, "Servidor", averageTime);
            sendAdjustments(clients, "Cliente", averageTime);

            System.out.println("Sincronización completada.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveTimes(List<Socket> list, String type) throws IOException {
        for (Socket s : list) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            int time = Integer.parseInt(reader.readLine());
            times.add(time);
            System.out.println("Tiempo recibido de " + type + ": " + time);
        }
    }

    private static void sendAdjustments(List<Socket> list, String type, int averageTime) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            PrintWriter writer = new PrintWriter(list.get(i).getOutputStream(), true);
            int adjustment = averageTime - times.get(i);
            writer.println(adjustment);
            System.out.println("Ajuste enviado a " + type + " " + (i + 1) + ": " + adjustment);
        }
    }
}

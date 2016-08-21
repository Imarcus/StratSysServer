import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Marcus on 2016-08-21.
 */
public class Server {

    public static void main(String[] args){
        Server server = new Server();
        server.startServer();
    }

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public void startServer(){
        try {
            serverSocket = new ServerSocket(54555);

            while(true){
                clientSocket = serverSocket.accept();
                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());

                SkiGuidePacket packet = (SkiGuidePacket)inFromClient.readObject();
                String recommendation = calculateRecommendation(packet);
                sendRecommendation(recommendation);
                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRecommendation(String recommendation) {
        try {
            System.out.println(recommendation);
            DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
            dOut.writeUTF(recommendation);
            dOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calculateRecommendation(SkiGuidePacket packet) {
        String result;
        if(packet.getAge() == SkiGuidePacket.Age.ZEROFOUR){
            result = String.valueOf(packet.getLength());
        } else if (packet.getAge() == SkiGuidePacket.Age.FIVEEIGHT){
            result = String.valueOf(packet.getLength() + 10) + " - " + String.valueOf(packet.getLength() + 20);
        } else { //NINEPLUS
            if(packet.getStyle().equals(SkiGuidePacket.Style.KLASSISK)){
                if(packet.getLength() >= 187){
                    result = "207";
                } else {
                    result = String.valueOf(packet.getLength() + 20);
                }
            } else { //FRISITL
                result = String.valueOf(packet.getLength() + 10) + " - " + String.valueOf(packet.getLength() + 15);
            }
        }
        return result;
    }
}

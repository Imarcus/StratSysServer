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

                //TODO instance of packet
                SkiRequestPacket packet = (SkiRequestPacket)inFromClient.readObject();
                SkiRecommendationPacket recommendation = calculateRecommendation(packet);
                sendRecommendation(recommendation);
                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRecommendation(SkiRecommendationPacket recommendation) {
        try {
            System.out.println(recommendation);
            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            outToServer.writeObject(recommendation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SkiRecommendationPacket calculateRecommendation(SkiRequestPacket packet) {
        SkiRecommendationPacket result;
        if(packet.getAge() == SkiRequestPacket.Age.ZEROFOUR){
            result = new SkiRecommendationPacket(packet.getLength(), 0, null);
        } else if (packet.getAge() == SkiRequestPacket.Age.FIVEEIGHT){
            result = new SkiRecommendationPacket(packet.getLength() + 10, packet.getLength() + 20, null);
        } else { //NINEPLUS
            if(packet.getStyle().equals(SkiRequestPacket.Style.KLASSISK)){
                if(packet.getLength() >= 187){
                    result = new SkiRecommendationPacket(207, 0, "Klassiska skidor tillverkas bara till längder upp till 207cm.");
                } else {
                    result = new SkiRecommendationPacket(packet.getLength() + 20, 0, null);
                }
            } else { //FRISITL
                result = new SkiRecommendationPacket(packet.getLength() + 10, packet.getLength() + 15, "Enligt tävlingsreglerna får " +
                        "inte skidan understiga kroppslängden med mer än 10cm.");
            }
        }
        return result;
    }
}

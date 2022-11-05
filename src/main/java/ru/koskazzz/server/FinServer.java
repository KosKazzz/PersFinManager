package ru.koskazzz.server;

import org.json.simple.JSONObject;
import ru.koskazzz.finmanager.PersonalFinMan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FinServer {
    public static void StartServer() {
        PersonalFinMan pfm = PersonalFinMan.LoadFromFile();
        try (ServerSocket serverSocket = new ServerSocket(8989)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    String fromClient = in.readLine();
                    pfm.addPurchaseList(fromClient);
                    JSONObject toClient = pfm.maxCategory();
                    out.write(toClient.toJSONString());
                    out.flush();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

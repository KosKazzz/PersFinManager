package ru.koskazzz.client;

import ru.koskazzz.parser.MultiParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FinClient {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 8989)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Введите - что купили и за сколько денег через пробел :");
            String fromClient = reader.readLine();
            out.write(MultiParser.ClientStringToJson(fromClient) + "\n");
            out.flush();
            String serverRequest = in.readLine();
            System.out.println(serverRequest);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection closed");
    }

}

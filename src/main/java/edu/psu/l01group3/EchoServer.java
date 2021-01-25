/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.psu.l01group3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author mtripo225
 */
public class EchoServer {

    /**
     * Receives data from a client and places it to our supplier
     * and gets processed through the stream. As messages come through,
     * we process them and "spit" them back out to the client. Aka, an
     * Echo Server! We keep streaming infinitely and only "process"
     * our responses, so long as they are not null.
     * @param br {@link BufferedReader} Our input reader for data in
     * @param out {@link PrintWriter} Our output writer
     */
    private static void recvAndSend(BufferedReader br, PrintWriter out) {
        Supplier<String> socketInput = () -> {
            try {
                return br.readLine();
            } catch (IOException ex) {
                return null;
            }
        };
        Stream<String> stream = Stream.generate(socketInput);
        stream.map(s -> {
            if (s != null) {
                System.out.println("Client request: " + s);
                out.println(s);
            }
            return s;
        }).allMatch(s -> s!=null);
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Waiting for connection.....");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client: " + clientSocket.getInetAddress().getHostName());


            try (BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                recvAndSend(br, out);
            }

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
}

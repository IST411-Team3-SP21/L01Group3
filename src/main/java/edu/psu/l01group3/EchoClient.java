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
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author mtripo225
 */
public class EchoClient {


    /**
     * To ensure we isolate our business logic into clearly
     * defined methods, we have a method which will write
     * our client's data, aka the data retrieve via the {@link Scanner},
     * We will provide the data to the supplier with the {@link String} type.
     * The supplier is typically used for lazy generation generation of data.
     * <p>
     * We pass the supplier to the generate method on {@link Stream} which gives
     * us, essentially, an infinite loop, where we will continuously wait, for infinity,
     * to receive data from the the Scanner.
     * <p>
     * We then chain in the map method to process the data received from the supplier (via scanner)
     * <p>
     * we also removed the simple line print for the server response, from the text, because this text
     * wasn't actually coming from the server..... We were just printing out the text, WE (the client)
     * sent to the server. We need to block and wait for a response from the server via our {@link BufferedReader}
     *
     * Last, we are using a do-while loop here for a reason. We want to be able to receive input from
     * the user before we evaluate whether to send it or not (QUIT or not)
     *
     * @param out {@link PrintWriter} The writer to write from
     * @param br {@link BufferedReader} The input buffer
     */
    private static void sendAndRecvEcho(PrintWriter out, BufferedReader br) throws IOException {
        String echoString;
        String response;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("Enter Some Text (\"quit\" to exit): ");
            echoString = scanner.nextLine();

            out.println(echoString);
            if (!echoString.equalsIgnoreCase("quit")) {
                response = br.readLine();
                System.out.println(response);
            } else {
                System.out.println("Exiting....");
                System.exit(0);
            }
        } while (!echoString.equalsIgnoreCase("quit"));
    }

    public static void main(String[] args) {
        try {
            System.out.println("Waiting for connection.....");
            InetAddress localAddress = InetAddress.getLocalHost();

            Socket clientSocket = new Socket(localAddress, 6000);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("Connected to Server: " + clientSocket.getInetAddress().getHostName());

            // Read in from CLI and write to printer
            sendAndRecvEcho(out, br);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}

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
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author mtrip225
 */
public class EchoClient {
    public static void main(String[] args) {
        try {
            System.out.println("Waiting for connection.....");
            InetAddress localAddress = InetAddress.getLocalHost();

            try (Socket clientSocket = new Socket(localAddress, 6000);
                 PrintWriter out = new PrintWriter(
                         clientSocket.getOutputStream(), true);
                 BufferedReader br = new BufferedReader(
                         new InputStreamReader(
                                 clientSocket.getInputStream()))) {
                System.out.println("Connected to server");
                Scanner scanner = new Scanner(System.in);
                Supplier<String> scannerInput = scanner::next;
                System.out.print("Enter text: ");
                Stream.generate(scannerInput)
                        .peek(s -> {
                            out.println(s);
                            System.out.println("Server response: " + s);
                            System.out.print("Enter text: ");
                        })
                        .allMatch(s -> !"quit".equalsIgnoreCase(s));

            }
        } catch (IOException ex) {
            // Handle exceptions
        }
    }
}

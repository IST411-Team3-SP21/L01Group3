/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.psu.l01individual;
/* 
Project: Lab 1
Purpose Details: Client-Server capitalization application
Course: IST 411
Author: Josiel Delgadillo
Date Developed: 1/20/2021
Last Date Changed:
Revision: 1
*/


import edu.psu.l01group3.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author josieldelgadillo
 */
public class EchoServer {
    public static void main(String[] args) {
        System.out.println("Capitalization Echo Server");
        
        //Create instance of ServerSocket
        try(ServerSocket serverSocket = new ServerSocket(6000)){
            
            // Wait for connection to client
            System.out.println("Server waiting for connection to client...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Server: Connected to client");
            
            try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    clientSocket.getInputStream()));
                    
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){
                
                // inside try
                // Repeat Client response
                String inputLine;
                while((inputLine = br.readLine().toUpperCase()) != null){
                    System.out.println("Client Response: " + inputLine);
                    out.println(inputLine);
                }
            }
            
        }
        catch(IOException ex){
            // Handle exceptions
            ex.printStackTrace();
        }
    }
}

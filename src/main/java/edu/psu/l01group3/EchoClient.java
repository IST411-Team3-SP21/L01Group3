/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.psu.l01group3;
/* 
Project: Lab 1
Purpose Details: Client-Server capitalization application
Course: IST 411
Author: Josiel Delgadillo
Date Developed: 1/20/2021
Last Date Changed:
Revision: 1
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author josieldelgadillo
 */
public class EchoClient {
    public static void main(String args[]) throws UnknownHostException {
           System.out.println("Capitalizing Echo Client");
           
           try{
               System.out.println("Client waiting on connection to server...");
               InetAddress localAddress = InetAddress.getLocalHost();
               
               try(Socket clientSocket = new Socket(localAddress, 6000);
                       PrintWriter out = new PrintWriter(
                            clientSocket.getOutputStream(), true);
                       BufferedReader br = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()))){
                   
                   System.out.println("Client: Connected to server");
                   
                   // Get and handle input from Client
                   Scanner scanner = new Scanner(System.in);
                   while(true){
                       System.out.println("Enter text: ");
                       String inputLine = scanner.nextLine();
                       
                       // Change input from client to uppercase
                       //inputLine = inputLine.toUpperCase();
                       
                       if("quit".equalsIgnoreCase(inputLine))
                           break;
                       
                       out.println(inputLine);
                       String response = br.readLine();
                       System.out.println("Client: " + response);
                   }
                   
               }
               catch(IOException ex){
                   //Handle Exception
               }
           }
           catch(Exception ex){
               // Handle Exception
           }
           
           
       }
    
}


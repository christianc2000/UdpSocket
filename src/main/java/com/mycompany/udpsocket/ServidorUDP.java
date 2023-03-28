/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.udpsocket;

/**
 *
 * @author Christian
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServidorUDP {
    private final int BUFFER_SIZE = 1024;
    private DatagramSocket serverSocket;
    private byte[] buffer;
    private DatagramPacket receivePacket;
    private DatagramPacket sendPacket;

    public ServidorUDP(int port) throws Exception {
        serverSocket = new DatagramSocket(port);
        buffer = new byte[BUFFER_SIZE];
        receivePacket = new DatagramPacket(buffer, buffer.length);
        sendPacket = new DatagramPacket(buffer, buffer.length);
    }

    public void run() {
        try {
            System.out.println("Servidor esperando datagramas...");

            while (true) {
                serverSocket.receive(receivePacket); // Espera a que llegue un datagrama

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String mensajeRecibido = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Datagrama recibido desde " + clientAddress.getHostName() + ":" + clientPort);
                System.out.println("Mensaje recibido: " + mensajeRecibido);

                // Envía la respuesta al cliente
                String mensajeRespuesta = "Respuesta del servidor: " + mensajeRecibido.toUpperCase();
                byte[] sendData = mensajeRespuesta.getBytes();
                sendPacket.setData(sendData);
                sendPacket.setAddress(clientAddress);
                sendPacket.setPort(clientPort);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServidorUDP servidor = new ServidorUDP(1234);
            servidor.run();
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}

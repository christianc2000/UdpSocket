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
import java.util.Scanner;

public class ClienteUDP {
    private final int BUFFER_SIZE = 1024;
    private DatagramSocket clientSocket;
    private byte[] buffer;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;

    public ClienteUDP(String hostname, int port) throws Exception {
        clientSocket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(hostname);
        buffer = new byte[BUFFER_SIZE];
        sendPacket = new DatagramPacket(buffer, buffer.length, serverAddress, port);
        receivePacket = new DatagramPacket(buffer, buffer.length);
    }

    public void enviarMensaje(String mensaje) throws Exception {
        byte[] sendData = mensaje.getBytes();
        sendPacket.setData(sendData);
        clientSocket.send(sendPacket);
    }

    public String recibirRespuesta() throws Exception {
        clientSocket.receive(receivePacket);
        String respuesta = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return respuesta;
    }

    public void cerrarConexion() {
        if (clientSocket != null) {
            clientSocket.close();
        }
    }

    public static void main(String[] args) {
        try {
            ClienteUDP cliente = new ClienteUDP("localhost", 1234);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Lee la entrada del usuario y la envía al servidor
                System.out.print("Ingrese un mensaje para el servidor: ");
                String mensaje = scanner.nextLine();
                cliente.enviarMensaje(mensaje);

                // Recibe la respuesta del servidor y la muestra en pantalla
                String respuesta = cliente.recibirRespuesta();
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}

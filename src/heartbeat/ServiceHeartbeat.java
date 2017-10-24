/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import p2p_filesharing.Node;

/**
 *
 * @author ChathurangaKCD
 */
public class ServiceHeartbeat {

    private static final String REQUEST_MESSAGE = "ISALIVE";
    private static final String REPLY_MESSAGE = "ALIVE";

    static boolean checkHeartbeat(Node node, int myPort) {
        try {
            InetAddress address = InetAddress.getByName(node.getIp());
            sendMessege(address, node.getPort(), myPort, REQUEST_MESSAGE);
            return true;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ServiceHeartbeat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    static void sendMessege(InetAddress IPAddress, int port, int myPort, String message) {
        try (DatagramSocket socket = new DatagramSocket(myPort)) {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(sendPacket);
        } catch (IOException e) {

        }
    }
}

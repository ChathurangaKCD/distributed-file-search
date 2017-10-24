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
import p2p_filesharing.Node;

/**
 *
 * @author ChathurangaKCD
 */
class ServiceHeartbeat {

    private static final String REQUEST_MESSAGE = "0000 ISALIVE";
    private static final String REPLY_MESSAGE = "0000 ALIVE";

    private static String getMyIP() {
        return "127.0.0.1";
    }

    private static int getMyPort() {
        return 9867;
    }

    static boolean checkHeartbeat(Node node) {
        try {
            InetAddress address = InetAddress.getByName(node.getIp());
            sendMessege(address, node.getPort(), buildMessage(REQUEST_MESSAGE));
            return true;
        } catch (UnknownHostException ex) {
        }
        return false;
    }

    static boolean replyHeartbeatRequest(Node node) {//"0000 ISALIVE local_ip local_port"
        try {
            InetAddress address = InetAddress.getByName(node.getIp());
            sendMessege(address, node.getPort(), buildMessage(REPLY_MESSAGE));
            return true;
        } catch (UnknownHostException ex) {
        }
        return false;
    }

    static String buildMessage(String message) {
        StringBuilder msg = new StringBuilder(message);
        msg.append(" ");
        msg.append(getMyIP());
        msg.append(" ");
        msg.append(getMyPort());
        msg.replace(2, 4, "" + msg.length());
        return msg.toString();
    }

    static void sendMessege(InetAddress IPAddress, int port, String message) {
        try (DatagramSocket socket = new DatagramSocket(getMyPort())) {
            byte[] sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            socket.send(sendPacket);
        } catch (IOException e) {

        }
    }
}

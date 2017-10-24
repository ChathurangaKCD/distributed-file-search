/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.net.DatagramSocket;
import java.net.SocketException;
import p2p_filesharing.Node;

/**
 *
 * @author ChathurangaKCD
 */
public class Test {
    public static void main(String[] args) throws SocketException {
        String s=ServiceHeartbeat.buildMessage("0000 ISALIVE");
        System.out.println(s);
        DatagramSocket datagramSocket = new DatagramSocket(9876);
        Node node =new Node("192.168.43.164", datagramSocket.getLocalPort(), "KCD",datagramSocket);
        HeartbeatHandler.getInstance().checkHeartbeat(node);
    }
}

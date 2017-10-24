/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import java.io.IOException;
import java.net.InetAddress;
import p2p_filesharing.Node;

/**
 *
 * @author ChathurangaKCD
 */
public class NetworkHeartbeat {
        static boolean checkHeartbeat(Node node) {
        try {
            InetAddress address = InetAddress.getByName(node.getIp());
            return address.isReachable(1000);
        } catch (IOException e) {
        }
        return false;
    }
}

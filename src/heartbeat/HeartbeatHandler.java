/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heartbeat;

import p2p_filesharing.Node;

/**
 *
 * @author ChathurangaKCD
 */
public class HeartbeatHandler {

    static int port;

    /**
     * Sends a ping and a service heartbeat request.
     * Returns false if ping is not successful.
     * @param node
     * @return
     */
    boolean checkHeartbeat(Node node) {
        if (NetworkHeartbeat.checkHeartbeat(node)) {
            ServiceHeartbeat.checkHeartbeat(node, port);
            return true;
        }
        return false;
    }
    void updateHeartbeat(Node node){
        
    }
}

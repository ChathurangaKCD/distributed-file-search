/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_filesharing;

/**
 *
 * @author Scorpius
 */
public class Neighbour {

    private String ip;
    private int port;
    private String name;

    public Neighbour(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
    }

    public Neighbour(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.name = "";
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return (ip + ":" + port).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Neighbour && ((Neighbour) obj).ip.equals(this.ip) && ((Neighbour) obj).port == this.port);
    }
}

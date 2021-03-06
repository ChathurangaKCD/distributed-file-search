/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2p_filesharing_layered.layers;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.Future;

import java.util.logging.Level;
import java.util.logging.Logger;
import p2p_filesharing_layered.Constants;

/**
 *
 * @author Scorpius
 */
public class WebService {

    private HttpServer server;
    private MessageParser messageParser;

    public WebService(MessageParser messageParser) {
        this.messageParser = messageParser;
    }

    public void sendPacket(String message, String ip, int port) {
        Future<HttpResponse<JsonNode>> future = Unirest.post(String.format("http://%s:%d/", ip, port))
                            .header("content-type", "application/json")
                            .body((new WebServiceMessage(
                                    message,
                                    Constants.IP_ADDRESS,
                                    Constants.PORT
                            )).getJsonMessage())
              .asJsonAsync(new Callback<JsonNode>() {

              public void failed(UnirestException e) {
                  System.out.println("The request has failed");
              }

              public void completed(HttpResponse<JsonNode> response) {
                   int code = response.getStatus();
                   Headers headers = response.getHeaders();
                   JsonNode body = response.getBody();
                   InputStream rawBody = response.getRawBody();
              }

              public void cancelled() {
                  System.out.println("The request has been cancelled");
              }

          });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpResponse<String> response = Unirest.post(String.format("http://%s:%d/", ip, port))
//                            .header("content-type", "application/json")
//                            .body((new WebServiceMessage(
//                                    message,
//                                    Constants.IP_ADDRESS,
//                                    Constants.PORT
//                            )).getJsonMessage())
//                            .asString();
//                } catch (UnirestException ex) {
//                    Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }).start();
    }

    public void start() {
        try {
            server = HttpServer.create(new InetSocketAddress(Constants.PORT), 0);
            server.createContext("/", httpExchange -> {
                InputStream is = httpExchange.getRequestBody();
                String data = getStringFromInputStream(is);

                httpExchange.sendResponseHeaders(200, 2);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write("OK".getBytes());
                outputStream.close();

                WebServiceMessage wsMessage = WebServiceMessage.getMessageObject(data);
                processData(wsMessage.getMessage(), wsMessage.getIp(), wsMessage.getPort());
            });
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }

        server.setExecutor(null);
        server.start();
    }

    public void terminate() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    private void processData(String data, String ipAddress, int port) {
        System.out.println("Web Service Receiving data");
        messageParser.parseMessage(data, ipAddress, port);
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}

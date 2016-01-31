/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Jonathon Prehn and Kevin Prehn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **/
package bropals.lib.simplegame.networking;

import bropals.lib.simplegame.logger.ErrorLogger;
import bropals.lib.simplegame.logger.InfoLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the server-side of a basic client-server system.
 * @author Jonathon
 */
public class Server {
    
    /**
     * A list of all the client's handlers connected to this server.
     */
    private final List<ClientHandler> clientHandlers =
            Collections.synchronizedList(new ArrayList<ClientHandler>());
    /**
     * Whether or not the server is running and listening/responding to clients.
     */
    private boolean runningServer = false;
    /**
     * The socket for this server.
     */
    private final ServerSocket serverSocket;
    /**
     * The message handler for this server; it handles input from the connected clients.
     */
    private final ServerMessageHandler messageHandler;
    
    /**
     * Creates a new server at the specified port.
     * @param port the port to create the server
     * @param messageHandler the message handler that the server should use.
     * @throws IOException thrown if something goes wrong while making the
     * server.
     */
    public Server(int port, ServerMessageHandler messageHandler) throws IOException {
        serverSocket = new ServerSocket(port);
        this.messageHandler = messageHandler;
    }
    
    /**
     * Has the server start to listen for clients. This thread may be
     * blocked while the server listens for a new client.
     */
    public void startServer() {
        runningServer = true;
        while (runningServer) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(this, socket, 
                        messageHandler);
                clientHandlers.add(handler);
                handler.start();
                InfoLogger.println("A new client has connected to the server");
            } catch(IOException ioe) {
                ErrorLogger.println("An error occured in the server: " + ioe);
                runningServer = false;
            }
        }
        InfoLogger.println("The server has stopped listening for new clients.");
    }
    
    /**
     * Broadcast a message to all clients except for the one who sent it.
     * @param sentMessage the one who sent the message.
     * @param message the message to broadcast.
     */
    public void broadcastMessage(ClientHandler sentMessage, String message) {
        synchronized(clientHandlers) {
            for (ClientHandler handler : clientHandlers) {
                if (!handler.equals(sentMessage)) {
                    handler.sendMessageToClient(message);
                }
            }
        }
    }
    
    /**
     * Broadcast a message to all clients including the one who sent it.
     * @param message the message to broadcast.
     */
    public void broadcastMessage(String message) {
        synchronized(clientHandlers) {
            for (ClientHandler handler : clientHandlers) {
                handler.sendMessageToClient(message);
            }
        }
    }

    /**
     * Remove a ClientHandler from the server's list of handlers.
     * @param handler The handler being removed from the server's list of handlers.
     */
    public void removeHandler(ClientHandler handler) {
        clientHandlers.remove(handler);
        InfoLogger.println("Server now has " + clientHandlers.size() + " clients");
    }

    /**
     * Closes the server so the Server doesn't accept any more clients.
     * This method is called after startServer() is called to stop
     * the server.
     */
    public void stopServer() {
        if (runningServer) {
            try {
                serverSocket.close();
                runningServer = false;
            } catch(Exception e) {
                System.out.println("Exception while closing the server");
            }
        }
    }
}

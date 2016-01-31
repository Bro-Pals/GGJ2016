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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Implements the client-side of a basic client-server program.
 * @author Jonathon
 */
public class Client extends Thread {
    
    /**
     * The socket that the client is connected to.s
     */
    private Socket socket;
    /**
     * The reader for the client's socket.
     */
    private BufferedReader fromServer;
    /**
     * The writer for the client's socket
     */
    private PrintStream toServer;
    /**
     * The message handler that is listener to messages for this client.
     */
    private final ClientMessageHandler messageHandler;
    /**
     * Whether or not this client's ClientMessageHandler is listening for messages.
     */
    private boolean running = false;
    
    /**
     * Creates a new client using the given message handler and attempts to connect it at the specified
     * address.
     * @param address the address of the server to connect to
     * @param port the port number. Should be the same as the server's port.
     * @param messageHandler the message handler that this Client should use.
     * @throws IOException thrown is something goes wrong in connecting to the
     *  server.
     */
    public Client(InetAddress address, int port, ClientMessageHandler messageHandler) throws IOException {
        socket = new Socket(address, port);
        this.messageHandler = messageHandler;
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer = new PrintStream(socket.getOutputStream(), true);
    }
    
    /**
     * Stops communication with the server and stop running the client.
     */
    public void closeClient() {
        running = false;
        try {
            socket.close();
        } catch(IOException e) {
            ErrorLogger.println("Unable to close client: " + e);
        }
    }

    /**
     * Has this client begin to listen to the server for messages. This
     * will start a new thread.
     */
    public void listenToServer() {
        running = true;
        start();
    }
    
    /**
     * Send a message to the server the client is connected to.
     * @param message The message being sent to the server
     */
    public void sendMessageToServer(String message) {
        toServer.println(message);
    }
    
    /**
     * Get the address of the client's socket.
     * @return The address of the client's socket.
     */
    public InetAddress getAddress() {
        return socket.getInetAddress();
    }
    
    @Override
    public void run() {
        try {
            String str;
            while (running && (str = fromServer.readLine()) != null ) {
                messageHandler.handleMessage(this, str);
            }
        } catch(IOException e) {
            ErrorLogger.println("Error in client while communicating with "
             + " the server: " + e);
        }
    }    
}

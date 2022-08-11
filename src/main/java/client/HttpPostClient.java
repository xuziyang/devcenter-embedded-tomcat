package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpPostClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 8080);

        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        out.write("POST /hello HTTP/1.0\r\n".getBytes());
        out.write("Content-Type: application/x-www-form-urlencoded\r\n".getBytes());
        out.write("Host: 127.0.0.1\r\n".getBytes());
        out.write("Content-Length: 8\r\n\r\n".getBytes());
        out.flush();
        out.write("name=xzy".getBytes());
        out.flush();

        // read the response
        StringBuffer response = new StringBuffer();
        byte[] buffer = new byte[4096];
        int bytes_read;

        // Reads HTTP response
        while ((bytes_read = in.read(buffer, 0, 4096)) != -1) {
            // Print server's response
            for (int i = 0; i < bytes_read; i++)
                response.append((char) buffer[i]);
        }
        System.out.println(response);

        socket.close();
    }
}

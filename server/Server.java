package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Base64;

public class Server {
  public Server() {

  }

  public void run() throws IOException {
    try (ServerSocket serverSocket = new ServerSocket(9876)) {
      // 401
      Socket socket = serverSocket.accept();

      write401(socket.getOutputStream());
      socket.close();

      // 403
      Socket socket2 = serverSocket.accept();
      String requestContent = getRequest(socket2.getInputStream());
      System.out.println(requestContent);
    }

    String credentials = new String(
        Base64.getDecoder().decode("anJvYjpwYXNzd29yZA=="),
        Charset.forName("UTF-8"));
    System.out.println(String.format("Encrypted: %s\nDecrypted: %s", "anJvYjpwYXNzd29yZA==", credentials));
  }

  private void write401(OutputStream out) throws IOException {
    out.write("HTTP/1.1 401 Unauthorized\r\n".getBytes());
    out.write("WWW-Authenticate: Basic realm=\"Jrob server\"\r\n".getBytes());
    out.write("Connection: close\r\n".getBytes());
    out.write("\r\n".getBytes());

    out.flush();
  }

  private String getRequest(InputStream in) throws IOException {
    StringBuffer buffer = new StringBuffer();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
      String line;

      while ((line = reader.readLine()).trim().length() != 0) {
        buffer.append(String.format("%s\n", line));
      }
    }

    return buffer.toString();
  }

  private void displayRequest(OutputStream out, String requestString) throws IOException {
    out.write("HTTP/1.1 200 OK\r\n".getBytes());
    out.write("Content-Type: text/text\r\n".getBytes());
    out.write(String.format("Content-Length: %d\r\n", requestString.length()).getBytes());
    out.write("\r\n".getBytes());
    out.write(requestString.getBytes());

    out.flush();
  }

  public static void main(String[] args) throws IOException {
    (new Server()).run();
  }
}
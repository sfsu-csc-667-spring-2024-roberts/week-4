package cgi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class ScriptHandler {
  public static final String SCRIPT_PATH = "./test-script.js";

  public static void main(String[] args) throws IOException {
    // Resource file path
    ProcessBuilder builder = new ProcessBuilder(args[0]);

    // HTTP headers and protocol and querystring added into the environment
    Map<String, String> env = builder.environment();
    env.put("HTTP_Content-Length", "42");
    env.put("SERVER_PROTOCOL", "HTTP/1.1");
    env.put("QUERY_STRING", "a=b&c=d");

    // Spawn the process
    Process scriptProcess = builder.start();

    // Connect body of POST request to stdin of scriptprocess
    // scriptProcess.getOutputStream.write(POST_BODY);
    readScriptResult(scriptProcess.getInputStream());
  }

  private static void readScriptResult(InputStream in) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    String line;

    while ((line = reader.readLine()) != null) {
      System.out.println(line);
    }
  }
}

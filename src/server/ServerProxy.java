package server;

import com.google.gson.Gson;
import model.AppointmentInfo;
import model.AppointmentInfoRequest;
import model.AppointmentRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ServerProxy {
  private static ServerProxy _proxy = new ServerProxy();

  public static ServerProxy getInstance() {
    return _proxy;
  }

  private String HOST_NAME = "http://scheduling-interview-2021-265534043.us-west-2.elb.amazonaws.com/";
  private String TOKEN = "051cd9d3-59a1-4a48-aed4-91f7c02e0b0d";
  Gson gson = new Gson();

  public void start() {
    try {
      URL url = new URL(HOST_NAME + "api/Scheduling/Start?token=" + TOKEN);

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("POST");
      http.setDoOutput(true);
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        System.out.println("Start successful");
      } else {
        System.out.println("Error: " + http.getResponseMessage());
      }
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public AppointmentInfo[] stop() {
    AppointmentInfo[] response = null;
    try {
      URL url = new URL(HOST_NAME + "api/Scheduling/Stop?token=" + TOKEN);

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("GET");
      http.setDoOutput(true);
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream respBody = http.getInputStream();
        String respData = readString(respBody);
        response = gson.fromJson(respData,AppointmentInfo[].class);
      } else {
        System.out.println("Error: " + http.getResponseMessage());
      }
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }
  public AppointmentInfo[] getSchedule() {
    AppointmentInfo[] response = null;
    try {
      URL url = new URL(HOST_NAME + "api/Scheduling/Schedule?token=" + TOKEN);

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("GET");
      http.setDoOutput(true);
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream respBody = http.getInputStream();
        String respData = readString(respBody);
        response = gson.fromJson(respData,AppointmentInfo[].class);
      } else {
        System.out.println("Error: " + http.getResponseMessage());
      }
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }
  public void addAppointmentToSchedule(AppointmentInfoRequest req) {
    try {
      URL url = new URL(HOST_NAME + "api/Scheduling/Schedule?token=" + TOKEN);

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("POST");
      http.setDoOutput(true);
      http.connect();

      OutputStream reqBody = http.getOutputStream();
      reqBody.write(gson.toJson(req).getBytes(Charset.forName("UTF-8")));
      reqBody.close();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        System.out.println("Added appointment to schedule");
      } else {
        System.out.println("Error: " + http.getResponseMessage());
      }
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public AppointmentRequest getAppointmentRequest() {
    AppointmentRequest response = null;
    try {
      URL url = new URL(HOST_NAME + "api/Scheduling/AppointmentRequest?token=" + TOKEN);

      HttpURLConnection http = (HttpURLConnection) url.openConnection();

      http.setRequestMethod("GET");
      http.setDoOutput(true);
      http.connect();

      if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
        InputStream respBody = http.getInputStream();
        String respData = readString(respBody);
        response = gson.fromJson(respData,AppointmentRequest.class);
      } else if (http.getResponseCode() == 204){
        // Processed all requests in queue.
      }
      else {
        System.out.println("Error: " + http.getResponseMessage());
      }
    } catch (MalformedURLException | ProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }
  private static String readString(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf, 0, len);
    }
    return sb.toString();
  }
}

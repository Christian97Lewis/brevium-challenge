import model.AppointmentInfo;
import model.AppointmentRequest;
import server.ServerProxy;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) throws ParseException {
    ServerProxy server = new ServerProxy();
    server.start();
    Scheduler scheduler = new Scheduler();
    scheduler.schedule();
    List<AppointmentInfo> endAppointments = Arrays.asList(server.stop());
  }
}
import model.AppointmentInfo;
import model.AppointmentInfoRequest;
import model.AppointmentRequest;
import server.ServerProxy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Scheduler {

  private List<AppointmentInfo> startSchedule;
  private Map<Integer, List<Date>> doctorSchedules = new HashMap<>();
  private Map<Integer, List<Date>> patientSchedules = new HashMap<>();

  ServerProxy server = new ServerProxy();

  public void schedule() throws ParseException {
    List<AppointmentInfo> startSchedule = Arrays.asList(server.getSchedule());
    setStartSchedule(startSchedule);
    initializeDoctorSchedules();
    initializePatientSchedules();
    while(true) {
      AppointmentRequest nextAppointment = server.getAppointmentRequest();
      if (nextAppointment == null) // TODO: Check response code rather than null
        break;
      else {
        scheduleNextAppointment(nextAppointment);
      }
    }
  }
  public void scheduleNextAppointment(AppointmentRequest ar) {
    AppointmentInfoRequest req = new AppointmentInfoRequest(ar.getPersonId(), ar.isNew(), ar.getRequestId());

    // If new, just find a date at 15 or 16 UTC that is available for a requested doctor
    if (ar.isNew()) {
      for (String prefDay : ar.getPreferredDays()) {
        for (int prefDoc : ar.getPreferredDocs()) {
          List<Date> takenTimes = doctorSchedules.get(prefDoc);
          Date possibleDate = new Date(prefDay);
          possibleDate.setHours(15);
          if (takenTimes.contains(possibleDate)){
            possibleDate.setHours(16);
            if (takenTimes.contains(possibleDate)){
            } else {
              req.setDoctorId(prefDoc);
              TimeZone tz = TimeZone.getTimeZone("UTC");
              DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
              df.setTimeZone(tz);
              req.setAppointmentTime(df.format(possibleDate));
              // add appointment to startSchedule and patient and doctor maps
            }
          } else {
            req.setDoctorId(prefDoc);
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df.setTimeZone(tz);
            req.setAppointmentTime(df.format(possibleDate));
            // add appointment to startSchedule and patient and doctor maps
          }
        }
      }
    }
    // If not new, make sure preferredDays isn't within 7 days before or after last visit using patientSchedule, then pick valid doctor time and day
    else {
      //TODO
    }
    server.addAppointmentToSchedule(req);
  }

  public void initializeDoctorSchedules() throws ParseException {
    doctorSchedules.put(1, new ArrayList<Date>());
    doctorSchedules.put(2, new ArrayList<Date>());
    doctorSchedules.put(3, new ArrayList<Date>());
    for (AppointmentInfo appointment : startSchedule) {
      int key = appointment.getDoctorId();
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      doctorSchedules.get(key).add(df.parse(appointment.getAppointmentTime()));
    }
  }

  public void initializePatientSchedules() throws ParseException {
    for (AppointmentInfo appointment : startSchedule) {
      int key = appointment.getPersonId();
      if (patientSchedules.containsKey(key)) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        patientSchedules.get(key).add(df.parse(appointment.getAppointmentTime()));
      } else {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        List<Date> appointments = new ArrayList<>();
        appointments.add(df.parse(appointment.getAppointmentTime()));
        patientSchedules.put(key, appointments);
      }
    }
  }

  public void setStartSchedule(List<AppointmentInfo> startSchedule) {
    this.startSchedule = startSchedule;
  }
}

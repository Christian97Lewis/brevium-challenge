package model;

import java.util.Date;

public class AppointmentInfo {
  int doctorId;
  int personId;
  String appointmentTime;
  boolean isNewPatientAppointment;

  public int getDoctorId() {
    return doctorId;
  }

  public int getPersonId() {
    return personId;
  }

  public String getAppointmentTime() {
    return appointmentTime;
  }

  public boolean isNewPatientAppointment() {
    return isNewPatientAppointment;
  }
}

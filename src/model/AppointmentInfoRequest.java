package model;

import java.util.Date;

public class AppointmentInfoRequest {
  int doctorId;
  int personId;
  String appointmentTime;
  boolean isNewPatientAppointment;
  int requestId;

  public AppointmentInfoRequest(int personId, boolean isNewPatientAppointment, int requestId) {
    this.personId = personId;
    this.isNewPatientAppointment = isNewPatientAppointment;
    this.requestId = requestId;
  }

  public void setDoctorId(int doctorId) {
    this.doctorId = doctorId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  public void setAppointmentTime(String appointmentTime) {
    this.appointmentTime = appointmentTime;
  }

  public void setNewPatientAppointment(boolean newPatientAppointment) {
    isNewPatientAppointment = newPatientAppointment;
  }

  public void setRequestId(int requestId) {
    this.requestId = requestId;
  }
}

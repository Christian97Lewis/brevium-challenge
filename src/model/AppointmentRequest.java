package model;

public class AppointmentRequest {
  int requestId;
  int personId;
  String[] preferredDays;
  int[] preferredDocs;
  boolean isNew;

  public int getRequestId() {
    return requestId;
  }

  public int getPersonId() {
    return personId;
  }

  public String[] getPreferredDays() {
    return preferredDays;
  }

  public int[] getPreferredDocs() {
    return preferredDocs;
  }

  public boolean isNew() {
    return isNew;
  }
}

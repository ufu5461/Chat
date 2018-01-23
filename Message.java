private class Message {
  private String messengeSender
  private int hourReceived
  private int minuteReceived
  private int rgbColour
  private String message
  
  private Message (User user,int hour, int minuteReceived, int colour, string parsedMessage){
    this.messegeSender = user.getName();
    this.hourreceived = hour;
    this.rgbColour = colour;
    this.message = parsedMessage;
  }  
  
  public String getSender(){
  return messageSender;}
  
  public String getTime(){
  return (hourReceived.toString() + "." + minutereceived.toString();
          }
  
  public String getText(){
  return message;}
  
  public int getColour(){
  return rgeColour;}

}

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
  
  getSender(){
  return messageSender;}
  
  getTime(){
  return (hourReceived.toString() + "." + minutereceived.toString();
          }
  
  getText(){
  return message;}
  
  getColour(){
  return rgeColour;}

}

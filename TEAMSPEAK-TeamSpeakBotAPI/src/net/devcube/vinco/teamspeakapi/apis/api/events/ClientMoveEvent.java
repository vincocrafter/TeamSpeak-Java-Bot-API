package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ClientMoveEvent extends BaseEvent {
   public ClientMoveEvent(String[] infos) {
      super(infos);
   }

   public String[] getInfos() {
      return super.getInfos();
   }

   public String getClientName() {
      return "";
   }

   public int getClientID() {
      return Integer.parseInt(this.get(3));
   }

   public int getReasonID() {
      return Integer.parseInt(this.get(2));
   }

   public int getTargetChannelID() {
      return Integer.parseInt(this.get(1));
   }

   public String toString() {
      return "ClientJoinEvent[ClientID=" + this.getClientID() + ",ReasonID=" + this.getReasonID() + ",TargetChannelID=" + this.getTargetChannelID() + "]";
   }
   
   public boolean isTargetChannelID(int id) {
	   return getTargetChannelID() == id;
   }
}

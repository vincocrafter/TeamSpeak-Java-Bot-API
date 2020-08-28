package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ClientLeaveEvent extends BaseEvent {
   public ClientLeaveEvent(String[] infos) {
      super(infos);
   }

   public String[] getInfos() {
      return super.getInfos();
   }

   public void printInfos() {
      super.printInfos();
   }

   public int getClientID() {
      return Integer.parseInt(this.get(3));
   }
}

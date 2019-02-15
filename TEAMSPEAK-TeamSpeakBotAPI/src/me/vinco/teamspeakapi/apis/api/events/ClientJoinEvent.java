package me.vinco.teamspeakapi.apis.api.events;

import me.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ClientJoinEvent extends BaseEvent {
   public ClientJoinEvent(String[] infos) {
      super(infos);
   }

   public String[] getInfos() {
      return super.getInfos();
   }

   public void printInfos() {
      super.printInfos();
   }

   public int getClientID() {
      return Integer.parseInt(this.get(4));
   }

   public String getClientName() {
      return this.get(6);
   }

   public String getClientUUID() {
      return this.get(5);
   }

   public String toString() {
      return "ClientJoinEvent[ClientID=" + this.getClientID() + ",ClientName=" + this.getClientName() + ",ClientUUID=" + this.getClientUUID() + "]";
   }
}

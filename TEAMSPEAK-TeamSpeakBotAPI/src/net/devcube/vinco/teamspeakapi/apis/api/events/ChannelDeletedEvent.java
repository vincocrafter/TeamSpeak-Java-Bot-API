package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelDeletedEvent extends BaseEvent {
   public ChannelDeletedEvent(String[] infos) {
      super(infos);
   }

   public String getClientName() {
      return this.get(2);
   }

   public int getClientID() {
      return Integer.parseInt(this.get(1));
   }

   public String getClientUUID() {
      return this.get(3);
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(4));
   }

   public String toString() {
      return "ChannelDeleteEvent[ClientID=" + this.getClientID() + ",ClientName=" + this.getClientName() + ",ClientUUID=" + this.getClientUUID() + ",ChannelID=" + this.getChannelID() + "]";
   }
}

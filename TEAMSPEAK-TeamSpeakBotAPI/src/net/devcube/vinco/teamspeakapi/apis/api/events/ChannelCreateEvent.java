package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelCreateEvent extends BaseEvent {
   public ChannelCreateEvent(String[] infos) {
      super(infos);
   }

   public int getClientID() {
      return Integer.parseInt(this.get(9));
   }

   public String getClientName() {
      return this.get(9).replace("\\s", " ");
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(1));
   }

   public String getChannelName() {
      return this.get(3);
   }

   public String getClientUUID() {
      return this.get(10);
   }

   public String toString() {
      return "ChannelCreateEvent[ClientID=" + this.getClientID() + ",ClientName=" + this.getClientName() + ",ClientUUID=" + this.getClientUUID() + ",ChannelID=" + this.getChannelID() + ",ChannelName=" + this.getChannelName() + "]";
   }
}

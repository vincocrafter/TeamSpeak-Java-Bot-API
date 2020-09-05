package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelEditedEvent extends BaseEvent {
	
   public ChannelEditedEvent(String[] infos) {
      super(infos);
   }

   public int getClientID() {
      return Integer.parseInt(this.get(5));
   }

   public String getClientName() {
      return this.get(4);
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(1));
   }

   public String getClientUUID() {
      return this.get(5);
   }
}

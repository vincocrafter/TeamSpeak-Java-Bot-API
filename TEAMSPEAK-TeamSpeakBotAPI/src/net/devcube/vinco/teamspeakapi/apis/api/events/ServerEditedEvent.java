package me.vinco.teamspeakapi.apis.api.events;

import me.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ServerEditedEvent extends BaseEvent {
	
   public ServerEditedEvent(String[] infos) {
      super(infos);
   }

   public int getClientID() {
      return this.toInt(this.get(2));
   }

   public String getClientName() {
      return this.get(3);
   }

   public String getClientUUID() {
      return this.get(4);
   }

   public int getReasonID() {
      return this.toInt(this.get(1));
   }
}

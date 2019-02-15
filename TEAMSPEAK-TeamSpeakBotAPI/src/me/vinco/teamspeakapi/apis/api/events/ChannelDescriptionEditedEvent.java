package me.vinco.teamspeakapi.apis.api.events;

import me.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelDescriptionEditedEvent extends BaseEvent {
   public ChannelDescriptionEditedEvent(String[] infos) {
      super(infos);
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(1));
   }

   public String getClientName() {
      return "";
   }

   public int getClientID() {
      return 0;
   }
}

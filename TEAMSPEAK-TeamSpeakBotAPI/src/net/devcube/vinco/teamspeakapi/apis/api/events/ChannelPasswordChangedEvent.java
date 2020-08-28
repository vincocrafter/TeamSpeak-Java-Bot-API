package me.vinco.teamspeakapi.apis.api.events;

import me.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelPasswordChangedEvent extends BaseEvent {
   public ChannelPasswordChangedEvent(String[] infos) {
      super(infos);
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(1));
   }
}

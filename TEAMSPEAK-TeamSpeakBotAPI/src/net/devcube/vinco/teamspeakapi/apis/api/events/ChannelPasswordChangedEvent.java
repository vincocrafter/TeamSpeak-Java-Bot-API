package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class ChannelPasswordChangedEvent extends BaseEvent {
   public ChannelPasswordChangedEvent(String[] infos) {
      super(infos);
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(1));
   }
}

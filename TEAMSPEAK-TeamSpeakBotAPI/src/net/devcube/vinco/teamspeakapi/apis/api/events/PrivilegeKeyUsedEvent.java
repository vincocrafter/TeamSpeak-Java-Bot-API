package net.devcube.vinco.teamspeakapi.apis.api.events;

import net.devcube.vinco.teamspeakapi.apis.api.event.BaseEvent;

public class PrivilegeKeyUsedEvent extends BaseEvent {
   public PrivilegeKeyUsedEvent(String[] infos) {
      super(infos);
   }

   public int getClientID() {
      return this.toInt(this.get(1));
   }

   public int getClientDataBaseID() {
      return this.toInt(this.get(2));
   }

   public String getClientUUID() {
      return this.get(3);
   }

   public String getToken() {
      return this.get(4);
   }

   public int getGroupID() {
      return this.toInt(this.get(6));
   }

   public int getChannelID() {
      return this.toInt(this.get(7));
   }
}

package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class OfflineMessage extends _DefaultInfo {
	
   public OfflineMessage(String[] infos) {
      super(infos);
   }

   public String[] getInfos() {
      return this.infos;
   }

   public int getMessageID() {
      return Integer.parseInt(this.get(0));
   }

   public String getClientUUID() {
      return this.get(1);
   }

   public String getSubject() {
      return this.get(2);
   }

   public String getMessage() {
      return this.get(3);
   }

   public long getTime() {
      return Long.parseLong(this.get(4));
   }
}

package me.vinco.teamspeakapi.apis.api.wrapper;

public class QueryClientInfo extends _DefaultInfo {
	
   public QueryClientInfo(String[] infos) {
      super(infos);
   }

   public String getNickName() {
      return this.get(6);
   }

   public String getClientName() {
      return this.getNickName();
   }

   public String getClientUUID() {
      return this.get(2);
   }

   public int getClientID() {
      return Integer.parseInt(this.get(4));
   }

   public int getVirtualServerID() {
      return Integer.parseInt(this.get(1));
   }

   public int getChannelID() {
      return Integer.parseInt(this.get(5));
   }
}

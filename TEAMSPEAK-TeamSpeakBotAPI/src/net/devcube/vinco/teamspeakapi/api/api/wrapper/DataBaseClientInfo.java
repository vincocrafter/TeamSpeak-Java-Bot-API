package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class DataBaseClientInfo {
	
	
   String[] infos;

   public DataBaseClientInfo(String[] infos) {
      this.infos = infos;
   }

   public void printInfos() {
      int i = 0;

      for(String s : this.infos) {
         System.out.println(i + " : " + s);
      }

   }

   public String get(int i) {
      String s = this.infos[i];
      return s.substring(s.indexOf("=") + 1, s.length());
   }

   public String getClientUUID() {
      return this.get(0);
   }

   public int getClientDataBaseID() {
      return Integer.parseInt(this.get(2));
   }

   public String getClientName() {
      return this.get(1);
   }

   public long getCreatingTime() {
      return Long.parseLong(this.get(3));
   }

   public long getLastConnection() {
      return Long.parseLong(this.get(4));
   }

   public int getConnections() {
      return Integer.parseInt(this.get(5));
   }

   public String getLastIP() {
      return this.get(13);
   }

   public String getDescription() {
      return this.get(7);
   }
}

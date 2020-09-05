package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class HostInfo extends _DefaultInfo {
	
   public HostInfo(String[] infos) {
      super(infos);
   }

   public String get(int i) {
      String s = this.infos[i];
      return s.substring(s.indexOf("=") + 1, s.length());
   }

   public int getInstanceUptime() {
      return Integer.parseInt(this.get(0));
   }

   public int getVirtualServersRunning() {
      return Integer.parseInt(this.get(2));
   }

   public int getMaxClients() {
      return Integer.parseInt(this.get(3));
   }

   public int getClientsOnline() {
      return Integer.parseInt(this.get(4));
   }

   public int getChannes() {
      return Integer.parseInt(this.get(5));
   }
}

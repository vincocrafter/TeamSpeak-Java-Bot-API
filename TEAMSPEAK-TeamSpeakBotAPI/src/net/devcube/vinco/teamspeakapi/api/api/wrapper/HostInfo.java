package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class HostInfo extends _DefaultInfo {
	
   public HostInfo(String[] infos) {
      super(infos);
   }

   public String get(int i) {
      String s = infos[i];
      return s.substring(s.indexOf("=") + 1, s.length());
   }

   public int getInstanceUptime() {
      return Integer.parseInt(get(0));
   }

   public int getVirtualServersRunning() {
      return Integer.parseInt(get(2));
   }

   public int getMaxClients() {
      return Integer.parseInt(get(3));
   }

   public int getClientsOnline() {
      return Integer.parseInt(get(4));
   }

   public int getChannes() {
      return Integer.parseInt(get(5));
   }
}

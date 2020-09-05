package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class Ban extends _DefaultInfo {
	
   public Ban(String[] infos) {
      super(infos);
   }

   public int getBanID() {
      return toInt(get(0));
   }

   public String getIP() {
      return get(2);
   }

   public String getClientUUID() {
      return get(3);
   }

   public String getLastNickName() {
      return get(4);
   }

   public long getCreatingTime() {
      return toLong(get(5));
   }

   public int getBanDuration() {
      return toInt(get(6));
   }

   public String getBannerName() {
      return get(7);
   }

   public int getBannerClientDataBaseID() {
      return toInt(get(8));
   }

   public String getBannerClientUUID() {
      return get(9);
   }

   public String getBanReason() {
      return get(10) != null?get(10):"";
   }

   public int getBanEnforcements() {
      return toInt(get(11).replace("|", ""));
   }
}

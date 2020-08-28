package me.vinco.teamspeakapi.apis.api.wrapper;

public class Ban extends _DefaultInfo {
	
   public Ban(String[] infos) {
      super(infos);
   }

   public int getBanID() {
      return this.toInt(this.get(0));
   }

   public String getIP() {
      return this.get(2);
   }

   public String getClientUUID() {
      return this.get(3);
   }

   public String getLastNickName() {
      return this.get(4);
   }

   public long getCreatingTime() {
      return this.toLong(this.get(5));
   }

   public int getBanDuration() {
      return this.toInt(this.get(6));
   }

   public String getBannerName() {
      return this.get(7);
   }

   public int getBannerClientDataBaseID() {
      return this.toInt(this.get(8));
   }

   public String getBannerClientUUID() {
      return this.get(9);
   }

   public String getBanReason() {
      return this.get(10) != null?this.get(10):"";
   }

   public int getBanEnforcements() {
      return this.toInt(this.get(11).replace("|", ""));
   }
}

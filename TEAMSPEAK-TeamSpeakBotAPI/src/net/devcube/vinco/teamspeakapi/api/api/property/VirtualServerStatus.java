package net.devcube.vinco.teamspeakapi.api.api.property;

public enum VirtualServerStatus {
	
   ONLINE("online"),
   OFFLINE("offline"),
   DEPLOY_RUNNING("deploy running"),
   BOOTING_UP("booting up"),
   SHUTTING_DOWN("shutting down"),
   VIRTUAL_ONLINE("virtual online"),
   UNKONWN("unkown");

   String value = "";

   private VirtualServerStatus(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}

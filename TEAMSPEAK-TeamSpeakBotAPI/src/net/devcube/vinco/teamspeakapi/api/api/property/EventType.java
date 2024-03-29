package net.devcube.vinco.teamspeakapi.api.api.property;

public enum EventType {
	
   SERVER("server"),
   CHANNEL("channel"),
   TEXT_SERVER("textserver"),
   TEXT_CHANNEL("textchannel"),
   TEXT_PRIVATE("textprivate"),
   PRIVILEGE_KEY_USED("tokenused");

   private String value = "";

   private EventType(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}

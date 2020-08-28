package net.devcube.vinco.teamspeakapi.apis.api.property;

public enum EventType {
   SERVER("server"),
   CHANNEL("channel"),
   TEXT_SERVER("textserver"),
   TEXT_CHANNEL("textchannel"),
   TEXT_PRIVATE("textprivate"),
   PRIVILEGE_KEY_USED("tokenused");

   String value = "";

   private EventType(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }
}

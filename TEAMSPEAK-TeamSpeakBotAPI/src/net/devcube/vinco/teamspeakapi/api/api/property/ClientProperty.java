package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ClientProperty {
	
   CLIENT_NICKNAME("client_nickname"),
   CLIENT_IS_TALKER("client_is_talker"),
   CLIENT_DESCRIPTION("client_description"),
   CLIENT_IS_CHANNEL_COMMANDER("client_is_channel_commander"),
   CLIENT_ICON_ID("client_icon_id");

   String value = "";

   private ClientProperty(String s) {
      this.value = s;
   }

   public String getValue() {
      return this.value;
   }
}

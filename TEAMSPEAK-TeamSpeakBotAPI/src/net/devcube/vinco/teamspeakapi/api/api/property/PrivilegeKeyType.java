package net.devcube.vinco.teamspeakapi.api.api.property;

public enum PrivilegeKeyType {
	
   SERVER_GROUP(0),
   CHANNEL_GROUP(1);

   private int value = 0;

   private PrivilegeKeyType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}

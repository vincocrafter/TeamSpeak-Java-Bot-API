package net.devcube.vinco.teamspeakapi.apis.api.property;

public enum PrivilegeKeyType {
   SERVER_GROUP(0),
   CHANNEL_GROUP(1);

   private int i = 0;

   private PrivilegeKeyType(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}

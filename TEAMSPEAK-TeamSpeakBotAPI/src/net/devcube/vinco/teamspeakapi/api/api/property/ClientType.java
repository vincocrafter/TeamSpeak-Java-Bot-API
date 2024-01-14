package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ClientType {
	
   CLIENT(0),
   QUERY(1);

   private int value;

   private ClientType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}

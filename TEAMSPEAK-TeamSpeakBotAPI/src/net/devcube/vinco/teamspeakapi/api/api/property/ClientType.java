package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ClientType {
	
   CLIENT(0),
   QUERY(1);

   int i;

   private ClientType(int i) {
      this.i = i;
   }

   public int getValue() {
      return this.i;
   }
}

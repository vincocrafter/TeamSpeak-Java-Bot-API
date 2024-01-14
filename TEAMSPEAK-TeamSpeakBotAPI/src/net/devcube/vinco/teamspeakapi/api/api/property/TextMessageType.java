package net.devcube.vinco.teamspeakapi.api.api.property;

public enum TextMessageType {
	
   CLIENT(1),
   CHANNEL(2),
   SERVER(3);

   private int value;

   private TextMessageType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}

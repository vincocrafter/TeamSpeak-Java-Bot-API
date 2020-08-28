package net.devcube.vinco.teamspeakapi.apis.api.property;

public enum TextMessageType {
   CLIENT(1),
   CHANNEL(2),
   SERVER(3);

   int i;

   private TextMessageType(int i) {
      this.i = i;
   }

   public int getI() {
      return this.i;
   }
}

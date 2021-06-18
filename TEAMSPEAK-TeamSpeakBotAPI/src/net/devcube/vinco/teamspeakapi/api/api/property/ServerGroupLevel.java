package net.devcube.vinco.teamspeakapi.api.api.property;

public enum ServerGroupLevel {
	
   CHANNEL_GUEST(10),
   SERVER_GUEST(15),
   QUERY_GUEST(20),
   CHANNEL_VOICE(25),
   SERVER_NORMAL(30),
   CHANNEL_OPERATOR(35),
   CHANNEL_ADMIN(40),
   SERVER_ADMIN(45),
   ADMIN_SERVER_QUERY(50);

   private int i = 0;

   private ServerGroupLevel(int i) {
      this.i = i;
   }

   public int getValue() {
      return this.i;
   }
}

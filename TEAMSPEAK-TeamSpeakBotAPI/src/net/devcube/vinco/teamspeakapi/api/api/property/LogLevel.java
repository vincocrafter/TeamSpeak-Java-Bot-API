package net.devcube.vinco.teamspeakapi.api.api.property;

public enum LogLevel {
	
   ERROR(1),
   WARNING(2),
   DEBUG(3),
   INFO(4);

   private int value = 0;

   private LogLevel(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }
}

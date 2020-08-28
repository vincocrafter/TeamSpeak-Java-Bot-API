package net.devcube.vinco.teamspeakapi.apis.api.property;

public enum LogLevel {
   ERROR(1),
   WARNING(2),
   DEBUG(3),
   INFO(4);

   private int i = 0;

   private LogLevel(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}

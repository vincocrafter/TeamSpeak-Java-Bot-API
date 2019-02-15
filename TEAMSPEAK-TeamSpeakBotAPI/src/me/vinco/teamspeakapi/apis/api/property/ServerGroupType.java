package me.vinco.teamspeakapi.apis.api.property;

public enum ServerGroupType {
   DEFAULT(0),
   QUERY(2),
   NORMAL(1);

   private int i = 0;

   private ServerGroupType(int i) {
      this.i = i;
   }

   public int getIndex() {
      return this.i;
   }
}

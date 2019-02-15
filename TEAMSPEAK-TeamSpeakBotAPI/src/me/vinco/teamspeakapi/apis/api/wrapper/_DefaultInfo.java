package me.vinco.teamspeakapi.apis.api.wrapper;

public class _DefaultInfo {
   String[] infos;

   public _DefaultInfo(String[] infos) {
      this.infos = infos;
   }

   public void printInfos() {
      int i = 0;

      for(String s : this.infos) {
         System.out.println(i + " : " + s);
         ++i;
      }

   }

   public String get(int i) {
      try {
         String s = this.infos[i];
         return s.substring(s.indexOf("=") + 1, s.length());
      } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
         arrayindexoutofboundsexception.printStackTrace();
         return "";
      }
   }

   public String[] getInfos() {
      return this.infos;
   }

   public int toInt(String s) {
      return Integer.parseInt(s);
   }

   public double toDouble(String s) {
      return Double.parseDouble(s);
   }

   public float toFloat(String s) {
      return Float.parseFloat(s);
   }

   public long toLong(String s) {
      return Long.parseLong(s);
   }

   public boolean toBol(int i) {
      return i == 1;
   }

   public boolean toBol(String s) {
      return Boolean.parseBoolean(s);
   }
}

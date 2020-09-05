package net.devcube.vinco.teamspeakapi.api.api.wrapper;

import java.io.File;

import net.devcube.vinco.teamspeakapi.api.api.property.ChannelCodec;

public class ChannelInfo extends _DefaultInfo {
	
   private int cid;

   public ChannelInfo(String[] infos, int chid) {
      super(infos);
      this.cid = chid;
   }

   public String[] getInfos() {
      return this.infos;
   }

   public void printInfos() {
      int i = 0;

      for(String s : this.infos) {
         System.out.println(i + " : " + s);
         ++i;
      }

   }

   public int getChannelID() {
      return this.cid;
   }

   public String getChannelOrginalName() {
      return this.get(1);
   }

   public String getChannelName() {
      String s = this.get(1);
      s = s.replace("\\s", " ");
      s = s.replace("\\p", "|");
      return s;
   }

   public String getChannelTopic() {
      return this.get(2);
   }

   public String getChannelDescription() {
      return this.get(3);
   }

   public String getChannelPassword() {
      return this.get(4);
   }

   private int getChannelQuality() {
      return Integer.parseInt(this.get(6));
   }

   public ChannelCodec getChannelCodec() {
      for(ChannelCodec channelcodec : ChannelCodec.values()) {
         if(channelcodec.getIndex() == this.getChannelQuality()) {
            return channelcodec;
         }
      }

      return null;
   }

   public int getChannelMaxClients() {
      return Integer.parseInt(this.get(7));
   }

   public int getChannelMaxFamilyClients() {
      return Integer.parseInt(this.get(8));
   }

   public int getNeededTalkPower() {
      return Integer.parseInt(this.get(22));
   }

   public boolean isPermanent() {
      return this.toBol(this.toInt(this.get(10)));
   }

   public boolean isSemiPermanent() {
      return this.toBol(this.toInt(this.get(11)));
   }

   public boolean isDefault() {
      return this.toBol(this.toInt(this.get(12)));
   }

   public boolean hasPassword() {
      return this.toBol(this.toInt(this.get(13)));
   }

   public File getFilePath() {
      return new File(this.get(21).replace("\\/", "/"));
   }

   public int getDeleteDelay() {
      return this.toInt(this.get(17));
   }

   public int getSecondsEmpty() {
      return this.toInt(this.get(27));
   }

   public int getIconID() {
      return this.toInt(this.get(26));
   }
}

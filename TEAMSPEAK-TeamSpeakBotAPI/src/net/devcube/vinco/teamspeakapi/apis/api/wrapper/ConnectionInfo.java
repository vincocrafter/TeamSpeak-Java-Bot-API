package net.devcube.vinco.teamspeakapi.apis.api.wrapper;

public class ConnectionInfo extends _DefaultInfo {
	
   public ConnectionInfo(String[] infos) {
      super(infos);
   }

   public int getFileTransfersBandwidthSend() {
      return this.toInt(this.get(0));
   }

   public int getFileTransfersBandwidthRecived() {
      return this.toInt(this.get(1));
   }

   public int getFileTransfersBytesSend() {
      return this.toInt(this.get(2));
   }

   public int getFileTransfersBytesRecived() {
      return this.toInt(this.get(3));
   }
}

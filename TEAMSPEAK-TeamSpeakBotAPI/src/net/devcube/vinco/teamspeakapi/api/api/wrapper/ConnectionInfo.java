package net.devcube.vinco.teamspeakapi.api.api.wrapper;

public class ConnectionInfo extends _DefaultInfo {
	
   public ConnectionInfo(String[] infos) {
      super(infos);
   }

   public int getFileTransfersBandwidthSend() {
      return toInt(get(0));
   }

   public int getFileTransfersBandwidthRecived() {
      return toInt(get(1));
   }

   public int getFileTransfersBytesSend() {
      return toInt(get(2));
   }

   public int getFileTransfersBytesRecived() {
      return toInt(get(3));
   }
}

package me.vinco.teamspeakapi.apis.api.exception.wrapper;

public class UnknownChannelException extends Throwable {
   private static final long serialVersionUID = 1L;
   String message;

   public UnknownChannelException() {
   }

   public UnknownChannelException(String msg) {
      this.message = null;
   }

   public void printStackTrace() {
      if(this.message != null) {
         System.err.println(this.message);
      } else {
         System.err.println("Der Channel ist ung\u00fcltig!");
      }

      super.printStackTrace();
   }
}

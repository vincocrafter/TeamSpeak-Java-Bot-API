package net.devcube.vinco.teamspeakapi.api.api.exception.wrapper;

public class UnknownQueryException extends Throwable {
   private static final long serialVersionUID = 1L;
   String message;

   public UnknownQueryException() {
   }

   public UnknownQueryException(String msg) {
      this.message = null;
   }

   public void printStackTrace() {
      if(this.message != null) {
         System.err.println(this.message);
      } else {
         System.err.println("Die Query ist ung\u00fcltig!");
      }

      super.printStackTrace();
   }
}

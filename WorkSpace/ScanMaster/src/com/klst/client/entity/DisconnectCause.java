package com.klst.client.entity;

/**
 * Created on 2015/7/1.
 */
public enum DisconnectCause {
   AdminDisconnected("OPERATOR_DISCONNECT"),
   RemoteDisconnected("ENDPOINT_HANGUP"),
   ResourceInsufficience("RESOURCE_DEFICIENCY"),
   NetworkError("NETWORK_ERROR"),
   NoAnswer("CALL_REJECT_NO_ANSWER"),
   RemoteBusy("CALL_REJECT_BUSY"),
   RemoteReject("CALL_REJECT_IMMEDIATELY"),
   CallReject("CAUSE_CALL_REJECT"),
   GKReject("CALL_REJECT_GK"),
   NoEncryption("NON_ENCRYPT_TERM_JOIN_ENCRYPT_MEETING"),
   SubLinkExisted("SLAVE_LINK_EXISTED"),
   None("None"),
   ;

   private String value;

   private DisconnectCause(String value)
   {
       this.value = value;
   }

   public String getValue()
   {
       return this.value;
   }

   public static DisconnectCause getDisconnectCause(String value)
   {
       for(DisconnectCause cause : DisconnectCause.values())
       {
           if(cause.getValue().equals(value) || cause.name().equals(value))
               return cause;
       }

       return null;
   }

}

package twilio.resources;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.TwiML;
import com.twilio.twiml.messaging.Body;
import com.twilio.type.PhoneNumber;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/twilio")
@Singleton
public class TwilioResource {
    public static final String ACCOUNT_SID = "ACca61c6cb35e705cfbb606222c869d9bb";
    public static final String AUTH_TOKEN = "0f5b63a9683ad9df936898a9913d49e4";

    public TwilioResource() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @POST
    @Path("sendMessage")
    public Response sendMessage(String message) {
        Message msg = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+919972569076"),
                new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                message)
                .create();
        return Response.accepted(msg.getSid()).build();
    }


    @POST
    @Path("receiveMessage")
    @Produces("text/xml")
    public String receiveMessage(@FormParam(value = "MessageStatus") String messageStatus,
                                 @FormParam(value = "ApiVersion") String apiVersion,
                                 @FormParam(value = "SmsSid") String smsSid,
                                 @FormParam(value = "SmsStatus") String smsStatus,
                                 @FormParam(value = "To") String to,
                                 @FormParam(value = "From") String from,
                                 @FormParam(value = "Body") String body,
                                 @FormParam(value = "MessageSid") String messageSid,
                                 @FormParam(value = "AccountSid") String accountSid) {
        Body.Builder bodyBuilder = new Body.Builder("Following message was received: " + body);
        com.twilio.twiml.messaging.Message.Builder builder = new com.twilio.twiml.messaging.Message.Builder();
        builder.body(bodyBuilder.build());

        com.twilio.twiml.messaging.Message msg = builder.build();

        MessagingResponse response = new MessagingResponse.Builder().message(msg).build();
        return response.toXml();
    }


}

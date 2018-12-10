//import org.zeromq.jeromq;
import org.zeromq.ZMQ;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;

public class Main {

    static int messageType;
    static String messageContent;

    @SuppressWarnings("Since15")
    public static void decodeMessage(byte[] message) throws UnsupportedEncodingException {
        messageType =  new BigInteger(Arrays.copyOfRange(message, 0, 3  )).intValue();
        messageContent=  new String(Arrays.copyOfRange(message, 8, message.length), "UTF-8");
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        ZMQ.Context context = ZMQ.context(1);
        //  Socket to talk to server
        ZMQ.Socket requester = context.socket(ZMQ.PULL);
        requester.connect("tcp://10.0.64.13:5556");
        requester.setReceiveBufferSize(10240);

            while (true) {

                    byte[] reply = requester.recv();
                    decodeMessage(reply);
                    System.out.println("Received " + messageContent);

            }
    }
}

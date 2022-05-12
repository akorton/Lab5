package Lab5.Server;

import java.net.SocketAddress;

public class ReadResult {
    private SocketAddress address;
    private byte[] message;

    public ReadResult(SocketAddress address, byte[] message){
        this.address = address;
        this.message = message;
    }

    public SocketAddress getAddress(){
        return address;
    }

    public byte[] getMessage() {
        return message;
    }
}

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 65002);
        final ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);

        try (Scanner scanner = new Scanner(System.in);
             SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.connect(inetSocketAddress);
            String msg;
            while (true) {
                System.out.println("Введите сообщение для сервера");
                msg = scanner.nextLine();
                if (msg.equals("end")) break;

                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(byteBuffer);
                System.out.println(new String(byteBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
                byteBuffer.clear();
            }
        }
    }
}

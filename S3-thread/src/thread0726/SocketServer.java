package thread0726;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Receiver;

public class SocketServer implements Runnable {

	@Override
	public void run() {
		
	}
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		System.out.println("服务器启动成功,监听8888端口");
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
					while(true) {
						Socket socket;
						try {
							socket = server.accept();
							System.out.println("连接成功");
							receiveFile(socket);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
			}
		});
		thread.run();
	}
	
	public static void receiveFile(Socket socket) throws IOException {
		int length=0;;
		DataInputStream din=new DataInputStream(socket.getInputStream());
		FileOutputStream fout=new FileOutputStream(new File("F:\\"+din.readUTF()));
		byte[] inputByte=new byte[1024];
		System.out.println("开始接收文件");
		while(true) {
			if(din !=null) {
				length=din.read(inputByte,0,inputByte.length);
				
			}
			if(length==-1) {
				
				break;
			}
			fout.write(inputByte, 0, inputByte.length);
	
			fout.flush();
		
		}
		System.out.println("完成接收");
		if(fout!=null) fout.close();
		if(din!=null) din.close();
		if(socket!=null) socket.close();
	}
}

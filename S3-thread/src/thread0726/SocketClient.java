package thread0726;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient {
	public static void main(String[]args) throws UnknownHostException, IOException {
		int length=0;
		
		Socket socket = new Socket("127.0.0.1",8888);
		DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
		File file= new File("F:\\新建文件夹\\20180219153103_1.jpg");
		System.out.println(file);
		FileInputStream fin = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		dout.writeUTF(file.getName());
		while( (length=fin.read(buffer, 0, buffer.length))>0) {
			dout.write(buffer,0,length);
			dout.flush();
		}
		if(dout!=null) dout.close();
		if(fin!=null) fin.close();
		if(socket!=null) socket.close();

	}
}

package th0726;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//多线程多块下载
public class Download2 {
	private int downNums = 0;

	public static void main(String[] args) throws IOException, InterruptedException {
		new Download2().download();
	}

	public void download() throws IOException, InterruptedException{
		URL url = new URL(
				"http://antiserver.kuwo.cn/anti.s?rid=MUSIC_71384675&response=res&format=mp3|aac&type=convert_url&br=128kmp3&agent=iPhone&callback=getlink&jpcallback=getlink.mp3");
		String filename="E:/LLQT.mp3";
		long time = System.currentTimeMillis();
		URLConnection conn = url.openConnection();
		// 获取文件总大小
		int filesize = conn.getContentLength();
		// 每块的大小(自定义1m)
		int blocksize = 1 * 1024 * 1024;
		// 计算块数
		int blocknums = filesize / blocksize;
		if (filesize % blocksize != 0) {
			blocknums++;
		}
		System.out.println("开始下载");
		// 分块下载
		for (int i = 0; i < blocknums; i++) {
			downNums++;
			int index=i;
			new Thread() {
				public void run() {
					try {
					System.out.println("第" + (index + 1) + "块开始下载");
					URLConnection conn = url.openConnection();
					InputStream in = conn.getInputStream();
					FileOutputStream out = new FileOutputStream(filename + index);
					// 开始的字节数
					int beginBytes = index * blocksize;
					// 结束的字节数
					int endBytes = beginBytes + blocksize;
					// 借书的字节数不能超过文件大小
					if (endBytes > filesize) {
						endBytes = filesize;
					}
					// 跳过开始的字节数
					in.skip(beginBytes);

					// 当前下载到的位置
					int position = beginBytes;
					byte[] buffer = new byte[1024];
					int count;
					while ((count = in.read(buffer)) > 0) {
						if (position + count > endBytes) {
							// 计算超出的部分
							int a = position + count - endBytes;
							// 减去超出的部分
							count = count - a;
						}
						out.write(buffer, 0, count);
						// 更新下载位置(向前推进
						position += count;
						// 如果下载位置达到该块位置 则结束
						if (position >= endBytes) {
							break;
						}
					}
					in.close();
					out.close();
					System.out.println("第" + (index + 1) + "块结束下载");
				
					synchronized (Download2.this) {
						Download2.this.downNums --;
						Download2.this.notify();
					}
				
				}catch (IOException e) {
					e.printStackTrace();
				}
				}
			
				
		
			}.start();
		}
		synchronized(this) {
			while(downNums>0) {
				wait();
			}
		}
		
		//合并文件
		marge(filename,blocknums);
		System.out.println("共花了:"+(System.currentTimeMillis() -time)/1000+"秒");
		System.out.println("下载完成");
		
	}

	// 合并文件的方法
	public static void marge(String path, int filenums) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		for (int i = 0; i < filenums; i++) {
			FileInputStream fis = new FileInputStream(path + i);
			byte[] buffer = new byte[1024];
			int count;
			while ((count = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fis.close();
		}
		fos.close();
	}
}

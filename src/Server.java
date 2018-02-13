
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Server {
	ArrayList<BufferedWriter> x=new ArrayList<BufferedWriter>();//这里之所以用到集合的概念是为了多用户的接入，以及数据的广播
	public void run() throws Exception {
			ServerSocket a=new ServerSocket(3560);
		while(true){
			final Socket b=a.accept();//接收客户端的数据流 ，这个方法可以查看API;
			new Thread(new Runnable(){
				public void run(){
			try {
				BufferedReader c= new BufferedReader(new InputStreamReader(b.getInputStream()));
				x.add(new BufferedWriter(new OutputStreamWriter(b.getOutputStream())));//这里每接收一个客户端的数据就建立一个BufferedWriter为了后面的
				//广播，这样就可以实现多用户的群发
				String msg=null;
				while(null!=(msg=c.readLine())){
					System.out.println(msg);
					broadcast(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}).start();
		}
}
	public synchronized void broadcast(String msg){//这里使用同步锁是为了防止多用户同时发送时，服务端是否能够全部接收转发
		Iterator<BufferedWriter> a=x.iterator();//获取之前的客户端的接入流，这里为了实现广播的功能，所以在这里用迭代器分别获取遍历发送数据
		while(a.hasNext()){
			BufferedWriter b=a.next();
			try {
				b.write(msg);
				b.newLine();
				b.flush();//这里同样的每次发送完要进行刷新操作，清除缓存数据
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	}
	public static void main(String[] args) throws Exception {
		new Server().run();
	}
}
/*
 * z这里主要考察数据流和进程的知识 ，如何通过数据流完成数据的发送和接收，用进程的知识解决发送和接收的在同一端的问题,还有就是如何实现多用户的聊天功能这里用集合的方法，
 * 通过集合存储客户端的输入流，而为了防止多用户同时接入的冲突，这一用了同步锁
 */

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
	ArrayList<BufferedWriter> x=new ArrayList<BufferedWriter>();//����֮�����õ����ϵĸ�����Ϊ�˶��û��Ľ��룬�Լ����ݵĹ㲥
	public void run() throws Exception {
			ServerSocket a=new ServerSocket(3560);
		while(true){
			final Socket b=a.accept();//���տͻ��˵������� ������������Բ鿴API;
			new Thread(new Runnable(){
				public void run(){
			try {
				BufferedReader c= new BufferedReader(new InputStreamReader(b.getInputStream()));
				x.add(new BufferedWriter(new OutputStreamWriter(b.getOutputStream())));//����ÿ����һ���ͻ��˵����ݾͽ���һ��BufferedWriterΪ�˺����
				//�㲥�������Ϳ���ʵ�ֶ��û���Ⱥ��
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
	public synchronized void broadcast(String msg){//����ʹ��ͬ������Ϊ�˷�ֹ���û�ͬʱ����ʱ��������Ƿ��ܹ�ȫ������ת��
		Iterator<BufferedWriter> a=x.iterator();//��ȡ֮ǰ�Ŀͻ��˵Ľ�����������Ϊ��ʵ�ֹ㲥�Ĺ��ܣ������������õ������ֱ��ȡ������������
		while(a.hasNext()){
			BufferedWriter b=a.next();
			try {
				b.write(msg);
				b.newLine();
				b.flush();//����ͬ����ÿ�η�����Ҫ����ˢ�²����������������
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
 * z������Ҫ�����������ͽ��̵�֪ʶ �����ͨ��������������ݵķ��ͺͽ��գ��ý��̵�֪ʶ������ͺͽ��յ���ͬһ�˵�����,���о������ʵ�ֶ��û������칦�������ü��ϵķ�����
 * ͨ�����ϴ洢�ͻ��˵�����������Ϊ�˷�ֹ���û�ͬʱ����ĳ�ͻ����һ����ͬ����
 */
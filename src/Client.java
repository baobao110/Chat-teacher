import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import jdk.internal.util.xml.impl.Input;


public class Client {
	public void run() throws Exception{
		final Socket socket=new Socket("192.168.8.178",3560);
		new Thread(new Runnable(){
			public void run(){
				BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
				try {
					BufferedWriter c= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					String msg=null;//������һ���ַ����������շ������ķ�����Ϣ,��Ȼ�Ľ�����Ϣ��������������Ϊû���ñ������գ�����һ��ʼ���ַ������Ǳ�û�����ݽ�����Ϣ
					while(null!=(msg=b.readLine())){
						c.write(msg);
						c.newLine();//����ķ����ǻ��е����ã���Ȼ�ͻ����޷����������
						c.flush();//�������ݴ�����Ҫˢ�»�������ݣ���д����Ĺ����з���һ��ʼû��д�÷������ֵ����ݷ��ͺ�ͻ���û���յ��������Ļ�Ӧ��
				}
			}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		},"���").start();
		
		new Thread(new Runnable(){
			public void run(){
				try {
					BufferedReader a=new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String msg=null;
					while(null!=(msg=a.readLine())){
						System.out.println(msg);
						}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},"����").start();//�����ö��̵߳ĸ���������߳����ڴ���ͻ��˵��շ����⡣
			
			
	}	
	public static void main(String[] args) throws Exception {
		new Client().run();
	}
}
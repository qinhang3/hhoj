package cn.edu.gdut.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cn.edu.gdut.controller.AdminController;

public class JudgeServerRoot extends Thread {

	private ServerSocket serverSocket ;
	private int port;
	private String Status; 

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public JudgeServerRoot(int port) {
		this.port = port;
	}
	@Override
	public void run() {
		try{
			serverSocket = new ServerSocket(port);
			setStatus("Running");
			while(true){
				Socket socket = serverSocket.accept();
				if(socket == null){
					break;
				}
				JudgeServer judgeServer = new JudgeServer(socket, AdminController.judgeQueue);
				AdminController.servers.put(judgeServer.getUniqueId(),judgeServer);
				judgeServer.start();
			}
		} catch (Exception e){
			setStatus("Exception:"+e.toString());
		} finally {
			setStatus("Stop");
		}
	}
	
	public void tryToStop(){
		try{
			serverSocket.close();
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
}

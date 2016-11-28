package com.katsunet.common;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MySQLConn {

	private String driver;
	private String connection;
	private String user;
	private String pass;
	private Connection c = null;
	
	public Connection getConnection(){
		return this.c;
	}
	
	public MySQLConn(String driver, String connection, String user, String pass){
		this.driver=driver;	
		this.connection=connection;
		this.user=user;
		this.pass=pass;
	}
	
	public boolean connect(boolean debug){
		try {
			Class.forName(this.driver).newInstance();
			this.c = DriverManager.getConnection(this.connection, this.user, this.pass);
		} catch(ClassNotFoundException e){
			System.out.println("Fatal: Could not load "+this.driver+" Class driver.");
			if(debug){
				e.printStackTrace();
			} else {
				System.out.println(e.toString());
			}
		} catch (SQLException|InstantiationException|IllegalAccessException e) {
			System.out.println("Fatal: Driver generated an exception.");
			if(debug){
				e.printStackTrace();
			} else {
				System.out.println(e.toString());
			}
			return false;
		}
		return true;
	}
	
	public boolean disconnect(){
		try {
			this.c.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}

package fileManage;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.String;

import schedule.Schedule;

public class FileManager {
	private static final String MAIN_DIRECTORY = "Data";
	
	public FileManager() {
		makeDirectory(MAIN_DIRECTORY);
	}
	
	private void makeDirectory(String dirName) {
		File dir = new File(System.getProperty("user.dir"), dirName);
		
		if(!dir.exists()){
	         dir.mkdir();
	    }
	}
	
	private void makeYearDir(int year) {
		File dir = new File(System.getProperty("user.dir") + "\\" + MAIN_DIRECTORY + "\\"
				, String.valueOf(year));
		
		if(!dir.exists()){
	         dir.mkdir();
	         
	         for(int i = 1 ; i <= 12 ; i++) {
	     		 dir = new File(System.getProperty("user.dir") + "\\" + MAIN_DIRECTORY + "\\"
	     				+ String.valueOf(year), String.valueOf(i));
	     		 
	     		 if(!dir.exists()) {
	     			 dir.mkdir();
	     		 }
	         }
	    }
	}
	
	private File[] readFileList(int year, int month) {
		
		String path = System.getProperty("user.dir") + "\\" + MAIN_DIRECTORY + "\\" 
				+ String.valueOf(year) + "\\" + String.valueOf(month) + "\\";
		
		File file = new File(path);
		File[] list = file.listFiles();
		
		return list;
	}
	
	private void writeEachFile(Schedule schedule) {
		String path = "";
		makeYearDir(schedule.getDueDate().get(Calendar.YEAR));
		path = System.getProperty("user.dir") + "\\" + MAIN_DIRECTORY + "\\" 
				+schedule.getDueDate().get(Calendar.YEAR) + "\\" 
				+ (schedule.getDueDate().get(Calendar.MONTH) + 1)
				+ "\\" + schedule.getName();
		
	    try{       
	        FileOutputStream fos = new FileOutputStream(path); 
	        ObjectOutput oo = new ObjectOutputStream(fos);  
	        oo.writeObject(schedule); 
	        oo.flush(); 
	       } 
	    catch(IOException e) {
	        	 
	    }     
	} 
	
	//TODO : interface 제공해주기

	public void writeFile(ArrayList<Schedule> scheduleList, int year, int month) {
		File[] fileList = readFileList(year, month);
		Schedule e;
		
		//먼저 해당 directory에 있던 file을 모두 삭제
		if(fileList != null) {
			for(int i = 0 ; i < fileList.length; i++) {
				fileList[i].delete();
			}
		}		
		//그 후 파일들을 새로 쓴다
		while(!scheduleList.isEmpty()) {
			e = scheduleList.get(0);
			writeEachFile(e);
			scheduleList.remove(0);			
		}
	}
	
	public ArrayList<Schedule> readFile(int year, int month) {
		ArrayList<Schedule> resultSet = new ArrayList<Schedule>();
		Schedule s = null;
		
		File[] fileList = readFileList(year, month);
		
		   try {
			   for(int i = 0 ; i < fileList.length; i++) {
		           FileInputStream fin = new FileInputStream(fileList[i].getPath()); 
		           ObjectInput oi = new ObjectInputStream(fin); 
		           s = (Schedule)oi.readObject();
		           resultSet.add(s);
			   }
	         } 
	         catch(IOException e) {
	        	 
	         }     
	         catch(ClassNotFoundException e) {
	        	 
	         } 
		   
		   return resultSet;
	}
}

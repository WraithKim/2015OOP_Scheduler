package fileManage;
import java.util.ArrayList;
import java.util.Calendar;
import java.io.File;
import java.io.FileInputStream;
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
	
	private boolean makeDirectory(String dirName) {
		File dir = new File(System.getProperty("user.dir"), dirName);
		return dir.mkdir();
	}
	
	private boolean makeYearDir(int year) {
		File dir = new File(System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				, String.valueOf(year));

		if(!dir.mkdir()) return false;
		for(int i = 1 ; i <= 12 ; i++) {
		 dir = new File(System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				+ String.valueOf(year), String.valueOf(i));
			if(!dir.mkdir()) return false;
		}
		return true;
	}
	
	private File[] readFileList(int year, int month) {
		
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				+ String.valueOf(year) + File.separator + String.valueOf(month) + File.separator;
		
		File file = new File(path);
		return file.listFiles();
	}
	
	private void writeEachFile(Schedule schedule) throws IOException{
		makeYearDir(schedule.getDueDate().get(Calendar.YEAR));
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				+schedule.getDueDate().get(Calendar.YEAR) + File.separator
				+ (schedule.getDueDate().get(Calendar.MONTH) + 1)
				+ File.separator + schedule.getDueDate().get(Calendar.DATE) 
				+ "_" + schedule.getName();

		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutput oo = new ObjectOutputStream(fos);
		oo.writeObject(schedule);
		oo.flush();
		oo.close();
	} 
	
	private void writeEachFile(String studentNumber) throws IOException {
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				+ "StudentNumber";
		
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutput oo = new ObjectOutputStream(fos);
		oo.writeObject(studentNumber);
		oo.flush();
		oo.close();
	}
	
	//interface 부분, write와 read 제공

	public boolean writeFile(ArrayList<Schedule> scheduleList, int year, int month) {
		File[] fileList = readFileList(year, month);
		Schedule e;
		
		//기존 folder에 있던 내용 삭제
		if(fileList != null) {
			for(File file : fileList){
				if(!file.delete()) return false;
			}
		}		
		//현재 갖고 있는 내용들 쓰기
		try {
			while (!scheduleList.isEmpty()) {
				e = scheduleList.get(0);
				writeEachFile(e);
				scheduleList.remove(0);
			}
		}catch(IOException ioe){
			return false;
		}

		return true;
	}
	
	public ArrayList<Schedule> readFile(int year, int month) {
		ArrayList<Schedule> resultSet = new ArrayList<>();
		
		File[] fileList = readFileList(year, month);
		
		   try {
			   for(File file : fileList) {
		           FileInputStream fin = new FileInputStream(file.getPath());
		           ObjectInput oi = new ObjectInputStream(fin); 
		           Schedule s = (Schedule)oi.readObject();
		           resultSet.add(s);
				   oi.close();
			   }
		   }
		   catch(IOException | ClassNotFoundException e) {
			   e.printStackTrace();
		   }
		   
		   return resultSet;
	}
	
	public boolean writeStudentNumber(String studentNumber) {
		try {
			writeEachFile(studentNumber);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String readStudentNumber() {
		String studentNumber = null;
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY + File.separator
				+ "StudentNumber";
		
		   try {
	           FileInputStream fin = new FileInputStream(path);
	           ObjectInput oi = new ObjectInputStream(fin); 
	           studentNumber = (String)oi.readObject();
			   oi.close();			   
		   }
		   catch(IOException | ClassNotFoundException e) {
			   e.printStackTrace();
		   }
		   
		   return studentNumber;
	}
	
}

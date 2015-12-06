package util;
import java.nio.file.Files;
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
import java.util.List;

import schedule.Schedule;

public class FileManager {
	private static final String MAIN_DIRECTORY = "Data";
	private static final String HOMEWORK_DIRECTORY = "Homework";
	private static final String STUDENT_NUMBER = "StudentNumber";

	private static FileManager ourInstance = new FileManager();

	public static FileManager getInstance(){
		return ourInstance;
	}

	private FileManager() {
		String path = makePath();
		makeDirectory(path);
	}
	
	private boolean makeDirectory(String path) {
		File dir = new File(path);
		return dir.mkdir();
	}
	
	private boolean makeYearDir(int year) {
		String path = makePath(String.valueOf(year));
		
		File dir = new File(path);

		if(!dir.mkdir()) return false;
		for(int i = 1 ; i <= 12 ; i++) {
			path = makePath(String.valueOf(year));
		 dir = new File(path, String.valueOf(i));
			if(!dir.mkdir()) return false;
		}
		return true;
	}
	
	private File[] readFileList(int year, int month, int day) {
		String path = makePath(String.valueOf(year), String.valueOf(month), String.valueOf(day));
		
		File file = new File(path);
		return file.listFiles();
	}
	
	private File[] readHomeworkFileList() {
		String path = makePath(HOMEWORK_DIRECTORY);
		
		File file = new File(path);
		return file.listFiles();
	}
	
	private void writeEachFile(Schedule schedule) throws IOException{
		makeYearDir(schedule.getDueDate().get(Calendar.YEAR));
		String path = makePath(String.valueOf(schedule.getDueDate().get(Calendar.YEAR)), String.valueOf(schedule.getDueDate().get(Calendar.MONTH) + 1),
				String.valueOf(schedule.getDueDate().get(Calendar.DATE)));
		
		makeDirectory(path);

		path = makePath(String.valueOf(schedule.getDueDate().get(Calendar.YEAR)), (String.valueOf(schedule.getDueDate().get(Calendar.MONTH) + 1)),
				String.valueOf(schedule.getDueDate().get(Calendar.DATE)), schedule.getName());
		
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutput oo = new ObjectOutputStream(fos);
		oo.writeObject(schedule);
		oo.flush();
		oo.close();
	} 
	
	private void writeEachFile(Homework homework) throws IOException{
		String path = makePath(HOMEWORK_DIRECTORY);
		
		makeDirectory(path);

		path = makePath(HOMEWORK_DIRECTORY, homework.getName());
		
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutput oo = new ObjectOutputStream(fos);
		oo.writeObject(homework);
		oo.flush();
		oo.close();
	} 
	
	private void writeEachFile(String studentNumber) throws IOException {	
		String path = makePath(STUDENT_NUMBER);
		
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutput oo = new ObjectOutputStream(fos);
		oo.writeObject(studentNumber);
		oo.flush();
		oo.close();
	}
	
	private String makePath(String... content) {
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY;
	
		if(content != null) {
			for(String fileName : content) {
				path += File.separator + fileName;
			}
		}
		return path;
	}
	
	//interface 부분, write와 read 제공

	public boolean writeScheduleFile(List<Schedule> scheduleList) throws IOException {
		if(scheduleList.isEmpty())
			return false;
		
		Schedule e = scheduleList.get(0);

		File[] fileList = readFileList(e.getDueDate().get(Calendar.YEAR), e.getDueDate().get(Calendar.MONTH)+1, e.getDueDate().get(Calendar.DATE));
		
		//기존 folder에 있던 내용 삭제
		if(fileList != null) {
			for(File file : fileList){
				Files.delete(file.toPath());
			}
		}		
		//현재 갖고 있는 내용들 쓰기
		while (!scheduleList.isEmpty()) {
			e = scheduleList.get(0);
			writeEachFile(e);
			scheduleList.remove(0);
		}
		
		return true;
	}
	
	public boolean writeHomeworkFile(List<Homework> homeworkList) throws IOException {
		if(homeworkList.isEmpty())
			return false;
		
		Homework e = homeworkList.get(0);
		
		File[] fileList = readHomeworkFileList();
		
		//기존 folder에 있던 내용 삭제
		if(fileList != null) {
			for(File file : fileList){
				Files.delete(file.toPath());
			}
		}		
		//현재 갖고 있는 내용들 쓰기
		while (!homeworkList.isEmpty()) {
			e = homeworkList.get(0);
			writeEachFile(e);
			homeworkList.remove(0);
		}
		
		return true;
	}
	
	public ArrayList<Schedule> readScheduleFile(int year, int month, int day) throws IOException, ClassNotFoundException {
		ArrayList<Schedule> resultSet = new ArrayList<>();
		
		File[] fileList = readFileList(year, month, day);
		if(fileList != null) {
			for (File file : fileList) {
				if (file == null) continue;
				FileInputStream fin = new FileInputStream(file.getPath());
				ObjectInput oi = new ObjectInputStream(fin);
				Schedule s = (Schedule) oi.readObject();
				resultSet.add(s);
				oi.close();
			}
		}
	   return resultSet;
	}
	
	public ArrayList<Homework> readHomeworkFile() throws IOException, ClassNotFoundException {
		ArrayList<Homework> resultSet = new ArrayList<>();
		
		File[] fileList = readHomeworkFileList();
		
		if(fileList != null) {
		   for(File file : fileList) {
	           FileInputStream fin = new FileInputStream(file.getPath());
	           ObjectInput oi = new ObjectInputStream(fin); 
	           Homework h = (Homework)oi.readObject();
	           resultSet.add(h);
			   oi.close();
		   }
		}
		return resultSet;
	}
	
	public boolean writeStudentNumber(String studentNumber) throws IOException {
		writeEachFile(studentNumber);
		
		return true;
	}
	
	public String readStudentNumber() throws IOException, ClassNotFoundException {
		String studentNumber = null;
		
		String path = makePath(STUDENT_NUMBER);
		
           FileInputStream fin = new FileInputStream(path);
           ObjectInput oi = new ObjectInputStream(fin); 
           studentNumber = (String)oi.readObject();
		   oi.close();			   
		   
		   return studentNumber;
	}
	
}

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

import schedule.Homework;
import schedule.Schedule;

public class FileManager {
	private static final String MAIN_DIRECTORY = "Data";
	private static final String HOMEWORK_DIRECTORY = "Homework";
	private static final String STUDENT_NUMBER = "StudentNumber";

	private static final FileManager ourInstance = new FileManager();

	public static FileManager getInstance(){
		return ourInstance;
	}

	private FileManager() {
		String path = makePath();
		makeDirectory(path);
	}

	//내부 메소드

	//path 생성 관련

	private String makePath(String... content) {
		String path = System.getProperty("user.dir") + File.separator + MAIN_DIRECTORY;

		if(content != null) {
			for(String fileName : content) {
				path += File.separator + fileName;
			}
		}
		return path;
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

	//파일 읽기 관련

	private File[] readFileList(Calendar day) {
		String path = makePath(String.valueOf(day.get(Calendar.YEAR)), String.valueOf(day.get(Calendar.MONTH)+1), String.valueOf(day.get(Calendar.DATE)));
		
		File file = new File(path);
		return file.listFiles();
	}
	
	private File[] readHomeworkFileList() {
		String path = makePath(HOMEWORK_DIRECTORY);
		
		File file = new File(path);
		return file.listFiles();
	}

	//파일 쓰기 관련

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
	
	//interface 부분

	//파일 읽기 관련

	public ArrayList<Schedule> readScheduleFile(Calendar day) throws IOException, ClassNotFoundException {

		ArrayList<Schedule> resultSet = new ArrayList<>();

		File[] fileList = readFileList(day);
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

	public String readStudentNumber() throws IOException, ClassNotFoundException {

		String path = makePath(STUDENT_NUMBER);

		FileInputStream fin = new FileInputStream(path);
		ObjectInput oi = new ObjectInputStream(fin);
		String studentNumber = (String)oi.readObject();
		oi.close();

		return studentNumber;
	}

	//파일 쓰기 관련

	public boolean writeScheduleFile(List<Schedule> scheduleList) throws IOException {
		if(scheduleList.isEmpty())
			return false;
		
		Schedule toGetDate = scheduleList.get(0);

		File[] fileList = readFileList(toGetDate.getDueDate());
		
		//기존 folder에 있던 내용 삭제
		if(fileList != null) {
			for(File file : fileList){
				Files.delete(file.toPath());
			}
		}		
		//현재 갖고 있는 내용들 쓰기
		for(Schedule elem : scheduleList) {
			writeEachFile(elem);
		}
		
		return true;
	}
	
	public boolean writeHomeworkFile(List<Homework> homeworkList) throws IOException {
		if(homeworkList.isEmpty())
			return false;
		
		File[] fileList = readHomeworkFileList();
		
		//기존 folder에 있던 내용 삭제
		if(fileList != null) {
			for(File file : fileList){
				Files.delete(file.toPath());
			}
		}		
		//현재 갖고 있는 내용들 쓰기
		for(Homework elem : homeworkList) {
			writeEachFile(elem);
		}
		
		return true;
	}
	
	public void writeStudentNumber(String studentNumber) throws IOException {
		writeEachFile(studentNumber);
	}
}

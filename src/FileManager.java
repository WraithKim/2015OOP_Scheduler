import java.util.ArrayList;
import java.io.File;
import java.lang.String;

public class FileManager {
	private static final String DELIMITER = "!:@:@$:#$^&&*^%^&!@##$@$##$!";
	
	private ArrayList<MemoFile> memoFiles;
	
	//TODO : 생성자 구현
	//생성자 호출 시 data 폴더가 있는지 확인하고 없으면 생성, 있을 경우 모든 내용을 읽어와서 memoFiles에 저장해 둔다.
	//연도 폴더를 만들 경우 사용하든 안 하든 1 ~ 12월 폴더도 하위에 미리 생성해 두기
	//각 내용은 일자별로 연_월_일.txt라는 파일의 형태로 저장
	public FileManager() {
		
	}
	
	//TODO : 디렉토리에 있는 리스트 가져와서 모든 파일을 읽는 작업 필요. + 쓰는 작업도 필요.(읽는 함수, 쓰는 함수, 업데이트 함수)
	/*
		Tip) 디렉토리의 파일 전체 목록을 가져오는것은 아래와 같이도 가능하다.
		File path = new File("C:\test\") ; 
		File[] list = path.listFiles() ;
	*/
	
	private void makeDirectory(String dirName) {
		//현재 디렉토리를 잘 받아 오는 것인지 테스트 필요함.
		File dir = new File(System.getProperty("user.dir"), dirName);
		
		if(!dir.exists()){
	         dir.mkdir();
	      }
	}
	
	private void writeMemoFile(MemoFile memoFile) {
		
	}
	
	//MemoFile를 String의 형태로 변환, Delimiter로 특수한 문자열 사용.
	private String transformForWrite(MemoFile memoFile) {
		String str = "";
		
		str += String.valueOf(memoFile.getYear()) 
				+ DELIMITER + String.valueOf(memoFile.getMonth())
				+ DELIMITER + String.valueOf(memoFile.getDay()) 
				+ DELIMITER + String.valueOf(memoFile.getHour())
				+ DELIMITER + String.valueOf(memoFile.getMin())
				+ DELIMITER + String.valueOf(memoFile.getLevel())
				+ DELIMITER + memoFile.getName()
				+ DELIMITER + memoFile.getContent()
				+ DELIMITER + String.valueOf(memoFile.getAlarmHour())
				+ DELIMITER + String.valueOf(memoFile.getAlarmMin())
				+ DELIMITER + memoFile.getIsAlarm()
				+ DELIMITER + memoFile.getIsDiary()
				+ DELIMITER + memoFile.getDiaryContent();
		
		return str;
	}
	
	//String을 MemoFile 형태로 변환, Delimiter로 특수한 문자열 사용.
	private MemoFile parseToMemoFile(String source) {
		String[] content = source.split(DELIMITER);
		
		MemoFile e = new MemoFile(Integer.parseInt(content[0]), Integer.parseInt(content[1])
				, Integer.parseInt(content[2]), Integer.parseInt(content[3]),
				Integer.parseInt(content[4]), Integer.parseInt(content[5]),
				content[6], content[7], Integer.parseInt(content[8]),
				Integer.parseInt(content[9]), Boolean.parseBoolean(content[10]),
				Boolean.parseBoolean(content[11]), content[12]);
		
		return e;
	}
	
	//TODO : interface 제공해주기
	/*
		특정 연, 월, 일 (혹은 일부) or 일기로 체크한 내용들 or 특정 일자의 특정 시간대의 알람 등..
		다양한 경우에 대해 요청할 시나리오를 생각해서 해당 memoFile을 찾아주기 (id의 arrayList<int>를 반환해주자, 복수 개 일 수도 있다)
		
		memoFile의 id를 인자로 넘겨서 특정 memoFile의 내용 열람 및 수정이 가능하도록 interface 제공해주기.
	*/
}

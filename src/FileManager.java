import java.util.ArrayList;
import java.io.File;
import java.lang.String;

public class FileManager {
	private static final String DELIMITER = "!:@:@$:#$^&&*^%^&!@##$@$##$!";
	
	private ArrayList<MemoFile> memoFiles;
	
	//TODO : ������ ����
	//������ ȣ�� �� data ������ �ִ��� Ȯ���ϰ� ������ ����, ���� ��� ��� ������ �о�ͼ� memoFiles�� ������ �д�.
	//���� ������ ���� ��� ����ϵ� �� �ϵ� 1 ~ 12�� ������ ������ �̸� ������ �α�
	//�� ������ ���ں��� ��_��_��.txt��� ������ ���·� ����
	public FileManager() {
		
	}
	
	//TODO : ���丮�� �ִ� ����Ʈ �����ͼ� ��� ������ �д� �۾� �ʿ�. + ���� �۾��� �ʿ�.(�д� �Լ�, ���� �Լ�, ������Ʈ �Լ�)
	/*
		Tip) ���丮�� ���� ��ü ����� �������°��� �Ʒ��� ���̵� �����ϴ�.
		File path = new File("C:\test\") ; 
		File[] list = path.listFiles() ;
	*/
	
	private void makeDirectory(String dirName) {
		//���� ���丮�� �� �޾� ���� ������ �׽�Ʈ �ʿ���.
		File dir = new File(System.getProperty("user.dir"), dirName);
		
		if(!dir.exists()){
	         dir.mkdir();
	      }
	}
	
	private void writeMemoFile(MemoFile memoFile) {
		
	}
	
	//MemoFile�� String�� ���·� ��ȯ, Delimiter�� Ư���� ���ڿ� ���.
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
	
	//String�� MemoFile ���·� ��ȯ, Delimiter�� Ư���� ���ڿ� ���.
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
	
	//TODO : interface �������ֱ�
	/*
		Ư�� ��, ��, �� (Ȥ�� �Ϻ�) or �ϱ�� üũ�� ����� or Ư�� ������ Ư�� �ð����� �˶� ��..
		�پ��� ��쿡 ���� ��û�� �ó������� �����ؼ� �ش� memoFile�� ã���ֱ� (id�� arrayList<int>�� ��ȯ������, ���� �� �� ���� �ִ�)
		
		memoFile�� id�� ���ڷ� �Ѱܼ� Ư�� memoFile�� ���� ���� �� ������ �����ϵ��� interface �������ֱ�.
	*/
}

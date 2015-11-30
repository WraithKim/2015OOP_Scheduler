import java.lang.String;

public class MemoFile {
	private static final int MIN_LEVEL = 1;
	private static final int MAX_LEVEL = 5;
	private static int fileId;
	
	private int id;
	private int year;
	private int month;
	private int day;
	private int hour;												//0h ~ 24h
	private int min;
	private int level;
	private String name;
	private String content;
	private int arlamHour;
	private int arlamMin;
	private boolean isArlam;
	private boolean isDiary;
	private String diaryContent;
	
	public MemoFile() {
		id = ++fileId;
		year = 0;
		month = 0;
		day = 0;
		hour = 0;
		min = 0;
		name = null;
		content = null;
		arlamHour = 0;
		arlamMin = 0;
		isArlam = false;
		level = 0;
		isDiary = false;
		diaryContent = null;
	}
	
	public MemoFile(int year, int month, int day, String name, String content) {
		this(year, month, day, 0, 0, 0, name, content, 0, 0, false, false, null);
	}
	
	public MemoFile(int year, int month, int day, int hour, int min, int level, String name, String content
			, int alarmHour, int alarmMin, boolean isAlarm, boolean isDiary, String diaryContent) {
		id = ++fileId;
		this.year = year;
		this.month = month;
		setHour(hour);
		setMin(min);
		setLevel(level);
		setName(name);
		setContent(content);
		setAlarmHour(alarmHour);
		setAlarmMin(alarmMin);
		this.isArlam = isAlarm;
		this.isDiary = isDiary;
		this.diaryContent = diaryContent;
	}
	
	public int getId() {
		return this.id;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getDay() {
		return this.day;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMin() {
		return this.min;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public int getAlarmHour() {
		return this.arlamHour;
	}
	
	public int getAlarmMin() {
		return this.arlamMin;
	}
	
	public boolean getIsAlarm() {
		return this.isArlam;
	}
	
	public boolean getIsDiary() {
		return this.isDiary;
	}
	
	public String getDiaryContent() {
		return this.diaryContent;
	}
	
	public void setHour(int hour) {
		if(hour >= 0 && hour <= 24)
			this.hour = hour;
		else
			this.hour = 0;
	}
	
	public void setMin(int min) {
		if(min >= 0 && min <= 60)
			this.min = min;
		else
			this.min = 0;
	}
	
	public void setLevel(int level) {
		if(level >= MIN_LEVEL && level <= MAX_LEVEL)
			this.level = level;
		else
			this.level = 1;
	}
	
	public void setName(String name) {
		if(name != null)
			this.name = name;
		else
			this.name = " ";
	}
	
	public void setContent(String content) {
		if(content != null)
			this.content = content;
		else
			this.content = " ";
	}
	
	public void setAlarmHour(int alarmHour) {
		if(alarmHour >= 0 && alarmHour <= 24)
			this.arlamHour = alarmHour;
		else
			this.arlamHour = 0;
	}
	
	public void setAlarmMin(int alarmMin) {
		if(alarmMin >= 0 && alarmMin <= 60)
			this.arlamMin = alarmMin;
		else
			this.arlamMin = 0;
	}
	
	public void onAlarm() {
		this.isArlam = true;
	}
	
	public void offAlarm() {
		this.isArlam = false;
	}
	
	public void useDiary() {
		this.isDiary = true;
	}
	
	public void useNotDiary() {
		this.isDiary = false;
	}
	
	public void setDiaryContent(String content) {
		this.diaryContent = content;
	}
}

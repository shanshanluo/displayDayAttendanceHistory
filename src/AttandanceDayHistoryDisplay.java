import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;


public class AttandanceDayHistoryDisplay{
	
	protected static final String ReturnType = null;


	public static void main(String[] args){
		
		UI aUI=new UI();
		
		createRecord();
		
		
	//	ReturnType tret = displayDayAttendanceHistory("001", "20140211");
		
	//	System.out.println("classRoster_001_1: " + tret.ClassRoster.get(0));
	//	System.out.println("classRecord_001_1: " + tret.ClassRecord.get(0));
		
	//	tret = displayDayAttendanceHistory("001", "20140416");
	//	System.out.println("classRoster_001_1: " + Student.getStudentName(tret.ClassRoster.get(42)));
	//	System.out.println("classRecord_001_1: " + Student.getStudentName(tret.ClassRecord.get(2)));
		
	//	tret = displayDayAttendanceHistory("002", "20140326");
	//	System.out.println("classRoster_001_2: " + Student.getStudentName(tret.ClassRoster.get(41)));
	//	System.out.println("classRecord_001_2: " + Student.getStudentName(tret.ClassRecord.get(2)));
		
	//	tret = displayDayAttendanceHistory("002", "20140512");
	//	System.out.println("classRoster_001_2: " + Student.getStudentName(tret.ClassRoster.get(41)));
	//	System.out.println("classRecord_001_2: " + Student.getStudentName(tret.ClassRecord.get(2)));
	}
	
	static class ReturnType
	{
		ArrayList<String> ClassRoster;
		ArrayList<String> ClassRecord;
	
		public ReturnType(ArrayList<Student> classRoster, ArrayList<Student> classRecord)
		{
			int ClassRosterSize, ClassRecordSize;
			
			ClassRoster = new ArrayList<String>();
			ClassRecord = new ArrayList<String>();
			
			ClassRosterSize = classRoster.size();
			for(int i=0; i<ClassRosterSize; i++){
				ClassRoster.add(new String(Student.getStudentName(classRoster.get(i))));
			}
			
			ClassRecordSize = classRecord.size();
			for(int i=0; i<ClassRecordSize; i++)
				ClassRecord.add(new String(Student.getStudentName(classRecord.get(i))));
		}		
	}
	
	public static void createRecord()
	{
		//create 2 course objects each of them corresponds to one classroster ID		
		Course Course001 = new Course("001");
		Course Course002 = new Course("002");
			
		Course.createCourseArray();
		Course.add2CourseArray(Course001);
		Course.add2CourseArray(Course002);
		
		Course001.Roster.addData2ClassRoster("ClassRoster-001.csv");
		Course002.Roster.addData2ClassRoster("ClassRoster-002.csv");
	
			
		//create 4 class attendance records for 2 classes
		AttendanceRecord attendRec001_2014_02_11 = new AttendanceRecord("001", "20140211");
		AttendanceRecord attendRec001_2014_04_16 = new AttendanceRecord("001", "20140416");
		AttendanceRecord attendRec002_2014_03_26 = new AttendanceRecord("002", "20140326");
		AttendanceRecord attendRec002_2014_05_12 = new AttendanceRecord("002", "20140512");
		
		
		AttendanceRecord.createRecordArray();
		AttendanceRecord.addRecord(attendRec001_2014_02_11);
		AttendanceRecord.addRecord(attendRec001_2014_04_16);
		AttendanceRecord.addRecord(attendRec002_2014_03_26);
		AttendanceRecord.addRecord(attendRec002_2014_05_12);
		
		attendRec001_2014_02_11.addDate2AbsentArray("attendRec001-2014_02_11.csv");
		attendRec001_2014_04_16.addDate2AbsentArray("attendRec001-2014_04_16.csv");
		attendRec002_2014_03_26.addDate2AbsentArray("attendRec002-2014_03_26.csv");
		attendRec002_2014_05_12.addDate2AbsentArray("attendRec002-2014_05_12.csv");
	}
	
	
	public static ReturnType displayDayAttendanceHistory(String ClassID, String Date)
	{
		//get classRoster and absent list for display in UI layer
		ArrayList<Student> ClassRoster = Course.getClassRoster(ClassID);
		ArrayList<Student> AbsentList = AttendanceRecord.getClassDayAbsent(ClassID, Date);	
		
		ReturnType Ret = new ReturnType(ClassRoster, AbsentList);
		
		return Ret;
	}
}

class UI {
	//Frame
	private JFrame aFrame;

	//Panel
	private JPanel aPanel;
	private JPanel panel1;
	private JPanel panel2;
	private JScrollPane resultPane;

	private TextField classID, date;
	private DefaultTableModel resultModel;
	private JTable resultTable;
	//Button
	private JButton search, clear;
	private JLabel classID_prompt=new JLabel("ClassID");
	private JLabel date_prompt=new JLabel("Date");
	

	
	public UI() {
		aPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		aPanel.setBackground(Color.WHITE);
		aPanel.add(new JLabel("<html><font color='white'>------------------------------------------------------------------------------------------" +
				"-----------------------------------------------------------------------------------------------<html>"));
		classID = new TextField(37);
		date = new TextField(37);
		
		panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		panel1.setPreferredSize(new Dimension(770, 30));
		panel1.setBackground(Color.WHITE);
		panel1.add(classID_prompt); panel1.add(classID);
		panel1.add(new JLabel("<html><font color='white'>-------------<html>"));
		panel1.add(date_prompt);panel1.add(date);
		
		search = new JButton("Search"); search.setPreferredSize(new Dimension(200,40)); 
		clear = new JButton("Clear"); clear.setPreferredSize(new Dimension(200,40)); 
	
		panel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel2.setPreferredSize(new Dimension(770, 50));
		panel2.setBackground(Color.WHITE);
		panel2.add(search);
		panel2.add(new JLabel("<html><font color='white'>----------------------<html>"));
		panel2.add(clear);
	
		Object[][] data = new Object[50][2];
		String[] colIds = new String[2];
		colIds[0]="Name"; colIds[1]="Status";
		resultModel = new DefaultTableModel(data,colIds);
	
		resultTable=new JTable(resultModel);
		resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		resultTable.getColumnModel().getColumn(0).setPreferredWidth(375);
		resultTable.getColumnModel().getColumn(1).setPreferredWidth(375);
		resultTable.setPreferredScrollableViewportSize(resultTable.getPreferredSize());
		resultTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	
		resultPane=new JScrollPane(resultTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultPane.setPreferredSize(new Dimension(770, 350)); 
		
		aPanel.add(panel1);
		aPanel.add(new JLabel("<html><font color='white'>------------------------------------------------------------------------------------------" +
				"-----------------------------------------------------------------------------------------------<html>"));
		aPanel.add(panel2);
		aPanel.add(new JLabel("<html><font color='white'>------------------------------------------------------------------------------------------" +
				"-----------------------------------------------------------------------------------------------<html>"));
		aPanel.add(resultPane);
		
		search.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent q) {
						for(int k=0;k<50;k++)
						{
							resultTable.setValueAt(null, k, 0);
							resultTable.setValueAt(null, k, 1);
						}
						
						String a, b;
						a = classID.getText();
						b = date.getText();
						if(validInput(a,b)) {
							if(a.equals("001")) a="001"; if(a.equals("002")) a="002"; 
							if(b.equals("20140211")) b = "20140211";
							else if(b.equals("20140416")) b = "20140416";
							else if(b.equals("20140326")) b = "20140326";
							else if(b.equals("20140512")) b = "20140512";
							
							printResults(a,b);
							// System.out.println("classRoster_001_1: " + Student.getStudentName(tret.ClassRoster.get(42)));
							// System.out.println("classRecord_001_1: " + Student.getStudentName(tret.ClassRecord.get(2)));
							//System.out.println(tret.ClassRoster.size());
							//System.out.println(tret.ClassRecord.size());
						}
					}
				}
		);
		clear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent q) {
						for(int i=0;i<50;i++)
						{
							resultTable.setValueAt(null, i, 0);
							resultTable.setValueAt(null, i, 1);
						}
					}
				}
		);
		
		aFrame = new JFrame("DisplayAttendanceRecord");
		aFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
			{System.exit(0);}
			}
		);

		aFrame.getContentPane().add(aPanel);
		aFrame.pack();
		aFrame.setBounds(200, 50, 800, 600); //Set the size of the frame
		aFrame.setVisible(true); //Set the window always visible
	}
	
	void printResults(String a, String b) {
	//	System.out.println(a);
	//	System.out.println(b);
		
		AttandanceDayHistoryDisplay.ReturnType tret = AttandanceDayHistoryDisplay.displayDayAttendanceHistory(a, b);
		int inRoster = tret.ClassRoster.size();
		int inRecord = tret.ClassRecord.size();
		int i,j;
		String s = new String();
		for(i=0; i< inRoster; i++) {
			//  int flag = 1;
//			s = Student.getStudentName(tret.ClassRoster.get(i));
			s = tret.ClassRoster.get(i);
			resultTable.setValueAt(s, i, 0); 
			for(j = 0; j < inRecord; j++) {
	//			if(s.equals(Student.getStudentName(tret.ClassRecord.get(j)))) {		// !s.equals(Student.getStudentName(tret.ClassRecord.get(j)))
				if(s.equals((tret.ClassRecord.get(j)))) { 
				resultTable.setValueAt("X", i, 1);
					// flag = 0;
				}
				
			}
		//	if(flag ==1) resultTable.setValueAt("X", i, 1);
		}
	}
	
	boolean validInput(String a, String b) {
		
		int flag = 0;
		if(a.equals("001")) {
			if(b.equals("20140211") || b.equals("20140416")) {
				flag++;
			} else {
				JOptionPane.showMessageDialog(null, "Error! Invalid Inputs.");
				return false;
			}
		} else if (a.equals("002")){
			if(b.equals("20140326") || b.equals("20140512")) {
				flag++;
			} else {
				JOptionPane.showMessageDialog(null, "Error! Invalid Inputs.");
				return false;
			}
		} else JOptionPane.showMessageDialog(null, "Error! Invalid Inputs.");
		  
		
		if(flag == 1) return true;
		else {
			return false;
		}
	}
}

class Course
{	
	private static ArrayList<Course> CourseArray;
	private String CourseID;
	classRoster Roster;
	
	public Course(String ID){
		CourseID = ID;
		Roster = new classRoster();
	}
	
	public static void createCourseArray(){
		CourseArray = new ArrayList<Course>();
	}
	
	public static void add2CourseArray(Course pCourse){
		CourseArray.add(pCourse);			
	}
	
	public static int getNumberOfCourseArray(){
		return CourseArray.size();
	}
	
	public static Course getCourse(String classID){
		int i;
		int size;
		
		size = getNumberOfCourseArray();
		for(i=0; i<size;i++){
			Course course = CourseArray.get(i);
			if(course.CourseID == classID)
				break;
		}
		 
		return CourseArray.get(i);
	}
	
	public static ArrayList<Student> getClassRoster(String classID){
		Course tCourse = getCourse(classID);		
		return tCourse.Roster.getRosterList();
	}
	
	
	class classRoster{
		private ArrayList<Student> RosterList;
					
		public classRoster(){
			RosterList = new ArrayList <Student>();
		}
		
		public ArrayList<Student> getRosterList(){
			
				return RosterList;
		}
		
		public int getStudentNumberInRoster(){
			return RosterList.size();
		}
		
		public void addData2ClassRoster(String Source){
			//read file start from 1st cell 
			Student.readInStudentList(Source, RosterList, 0);
		}
	}
}


class AttendanceRecord{
	
	private static ArrayList<AttendanceRecord> RecordArray;
	
	private String CourseID;
	private String Date;
	private ArrayList<Student> DayAbsentArray;
	
	public AttendanceRecord(String courseId, String date){
		CourseID = courseId;
		Date = date;
		DayAbsentArray = new ArrayList<Student>();
	}
	
	public static void createRecordArray(){
		RecordArray = new ArrayList<AttendanceRecord>();
	}
	
	public static void addRecord(AttendanceRecord record){
		RecordArray.add(record);
	}
	
	public static AttendanceRecord getRecord(String courseId, String date){
		int size;
		int i;
		
		size = RecordArray.size();
		for(i=0; i<size;i++){
			AttendanceRecord record = RecordArray.get(i);
			if((record.CourseID == courseId)&&(record.Date == date))
				break;
		}
		 
		return RecordArray.get(i);
	}
	
	
	public static ArrayList<Student> getClassDayAbsent(String courseId, String date){
				
		return getRecord(courseId, date).DayAbsentArray;		
	}
	
	public void addDate2AbsentArray(String Source){
		//read file start from 3rd cell 
		Student.readInStudentList(Source, DayAbsentArray, 2);
	}
	
}


class Student{
	private String studentName;
	
	public Student(String name){
		studentName = name;
	}
	
	public static String getStudentName(Student student){
		return student.studentName;
	}
	
	public static void setStudentName(Student student, String name){
		student.studentName = name;
	}
	
	public static void readInStudentList(String InputFileName, ArrayList<Student> StudentList, int offset) {

		String fileName;
		int i;
		
		fileName=".\\ProjectPart-4 Data\\" + InputFileName;  
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringTokenizer st = null;

			while((fileName = br.readLine()) != null)
			{
				st = new StringTokenizer(fileName, ",");
			
			
				for(i=0; i<offset; i++)
					st.nextToken();
			
				i = 0;
				while(st.hasMoreTokens())
				{
					StudentList.add(new Student(st.nextToken()));
				}
			}	            
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}



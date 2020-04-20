import java.util.ArrayList;

//import java.util.concurrent.Semaphore;


public class Process extends Thread {
	
	public int processID;
    ProcessState status=ProcessState.New;	
    Boolean ranOnceBefore;
    ArrayList<SemaphoreI> SemaphoreAccess;

	
	public Process(int m , ArrayList<SemaphoreI> sems) {
		processID = m;
		this.SemaphoreAccess = sems;
	}
	@Override
	public void run() {
		//check for the ready queue whose next
		
		
		switch(processID)
		{
		case 1:process1();break;
		case 2:process2();break;
		case 3:process3();break;
		case 4:process4();break;
		case 5:process5();break;
		}

	}
	
	public boolean SemWriteWait() {
		for(int i = 0 ; i<SemaphoreAccess.size() ; i++) {
			SemaphoreI s = this.SemaphoreAccess.get(i);
			if(s.type.equals("Writing")) {
				if(s.available == true) {
					s.changeAvailability();
					return true;
				}
				else {
					return false;
				}		
			}
		}
		return false;
		
	}
	public void SemWritePost(){
		for(int i = 0 ; i<SemaphoreAccess.size() ; i++) {
			SemaphoreI s = this.SemaphoreAccess.get(i);
			if(s.type.equals("Writing"))
			s.changeAvailability();
		}
	}
	
	
	private void process1() {
		boolean proceed =this.SemWriteWait();
		// check for semaphore's availability
		if(proceed) {
		OperatingSystem.printText("Enter File Name: ");
		OperatingSystem.printText(OperatingSystem.readFile(OperatingSystem.TakeInput()));
		
		setProcessState(this,ProcessState.Terminated);
		this.SemWritePost();
		}
		else {
			setProcessState(this, ProcessState.Waiting);
		}
		}
	
	private void process2() {
		
		OperatingSystem.printText("Enter File Name: ");
		String filename= OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter Data: ");
		String data= OperatingSystem.TakeInput();
		OperatingSystem.writefile(filename,data);
		setProcessState(this,ProcessState.Terminated);
		}
	private void process3() {
		int x=0;
		while (x<301)
		{ 
			OperatingSystem.printText(x+"\n");
			x++;
		}
		setProcessState(this,ProcessState.Terminated);
		}
	
	private void process4() {
	
		int x=500;
		while (x<1001)
		{
			OperatingSystem.printText(x+"\n");
			x++;
		}	
		setProcessState(this,ProcessState.Terminated);
		}
	private void process5() {
		
		OperatingSystem.printText("Enter LowerBound: ");
		String lower= OperatingSystem.TakeInput();
		OperatingSystem.printText("Enter UpperBound: ");
		String upper= OperatingSystem.TakeInput();
		int lowernbr=Integer.parseInt(lower);
		int uppernbr=Integer.parseInt(upper);
		String data="";
		
		while (lowernbr<=uppernbr)
		{
			data+=lowernbr++ +"\n";
		}	
		OperatingSystem.writefile("P5.txt", data);
		setProcessState(this,ProcessState.Terminated);
	}
	
	 public static void setProcessState(Process p, ProcessState s) {
		 p.status=s;
		 if (s == ProcessState.Terminated)
		 {
			 OperatingSystem.ProcessTable.remove(OperatingSystem.ProcessTable.indexOf(p));
		 }
	}
	 
	 public boolean checkIfCanUnblock() {
		 
	 }
	 
	 public static ProcessState getProcessState(Process p) {
		 return p.status;
	}
}

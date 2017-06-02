package disk_info;
import java.io.File;
public class FreeDiskSpace {
	
	public static String free_disk_info(String saveFileAs, String urlPath){
		    String Sub_Str = saveFileAs.substring(0,2);
		    System.out.println(Sub_Str);
	        File file = new File(Sub_Str);  
	        long totalSpace = file.getTotalSpace();  
	        long freeSpace = file.getFreeSpace();  
	        long usedSpace = totalSpace - freeSpace;
	        String total_info = String.valueOf(" £”‡ø…”√ø’º‰£∫"+String.valueOf(freeSpace/1024/1024/1024)+"G");
	        return total_info;  
	   
	}




}


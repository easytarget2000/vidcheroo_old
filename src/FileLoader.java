import java.io.File;
import java.util.ArrayList;


public class FileLoader {
	
	private ArrayList<String> filePathList = new ArrayList<>();
	
	public FileLoader() {
		loadFileList("C:\\Users\\Michel\\Projekte\\VIDCHEROO\\FEED\\");
	}
	
	public void loadFileList(String mediaDirectory) {
		
		// Empty the ArrayList.
		for(int i=0; i < filePathList.size(); i++)
			filePathList.remove(i);
		
		System.out.println("Looking for media files in " + mediaDirectory);
		
		if(mediaDirectory.length() > 1) {
			File fileDirectory = new File(mediaDirectory);
			
			for (final File fileEntry : fileDirectory.listFiles()) {
		        
				if (!fileEntry.isDirectory()) {
					filePathList.add(mediaDirectory + "\\" + fileEntry.getName());
		            System.out.println(fileEntry.getName());
		        }
				
		    }
			
			System.out.println("" + filePathList.size());
		}
		
	}
	
	public String getNextVideoPath() {
		String strNextVideo = "";
		int iNextVideo = 0;
		
		if(filePathList.size()>0) {
			iNextVideo = (int) (Math.random() * filePathList.size());
			strNextVideo = filePathList.get(iNextVideo);
		}
		
		return strNextVideo;
	}
	
	public int getFileListLength() {
		return filePathList.size();
	}


}

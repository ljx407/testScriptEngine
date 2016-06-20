/**
 * 
 */
package com.ljx.log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.junit.Test;

/**
 * @author admin
 */
public class OnLineLog {

	@Test
	public void log() throws Exception{
		File dir = new File("C:\\Users\\admin\\Desktop\\basicfinance");
		File files[] = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".zip");
			}
		});
		Map<String,RandomAccessFile> logFiles = new HashMap<String,RandomAccessFile>();
		for(File file:files){
			String day = file.getName().split("_")[1];
			if(!logFiles.containsKey(day)){
				logFiles.put(day, new RandomAccessFile("C:\\Users\\admin\\Desktop\\basicfinance\\logs\\"+day+"_catalina.out","rw"));
			}
			
			@SuppressWarnings("unused")
			RandomAccessFile logFile = logFiles.get(day);
			ZipInputStream Zin=new ZipInputStream(new FileInputStream(file));
			BufferedInputStream Bin=new BufferedInputStream(Zin);
			ZipEntry entry;
			RandomAccessFile Fout = logFiles.get(day);
			while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
				Fout.seek(Fout.length());  
				int b;  
				while((b=Bin.read())!=-1){  
					Fout.write(b);  
				}  	
			}
			Bin.close();  
			Zin.close();  
		}
		 

	}
}

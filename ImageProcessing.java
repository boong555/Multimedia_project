import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;





public class ImageProcessing {
	
	private  int width = 352; 
	private  int height = 288; 
	
	private int NumberOfFrame;
	
	private BufferedImage[] BufferedImageObj;
	
	
	public ImageProcessing(String folderPath) {
		NumberOfFrame = 0;
		readImage(folderPath);
		
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}

	public BufferedImage getBufferedImage(int index) {
			return BufferedImageObj[index];
	}
	public BufferedImage[] getBufferedImage() {
		return BufferedImageObj;
	}
	
	public int getNumberOfFrame() {
		return NumberOfFrame;
	}

	public void setNumberOfFrame(int numberOfFrame) {
		NumberOfFrame = numberOfFrame;
	}

	public void setBufferedImage(String fileName,int index) {
		
		 BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		    try {
		    	
			    File file = new File(fileName);
			    InputStream is = new FileInputStream(file);
	
			    long len = file.length();
			    byte[] bytes = new byte[(int)len];
			    
			    int offset = 0;
		        int numRead = 0;
		        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
		            offset += numRead;
		        }
		        		
		    	int ind = 0;
				for(int y = 0; y < height; y++){
			
					for(int x = 0; x < width; x++){
				 
						byte a = 0;
						byte r = bytes[ind];
						byte g = bytes[ind+height*width];
						byte b = bytes[ind+height*width*2]; 
						
						//setRGBfromByte(index,x,y,r,g,b);
						//System.out.println(  "X,Y "+x+","+ y+" ["+r+","+g+","+b+"] ");
						
						int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
						//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
						//System.out.println(pix);
						img.setRGB(x,y,pix);
						ind++;
					}
					//System.out.println();
				}
			
			
		    } catch (FileNotFoundException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    
		    BufferedImageObj[index] = img;
		
	}

	
	public void readImage(String folderpath){
		
		String filePath = folderpath;
		File[] files = new File(filePath).listFiles();
		ArrayList<String> rgbfiles = new ArrayList<String>();
		
		for (File file : files) {
		    if (file.isFile()) {
		        //results.add(file.getName());
		    	if(getFileExtension(file).equalsIgnoreCase(".rgb")){
		    		rgbfiles.add(file.getPath());
		    	}
		    }
		}
		
		
		NumberOfFrame = rgbfiles.size();
		
		BufferedImageObj = new BufferedImage[NumberOfFrame];
		

		
		for(int index = 0;index<NumberOfFrame;index++){
			//System.out.println(rgbfiles.get(index));
			setBufferedImage(rgbfiles.get(index),index);
	
		}
		
	
		
	}



}

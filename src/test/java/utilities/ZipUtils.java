package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	private static ArrayList<String> fileList = new ArrayList<String>();
	private static String SOURCE_FOLDER = "";
	
	/**
	 * Method to ZIP File, Directory or Folder
	 * @param SOURCE_FOLDER
	 * @param OUTPUT_ZIP_LOCATION
	 */
	public static void zipDirectory(String SOURCE_FOLDER, String OUTPUT_ZIP_LOCATION) 
	{
		ZipUtils.SOURCE_FOLDER = SOURCE_FOLDER;
		generateFileList(SOURCE_FOLDER); 
	    zipIt(OUTPUT_ZIP_LOCATION);
	}
	
	/**
	 * NOT TO BE CALLED LOCALLY
	 */
	// Method to ZIP files available in a folder
	private static void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;

            for (String file: fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            System.out.println("Folder successfully compressed");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
	
	/**
	 * NOT TO BE CALLED LOCALLY
	 */
	// Method to generate list of file present in a folder
	private static void generateFileList(String node) {
        // add file only
        if (new File(node).isFile()) {
            fileList.add(generateZipEntry(node));
        }

        if (new File(node).isDirectory()) {
            String[] subNode = new File(node).list();
            for (String filename: subNode) {
                generateFileList(node + File.separator + filename);
            }
        }
    }
	
	/**
	 * NOT TO BE CALLED LOCALLY
	 */
	// Method generated the ZIP entry of a given file
	private static String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
}

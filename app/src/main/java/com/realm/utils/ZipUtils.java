package com.realm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class ZipUtils {

	private static final String REST_ROOT = "restphone_temp";
	private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte
	/**
	 * 解压缩一个文件
	 *
	 * @param zipFile 压缩文件
	 * @param folderPath 解压缩的目标目录
	 * @throws IOException 当解压缩过程出错时抛出
	 */
	public static void upZipFileAndCopyToPhotos(Context context,File zipFile, String folderPath) throws ZipException, IOException {
		upZipFile(zipFile, folderPath);

		File cacheDir = new File(folderPath);
		File[] files = cacheDir.listFiles();
		if (files == null || files.length == 0) {
			return ;
		}
		for (File file : files) {
			Bitmap rawBitmap1 = BitmapFactory.decodeFile(file.getAbsolutePath(), null); 
			saveLocalImage(context,rawBitmap1,file.getName(),true) ;
		}
		deleteFilePath(new File(Environment.getExternalStorageDirectory() + File.separator + REST_ROOT));
	}

	/**
	 * 拷贝文件到照片目录下.
	 *
	 * @param file 压缩文件
	 * @throws IOException 当解压缩过程出错时抛出
	 */
	public static void copyToPhotos(Context context,File file,String newNewFileName) throws IOException {
		saveLocalImage2(context, file.getAbsolutePath(),newNewFileName);
		deleteFilePath(new File(Environment.getExternalStorageDirectory() + File.separator + REST_ROOT));
	}

	/**
	 * 保存图片到相册目录.
	 */
	private static boolean saveLocalImage2(Context context,String filepath,String newNewFileName) {
		try {
			String url = MediaStore.Images.Media.insertImage(context.getContentResolver(),filepath, newNewFileName, newNewFileName);
			if(url == null){
				return false;
			}else{
				String savepath = getFilePathByContentResolver(context, Uri.parse(url));
				if(savepath == null){
					return false;
				}else{
					int indexLastFile = savepath.lastIndexOf(File.separator);
					String path = savepath.substring(0, indexLastFile + 1);
					String nameold = savepath.substring(indexLastFile+1);
					String newPath  = path+newNewFileName;
					renameFile(path,nameold,newNewFileName);
					Log.e("cy", "cy== path==" + savepath);
					Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri uri = Uri.fromFile(new File(newPath));
					intent.setData(uri);
					context.sendBroadcast(intent);
				}
			}
			System.gc();
			return true;
		}catch (Exception ex) {
			return false;
		} catch (OutOfMemoryError e){
			return false;
		}
	}

	/** *//**文件重命名
	 * @param path 文件目录
	 * @param oldname  原来的文件名
	 * @param newname 新文件名
	 */
	public static void renameFile(String path, String oldname, String newname){
		if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile=new File(path+"/"+oldname);
			File newfile=new File(path+"/"+newname);
			if(!oldfile.exists()){
				return ;//重命名文件不存在
			}
			if(newfile.exists()) {//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				Log.e("cy", "cy===" + newname + "已经存在！");
				try{
					newfile.delete();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
			oldfile.renameTo(newfile);
		}
	}

	private static String getFilePathByContentResolver(Context context, Uri uri) {
		if (null == uri) {
			return null;
		}
		Cursor c = context.getContentResolver().query(uri, null, null, null, null);
		String filePath  = null;
		if (null == c) {
			throw new IllegalArgumentException(
					"Query on " + uri + " returns null result.");
		}
		try {
			if ((c.getCount() != 1) || !c.moveToFirst()) {
			} else {
				filePath = c.getString(
						c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
			}
		} finally {
			c.close();
		}
		return filePath;
	}

	/**
	 * 保存图片到相册目录.
	 */
	public static boolean saveLocalImage(Context context,Bitmap bitmap,String newNewFileName,boolean cleanFlag) {
		try {

			String url = MediaStore.Images.Media.insertImage(context.getContentResolver(),
					bitmap, newNewFileName, newNewFileName);
			if(url == null){
				return false;
			}else{
				String savepath = getFilePathByContentResolver(context, Uri.parse(url));
				if(savepath == null){
					return false;
				}else{
					int indexLastFile = savepath.lastIndexOf(File.separator);
					String path = savepath.substring(0, indexLastFile + 1);
					String nameold = savepath.substring(indexLastFile+1);
					String newPath  = path+newNewFileName;
					renameFile(path,nameold,newNewFileName);
					Log.e("cy", "cy== path==" + savepath);
					Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri uri = Uri.fromFile(new File(newPath));
					intent.setData(uri);
					context.sendBroadcast(intent);
				}
			}
			if(cleanFlag){
				if(bitmap!=null){
					bitmap.recycle();
					bitmap=null;
				}
			}
			System.gc();
			return true;
		}catch (Exception ex) {
			return false;
		} catch (OutOfMemoryError e){
			return false;
		}
	}
	/**
	 * 解压缩一个文件
	 *
	 * @param zipFile 压缩文件
	 * @param folderPath 解压缩的目标目录
	 * @throws IOException 当解压缩过程出错时抛出
	 */
	public static void upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
		File desDir = new File(folderPath);
		if (!desDir.exists()) {
			desDir.mkdirs();
		}
		ZipFile zf = new ZipFile(zipFile);
		for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) {
			ZipEntry entry = ((ZipEntry)entries.nextElement());
			InputStream in = zf.getInputStream(entry);
			String str = folderPath + File.separator + entry.getName();
			str = new String(str.getBytes("8859_1"), "GB2312");
			File desFile = new File(str);
			if (!desFile.exists()) {
				File fileParentDir = desFile.getParentFile();
				if (!fileParentDir.exists()) {
					fileParentDir.mkdirs();
				}
				desFile.createNewFile();
			}
			OutputStream out = new FileOutputStream(desFile);
			byte buffer[] = new byte[BUFF_SIZE];
			int realLength;
			while ((realLength = in.read(buffer)) > 0) {
				out.write(buffer, 0, realLength);
			}
			in.close();
			out.close();
		}
	}
	
	/**
	 * 删除目录下的文件.
	 * @param fileDir
	 */
	private static void deleteFilePath(File fileDir){ 
		if(fileDir.exists()){                    //判断文件是否存在
			if(fileDir.isDirectory()){              //否则如果它是一个目录
				File files[] = fileDir.listFiles();               //声明目录下所有的文件 files[];
				if (files!=null){
					for(int i = 0; i < files.length; i++){            //遍历目录下所有的文件
						if(files[i].isDirectory()){
							deleteFilePath(files[i]);
						}else{
							Log.i("deleteFilePath","删除文件"+files[i].getAbsolutePath());
							files[i].delete();
						}
					} 
				}
			} 
			Log.i("deleteFilePath","文件夹"+fileDir.getAbsolutePath()+"里的文件删除完毕.");
			fileDir.delete();//目录也删除掉.
			Log.i("deleteFilePath","删除文件夹"+fileDir.getAbsolutePath());
		}else{ 
			Log.i("deleteFilePath","所删除的文件不存在！"); 
		} 
	}

	/**
	 * 判断使用外部存储路径还是内部存储路径
	 * @param context
	 * @param dir
	 * @return 返回路径
	 */
	public static String getFilePath(Context context,String dir) {
		String directoryPath="";
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ) {//判断外部存储是否可用
			directoryPath =context.getExternalFilesDir(dir).getAbsolutePath();
		}else{//没外部存储就使用内部存储
			directoryPath=context.getFilesDir()+File.separator+dir;
		}
		File file = new File(directoryPath);
		if(!file.exists()){//判断文件目录是否存在
			file.mkdirs();
		}
		return directoryPath;
	}

	/**
	 * 解压目标文件
	 * @param context
	 * @param destDirName 存放解压文件的目录名,默认路径为data/data/packagename/app_destDirName
	 * @param fileName
	 */
	public static void UnZipFile(Context context,String destDirName,String fileName) {
		File zipfileDir = context.getDir(destDirName, Activity.MODE_PRIVATE);
		InputStream stream = null;
		ZipInputStream inZip = null;
		try {
			//将Assets文件夹下面的压缩包，转换成字节读取流
			stream = context.getAssets().open(fileName);
			//将字节读取流转成zip读取流
			inZip = new ZipInputStream(stream);
			//压缩文件实体
			ZipEntry zipEntry;
			//压缩文件实体中的文件名称
			String szName = "";
			while ((zipEntry = inZip.getNextEntry()) != null) {
				szName = zipEntry.getName();
				if (zipEntry.isDirectory()) {
					//zipEntry是目录,则创建目录
					szName = szName.substring(0, szName.length() - 1);
					File folder = new File(zipfileDir, szName);
					folder.mkdirs();
				} else {
					//否则创建文件,并输出文件的内容
					File file = new File(zipfileDir, szName);
					file.createNewFile();
					FileOutputStream out = new FileOutputStream(file);
					int len;
					byte[] buffer = new byte[BUFF_SIZE];
					while ((len = inZip.read(buffer)) != -1) {
						out.write(buffer, 0, len);
						out.flush();
					}
					out.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inZip != null) {
				try {
					inZip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

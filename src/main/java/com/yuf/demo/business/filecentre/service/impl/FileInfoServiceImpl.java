package com.yuf.demo.business.filecentre.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yuf.demo.business.filecentre.entity.FileInfo;
import com.yuf.demo.business.filecentre.mapper.FileInfoMapper;
import com.yuf.demo.business.filecentre.service.IFileInfoService;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author yuf
 * @since 2019-01-27
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {
	
	@Value("${storage.path}")
    private String storageBasePath;
	@Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public String saveFile(FileInfo fileInfo) {
        if (fileInfo.getId() == null) {
            fileInfo.createDefaultInfo();
            fileInfoMapper.insert(fileInfo);
        }
        return fileInfo.getId();
    }

    @Override
    public FileInfo getFileByFileId(String fileId) {
        // TODO Auto-generated method stub
        return fileInfoMapper.selectById(fileId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteFileByFileId(String fileId) {
        FileInfo file = getFileByFileId(fileId);
        if (file != null && deleteDiskFile(file) == true) {
            if(fileInfoMapper.deleteById(fileId) > 0)return true;
        }
        return false;
    }

    @Override
	public boolean deleteFileByFileIdForce(String fileId) {
    	FileInfo file = getFileByFileId(fileId);
    	if(file != null){
    		File diskfile = new File(storageBasePath + file.getFullPath());
    		diskfile.delete();
            if(fileInfoMapper.deleteById(fileId) > 0)return true;
    	}
		return false;
	}
    
    //删除真实文件
    private boolean deleteDiskFile(FileInfo fileInfo) {
        File file = new File(storageBasePath + fileInfo.getFullPath());
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;

    }

    //删除数据库中不存在本地文件
    @Override
    public List<String> deleteFileNotInDB(){
    	List<String> delFileNames = new ArrayList<>();
    	//读取本地所有文件名
    	File mainFile = new File(storageBasePath);
		List<String> fileFullNames = new ArrayList<>();
		File[] files = mainFile.listFiles();
		for (File file : files) {
			if(file.isDirectory()){
				File[] fileNames = file.listFiles();
				List<String> names = new ArrayList<>(fileNames.length);
				for (File f : fileNames) {
					names.add(file.getName()+"/"+f.getName());
				}
				fileFullNames.addAll(names);
			}
		}
		List<String> fileInfosFullPath = fileInfoMapper.getFullPath(null);
		for (String string : fileFullNames) {
			if(!fileInfosFullPath.contains(string)){
				boolean flag = false;
				File file = new File(storageBasePath + string);
		        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		        if (file.exists() && file.isFile()) {
		        	flag = file.delete();
		        	delFileNames.add(flag!=false?string:"删除失败："+string);
		        }
			}
		}
    	return delFileNames;
    }
    
//    @Override
//    public BasePage<T> getFileByParams(Map params,BasePage page) {
//    	String busiType = (String) params.get("busiType");
//		List<FileInfo> list = fileInfoMapper.getFileByParams(params,page);
//		if(busiType != null){
//			return (BasePage<T>) page.setRecords(list);
//		}
//		List<Map> datas = new ArrayList<>();
////		String[] typeName = {"一河一策","通知公告","工作汇报","公示牌","问题图片","问题视频","问题记录图片","问题记录视频","排污口","企业图片","投诉建议图片","投诉建议视频","投诉建议处理图片","摄像头","无人机","水质监测","一河一档"};
//		String[] typeName = {"一河一策","通知公告","工作汇报","一河一档"};
//		Map<String,List<FileInfo>> map = new HashMap<>();
//		for (FileInfo fileInfo : list) {
//			int nameKey = Integer.parseInt(fileInfo.getBusiType());
//			if(nameKey == 16)nameKey = 3;
//			String key = typeName[nameKey];
//			
//			if(map.containsKey(key)){
//				map.get(key).add(fileInfo);
//			}else{
//				List<FileInfo> sublist = new ArrayList<>();
//				sublist.add(fileInfo);
//				map.put(key, sublist);
//			}
//		}
//		for (String string : map.keySet()) {
//			Map subMap = new HashMap();
//			subMap.put("fileType", string);
//			subMap.put("resources",map.get(string));
//			datas.add(subMap);
//		}
//        return (BasePage<T>) page.setRecords(datas);
//    }

	@Override
	public FileInfo saveFile2Disk(MultipartFile multiFile, String busiType){
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		String folder = format.format(new Date()) ;
		String dirPath = storageBasePath + folder + File.separator;
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
//			Runtime.getRuntime().exec("chmod 777 "+ dirPath);
		}
		
		String fileName = busiType + "-" + System.currentTimeMillis()+ "-" + multiFile.getOriginalFilename();
		String fullFilePath = dirPath + fileName;
		File file = new File(fullFilePath);
		try {
			file.createNewFile();
			multiFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileInfo fileInfo = new FileInfo();
		fileInfo.setBusiType(busiType);
		fileInfo.setOriginalName(multiFile.getOriginalFilename());
		fileInfo.setFullPath(folder +"/"+ fileName);
		fileInfo.setFileName(fileName);
		fileInfo.setFileSize(new BigDecimal(multiFile.getSize()));
		fileInfo.setUploadTime(new Date());
		
//		Runtime.getRuntime().exec("chmod 777 "+ fullFilePath);
		return fileInfo;
	}

	@Override
	public Map<Integer,List<FileInfo>> saveExcelPic(MultipartFile file, String busiType){
		Workbook wb = null;
		try {
			wb = new XSSFWorkbook(file.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XSSFSheet sheet = (XSSFSheet) wb.getSheetAt(0);
		//获取图片和位置 (xlsx)
//		Map<String, PictureData> sheetList = new HashMap<String, PictureData>();
		Map<Integer, PictureData> sheetList = new HashMap<Integer, PictureData>();
		List<POIXMLDocumentPart> list = sheet.getRelations();
		for (POIXMLDocumentPart part : list) {
			if (part instanceof XSSFDrawing) {
				
				XSSFDrawing drawing = (XSSFDrawing) part;
				List<XSSFShape> shapes = drawing.getShapes();
				for (XSSFShape shape : shapes) {
					XSSFPicture picture = (XSSFPicture) shape;
					XSSFClientAnchor anchor = picture.getPreferredSize();
					CTMarker marker = anchor.getFrom();
//					String key = marker.getRow() + "-" + marker.getCol();
					Integer key = marker.getRow();
					sheetList.put(key, picture.getPictureData());
				}
			}
		}
		//excel图片文件夹
		String dirPath = storageBasePath + "excelPic" + File.separator;
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs();
//			Runtime.getRuntime().exec("chmod 777 "+ dirPath);
		}
		
		//将图片存到本地磁盘
		Map<Integer,List<FileInfo>> mapFiles = new HashMap<>();
		
		for (Integer key : sheetList.keySet()) {
			List<FileInfo> subFiles = new ArrayList<>();
		    // 获取图片流  
		    PictureData pic = sheetList.get(key);  
		    // 获取图片原始名  
		    String picName = key.toString();  
		    // 获取图片格式  
		    String ext = pic.suggestFileExtension();  
		      
		    byte[] data = pic.getData();  
		    
		    //图片保存路径 
		    String originalName = picName + "." + ext;
		    String fileName = busiType + "-" + System.currentTimeMillis()+ "-" + originalName;
			String fullFilePath = "excelPic" + "/" + fileName;
			
			try(FileOutputStream out = new FileOutputStream(dirPath + fileName);){
				
				out.write(data);  
				FileInfo fileInfo = new FileInfo();
				fileInfo.createDefaultInfo();
				fileInfo.setBusiType(busiType);
				fileInfo.setOriginalName(originalName);
				fileInfo.setFullPath(fullFilePath);
				fileInfo.setFileName(fileName);
				fileInfo.setFileSize(new BigDecimal(data.length));
				fileInfo.setUploadTime(new Date());
				fileInfoMapper.insert(fileInfo);//持久化到数据库
				subFiles.add(fileInfo);
				mapFiles.put(key, subFiles);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}  
		
		return mapFiles;
	}
}

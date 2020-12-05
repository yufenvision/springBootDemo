package com.yuf.demo.business.filecenter.controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuf.demo.business.filecenter.entity.FileInfo;
import com.yuf.demo.business.filecenter.mapper.FileInfoMapper;
import com.yuf.demo.business.filecenter.service.IFileInfoService;
import com.yuf.demo.utils.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author yuf
 * @since 2019-01-27
 */
@Api(tags="文件中心接口")
@RestController
@RequestMapping("/filecenter/fileInfo" )
public class FileInfoController {
	@Value("${storage.path}")
	private String storagePath;
	@Autowired
	private IFileInfoService fileInfoService;
	@Autowired
	private FileInfoMapper fileInfoMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(FileInfoController.class);
	
	@ApiOperation(value="多个文件上传接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="files",value="可上传多个文件"),
		@ApiImplicitParam(name="busiType",value="业务类型(0-一河一策，1-通知公告，2-工作汇报，3-公示牌,4-问题图片,5-问题视频,6-问题记录图片,7-问题记录视频,8-排污口,9-企业图片,10-投诉建议图片,11-投诉建议视频,12-投诉建议处理图片,13-摄像头,14-无人机,15-水质监测,16-一河一档,17-人员头像)")
	})
//	@RequiresPermissions("fileInfo:upload")
	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public Response<List<FileInfo>> upload(@RequestParam("files") MultipartFile[] files, @RequestParam String busiType){
		logger.info("files count = " + files.length);
		
		Response<List<FileInfo>> result = new Response<>();
		List<FileInfo> fileInfos = new ArrayList<FileInfo>();
		for(MultipartFile  multiFile : files){
			try {
				FileInfo fileInfo = fileInfoService.saveFile2Disk(multiFile,busiType);
				fileInfoService.saveFile(fileInfo);
				fileInfos.add(fileInfo);
			}catch (Exception e) {
				logger.error("save file failed ! busiType = " + busiType, e);
			} 
		}	
		result.setData(fileInfos);
		if(fileInfos.isEmpty()){
			result.setStatus(Response.Status.ERROR);
		}
		return result;	
	}
	
	@ApiOperation(value="单个文件上传接口")
//	@RequiresPermissions("fileInfo:uploadOne")
	@RequestMapping(value="/uploadOne",method = { RequestMethod.POST })
	@ApiImplicitParams({
		@ApiImplicitParam(name="file",value="上传单个文件"),
		@ApiImplicitParam(name="busiType",value="业务类型(0-一河一策，1-通知公告，2-工作汇报，3-公示牌,4-问题图片,5-问题视频,6-问题记录图片,7-问题记录视频,8-排污口,9-企业图片,10-投诉建议图片,11-投诉建议视频,12-投诉建议处理图片,13-摄像头,14-无人机,15-水质监测,16-一河一档")
	})
	public Response<FileInfo> uploadOne(@RequestParam MultipartFile file, @RequestParam String busiType){
		Response<FileInfo> result = new Response<>();
		try {
			FileInfo fileInfo = fileInfoService.saveFile2Disk(file,busiType);
			fileInfoService.saveFile(fileInfo);	
			result.setData(fileInfo);
		}catch (Exception e) {
			result.setStatus(Response.Status.FAILURE);
			logger.error("save file failed ! busiType = " + busiType, e);
		} 
		return result;
	}
	
	
	@ApiOperation(value="通过文件id删除接口")
	@ApiImplicitParams({@ApiImplicitParam(name="ids",value="文件id列表[\"fileId1\",\"fileId2\",\"fileId3\",....]")})
//	@RequiresPermissions("fileInfo:delete")
	@RequestMapping(value = "/delete",method = { RequestMethod.DELETE })
//	public ResultForm delete(@RequestBody List<String> ids,@RequestParam String timestamp,@RequestParam String signature){
	public Response<List<String>> delete(@RequestBody List<String> ids){
		Response<List<String>> result = new Response<>();
//		if(!verification(fileId,timestamp,signature)){
//			result.setFailForm(Status.VERIFY_FAIL,"验签失败");
//			return result;
//		}
		int success=0;
		List<String> failureIds = new ArrayList<>();
 		for (String id : ids) {
			if(fileInfoService.deleteFileByFileId(id)){
				success++;
			}else{
				failureIds.add(id);
			}
		}
		if(failureIds.size()>0){
			result.setMsg("删除成功"+success+"条"+"/"+"删除失败"+failureIds.size()+"条"+"——原因：这些文件不存在，data为删除失败文件的id的列表");
			result.setData(failureIds);
		}else{
//			result.setFailForm(Status.VERIFY_FAIL,"文件不存在");
			result.setMsg("删除成功"+success+"条");
		}
		
		return result;
		
	}
	
	@ApiOperation(value="通过文件id下载单个文件")
	@RequestMapping(value = "/downLoadOne",method = { RequestMethod.GET })
	public String downLoadOne(@RequestParam(name="fileId") String id,HttpServletResponse response) throws UnsupportedEncodingException{
		FileInfo fileInfo = fileInfoMapper.selectById(id);
		if(fileInfo !=null){
			String path = storagePath+fileInfo.getFullPath();
			//针对paths下载
			File file = new File(path);
			if(file != null && file.exists()){
				String fileName = fileInfo.getOriginalName(); // 文件的默认保存名
				// 设置响应头和客户端保存文件名
				response.reset();
				response.setCharacterEncoding("utf-8");
//				response.setContentType("multipart/form-data");
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
		        try {
		        	// 将目标文件读到流中
		        	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));// 文件的存放路径
		        	OutputStream os = response.getOutputStream();
		        	// 循环取出流中的数据
		        	byte[] b = new byte[2048];
		        	int len;
		            while ((len = bis.read(b)) > 0){
		            	os.write(b, 0, len);
		            }
		            os.close();
		            bis.close();
		            logger.info("下载成功");
		        } catch (IOException e) {
		            logger.error("下载失败",e);
		        } 
			}
		}
		return null;
	}
	
	
	@ApiOperation(value="打包下载多个文件")
	@ApiImplicitParams({@ApiImplicitParam(name="fileIds",value="多个文件Id的字符串"),@ApiImplicitParam(name="name",value="打包下载后的zip文件名")})
	@RequestMapping(value = "/downLoadZip",method = { RequestMethod.GET })
	public void downLoadZip(@RequestParam(name="fileIds") List<String> ids,String name,HttpServletResponse response) throws IOException{
		String downLoadZipName = name+".zip";
		List<FileInfo> fileInfos = fileInfoMapper.selectBatchIds(ids);
		List<String> filePaths = new ArrayList<>();
		for (FileInfo fileInfo : fileInfos) {
			filePaths.add(storagePath+fileInfo.getFullPath());
		}
		
		//压缩文件存放路径
		String zipName = "temp.zip";
		String zipFilePath = storagePath+zipName;
		
		//压缩文件
        File zip = new File(zipFilePath);  
        if (!zip.exists()){     
            zip.createNewFile();     
        }
        //创建zip文件输出流  
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
        this.zipFile(filePaths,zos);
        zos.close();
        
        response.setContentType("text/html; charset=UTF-8"); //设置编码字符  
        response.setContentType("application/octet-stream"); //设置内容类型为下载类型  
        response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(downLoadZipName, "UTF-8"));//设置下载的压缩文件名称
        OutputStream out = response.getOutputStream();   //创建页面返回方式为输出流，会自动弹出下载框 
        
        
        //将打包后的文件写到客户端，输出的方法同上，使用缓冲流输出
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(zipFilePath)); //创建文件缓冲输入流 
        byte[] buff = new byte[bis.available()];//从输入流中读取不受阻塞
        bis.read(buff);//读取数据文件
        bis.close();
        out.write(buff);//输出数据文件
        out.flush();//释放缓存
        out.close();//关闭输出流
        
	}
	
	
	/**
     * @param filePaths 需要压缩的文件路径集合
	 * @param zos 临时压缩文件输出流
	 * @return
	 * @throws IOException
	 */
	private String zipFile(List<String> filePaths,ZipOutputStream zos) throws IOException { 
		//循环读取文件路径集合，获取每一个文件的路径  
		for(String filePath : filePaths){ 
			File inputFile = new File(filePath); //根据文件路径创建文件
			if(inputFile.exists()) { //判断文件是否存在  
				if (inputFile.isFile()) { //判断是否属于文件，还是文件夹 
					//创建输入流读取文件  
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile)); 
					//将文件写入zip内，即将文件进行打包  
					zos.putNextEntry(new ZipEntry(inputFile.getName())); 
					//写入文件的方法，同上                  
					int size = 0; 
					byte[] buffer = new byte[1024]; //设置读取数据缓存大小			 
					while ((size = bis.read(buffer)) > 0) { 
						zos.write(buffer, 0, size); 
					} 
					//关闭输入输出流  
					zos.closeEntry(); 
					bis.close(); 
				} else { 
					//如果是文件夹，则使用穷举的方法获取文件，写入zip  
					try { 
						File[] files = inputFile.listFiles(); 
						List<String> filePathsTem = new ArrayList<String>(); 
						for (File fileTem:files) { 
							filePathsTem.add(fileTem.toString()); 
						} 
						return zipFile(filePathsTem,zos); 
					} catch (Exception e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
		} 
		return null; 
	} 
	
//	@ApiOperation(value="文件查询接口")
//	@ApiImplicitParams({
////		@ApiImplicitParam(name = "isAPP",value = "是否是APP端调用(必填,默认为true)", defaultValue = "true"),
//		@ApiImplicitParam(name = "busiType",value = "业务类型(选填,若传了业务类型，则返回单一数据列表)"),
//		@ApiImplicitParam(name = "fileName",value = "关键字查询，匹配文件名，原始文件名")
//	})
//	@GetMapping("/fileList")
//	public ResultForm fileList(@RequestParam Map params,BasePage page){
//		ResultForm result = new ResultForm();
//		result.setData(fileInfoService.getFileByParams(params, page));
//		return result;
//	}
		
	
	/*
	 * 清理脏数据的接口
	 * 1、删除没有关联到相关中间表的文件（只上传了文件没有做业务关联）
	 */
	@ApiOperation(value="删除-未关联业务中间表的文件")
	@ApiImplicitParam(name = "isForceDel", value= "是否强制删除（本地不存在该文件也删除）")
	@GetMapping("/cleanUselessFile")
	public Response cleanUselessFile(@RequestParam(defaultValue = "false") Boolean isForceDel){
		List<String> delIds = fileInfoMapper.getUselessIds();
		if(isForceDel != true || isForceDel == null){
			return delete(delIds);
		}
		int num = 0;
		for (String fileId : delIds) {
			if(fileInfoService.deleteFileByFileIdForce(fileId))num++;
		}
		Response result = new Response();
		result.setMsg("强制删除成功"+num+"条");
		return result;
	}
	
	/*
	 * 清理脏数据的接口,根据fileName清除掉:
	 * 2、数据库中没有，而本地有的文件（文件数据未删除，刷库会发生）
	 */
	@ApiOperation(value="删除-库中没有但本地有的文件")
	@DeleteMapping("/cleanFileNotInDB")
	public Response cleanFileNotInDB(){
		Response result = new Response();
		result.setData(fileInfoService.deleteFileNotInDB());
		return result;
	}
	
	/**
	 * 清理脏数据的接口：数据中有，本地没有的文件
	 * @return
	 */
	@ApiOperation(value="删除-库中有但本地没有的文件")
	@DeleteMapping("/cleanFileNotInDisk")
	public Response cleanFileNotInDisk(){
		Response result = new Response();
		result.setData(fileInfoService.deleteFileNotInDisk());
		return result;
	}

	@ApiOperation(value="获取图片文件url")
	@GetMapping("/getPicFromUrl")
	public byte[] getPicFromUrl(){
		URL url = null;
		byte[] data = null;
		try {
			url = new URL("http://120.202.98.222:9003/yuxing/61619820/2019/06/61619820_1_20190616170200.jpg");
			URLConnection connection = url.openConnection();
			InputStream inStream = connection.getInputStream();
			data = readInputStream(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://120.202.98.222:9003/yuxing/61619820/2019/06/61619820_1_20190616170200.jpg");
		URLConnection connection = url.openConnection();
		InputStream inStream = connection.getInputStream();
		byte[] data = readInputStream(inStream);

		File imageFile = new File("D:/BeautyGirl.jpg");
		// 创建输出流
		FileOutputStream outStream = new FileOutputStream(imageFile);
		// 写入数据
		outStream.write(data);
		// 关闭输出流
		outStream.close();
		System.out.println("完成！");
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
}

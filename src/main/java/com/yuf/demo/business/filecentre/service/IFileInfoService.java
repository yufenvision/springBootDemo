package com.yuf.demo.business.filecentre.service;

import com.yuf.demo.business.filecentre.entity.FileInfo;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author yuf
 * @since 2019-01-27
 */
public interface IFileInfoService extends IService<FileInfo> {
	/**
     * 保存文件信息到数据库
     * @param fileInfo
     * @return 文件id
     */
    String saveFile(FileInfo fileInfo);

    /**
     * 通过id查询文件
     *
     * @param fileId
     * @return
     */
    FileInfo getFileByFileId(String fileId);

    /**
     * 通过文件id删除数据库中文件表和文件中间表信息(本地文件不存在删除失败)，以及本地文件
     * @param fileId
     * @return
     */
    boolean deleteFileByFileId(String fileId);

    
    /**
     * 强制删除数据库中文件表和文件中间表信息（本地文件不存在也删除），以及本地文件
     * @param fileId
     * @return
     */
    boolean deleteFileByFileIdForce(String fileId);
    /**
     * 通过查询条件获取文件列表
     *
     * @param params
     * @return
     */
//    BasePage getFileByParams(Map params,BasePage page);

    /**
     * 删除没有在数据库中的本地文件
     * @return
     */
    List<String> deleteFileNotInDB();
    
    /**
     * 将文件存入本地磁盘
     * @param multiFile 
     * @param busiType 文件类型
     * @return 文件信息对象
     */
    FileInfo saveFile2Disk(MultipartFile multiFile, String busiType);

    
    
    /**
     * 将excel文件存入本地磁盘与数据库
     * @param file excel文件，（只能读取第一个sheet的图片）
     * @param busiType 图片所属业务类型
     * @return 返回excel图片所在行数，与数据库中该图片文件信息
     */
    Map<Integer,List<FileInfo>> saveExcelPic(MultipartFile file, String busiType);
}

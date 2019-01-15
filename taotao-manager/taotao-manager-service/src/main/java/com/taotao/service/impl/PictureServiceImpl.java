package com.taotao.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USER}")
	private String FTP_USER;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_IMAGE_BASE_PATH}")
	private String FTP_IMAGE_BASE_PATH;
	@Value("${FTP_IMAGE_BASE_URL}")
	private String FTP_IMAGE_BASE_URL;

	@Override
	public Map uoloadPicture(MultipartFile uploadFile) {
		Map<Object, Object> resultMap = new HashMap<>();
		// 生成新文件名
		// 要去原始文件名
		String oldName = uploadFile.getOriginalFilename();
		String newName = IDUtils.genImageName();
		newName = newName + oldName.substring(oldName.lastIndexOf("."));
		InputStream inputStream = null;
		try {
			inputStream = uploadFile.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String imagePath = new DateTime().toString("/yyyy/MM/dd");
		boolean b = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER, FTP_PASSWORD, FTP_IMAGE_BASE_PATH, imagePath,
				newName, inputStream);
		if (!b) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败");
		} else {
			resultMap.put("error", 0);
			resultMap.put("url", FTP_IMAGE_BASE_URL + imagePath + "/" + newName);
		}
		System.out.println(resultMap);
		return resultMap;
	}

}

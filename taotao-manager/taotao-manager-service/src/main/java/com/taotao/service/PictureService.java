package com.taotao.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	public Map uoloadPicture(MultipartFile uploadFile);
}

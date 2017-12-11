package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * 上传图片处理
 * <p>Title: PictureController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年9月4日下午3:18:33
 * @version 1.0
 */
@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		Map result = pictureService.uploadPicture(uploadFile);
		//为了保证功能的兼容性，需要把Result转换成json格式的字符串。
		String json = JsonUtils.objectToJson(result);
		return json;
	}

//	@RequestMapping("/pic/upload")
//	@ResponseBody
//	public Map picUpload(MultipartFile uploadFile){
//		try{
//			//接收上传的文件
//			//取扩展名
//			String originalFilename = uploadFile.getOriginalFilename();
//			//取出不带点的后缀扩展名
//			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//
//			//上传到图片服务器
//			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
//			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
//			url = IMAGE_SERVER_URL+url;
//			//响应上传图片的url
//			Map result = new HashMap();
//			result.put("error", 0);
//			result.put("url", url);
//			return result;
//		}catch (Exception e){
//			e.printStackTrace();
//			Map result = new HashMap();
//			result.put("error", 1);
//			result.put("message", "图片上传失败");
//			return result;
//		}
//
//	}
}

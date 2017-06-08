package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.utills.FastDFSClient;
import cn.e3mall.utills.JsonUtils;

/**
 * 
 * <p>Title: PictureController</p>
 * <p>Description:图片上传动作类</p>
 * @version 1.0
 */ 
@Controller
public class PictureController {
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")// 文件上传的路径
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile){
		//把图片上传到服务器中,得到一个地址,和文件名
		try {
			FastDFSClient fastDFSClient=new FastDFSClient("classpath:conf/client.conf");
			//取文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);//不包含.
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			
			// 补充完整url 路径返回给,相应的kingEditorParams 让他做判断
			url=IMAGE_SERVER_URL+url;
			Map result=new HashMap<>();
			result.put("error", 0);
			result.put("url", url);
			return JsonUtils.objectToJson(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			Map result=new HashMap<>();
			result.put("error", 1);
			result.put("message", "照片上传失败");
			return JsonUtils.objectToJson(result);
		}
	}
}

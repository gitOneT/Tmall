package cn.e3mal.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.utills.FastDFSClient;

/**
 * 
 * <p>Title: FastDfsTest</p>
 * <p>Description:图片上传的测试类 </p>
 * @version 1.0
 */
public class FastDfsTest {
	///@Test  图片路径需要换一下
	public  void UploadPic() throws Exception{
		//创建一个配置文件，文件名称随意，内容就是tracker的服务器地址。
		//使用全局对象加载配置文件
		ClientGlobal.init("E:/WorkSpace/work-ssm/e3-manager-web/src/main/resources/conf/client.conf");
		//创建一个trackerClient对象.
		TrackerClient trackerClient = new TrackerClient();
		//通过一个trackerClient对象,获取trackServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个storageServer的引用.可以是null.
		StorageServer storageServer=null;
		//创建一个StorageClient ,参数需要TrackerServer 和StorageServer
		StorageClient client=new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传对象.
		String[] file = client.upload_appender_file("F:/UiPic图片/125.jpg", "jpg", null);
		for (String string : file) {
			System.out.println(string);
		}
	}
	
	/**
	 * 测试图片上传工具类
	 * @throws Exception 
	 */
	//@Test
	public void uploadUilsTest() throws Exception{
		FastDFSClient client=new FastDFSClient("E:/WorkSpace/work-ssm/e3-manager-web/src/main/resources/conf/client.conf");
		String string = client.uploadFile("F:/UiPic图片/timg (9).jpg");
		System.out.println(string);
		
	}

}

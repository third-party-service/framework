package com.jzg.framework.dfs.fastdfs;

import org.apache.commons.io.IOUtils;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.csource.fastdfs.StorageServer;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by JZG on 2016/11/29.
 */
public class TestFileManagerTest {
    /**
     * 测试文件上传
     *
     * @throws Exception
     */
    @Test
    public void upload() throws Exception {
        File content = new File("D:\\1.jpg");

        FileInputStream fis = new FileInputStream(content);
        byte[] file_buff = null;


        file_buff = IOUtils.toByteArray(fis);

        /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] tmp = new byte[2048];
        int count = 0;
        while ((count = fis.read(tmp)) != -1) {
            outputStream.write(tmp, 0, count);
        }
        file_buff = outputStream.toByteArray();*/

        DFSFile file = new DFSFile("521", file_buff, "jpg");

        String fileAbsolutePath = DFSFileManager.upload(file);
        System.out.println(fileAbsolutePath);
        fis.close();
    }

    @Test
    public void testString() {
        String strServers = "192.168.0.149:22122,192.168.0.150:22122";
        String[] arrServers = new String[]{strServers};
        System.out.println(arrServers);
    }

    /**
     * 测试删除文件
     *
     * @throws Exception
     */
    @Test
    public void delFile() throws Exception {
        DFSFileManager.deleteFile("group1", "M00/00/02/wKgBzFctj7CAd7AnAA1rIuRd3Es290.jpg");

        FileInfo file = DFSFileManager.getFile("group1", "M00/00/02/wKgBzFctj7CAd7AnAA1rIuRd3Es290.jpg");
        System.out.println(file);
    }

    /**
     * 测试获取文件信息
     *
     * @throws Exception
     */
    @Test
    public void getFile() throws Exception {
        FileInfo file = DFSFileManager.getFile("group1", "M00/00/00/wKgBlFcfFFGAMXzoAA1rIuRd3Es390.jpg");
        Assert.assertNotNull(file);
        String sourceIpAddr = file.getSourceIpAddr();
        long size = file.getFileSize();
        System.out.println("ip:" + sourceIpAddr + ",size:" + size);
    }


    @Test
    public void getStorageServer() throws Exception {
        StorageServer[] ss = DFSFileManager.getStoreStorages("group1");
        Assert.assertNotNull(ss);

        for (int k = 0; k < ss.length; k++) {
            System.err.println(k + 1 + ". " + ss[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + ss[k].getInetSocketAddress().getPort());
        }
    }

    @Test
    public void getFetchStorages() throws Exception {
        ServerInfo[] servers = DFSFileManager.getFetchStorages("group1", "M00/00/00/wKgBlFcfFFGAMXzoAA1rIuRd3Es390.jpg");
        Assert.assertNotNull(servers);

        for (int k = 0; k < servers.length; k++) {
            System.err.println(k + 1 + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
        }
    }
}

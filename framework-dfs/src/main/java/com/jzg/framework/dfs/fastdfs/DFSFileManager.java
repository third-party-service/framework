package com.jzg.framework.dfs.fastdfs;


import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;


/**
 * Created by JZG on 2016/11/29.
 */
public final class DFSFileManager {
    /**
     * logger日志
     */
    private static Logger logger = LoggerFactory.getLogger(DFSFileManager.class);

    /**
     * 默认端口
     */
    private static final String DEFAULT_TRACKER_NGNIX_PORT = "80";

    private DFSFileManager() {

    }

    static {
        // 初始化FastDFS客户端配置
        try {
            if (StringUtils.isEmpty(DefaultGlobal.clientConfigFilePath)) {
                ClientGlobal.init(DefaultGlobal.CLIENT_CONFIG_FILE);
            } else {
                ClientGlobal.init(DefaultGlobal.clientConfigFilePath + "/" + DefaultGlobal.CLIENT_CONFIG_FILE);
            }

            TrackerGroup trackerGroup = ClientGlobal.getG_tracker_group();

            if (logger.isDebugEnabled()) {
//                logger.debug("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 上传文件至FASTDFS
     *
     * @param file
     * @return 上传文件Url
     */
    public static String upload(DFSFile file) {
        String fileAbsolutePath = "";
        TrackerClient trackerClient = null;
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient = null;

        if (logger.isDebugEnabled()) {
            logger.debug("File Name: " + file.getName() + "		File Length: " + file.getContent().length);
        }

        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("width", StringUtils.isEmpty(file.getHeight()) ? DefaultGlobal.FILE_DEFAULT_HEIGHT : file.getWidth());
        meta_list[1] = new NameValuePair("heigth", StringUtils.isEmpty(file.getWidth()) ? DefaultGlobal.FILE_DEFAULT_WIDTH : file.getHeight());
        meta_list[2] = new NameValuePair("author", StringUtils.isEmpty(file.getAuthor()) ? DefaultGlobal.FILE_DEFAULT_AUTHOR : file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);

            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);

            if (uploadResults == null || uploadResults.length < 2) {
                logger.error("upload file fail, error code: " + storageClient.getErrorCode());
            } else {
                String groupName = uploadResults[0];
                String remoteFileName = uploadResults[1];

                if (logger.isDebugEnabled()) {
                    logger.debug("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:"
                            + " " + remoteFileName);
                }

                fileAbsolutePath = getFileUrl(trackerServer.getInetSocketAddress().getHostName(), groupName, remoteFileName);
            }
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
        }

        return fileAbsolutePath;
    }

    /**
     * 上传文件至FASTDFS
     *
     * @param file
     * @return 上传文件Url
     */
    public static String upload(DFSFile file, String readServer) {
        String fileAbsolutePath = "";
        TrackerClient trackerClient = null;
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient = null;

        if (logger.isDebugEnabled()) {
            logger.debug("File Name: " + file.getName() + "		File Length: " + file.getContent().length);
        }

        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("width", StringUtils.isEmpty(file.getHeight()) ? DefaultGlobal.FILE_DEFAULT_HEIGHT : file.getWidth());
        meta_list[1] = new NameValuePair("heigth", StringUtils.isEmpty(file.getWidth()) ? DefaultGlobal.FILE_DEFAULT_WIDTH : file.getHeight());
        meta_list[2] = new NameValuePair("author", StringUtils.isEmpty(file.getAuthor()) ? DefaultGlobal.FILE_DEFAULT_AUTHOR : file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);

            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);

            if (uploadResults == null || uploadResults.length < 2) {
                logger.error("upload file fail, error code: " + storageClient.getErrorCode());
            } else {
                String groupName = uploadResults[0];
                String remoteFileName = uploadResults[1];

                if (logger.isDebugEnabled()) {
                    logger.debug("upload file successfully!!!  " + "group_name: " + groupName + ", remoteFileName:"
                            + " " + remoteFileName);
                }

                fileAbsolutePath = getFileUrl(readServer, groupName, remoteFileName);
            }
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
        }

        return fileAbsolutePath;
    }


    /**
     * 上传文件
     *
     * @param file
     * @return 返回groupName及remoteFileName
     */
    public static String[] uploadFile(DFSFile file) {
        if (logger.isDebugEnabled()) {
            logger.debug("File Name: " + file.getName() + "		File Length: " + file.getContent().length);
        }

        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        StorageClient storageClient = null;

        NameValuePair[] meta_list = new NameValuePair[3];
        meta_list[0] = new NameValuePair("width", StringUtils.isEmpty(file.getHeight()) ? DefaultGlobal.FILE_DEFAULT_HEIGHT : file.getWidth());
        meta_list[1] = new NameValuePair("heigth", StringUtils.isEmpty(file.getWidth()) ? DefaultGlobal.FILE_DEFAULT_WIDTH : file.getHeight());
        meta_list[2] = new NameValuePair("author", StringUtils.isEmpty(file.getAuthor()) ? DefaultGlobal.FILE_DEFAULT_AUTHOR : file.getAuthor());

        long startTime = System.currentTimeMillis();
        String[] uploadResults = null;
        try {
            trackerServer = trackerClient.getConnection();
            storageClient = new StorageClient(trackerServer, storageServer);

            uploadResults = storageClient.upload_file(file.getContent(), file.getExt(), meta_list);
            if (uploadResults == null) {
                logger.error("upload file fail, error code: " + storageClient.getErrorCode());
            }
        } catch (IOException e) {
            logger.error("IO Exception when uploadind the file: " + file.getName(), e);
        } catch (Exception e) {
            logger.error("Non IO Exception when uploadind the file: " + file.getName(), e);
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");
        }
        return uploadResults;
    }

    /**
     * 通过group及remoteFileName获取Url
     *
     * @param uploadResults [0] groupName
     *                      [1] remoteFileName
     * @return
     */
    public static String getFileUrl(String[] uploadResults) {
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];

        return getFileUrl(groupName, remoteFileName);
    }

    /**
     * 通过group及remoteFileName获取Url
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static String getFileUrl(String groupName, String remoteFileName) {
        String fileAbsolutePath = "";
        TrackerServer trackerServer = null;

        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            fileAbsolutePath = getFileUrl(trackerServer.getInetSocketAddress().getHostName(), groupName, remoteFileName);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("get file url successfully!!!  " + "group_name: " + groupName + ", remoteFileName:"
                    + " " + remoteFileName);
        }
        return fileAbsolutePath;
    }

    /**
     * 获取完整URL
     *
     * @param hostName       跟踪服务器
     * @param groupName      组名
     * @param remoteFileName 远程文件名
     * @return
     */
    private static String getFileUrl(String hostName, String groupName, String remoteFileName) {
        String fileAbsolutePath = "";
        if (DEFAULT_TRACKER_NGNIX_PORT.equals(String.valueOf(ClientGlobal.getG_tracker_http_port()))) {
            fileAbsolutePath = String.format("%s%s/%s/%s", DefaultGlobal.PROTOCOL, hostName
                    , groupName
                    , remoteFileName);
        } else {
            fileAbsolutePath = String.format("%s%s:%s/%s/%s", DefaultGlobal.PROTOCOL, hostName
                    , ClientGlobal.getG_tracker_http_port()
                    , groupName
                    , remoteFileName);
        }
        return fileAbsolutePath;
    }


    /**
     * 通过groupName和remoteFileName获取完整URL
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            FileInfo fileInfo = storageClient.get_file_info(groupName, remoteFileName);

            return fileInfo;
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 删除上传文件
     *
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception {
        TrackerServer trackerServer = null;
        StorageServer storageServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            storageClient.delete_file(groupName, remoteFileName);
        } catch (IOException e) {
            logger.error("IO Exception: Get File from Fast DFS failed", e);
        } catch (Exception e) {
            logger.error("Non IO Exception: Get File from Fast DFS failed", e);
        } finally {
            if (storageServer != null) {
                try {
                    storageServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }


    /**
     * 获取存储节点
     *
     * @param groupName
     * @return
     */
    public static StorageServer[] getStoreStorages(String groupName) {
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            return trackerClient.getStoreStorages(trackerServer, groupName);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * 获取存储节点
     *
     * @param groupName
     * @param remoteFileName
     * @return
     */
    public static ServerInfo[] getFetchStorages(String groupName, String remoteFileName) {
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
        } catch (Exception ex) {
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } finally {
            if (trackerServer != null) {
                try {
                    trackerServer.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }
}

/*
 * Create Author  : dsfan
 * Create Date    : 2016年9月12日
 * File Name      : HdfsClient.java
 */

package com.baifendian.swordfish.common.hadoop;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * hdfs 操作类
 * <p>
 * 
 * @author : dsfan
 * @date : 2016年9月12日
 */
public class HdfsClient implements Closeable {

    /** LOGGER */
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsClient.class);

    /** HdfsClient 实例 */
    private static volatile HdfsClient instance;

    /** {@link FileSystem} */
    private FileSystem fileSystem;

    /**
     * @param conf
     * @throws HdfsException
     */
    private HdfsClient(Configuration conf) throws HdfsException {
        try {
            fileSystem = FileSystem.get(conf);
        } catch (IOException e) {
            throw new HdfsException("创建 HdfsClient 实例失败", e);
        }
    }

    /**
     * 初始化，仅需调用一次
     * <p>
     *
     * @param conf
     */
    public static void init(Configuration conf) {
        if (instance == null) {
            synchronized (HdfsClient.class) {
                if (instance == null) {
                    instance = new HdfsClient(conf);
                }
            }
        }
    }

    /**
     * 获取 HdfsClient 实例 （单例）
     * <p>
     * 
     * @return {@link HdfsClient}
     * @throws HdfsException
     */
    public static HdfsClient getInstance() throws HdfsException {
        if (instance == null) {
            LOGGER.error("获取 HdfsClient 实例失败，请先调用 init(Configuration conf) 方法初始化");
            throw new HdfsException("获取 HdfsClient 实例失败，请先调用 init(Configuration conf) 方法初始化");
        }
        return instance;
    }

    /**
     * 添加文件到 HDFS 指定目录
     * <p>
     *
     * @param fileName
     *            文件名称
     * @param content
     *            文件内容
     * @param destPath
     *            目标目录
     * @param isOverwrite
     *            当目标文件已经不存在时，是否覆盖
     * @throws HdfsException
     */
    public void addFile(String fileName, byte[] content, String destPath, boolean isOverwrite) throws HdfsException {
        LOGGER.debug("Begin addFile. fileName:" + fileName + ", path:" + destPath);
        // 创建目标文件路径
        String destFile;
        if (destPath.charAt(destPath.length() - 1) != '/') {
            destFile = destPath + "/" + fileName;
        } else {
            destFile = destPath + fileName;
        }

        // 判断文件是否存在
        Path path = new Path(destFile);
        try {
            // 文件已经存在
            if (fileSystem.exists(path)) {
                if (isOverwrite) { // 覆盖
                    fileSystem.delete(path, false);// 先删除目标文件
                } else { // 不覆盖的情况
                    LOGGER.error("File " + destFile + " already exists");
                    return;
                }
            }
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }

        try (
                FSDataOutputStream out = fileSystem.create(path);
                InputStream in = new BufferedInputStream(new ByteArrayInputStream(content));) {
            byte[] b = new byte[1024];
            int numBytes = 0;
            while ((numBytes = in.read(b)) > 0) {
                out.write(b, 0, numBytes);
            }
            out.hflush();
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }
        LOGGER.debug("End addFile. fileName:" + fileName + ", path:" + destPath);
    }

    /**
     * 从 hdfs 读取文件内容，并写入到本地文件中
     * <p>
     *
     * @param hdfsFile
     *            hdfs 文件路径
     * @param localFile
     *            本地 文件路径
     * @param overwrite
     *            是否覆盖已存在的本地文件
     * @throws HdfsException
     */
    public void readFile(String hdfsFile, String localFile, boolean overwrite) throws HdfsException {

        // 文件路径
        Path pathObject = new Path(hdfsFile);
        File fileObject = new File(localFile);
        try {
            // 判断 hdfs 文件是否合法
            if (fileSystem.exists(pathObject)) {
                if (fileSystem.isDirectory(pathObject)) { // 是目录的情况
                    LOGGER.error(hdfsFile + " 是目录");
                    throw new HdfsException("hdfs 路径是个目录");
                }
            } else {
                LOGGER.error(hdfsFile + " 不存在");
                throw new HdfsException("hdfs 文件不存在");
            }

            // 不覆盖的情况下，已经存在的文件，则不处理
            if (!overwrite && fileObject.exists()) {
                LOGGER.info(localFile + " 已经存在，不覆盖");
                return;
            }

            // 目标文件的目录不存在时，需要创建相关的目录
            File parentPath = fileObject.getParentFile();
            if (parentPath != null && !parentPath.exists()) {
                FileUtils.forceMkdir(parentPath);
            }
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }

        try (
                FSDataInputStream in = fileSystem.open(pathObject);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(fileObject));) {
            byte[] b = new byte[1024];
            int numBytes = 0;
            while ((numBytes = in.read(b)) > 0) {
                out.write(b, 0, numBytes);
            }
            out.flush();
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }
    }

    /**
     * 从 hdfs 读取文件内容
     * <p>
     *
     * @param hdfsFile
     *            hdfs 文件路径
     * @return byte[]
     * @throws HdfsException
     */
    public byte[] readFile(String hdfsFile) throws HdfsException {

        // 文件路径
        Path pathObject = new Path(hdfsFile);
        try {
            // 判断 hdfs 文件是否合法
            if (fileSystem.exists(pathObject)) {
                if (fileSystem.isDirectory(pathObject)) { // 是目录的情况
                    LOGGER.error(hdfsFile + " 是目录");
                    throw new HdfsException("hdfs 路径是个目录");
                }
            } else {
                LOGGER.error(hdfsFile + " 不存在");
                throw new HdfsException("hdfs 文件不存在");
            }
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }

        try (
                FSDataInputStream in = fileSystem.open(pathObject);) {
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            LOGGER.error("操作 Hdfs 异常", e);
            throw new HdfsException("操作 Hdfs 异常", e);
        }
    }

    /**
     * 删除目录或文件
     * <p>
     *
     * @param path
     *            目录和文件路径
     * @param recursive
     *            当路径表示目录时，是否递归删除子目录
     * @return 是否删除成功
     * @throws HdfsException
     */
    public boolean delete(String path, boolean recursive) throws HdfsException {
        Path pathObject = new Path(path);
        try {
            return fileSystem.delete(pathObject, recursive);
        } catch (IOException e) {
            LOGGER.error("删除路径异常", e);
            throw new HdfsException("删除路径异常", e);
        }
    }

    /**
     * 重命名目录或文件
     * <p>
     *
     * @param path
     *            目录和文件路径
     * @param destPath
     *            目标路径
     * @return 是否重命名成功
     * @throws HdfsException
     */
    public boolean rename(String path, String destPath) throws HdfsException {
        Path pathObject = new Path(path);
        Path destPathObject = new Path(destPath);
        try {
            return fileSystem.rename(pathObject, destPathObject);
        } catch (IOException e) {
            LOGGER.error("重命名路径异常", e);
            throw new HdfsException("重命名路径异常", e);
        }
    }

    /**
     * 创建目录
     * 
     * @param dir
     * @throws HdfsException
     */
    public void mkdir(String dir) throws HdfsException {
        Path path = new Path(dir);
        try {
            if (fileSystem.exists(path)) {
                LOGGER.error("Dir {} already not exists", dir);
                return;
            }
            fileSystem.mkdirs(path);
        } catch (IOException e) {
            LOGGER.error("创建目录异常", e);
            throw new HdfsException("创建目录异常", e);
        }
    }

    /**
     * copy 一个文件到另一个目标文件
     * <p>
     *
     * @param srcPath
     *            源文件
     * @param dstPath
     *            目标文件
     * @param deleteSource
     *            是否删除源文件
     * @param overwrite
     *            是否覆盖目标文件
     * @return 是否成功
     * @throws HdfsException
     */
    public boolean copy(String srcPath, String dstPath, boolean deleteSource, boolean overwrite) throws HdfsException {
        Path srcPathObj = new Path(srcPath);
        Path dstPathObj = new Path(dstPath);
        try {
            return FileUtil.copy(fileSystem, srcPathObj, fileSystem, dstPathObj, deleteSource, overwrite, fileSystem.getConf());
        } catch (IOException e) {
            LOGGER.error("copy 异常", e);
            throw new HdfsException("copy 异常", e);
        }
    }

    /**
     * 获取文件或目录的逻辑空间大小（单位为Byte）
     * <p>
     *
     * @param filePath
     * @return 文件或目录的大小
     */
    public long getFileLength(String filePath) {
        return getContentSummary(filePath).getLength();
    }

    public boolean exists(String filePath) throws IOException {
        return fileSystem.exists(new Path(filePath));
    }

    /**
     * 获取文件或目录的状态信息
     * <p>
     *
     * @param filePath
     * @return {@link FileStatus}
     */
    public FileStatus getFileStatus(String filePath) {
        Path path = new Path(filePath);
        try {
            return fileSystem.getFileStatus(path);
        } catch (IOException e) {
            LOGGER.error("获取文件状态异常", e);
            throw new HdfsException("获取文件状态异常", e);
        }
    }

    /**
     * 获取目录下的文件列表
     * <p>
     *
     * @param filePath
     * @return {@link FileStatus}
     */
    public FileStatus[] listFileStatus(String filePath) {
        Path path = new Path(filePath);
        try {
            return fileSystem.listStatus(new Path(filePath));
        } catch (IOException e) {
            LOGGER.error("获取文件列表异常", e);
            throw new HdfsException("获取文件列表异常", e);
        }
    }

    /**
     * 获取文件或目录的内容概要（包括逻辑空间大小、物理空间大小等）
     * <p>
     *
     * @param filePath
     * @return {@link ContentSummary}
     */
    public ContentSummary getContentSummary(String filePath) {
        Path path = new Path(filePath);
        try {
            return fileSystem.getContentSummary(path);
        } catch (IOException e) {
            LOGGER.error("获取文件的内容概要异常", e);
            throw new HdfsException("获取文件的内容概要异常", e);
        }
    }

    /**
     * 获取 hdfs url地址
     * <p>
     *
     * @return url 地址
     */
    public String getUrl() {
        return fileSystem.getUri().toString();
    }

    @Override
    public void close() throws HdfsException {
        if (fileSystem != null) {
            try {
                fileSystem.close();
            } catch (IOException e) {
                LOGGER.error("关闭 HdfsClient 实例失败", e);
                throw new HdfsException("关闭 HdfsClient 实例失败", e);
            }
        }
    }

    /**
     * 得到 hdfs 空间总容量以及剩余容量信息
     *
     * @return
     * @throws IOException
     */
    public FsStatus getCapacity() throws IOException {
        FsStatus ds = fileSystem.getStatus();
        return ds;
    }
}

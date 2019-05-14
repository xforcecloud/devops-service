package io.choerodon.devops.infra.common.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;
import io.choerodon.core.exception.CommonException;
import io.choerodon.devops.domain.application.valueobject.HighlightMarker;
import io.choerodon.devops.domain.application.valueobject.InsertNode;
import io.choerodon.devops.domain.application.valueobject.ReplaceMarker;
import io.choerodon.devops.domain.application.valueobject.ReplaceResult;
import io.codearte.props2yaml.Props2YAML;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.parser.ParserImpl;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.resolver.Resolver;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by younger on 2018/4/13.
 */
public class FileUtilEx {
    private static final Logger logger = LoggerFactory.getLogger(FileUtilEx.class);

    private FileUtilEx() {
    }

    /**
     * 从文件夹中查找指定文件 非深度优先遍历
     */
    public static File queryFileFromFiles(File file, String fileName) {
        //assume the file is dir
        LinkedList<File> dirs = new LinkedList<>();
        dirs.addLast(file);
        while (!dirs.isEmpty()) {
            File dir = dirs.pollFirst();
            File[] files = dir.listFiles();
            if(files != null) {
                for (File file1 : files) {
                    if(file1 != null) {
                        if (file1.isDirectory()) {
                            //stack in
                            dirs.addLast(file1);
                        } else {
                            //check current dir
                            if (file1.getName().equals(fileName)) {
                                return file1;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}

package cn.wendale.examples;

import cn.wendale.base.SimHash;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.io.Files.readLines;
import static java.lang.String.format;

/**
 * Created with IntelliJ IDEA.
 * User: yang.wenjun
 * Date: 2017/5/15
 * Time: 上午10:58
 * Description:
 */


public class CorpusUniqBySimHash {

    // 原始语料，已分词，空白符号分割
    private String filePath;

    // 去重后的语料
    private String outPath;

    // 被过滤掉的语料
    private String dupPath;

    private static final Logger LOGGER = LoggerFactory.getLogger(CorpusUniqBySimHash.class);

    public CorpusUniqBySimHash(String filePath, String outPath, String dupPath) {
        this.filePath = filePath;
        this.outPath = outPath;
        this.dupPath = dupPath;
    }


    public void process() throws IOException {
        final Writer writer = Files.newWriter(new File(outPath), UTF_8);
        final Writer dupWriter = Files.newWriter(new File(dupPath), UTF_8);
        final SimHash<String> corpusSimHash = new SimHash<>();
        readLines(new File(filePath), UTF_8, new LineProcessor<Object>() {
            private Integer processed = 0;

            private Integer duplicate = 0;

            @Override
            public boolean processLine(String line) throws IOException {
                processed++;
                if (StringUtils.isBlank(line)) {
                    return true;
                }

                String[] items = line.split("\\s");
                List<String> itemList = Lists.newArrayList(items);


                if (itemList.size() == 0) {
                    return true;
                }
                if (corpusSimHash.checkAndAdd(itemList)) {
                    dupWriter.write(line + "\n");
                    duplicate++;
                } else {
                    writer.write(line + "\n");
                }

                if (processed % 1000000 == 0) {
                    LOGGER.info(format("处理了%d行, 其中重复%d行", processed, duplicate));
                }
                return true;
            }

            @Override
            public Object getResult() {
                return null;
            }
        });
        writer.close();
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.printf("usage: <cmd> <filePath> <outPath> <ducpPath>");
            System.exit(-1);
        }
        new CorpusUniqBySimHash(args[0], args[1], args[2]).process();
        LOGGER.info("Done!");
    }
}

package org.examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class VideoStreamService {

    public static final String VIDEO = "/video";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";
    public static final String CONTENT_RANGE = "Content-Range";
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    public static final String BYTES = "bytes";
    public static final int CHUNK_SIZE = 314700;
    public static final int BYTE_RANGE = 1024;
    private final Logger logger = LoggerFactory.getLogger(VideoStreamService.class);

    /**
     * Prepare the content.
     *
     * @param fileName String.
     * @param fileType String.
     * @param requestedRange    String.
     * @return ResponseEntity.
     */
    public ResponseEntity<byte[]> prepareContent(final String fileName, final String fileType, final String requestedRange) {

        try {
            final String fileKey = fileName + "." + fileType;

            final Long fileSize = getFileSize(fileKey);
            Range range=parseRequestedRange(requestedRange,fileSize);

            final byte[] data = readByteRangeNew(fileKey, range.start(), range.end());
            final String contentLength = String.valueOf((range.end() - range.start()) + 1);
            HttpStatus httpStatus = HttpStatus.PARTIAL_CONTENT;
            if (range.end() >= fileSize) {
                httpStatus = HttpStatus.OK;
            }
            return ResponseEntity.status(httpStatus)
                    .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                    .header(ACCEPT_RANGES, BYTES)
                    .header(CONTENT_LENGTH, contentLength)
                    .header(CONTENT_RANGE, BYTES + " " + range.start() + "-" + range.end() + "/" + fileSize)
                    .body(data);
        } catch (IOException e) {
            logger.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    Range parseRequestedRange(String requestedRange,long fileSize){
        long rangeStart = 0,rangeEnd = Math.min(fileSize,CHUNK_SIZE);
        if(StringUtils.hasText(requestedRange)){
            return new Range(rangeStart,rangeEnd);
        }
        String[] ranges = requestedRange.split("-");

        rangeStart = Long.parseLong(ranges[0].substring(6));
        if (ranges.length > 1) {
            rangeEnd = Long.parseLong(ranges[1]);
        } else {
            rangeEnd = rangeStart + CHUNK_SIZE;
        }

        rangeEnd = Math.min(rangeEnd, fileSize - 1);
        return new Range(rangeStart,rangeEnd);
    }
    /**
     * ready file byte by byte.
     *
     * @param filename String.
     * @param start    long.
     * @param end      long.
     * @return byte array.
     * @throws IOException exception.
     */
    public byte[] readByteRangeNew(String filename, long start, long end) throws IOException {
        Path path = Paths.get(getFilePath(), filename);
        byte[] data = Files.readAllBytes(path);
        byte[] result = new byte[(int) (end - start) + 1];
        System.arraycopy(data, (int) start, result, 0, (int) (end - start) + 1);
        return result;
    }


    public byte[] readByteRange(String filename, long start, long end) throws IOException {
        Path path = Paths.get(getFilePath(), filename);
        try (InputStream inputStream = (Files.newInputStream(path));
             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
            byte[] data = new byte[BYTE_RANGE];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                bufferedOutputStream.write(data, 0, nRead);
            }
            bufferedOutputStream.flush();
            byte[] result = new byte[(int) (end - start) + 1];
            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
            return result;
        }
    }

    /**
     * Get the filePath.
     *
     * @return String.
     */
    private String getFilePath() {
        URL url = this.getClass().getResource(VIDEO);
        assert url != null;
        return new File(url.getFile()).getAbsolutePath();
    }

    /**
     * Content length.
     *
     * @param fileName String.
     * @return Long.
     */
    public Long getFileSize(String fileName) {
        return Optional.ofNullable(fileName)
                .map(file -> Paths.get(getFilePath(), file))
                .map(this::sizeFromFile)
                .orElse(0L);
    }

    /**
     * Getting the size from the path.
     *
     * @param path Path.
     * @return Long.
     */
    private Long sizeFromFile(Path path) {
        try {
            return Files.size(path);
        } catch (IOException ioException) {
            logger.error("Error while getting the file size", ioException);
        }
        return 0L;
    }
    static record Range (long start,long end){

    }
}
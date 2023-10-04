package org.examples.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class VideoStreamService {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String VIDEO_CONTENT = "video/";
    public static final String CONTENT_RANGE = "Content-Range";
    public static final String ACCEPT_RANGES = "Accept-Ranges";
    public static final String BYTES = "bytes";
    public static final long CHUNK_SIZE = DataSize.ofKilobytes(255).toBytes();
    final String BASE_DIR = "d:\\tmp\\";

    private final Logger logger = LoggerFactory.getLogger(VideoStreamService.class);

    public ResponseEntity<byte[]> prepareContent(final String fileName, final String fileType, final String requestedRange) throws IOException {

        final String fileKey = fileName + "." + fileType;
        Path pathFile = getFilePath(fileKey);
        final Long fileSize = getFileSize(pathFile);
        Range range = parseRequestedRange(requestedRange, fileSize);
        final long contentLength = range.end() - range.start() + 1;
        HttpStatus httpStatus = HttpStatus.PARTIAL_CONTENT;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream((int) contentLength);

        try (final InputStream fileInputStream = Files.newInputStream(pathFile, StandardOpenOption.READ)) {
            StreamUtils.copyRange(fileInputStream, outputStream, range.start(), range.end());
        }
        return ResponseEntity.status(httpStatus)
                .header(CONTENT_TYPE, VIDEO_CONTENT + fileType)
                .header(ACCEPT_RANGES, BYTES)
                .header(CONTENT_LENGTH, String.valueOf(contentLength))
                .header(CONTENT_RANGE, BYTES + " " + range.start() + "-" + range.end() + "/" + fileSize)
                .body(outputStream.toByteArray());
    }

    Range parseRequestedRange(String requestedRange, long fileSize) {
        long rangeStart = 0, rangeEnd = Math.min(fileSize, CHUNK_SIZE);
        if (!StringUtils.hasText(requestedRange)) {
            return new Range(rangeStart, rangeEnd);
        }

        String[] ranges = requestedRange.split("-");
        rangeStart = Long.parseLong(ranges[0].substring(6));
        if (ranges.length > 1) {
            rangeEnd = Long.parseLong(ranges[1]);
        } else {
            rangeEnd = rangeStart + CHUNK_SIZE;
        }
        rangeEnd = Math.min(rangeEnd, fileSize - 1);
        return new Range(rangeStart, rangeEnd);
    }

    private Path getFilePath(String fileName) {
        return Paths.get(BASE_DIR, fileName);
    }

    public Long getFileSize(Path pathFile) throws IOException {
        return Files.size(pathFile);
    }

    record Range(long start, long end) {
    }
}
package com.fitanywhere.coursedetail.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

@Service
public class CourseVideoUtil {

    @Autowired
    private ResourceLoader loader;
    @Autowired
    private  CourseDetailRepository repository;
    @Autowired
    private CourseDetailServiceImpl courseDetailSvc;


    public ResponseEntity<StreamingResponseBody> getPartialVideo(String rangeHeader, Integer cdId, Integer sale) throws IOException {
        StreamingResponseBody responseStream;

        String filePathString = null;

        if (sale == 1){
            filePathString = courseDetailSvc.getSaleVideoPath(cdId);
        }else if (sale == 2){
            filePathString = courseDetailSvc.getCourseVideoPath(cdId);
        }

        File videoFile = new File(filePathString);

        Long fileSize = videoFile.length();
        byte[] buffer = new byte[5 * 1024 * 1024];
        HttpHeaders responseHeaders = new HttpHeaders();
        if(rangeHeader == null){
            responseHeaders.add("Content-Type", "video/mp4");
            responseHeaders.add("Content-Length", fileSize.toString());
            responseStream = outputStream -> {
                RandomAccessFile file = new RandomAccessFile(videoFile, "r");
                try(file){
                    long pos = 0;
                    file.seek(pos);
                    while(pos < fileSize - 1){
                        file.read(buffer);
                        outputStream.write(buffer);
                        pos += buffer.length;
                    }
                    outputStream.flush();
                }catch (Exception e){
                }
            };
            return new ResponseEntity<StreamingResponseBody>(responseStream, responseHeaders, HttpStatus.OK);
        }

        String[] ranges = rangeHeader.split("-");
        Long rangeStart = Long.parseLong(ranges[0].substring(6));
        Long rangeEnd;
        if(ranges.length > 1){
            rangeEnd = Long.parseLong(ranges[1]);
        }else{
            rangeEnd = fileSize - 1;
        }

        if(fileSize < rangeEnd){
            rangeEnd = fileSize - 1;
        }

        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        responseHeaders.add("Content-Type", "video/mp4");
        responseHeaders.add("Content-Length", contentLength);
        responseHeaders.add("Accept-Ranges", "bytes");
        responseHeaders.add("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize);
        Long newRangeEnd = rangeEnd;
        responseStream = outputStream -> {
          RandomAccessFile file = new RandomAccessFile(videoFile, "r");
          try(file){
                long pos = rangeStart;
                file.seek(pos);
                while (pos < newRangeEnd){
                    file.read(buffer);
                    outputStream.write(buffer);
                    pos += buffer.length;
                }
                outputStream.flush();
          } catch (Exception e){}
        };
       return new ResponseEntity<StreamingResponseBody>(responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
    }
}
package com.fitanywhere.course.model;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private ResourceLoader loader;

	@Override
	public ResponseEntity<StreamingResponseBody> getPartialVideo(String rangeHeader, String videoId)
			throws IOException {
		StreamingResponseBody responseStream;
		// 這兩行取代我的第29行 (因為我影片是先放在static資料夾裡, 所以得這樣拿到路徑)
		String filePathString = videoId;
		File videoFile = new File(filePathString);  

//		File videoFile = loader.getResource("videoPath").getFile();
		Long fileSize = videoFile.length();
		byte[] buffer = new byte[1024];
		HttpHeaders responseHeaders = new HttpHeaders();
		// 這段IF是代表第一次撥放影片 (所以header裡面還沒有range的資料)
		if (rangeHeader == null) {
			responseHeaders.add("Content-Type", "video/mp4");
			responseHeaders.add("Content-Length", fileSize.toString());
			responseStream = outputStream -> {
				// 基礎Java學到的InputStrea/OutputStream都是循序IO
				// 但影片我們要去抓分段的資料讀進來後回傳給前端, 所以用可以移動位置讀取的RandomAccessFile
				RandomAccessFile file = new RandomAccessFile(videoFile, "r");
				try (file) {
					long pos = 0;
					file.seek(pos);
					while (pos < fileSize - 1) {
						file.read(buffer);
						outputStream.write(buffer);
						// 累加讀取過的資料量
						pos += buffer.length;
					}
					outputStream.flush();
				} catch (Exception e) {
				}
			};
			return new ResponseEntity<StreamingResponseBody>(responseStream, responseHeaders, HttpStatus.OK);
		}
		// 以下為如果rangeHeader有內容, 就是代表影片分段傳送完畢, 瀏覽器會再跟Server繼續請求下一段資料
		String[] ranges = rangeHeader.split("-");
		Long rangeStart = Long.parseLong(ranges[0].substring(6));
		Long rangeEnd;
		if (ranges.length > 1) {
			rangeEnd = Long.parseLong(ranges[1]);
		} else {
			rangeEnd = fileSize - 1;
		}

		if (fileSize < rangeEnd) {
			rangeEnd = fileSize - 1;
		}

		String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
		responseHeaders.add("Content-Type", "video/mp4");
		// 告訴瀏覽器這次傳輸的資料量有多少
		responseHeaders.add("Content-Length", contentLength);
		responseHeaders.add("Accept-Ranges", "bytes");
		// 讓瀏覽器知道這次的資料範圍介於哪裡到哪裡
		responseHeaders.add("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize);
		Long newRangeEnd = rangeEnd;
		responseStream = outputStream -> {
			RandomAccessFile file = new RandomAccessFile(videoFile, "r");
			try (file) {
				long pos = rangeStart;
				file.seek(pos);
				while (pos < newRangeEnd) {
					file.read(buffer);
					outputStream.write(buffer);
					pos += buffer.length;
				}
				outputStream.flush();
			} catch (Exception e) {
			}
		};
		// HttpStatus.PARTIAL_CONTENT 代表 Status code: 206
		// 所以瀏覽器就會知道到資料傳送完後, 就會再繼續跟Server請求下一段影片資料
		return new ResponseEntity<StreamingResponseBody>(responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);

	}

}

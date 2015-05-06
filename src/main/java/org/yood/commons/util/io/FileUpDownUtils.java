package org.yood.commons.util.io;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.yood.commons.util.TextUtils;

public class FileUpDownUtils {

	public static final String UPLOAD_PATH = "upload/timesheet";
	public static final String PROJECT_PATH = "tsc";

	private FileUpDownUtils() {
	}


	public static String saveFileToServer(MultipartFile file,
			HttpServletRequest request, String uploadPath)
			throws IllegalStateException, IOException {
		if (!file.isEmpty()) {
			String dir = getContextUploadPath(request, uploadPath);
			String fileName = file.getOriginalFilename();
			fileName = TextUtils.makeUuid()
					+ fileName.substring(fileName.lastIndexOf("."));
			File saveFile = new File(dir, fileName);
			file.transferTo(saveFile);
			ImageUtils.compressImageByWidth(saveFile.getAbsolutePath(),
					saveFile.getAbsolutePath(), 250);
			String result = saveFile.getAbsolutePath();
			result = result.substring(
					result.lastIndexOf(PROJECT_PATH) + PROJECT_PATH.length()
							+ 1).replace("\\", "/");
			return result;
		}
		return null;
	}

	/**
	 * 获取上下文上传路径
	 *
	 * @param request
	 * @param uploadPath
	 * @return
	 */
	public static String getContextUploadPath(HttpServletRequest request,
			String uploadPath) {
		String path = request.getSession().getServletContext()
				.getRealPath(uploadPath == null ? UPLOAD_PATH : uploadPath);
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir.getAbsolutePath();
	}

	public static boolean isFileSizeOverflow(MultipartFile file, long maxSize) {
		return file.getSize() > maxSize;
	}
}

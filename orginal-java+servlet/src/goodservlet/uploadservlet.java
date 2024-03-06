package goodservlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class historygoodservlet
 */
@WebServlet("/uploadservlet")
@MultipartConfig
public class uploadservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "./img";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public uploadservlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String getFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
    
    private String randomString(int length) {
	    String allowedChars = "0123456789abcdefghijklmnopqrstuvwxyz";
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        sb.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
	    }
	    return sb.toString();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = getFileName(filePart);
		String fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();

		// 确保文件是png或jpg格式
		if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg")) {
			System.out.print("在前端");
		}

		// 使用长度为20的只包含数字或字符的随机字符串作为文件名
		String filePath =  randomString(20) + fileExtension;
        filePart.write(UPLOAD_DIR + "/" + filePath);
        response.getWriter().write(filePath);
    }

    


}

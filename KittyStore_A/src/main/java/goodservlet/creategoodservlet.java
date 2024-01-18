package goodservlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.swing.JOptionPane;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class creatgoodservlet
 */
@MultipartConfig //非常重要
public class creategoodservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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

	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}


	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");	
			HttpSession session = request.getSession();    
			user u = (user)session.getAttribute("admin");	
			String  goodname = request.getParameter("goodname");
		 	String  description = request.getParameter("description");
		 	String priceStr = request.getParameter("price");
		// 	System.out.print(priceStr );
		 	double price =0.0;
		 	try {
		 	    price = Double.parseDouble(priceStr);
		 	    System.out.print(price);
		 	} catch (NumberFormatException e) {
		 	    try {
		 	        price = (double) Integer.parseInt(priceStr);
		 	    } catch (NumberFormatException ex) {
		 	    	System.out.println("error");
  	 	    	    request.getRequestDispatcher("upload_goods.jsp").forward(request,response); 
//				    return;
		 	    }
		 	}
//		 // 获取上传的文件
//			Part filePart = request.getPart("picture"); // 与<input type="file" name="picture">中的name相对应
//			String fileName = getFileName(filePart);
//			String fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
//
//			// 确保文件是png或jpg格式
//			if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg")) {
//				System.out.print("在前端");
//			}
//
//			// 使用长度为20的只包含数字或字符的随机字符串作为文件名
//			
//			
//			String filePath =  randomString(20) + fileExtension;
//
//			// 将文件保存到./img文件夹
//			File uploads = new File(getServletContext().getRealPath("./img"));
//			File file = new File(uploads, filePath);
//			try (InputStream input = filePart.getInputStream()) {
//				Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//			}
//			String picture = "./img/"+filePath;
//			
            
		        Collection<Part> fileParts = request.getParts(); // 获取所有上传的文件
		        List<String> fileNames = new ArrayList<>(); // 用于存储所有文件的路径
		        for (Part filePart : fileParts) {
		            if (getFileName(filePart) != null && getFileName(filePart).length() > 0) {
		                String fileName = getFileName(filePart); // 获取上传的文件名
		                String extension = fileName.substring(fileName.lastIndexOf(".")); // 获取文件的扩展名
		                String randomFileName = randomString(20) + extension; // 生成随机文件名
		                String uploadPath = getServletContext().getRealPath("./img") + File.separator + randomFileName; // 设置上传路径
		                File fileUploadDirectory = new File(getServletContext().getRealPath("./img"));
		                if (!fileUploadDirectory.exists()) {
		                    fileUploadDirectory.mkdirs();
		                }
		                filePart.write(uploadPath); // 保存文件
		                System.out.print(uploadPath);
		                fileNames.add("./img" + File.separator + randomFileName); // 将文件路径添加到列表
		            }
		        }
		        String picture = String.join(",", fileNames);
	        int state = 0;
	        String numberStr = request.getParameter("number");
	        int number = Integer.parseInt(numberStr);
	        String kind = request.getParameter("kind");
		 	String subkind = request.getParameter("subkind");
		 // 检查subkind是否为空或空字符串
		 	if (subkind == null || subkind.isEmpty()) {
		 	    // 如果subkind为空或空字符串，则提供一个默认值
		 	    subkind = "默认子类"; // 这里可以设置为你想要的默认值
		 	}
	        int owner = u.getUserid();//userid为owner
	        if(null==picture) {
	        	picture="./img/buyer/food-1.png";//设置默认值
	        }
	        good g=new good();
	        goodsql gs = new goodsqlimpl();
			good gf = null;
			Map<String, Boolean> map = new HashMap<>();
	        try {
	        	//检测商品名称是否唯一
				if(gs.unique(goodname)==1) {
					gf=new good();
					gf.setGoodname(goodname);
					gf.setDescription(description);
					gf.setPrice(price);
					gf.setPicture(picture);
					gf.setState(state);
					gf.setNumber(number);
					gf.setKind(kind);
					gf.setSubkind(subkind);
					gf.setOwner(owner);
					gs.add(gf);
					List<good> gList = null;
	       			 try {
	       					gList = gs.showall(u.getUserid());
	       			 } catch (SQLException e) {
	       				e.printStackTrace();
	       			 }
	       			session.setAttribute("gL", gList);
	       			//System.out.println("pic:.."+gf.getPicture());
					//request.getRequestDispatcher("show_goods.jsp").forward(request,response); 
	       			map.put("isUnique", true);
				}
				else {
					//注释的代码与前端script联系，出现提示框，但是这样操作会引发sqlite多线程锁定
//					boolean isDuplicate = true;
//					request.setAttribute("isDuplicate", isDuplicate);
//		 	    	System.out.println("11error");
					session.setAttribute("err","请勿上传重名商品！");
					session.setAttribute("to","upload_goods");
				    //request.getRequestDispatcher("error.jsp").forward(request,response); 
				    map.put("isUnique", false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("y78y");
				e.printStackTrace();
			}

	     // 创建一个StringBuilder对象
	     StringBuilder json = new StringBuilder();

	     // 将map转换为JSON格式的字符串
	     json.append("{");
	     for (Map.Entry<String, Boolean> entry : map.entrySet()) {
	         json.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue()).append(",");
	     }
	     json.deleteCharAt(json.length() - 1);  // 删除最后一个逗号
	     json.append("}");

	     // 设置响应的内容类型为JSON
	     response.setContentType("application/json");

	     // 获取响应的输出流
	     PrintWriter out = response.getWriter();

	     // 将JSON字符串写入响应
	     out.print(json.toString());

	     // 关闭输出流
	     out.flush();

		
	}

}

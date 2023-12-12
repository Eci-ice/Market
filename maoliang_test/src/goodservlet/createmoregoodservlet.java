package goodservlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
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

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;
import vo.user;

/**
 * Servlet implementation class creategoodservletabc
 */
@MultipartConfig
public class createmoregoodservlet extends HttpServlet {
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
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	request.setCharacterEncoding("UTF-8");
	    	response.setCharacterEncoding("UTF-8");
	    	HttpSession session = request.getSession();    
	    	user u = (user)session.getAttribute("admin");
	    	String productListStr = request.getParameter("productList");
	    	System.out.print(productListStr);
	    	
	    	Type listType = new TypeToken<ArrayList<good>>(){}.getType();
	    	List<good> productList = new Gson().fromJson(productListStr, listType);

	    	// 创建数据库访问实例
	    	goodsql gs = new goodsqlimpl();

	    	// 处理每个商品信息
	    	for (good good : productList) {
	    		try {
		    	    String goodname = good.getGoodname();
		    	    String description = good.getDescription();
		    	    double price = good.getPrice();
		    	    // 获取上传的文件
		            Part filePart = request.getPart(good.getPicture()); // 与<input type="file" name="picture">中的name相对应
		            String fileName = getFileName(filePart);
		            String fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();

		            // 确保文件是png或jpg格式
		            if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg")) {
		                System.out.print("在前端");
		            }

		            // 使用长度为20的只包含数字或字符的随机字符串作为文件名
		            String filePath =  randomString(20) + fileExtension;

		            // 将文件保存到./img文件夹
		            File uploads = new File(getServletContext().getRealPath("./img"));
		            File file = new File(uploads, filePath);
		            try (InputStream input = filePart.getInputStream()) {
		                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		            }
		            String picture = "./img/"+filePath;
	
		            int state = 0;
			        String numberStr = request.getParameter("number");
			        int number = Integer.parseInt(numberStr);
		            String kind = good.getKind();
		            String subkind  = good.getSubkind();
		            int owner = u.getUserid();
		            
		            good gf = new good();
		            if(gs.unique(goodname)==1) {//对于商品名重复仍需修改
		            gf.setGoodname(goodname);
		            gf.setDescription(description);
		            gf.setPrice(price);
		            gf.setPicture(picture);
		            gf.setState(state);
		            gf.setNumber(number);
		            gf.setKind(kind);
		            gf.setSubkind(subkind);
		            gf.setOwner(owner);
		            }
		            // 插入商品信息到数据库
		            
	                gs.add(gf);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        // 获取所有商品的列表
	        List<good> allGoodsList = null;
	        try {
	            allGoodsList = gs.showall(u.getUserid());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        // 设置商品列表到会话
	        session.setAttribute("gL", allGoodsList);
//	        session.setAttribute("gL", gList);
	        // 重定向到商品展示页面
//	        response.sendRedirect("show_goods.jsp");
	        request.getRequestDispatcher("show_goods.jsp").forward(request, response);
	    }

}

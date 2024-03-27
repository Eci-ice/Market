package goodservlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
	    		String goodname = good.getGoodname();
	    		try {
					if(gs.unique(goodname)==0) {
						request.setAttribute("err","请勿上传重名商品：" + goodname + "！");
					    request.setAttribute("to","upload_moregoods");
					    request.getRequestDispatcher("error.jsp").forward(request,response);
					    return;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	for (good good : productList) {
	    		try {
		    	    String goodname = good.getGoodname();
		    	    String description = good.getDescription();
		    	    double price = good.getPrice();
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
			        int number = good.getNumber();
		            String kind = good.getKind();
		            String subkind  = good.getSubkind();
		            int owner = u.getUserid();
		            
		            good gf = new good();
		            
			            gf.setGoodname(goodname);
			            gf.setDescription(description);
			            gf.setPrice(price);
			            gf.setPicture(picture);
			            gf.setState(state);
			            gf.setNumber(number);
			            gf.setKind(kind);
			            gf.setSubkind(subkind);
			            gf.setOwner(owner);
		            
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

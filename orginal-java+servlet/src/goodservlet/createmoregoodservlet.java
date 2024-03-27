package goodservlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
import java.nio.file.Paths;
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

		    // 获取所有商品的信息
		    String[] goodnames = request.getParameterValues("goodname[]");
		    String[] prices = request.getParameterValues("price[]");
		    String[] descriptions = request.getParameterValues("description[]");
		    String[] numbers = request.getParameterValues("number[]");
		    String[] kinds = request.getParameterValues("kind[]");
		    String[] subkinds = request.getParameterValues("subkind[]");

		    // 获取所有上传的文件
		    List<Part> fileParts = request.getParts().stream().filter(part -> "mediaFile[]".equals(part.getName())).collect(Collectors.toList());
		    
		    // 创建数据库访问实例
		    goodsql gs = new goodsqlimpl();
		    
		    // 创建一个Map来存储响应数据
		    Map<String, Boolean> map = new HashMap<>();
		
		    
		    for (int i = 0; i < goodnames.length; i++) {
		        String goodname = goodnames[i];

		        // 检查是否存在同名商品
		        try {
					if(gs.unique(goodname)==0) {
					    session.setAttribute("err","请勿上传重名商品：" + goodname + "！");
					    session.setAttribute("to","upload_moregoods");
					    map.put("isUnique", false);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    // 处理每个商品信息
		 // 如果map中没有isUnique键，说明所有商品都是唯一的
		    if (!map.containsKey("isUnique")) {
		        map.put("isUnique", true);
			    try {
				    for (int i = 0; i < goodnames.length; i++) {
				        String goodname = goodnames[i];
				        System.out.print(goodname+" ");
				        String description = descriptions[i];
				        double price = Double.parseDouble(prices[i]);
				        int number = Integer.parseInt(numbers[i]);
				        String kind = kinds[i];
				        String subkind = subkinds[i];
		
				        // 处理上传的文件
				        Part filePart = fileParts.get(i);
				        String fileName = getFileName(filePart); // 获取上传的文件名
				        InputStream fileContent = filePart.getInputStream();
				        //处理文件内容
				        String extension = fileName.substring(fileName.lastIndexOf(".")); // 获取文件的扩展名
				        String randomFileName = randomString(20) + extension; // 生成随机文件名
				        String uploadPath = getServletContext().getRealPath("./img") + File.separator + randomFileName; // 设置上传路径
				        File fileUploadDirectory = new File(getServletContext().getRealPath("./img"));
				        if (!fileUploadDirectory.exists()) {
				            fileUploadDirectory.mkdirs();
				        }
				        filePart.write(uploadPath); // 保存文件
				        System.out.print(uploadPath);
				        String picture = "./img" + File.separator + randomFileName; // 将文件路径添加到列表
		
				        // 创建商品对象并设置属性
				        good gf = new good();
				        gf.setGoodname(goodname);
				        gf.setDescription(description);
				        gf.setPrice(price);
				        gf.setNumber(number);
				        gf.setKind(kind);
				        gf.setSubkind(subkind);
				        gf.setOwner(u.getUserid());
				        gf.setPicture(picture);
		
				        // 插入商品信息到数据库
				        gs.add(gf);
				    }
			    }catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    
			    // 获取所有商品的列表
			    List<good> allGoodsList= null;
				try {
					allGoodsList = gs.showall(u.getUserid());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			    // 设置商品列表到会话
			    session.setAttribute("gL", allGoodsList);
		    
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

package goodservlet;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import sql.goodsql;
import sqlimpl.goodsqlimpl;
import vo.good;

@MultipartConfig
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");		
		HttpSession session = request.getSession(); 	
		String  goodname = request.getParameter("goodname");
		String  description = request.getParameter("description");
		String priceStr = request.getParameter("price");
		double price =0.0;
		try {
			price = Double.parseDouble(priceStr);
			//System.out.print(price);
		} catch (NumberFormatException e) {
			try {
				price = (double) Integer.parseInt(priceStr);
			} catch (NumberFormatException ex) {
				System.out.println("error");
				request.getRequestDispatcher("upload_goods.jsp").forward(request,response); 
			}
		}

		
		int state = 0;
		int number = 1;
		String kind = "Maoliang";
		good g=new good();
		goodsql gs =new goodsqlimpl();
		good gf = null;
		try {
			if(gs.oldunique()==1) {
				gf=new good();
				gf.setGoodname(goodname);
				gf.setDescription(description);
				gf.setPrice(price);
				gf.setPicture("./img/buyer/food-1.jpg"); //默认
				gf.setState(state);
				gf.setNumber(number);
				gf.setKind(kind);
				gs.add(gf);
			
				// 获取上传的文件
				Part filePart = request.getPart("picture"); // 与<input type="file" name="picture">中的name相对应
				String fileName = getFileName(filePart);
				String fileExtension = fileName.substring(fileName.lastIndexOf('.')).toLowerCase();

				// 确保文件是png或jpg格式
				if (!fileExtension.equals(".png") && !fileExtension.equals(".jpg")) {
					System.out.print("在前端");
				}

				// 使用goodid作为文件名
				
				int goodid=-1;
				try {
					goodid = gs.search(goodname).getGoodid();// 生成goodid
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				String newFileName = Integer.toString(goodid) + fileExtension;

				// 将文件保存到./img文件夹
				File uploads = new File(getServletContext().getRealPath("./img"));
				File file = new File(uploads, newFileName);
				try (InputStream input = filePart.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				}
				gs.img(goodid, "./img/" + newFileName);
				
				List<good> gList = null;
				try {
					gList = gs.showall();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				session.setAttribute("gL", gList);
				//System.out.println("pic"+gf.getPicture());
				request.getRequestDispatcher("show_goods.jsp").forward(request,response); 
			}
			else {
				request.setAttribute("err","只能上架一个商品，请先下架当前商品！");
				request.setAttribute("to","upload_goods");
				request.getRequestDispatcher("error.jsp").forward(request,response);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.example.maoliang.Controller;


import com.example.maoliang.Controller.utils.Page;
import com.example.maoliang.Controller.utils.Result;
import com.example.maoliang.Entity.Good;
import com.example.maoliang.Entity.Usr;
import com.example.maoliang.Service.GoodService;
import com.example.maoliang.dto.Likedata;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.example.maoliang.Controller.utils.Page.*;

@RestController
@RequestMapping( "/cart/*")
public class CartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private GoodService goodService;
    @Autowired
    public HttpSession session;

    @RequestMapping("/modify-like-control")
    public Result modifyLike(@RequestBody Likedata likedata) {
//        int userId = currentUsr.getUserid();
//        if (currentUsr == null ) {
//            // 非法用户，跳转到错误页面
//            request.setAttribute("message", "非法用户");
//            return "error";
//        }
        // 根据请求参数执行相应的操作
        int goodId=likedata.getGoodId();
        int userId=likedata.getUserId();
        if ("0".equals(likedata.getIsCancel())) {
            // 收藏商品
            goodService.modifyLike(goodId, userId, 1); // 1 表示要添加收藏
            return new Result(SUCCESS_PAGE,"成功收藏","buyermain");
        } else {
            // 取消收藏
            goodService.modifyLike(goodId, userId, 0); // 0 表示要取消收藏
            return new Result(SUCCESS_PAGE,"成功取消收藏","buyermain");
        }
    }

    @RequestMapping("/buyer-cart-control")
    public Result showBuyerCart() {
        Usr currentUsr = (Usr) session.getAttribute("admin");
        if (currentUsr != null) {
            List<Good> goodsList = goodService.showBuyerAll(currentUsr.getUserid());
            return new Result(SUCCESS_PAGE,"成功取消收藏", goodsList);
        } else {
            return new Result(ERROR_PAGE,"请先登录", "login");
        }
    }

    @RequestMapping("/create-cart")//createcartservlet.java改写
    //通过 商品id与用户id去查询，有的话数量+1，没有的话，默认数量为1
    public Result createCart(@RequestParam("goodid") int goodId, @RequestParam("userid") int userid) throws SQLException {
//        try{这五个接口
//        Usr u = (Usr) session.getAttribute("admin");
        int buyingId = goodService.findCart(goodId, userid);
        if (buyingId == -1) {
            goodService.addToCart(goodId, userid);
        } else {
            goodService.modifyBuyNumber(buyingId, -1); // -1 indicates increment by 1
        }

//        List<Good> gList = goodService.showAllGoods(u.getUserid());
        return new Result(SUCCESS_PAGE, "成功添加到购物车",null);
//    } catch (SQLException e) {
//        LOGGER.error("创建错误", e);
//        return new Result(ERROR_PAGE,"创建出错，请稍后重试","creat");
//    }
    }

//    @GetMapping("/showLike")
//    public String showLike(Model model) {
//        Usr user = (Usr) session.getAttribute("admin");
//
//        try {
//            List<GoodList> likeList = goodService.showLike(user.getUserid());
//            model.addAttribute("likeList", likeList);
//            return Page.BUYER_LIKE;
//        } catch (SQLException e) {
//            // Handle exception
//            e.printStackTrace();
//            model.addAttribute("error", "Error retrieving liked items.");
//            return Page.ERROR_PAGE;
//        }
//    }
//    @PostMapping("/modifyCart")
//    public String modifyCart(@RequestParam("buyingid") int buyingid, @RequestParam("number") int number, Model model) {
//        Usr user = (Usr) session.getAttribute("admin");
//
//        try {
//            goodService.modifyBuyNumber(buyingid, number);
//            List<GoodList> cartList = goodService.showBuyerAll(user.getUserid());
//            model.addAttribute("cL", cartList);
//            return Page.BUYER_CART;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Error modifying cart.");
//            return Page.ERROR_PAGE;
//        }
//    }
//
//    @GetMapping("/allCart")
//    public String allCart(Model model) {
//        Usr user = (Usr) session.getAttribute("admin");
//
//        try {
//            List<GoodList> cartList = goodService.showBuyerAll(user.getUserid());
//            model.addAttribute("cL", cartList);
//            return Page.BUYER_CART;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            model.addAttribute("error", "Error retrieving buyer's cart.");
//            return Page.ERROR_PAGE;
//        }
//    }


//@GetMapping("/showLike")
//public String showLike(Model model) {
//    Usr user = (Usr) session.getAttribute("admin");
//
//    if (user != null) {
//        List<GoodList> likeList = goodService.showLike(user.getUserid());
//        model.addAttribute("likeList", likeList);
//        return Page.BUYER_LIKE;
//    } else {
//        model.addAttribute("error", "用户未登录或登录信息已过期");
//        return Page.ERROR_PAGE;
//    }
//}
// /////////////
//@GetMapping("/showLike")
//public String showLike(Model model) {
//    Usr user = (Usr) session.getAttribute("admin");
//
//    if (user != null) {
//        List<Good> likeList = goodService.showLike(user.getUserid());
//        List<GoodList> convertedList = new ArrayList<>();
//        for (Good good : likeList) {
//            GoodList goodList = new GoodList();
//            goodList.setG(good);
//            convertedList.add(goodList);
//        }
//        model.addAttribute("likeList", convertedList);
//        return Page.BUYER_LIKE;
//    } else {
//        model.addAttribute("error", "用户未登录或登录信息已过期");
//        return Page.ERROR_PAGE;
//    }
//}
////////////////////////////////
//@GetMapping("/showLike")
//public String showLike(Model model) {
//    Usr user = (Usr) session.getAttribute("admin");
//
//    if (user != null) {
//        List<Good> likeList = goodService.showLike(user.getUserid(), 1); // 获取islike等于1的商品信息
//        List<GoodList> convertedList = new ArrayList<>();
//        for (Good good : likeList) {
//            GoodList goodList = new GoodList();
//            goodList.setG(good);
//            convertedList.add(goodList);
//        }
//        model.addAttribute("likeList", convertedList);
//        return Page.BUYER_LIKE;
//    } else {
//        model.addAttribute("error", "用户未登录或登录信息已过期");
//        return Page.ERROR_PAGE;
//    }
//}

    @PostMapping("/showLike")//展示收藏的商品
    @ResponseBody
    public List<Map<String, Object>> showLike(@RequestParam("userId") int userId, Model model) {
        return goodService.showLike(userId, 1);
    }

    @RequestMapping("submit-selected-orders")
    public Result submitSelectedOrders(@RequestParam("selectedOrders") List<Integer> selectedOrders) {

        return new Result("success","提交购物车成功",null);

    }
//////////////////////////////这个是showLike第一版本
//    @GetMapping("/showLike")
//    public String showLike(Model model) {
//        Usr user = (Usr) session.getAttribute("admin");
//
////        try {
//            List<GoodList> likeList = goodService.showLike(user.getUserid());
//            model.addAttribute("likeList", likeList);
//            return Page.BUYER_LIKE;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            model.addAttribute("error", "无法获取收藏的商品。请稍后再试。");
//            return Page.ERROR_PAGE;
//        }
//    }
///////////////////////////zhuyaojie
//    @PostMapping("/modifyCart")
//    public String modifyCart(@RequestParam("buyingid") int buyingId, @RequestParam("number") int quantity, Model model) {
//        Usr user = (Usr) session.getAttribute("admin");

//        try {
//            goodService.modifyBuyNumber(buyingId, quantity);
//            List<GoodList> cartList = goodService.showBuyerAll(user.getUserid());
//            model.addAttribute("cL", cartList);
//            return Page.BUYER_CART;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            model.addAttribute("error", "无法修改购物车。请稍后再试。");
//            return Page.ERROR_PAGE;
//        }
//    }


    @GetMapping("/allCart")//展示购物车所有商品
    public ResponseEntity<List<Map<String, Object>>> getAllBuyerCart(@RequestParam("userId") int userId) {
        List<Map<String, Object>> buyerCart = goodService.showBuyerCart(userId);
        return ResponseEntity.ok(buyerCart);
    }

    @PostMapping("/addLike")//添加收藏
    public String addLike(@RequestParam("goodid") int goodid, @RequestParam("iscancel") String iscancel, @RequestParam("userid") int userid, Model model) throws SQLException {
        Usr user = (Usr) session.getAttribute("admin");

        if (user != null) {
            // User is not null, proceed with the logic
            if ("0".equals(iscancel)) {
                goodService.addToLike(goodid, userid);
                model.addAttribute("message", "成功收藏");
            } else {
                // 取消收藏
                goodService.cancelLike(goodid, userid);
                model.addAttribute("message", "成功取消收藏");
            }
            model.addAttribute("to", "buyermain");
            return Page.SUCCESS_PAGE;
        } else {
            // Handle the case where user is null
            model.addAttribute("error", "用户未登录或登录信息已过期");
            return Page.ERROR_PAGE;
        }
    }

    @PostMapping("/delete-cart-item")
    public String deleteCartItem(@RequestParam("buyingid") int buyingId, @RequestParam("userid") int userid, Model model) {
//        Usr user = (Usr) session.getAttribute("admin");
        goodService.removeBuyingItem(buyingId, userid);
        model.addAttribute("message", "购物车商品删除成功");
        model.addAttribute("to", "buyermain");
        return Page.SUCCESS_PAGE;
    }

}
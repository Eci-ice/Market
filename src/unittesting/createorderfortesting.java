package unittesting;

import java.sql.SQLException;
import java.util.List;

import sql.goodsql;
import sql.ordersql;
import sqlimpl.goodsqlimpl;
import sqlimpl.ordersqlimpl;
import vo.good;
import vo.order;

public class createorderfortesting {
    public String create(String address, String telephone, String buyername, String id) {
        int goodid = -1;
        if (address.isEmpty() || telephone.isEmpty() || buyername.isEmpty()) {
            return "fail1"; // 输入为空
        }

        if (telephone.length() != 11 || !telephone.matches("\\d+")) {
            return "fail7"; // 无效的电话号码，检查是否为十一位的数字电话
        }


        // 检查商品ID是否有效
        try {
            goodid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return "fail5"; // 无效的商品ID
        }

        if (goodid <= 0) {
            return "fail5"; // 无效的商品ID
        }

        // 查询商品信息
        goodsql gs = new goodsqlimpl();
        good g = null;
        try {
            g = gs.search(goodid);
        } catch (SQLException e) {
            e.printStackTrace();
            return "fail1"; // 数据库错误
        }

        if (g != null) {
            // 检查是否已存在相同订单
            if (orderAlreadyExists(address, telephone, buyername, goodid)) {
                return "fail8"; // 重复订单
            }

            ordersql ors = new ordersqlimpl();
            order orf = new order();
            orf.setAddress(address);
            orf.setTelephone(telephone);
            orf.setBuyername(buyername);
            orf.setGoodid(goodid);
            orf.setOrderstate(0); // 状态零：未成功订单

            try {
                ors.add(orf);
                List<order> orList = ors.showall();
                return "success";
            } catch (SQLException e) {
                e.printStackTrace();
                return "fail3"; // 数据库错误
            }
        } else {
            return "fail7"; // 请先下架当前商品，再进行发布
        }
    }

    // 检查是否已存在相同订单
    private boolean orderAlreadyExists(String address, String telephone, String buyername, int goodid) {
        ordersql ors = new ordersqlimpl();
        List<order> existingOrders;
        try {
            existingOrders = ors.showall();
            for (order existingOrder : existingOrders) {
                if (existingOrder.getAddress().equals(address) &&
                    existingOrder.getTelephone().equals(telephone) &&
                    existingOrder.getBuyername().equals(buyername) &&
                    existingOrder.getGoodid() == goodid) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

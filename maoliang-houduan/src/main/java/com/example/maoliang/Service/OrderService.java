package com.example.maoliang.Service;


import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.example.maoliang.Entity.Order;
import com.example.maoliang.Repository.OrderListRepository;
import com.example.maoliang.Repository.OrderRepository;
import com.unionpay.acp.sdk.SDKConstants;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.SDKConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.unionpay.acp.demo.BackRcvResponse.getAllRequestParam;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    OrderListRepository orderListRepository;
    @Autowired
    public HttpSession session;
    public void createOrder(Order order) {
        orderRepository.add(order);
    }

    public List<Order> showOrderInformation() {
        return orderListRepository.searchOrderInformation();
    }


    public void deletegood(int goodid) {
        orderRepository.deletegood(goodid);
    }

    public boolean deleteOrder(int orderid, int orderstate) {
        return orderRepository.deleteOrder(orderid, orderstate);
    }

    public boolean confirmOrder(int orderid, int orderstate) {
        return orderRepository.confirmOrder(orderid, orderstate);
    }

    public boolean buyerhistoryconfirmOrder(int orderid, int orderstate) {
        return orderRepository.buyerhistoryconfirmOrder(orderid, orderstate);
    }

    public List<Order> showbuyerorderinfo(String name) {
        return  orderListRepository.showbuyerorderinfo(name);
    }

    public List<Order> showbuyerhistoryorderinfo(String name) {
        return  orderListRepository.showbuyerhistoryorderinfo(name);
    }

    public String toaliPay(String subject, BigDecimal money) throws Exception {
        String TradeNo=this.generateTradeNo(subject);
        AlipayTradePagePayResponse pay = Factory.Payment.Page().pay("订单id："+subject, TradeNo ,
                String.valueOf(money), "http://localhost:3000/#/buyer-pay");
        session.setAttribute("now-TradeNo", TradeNo);
        String payForm = null;
        if (ResponseChecker.success(pay)) {
            payForm = pay.getBody();
        }
        return payForm;
    }

    public String toUnionPay(String orderid, BigDecimal money) throws Exception {
        SDKConfig.getConfig().loadPropertiesFromSrc(); // 从classpath加载acp_sdk.properties文件
        String orderId = generateTradeNo(orderid); // 生成商户订单号
        String txnAmt = money.multiply(new BigDecimal(100)).intValue() + ""; // 转为分
        //session.setAttribute("now-TradeNo", orderId);
        Map<String, String> reqData = new HashMap<>();
        reqData.put("version", "5.1.0");           // 版本号
        reqData.put("encoding", "UTF-8");          // 字符集编码
        reqData.put("signMethod", SDKConfig.getConfig().getSignMethod());           // 签名方法
        reqData.put("txnType", "01");              // 交易类型
        reqData.put("txnSubType", "01");           // 交易子类型
        reqData.put("bizType", "000201");          // 业务类型
        reqData.put("channelType", "07");          // 渠道类型
        reqData.put("merId", "777290058207983");   // 商户号
        reqData.put("accessType", "0");            // 接入类型
        reqData.put("orderId", orderId);           // 商户订单号
        reqData.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 订单发送时间
        reqData.put("txnAmt", txnAmt);             // 交易金额，单位分
        reqData.put("currencyCode", "156");        // 交易币种
        reqData.put("frontUrl", SDKConfig.getConfig().getFrontUrl()); // 前台通知地址
        reqData.put("backUrl", SDKConfig.getConfig().getBackUrl());   // 后台通知地址
        System.out.println("reqData"+reqData);
        Map<String, String> reqDataSigned = AcpService.sign(reqData, "UTF-8");
        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
//        Map<String, String> rspData = AcpService.post(reqDataSigned, requestFrontUrl, "UTF-8");
        return  AcpService.createAutoFormHtml(requestFrontUrl, reqDataSigned, "UTF-8");
    }

    public void backRcvResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String encoding = request.getParameter(SDKConstants.param_encoding);
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(request);

        //LogUtil.printRequestLog(reqParam);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {
            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext()) {
                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                value = new String(value.getBytes(encoding), encoding);
                valideData.put(key, value);
            }
        }

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            System.out.println("验证签名结果[失败].");
        } else {
            //LogUtil.writeLog("验证签名结果[成功].");
            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

            String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String orderSn =orderId; //orderId其实存的是Sn

            String respCode = valideData.get("respCode");

            System.out.println("valideData:"+valideData);
            if("00".equals(respCode)){  // 00 交易成功
                System.out.println("交易成功");
                String[] splitData = orderId.split("Z");
                int orderid = Integer.parseInt(splitData[0]);
                orderRepository.buyerhistoryconfirmOrder(orderid, 3);
                response.sendRedirect("http://localhost:3000/#/buyer-pay");
            }else if("A6".equals(respCode)){  // A6 部分成功
                System.out.println("部分成功");
            }

        }
        //返回给银联服务器http 200  状态码
        response.getWriter().print("ok");
    }

    private String generateTradeNo(String subject) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
        String tradeNo = subject+"Z"+LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return tradeNo;
    }
    public String queryOrderAliPaymentStatus(String tradeNo) {
        try {
            AlipayTradeQueryResponse response = Factory.Payment.Common().query(tradeNo);

            if (response != null && "10000".equals(response.code)) {
             //   System.out.println(response.buyerUserId);
                String tradeStatus = response.tradeStatus;
                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    // 支付成功
                    return "已支付";
                } else {
                    // 其他状态，例如：WAIT_BUYER_PAY、TRADE_CLOSED 等
                    return "未支付成功！";
                }
            } else {
                // 查询失败或未找到订单
                return "Failed to query order status";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception occurred while querying order status";
        }
    }

    public void successRedict(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:3000/#/buyer-pay");
    }

}

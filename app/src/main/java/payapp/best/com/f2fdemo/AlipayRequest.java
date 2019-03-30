package payapp.best.com.f2fdemo;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

import org.json.JSONException;
import org.json.JSONObject;

import static payapp.best.com.f2fdemo.AlipayConfig.ALIPAY_CLIENT;

public class AlipayRequest {

    /**
     * 生成支付预创建订单请求
     * @return 请求
     */
    public static AlipayTradePrecreateRequest OrderRequest(){

        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = "ting" + System.currentTimeMillis()
                + (long) (Math.random() * 10000000L);
        // （必填）订单总金额 ，默认单位为元，精确到小数点后两位，取值范围[0.01,100000000]
        String totalAmount = "0.01";
        //  （必填）订单标题
        String subject = "App当面付测试";
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天 (屁股后面的字母一定要带，不然报错)
        String timeoutExpress = "30m";
        //对交易或商品的描述
        String body = "这是一条测试订单";
        //  （必填）商户门店编号
        String storeId = "ZZZ_001";
        //（选填）卖家支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
        String sellerId = "2088102146225135";

        //创建API对应的request类
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        //设置订单参数
        request.setBizContent("{" +
                "     \"out_trade_no\":\""+ outTradeNo +"\"," +
                "     \"total_amount\":\"" + totalAmount +"\"," +
                "     \"subject\":\""+ subject +"\"," +
                "     \"timeout_express\":\""+ timeoutExpress +"\"," +
                "     \"body\":\""+ body +"\"," +
                "     \"store_id\":\""+ storeId +"\"" +
                " }");

        return request;
    }

    public static AlipayTradeQueryRequest QueryRequest(String outTradeNo){
        // 创建查询请求request
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        //设置查询参数
        request.setBizContent("{" +
                "     \"out_trade_no\":\""+ outTradeNo +"\"" +
                " }");
        return request;
    }

    // 轮询查询订单支付结果
    public static AlipayTradeQueryResponse loopQueryResult(AlipayTradeQueryRequest request) {
        AlipayTradeQueryResponse queryResult = null;
        for (int i = 0; i < AlipayConfig.getMaxQueryRetry(); i++) {
            sleep(AlipayConfig.getQueryDuration());

            try {
                AlipayTradeQueryResponse response = ALIPAY_CLIENT.execute(request);
                if (response != null) {
                    if (stopQuery(response)) {
                        return response;
                    }
                    queryResult = response;
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }

        }
        return queryResult;
    }

    // 判断是否停止查询
    private static boolean stopQuery(AlipayTradeQueryResponse response) {
//        System.out.println(response.getBody());
        try {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONObject object = jsonObject.getJSONObject("alipay_trade_query_response");
            if (AlipayConfig.SUCCESS.equals(object.getString("code"))) {
                if ("TRADE_FINISHED".equals(object.getString("trade_status")) ||
                        "TRADE_SUCCESS".equals(object.getString("trade_status")) ||
                        "TRADE_CLOSED".equals(object.getString("trade_status"))) {
                    // 如果查询到交易成功、交易结束、交易关闭，则返回对应结果
                    return true;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

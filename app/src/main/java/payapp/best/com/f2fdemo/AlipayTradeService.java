package payapp.best.com.f2fdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import static payapp.best.com.f2fdemo.AlipayConfig.ALIPAY_CLIENT;

public class AlipayTradeService extends Service {

    private Socket socket;
    private PrintWriter sendMsg;

    private SendMsgBinder mBinder = new SendMsgBinder();

    class SendMsgBinder extends Binder {

        //测试当面付2.0生成支付二维码
        public void test_trade_precreate() {
            new Thread(new PrecreateThread()).start();
        }

        //测试当面付2.0查询订单
        public void query_trade(String outTradeNo) {
            new Thread(new QueryThread(outTradeNo)).start();
        }
    }

    public AlipayTradeService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化客户端
        AlipayConfig.ALIPAY_CLIENT = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //请求支付宝返回订单二维码
    class PrecreateThread implements Runnable{

        public PrecreateThread(){
        }

        @Override
        public void run() {
            AlipayTradePrecreateResponse response = null;
            try {
                response = ALIPAY_CLIENT.execute(AlipayRequest.OrderRequest());
//                System.out.println(response.getBody());
                if (response.isSuccess()) {
                    System.out.println("调用成功");
                    JSONObject jsonObject = new JSONObject(response.getBody());
                    JSONObject aliObject = jsonObject.getJSONObject("alipay_trade_precreate_response");
                    String out_trade_no = aliObject.getString("out_trade_no");
                    String url = aliObject.getString("qr_code");
                    //发送广播
                    send_order_msg(out_trade_no, url);
                } else {
                    System.out.println("调用失败");
                }
            } catch (AlipayApiException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    //支付订单是否已被支付查询
    class QueryThread implements Runnable{

        private String outTradeNo;

        public QueryThread(String outTradeNo){
            this.outTradeNo = outTradeNo;
        }

        @Override
        public void run() {
            AlipayTradeQueryResponse response = AlipayRequest.loopQueryResult(AlipayRequest.QueryRequest(outTradeNo));
            try {
                JSONObject jsonObject = new JSONObject(response.getBody());
//                System.out.println(response.getBody());
                JSONObject object = jsonObject.getJSONObject("alipay_trade_query_response");
                String msg = object.getString("trade_status");
                send_query_msg(msg);
                //往单片机发消息
//                sendMsg();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //连接单片机
    class ConnectThread implements Runnable{

        private Boolean flag = true;

        @Override
        public void run() {
            while (flag){
                try {
                    socket = new Socket("192.168.1.160", 80);
                    try {
                        sendMsg = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
                        sendMsg.println(make_up_mag());
                        sendMsg.flush();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    flag = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //预创建订单广播
    private void send_order_msg(String out_trade_no, String url){
        Intent intent = new Intent("payapp.best.com.f2fdemo.RECEIVE_MSG");
        intent.putExtra("operation", "alipay_trade_precreate_response");
        intent.putExtra("outTradeNo", out_trade_no);
        intent.putExtra("URL", url);
        sendBroadcast(intent);
    }

    //订单查询广播
    private void send_query_msg(String result){
        Intent intent = new Intent("payapp.best.com.f2fdemo.RECEIVE_MSG");
        intent.putExtra("operation", "alipay_trade_query_response");
        intent.putExtra("Result", result);
        sendBroadcast(intent);
    }

    //发送消息到单片机
    private void sendMsg() {
        new Thread(new ConnectThread()).start();
    }

    private String make_up_mag(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        String msg = "<MESSAGE>" + "\n" +
                "<USER_ID>" + "0003108" + "</USER_ID>" + "\n" +
                "<TIME>" + simpleDateFormat.format(date) + "</TIME>" + "\n" +
                "<TRADE_STATUS>" + "CNY100.00" + "</TRADE_STATUS>" + "\n" +
                "</MESSAGE>" + "\n";
        return msg;
    }
}

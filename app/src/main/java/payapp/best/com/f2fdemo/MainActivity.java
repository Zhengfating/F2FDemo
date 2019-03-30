package payapp.best.com.f2fdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView tx;
    private Button bt;
    private ImageView image;

    private MsgReceiver receiver;

    private AlipayTradeService.SendMsgBinder sendMsgBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            sendMsgBinder = (AlipayTradeService.SendMsgBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成订单
                sendMsgBinder.test_trade_precreate();
//                sendMsgBinder.query_trade("tradeprecreate1551866634137656976");
            }
        });
    }

    private void initData(){
        tx = findViewById(R.id.text);
        bt = findViewById(R.id.button);
        image = findViewById(R.id.imageView);

        //开启并绑定服务
        Intent bindIntent = new Intent(this, AlipayTradeService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);

        //注册广播接收器
        receiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter("payapp.best.com.f2fdemo.RECEIVE_MSG");
        registerReceiver(receiver, filter);
    }

    //广播接收器
    class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String getResult = intent.getStringExtra("operation");
            switch (getResult){
                case "alipay_trade_precreate_response":
                    String url = intent.getStringExtra("URL");
                    image.setImageBitmap(Alipay2DCode.generateBitmap(url));
                    tx.setText("二维码已生成，等待支付");
                    tx.setTextColor(Color.BLUE);
                    //开始查询订单支付情况
                    String out_trade_no = intent.getStringExtra("outTradeNo");
                    sendMsgBinder.query_trade(out_trade_no);
                    break;
                case "alipay_trade_query_response":
                    String result = intent.getStringExtra("Result");
                    if ("TRADE_SUCCESS".equals(result)){
                        tx.setText("支付成功！！！");
                        tx.setTextColor(Color.BLUE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
        unregisterReceiver(receiver);
    }
}

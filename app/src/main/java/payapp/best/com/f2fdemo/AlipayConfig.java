package payapp.best.com.f2fdemo;

import com.alipay.api.AlipayClient;

/**
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2019-02-26
 **/
public class AlipayConfig {

    /**
     * 支付宝网关（固定）
     */
    public static final String URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    public static final String APP_ID = "2019022763398302";

    /**
     * 商户私钥，您的JAVA格式RSA2私钥
     */
    public static final String APP_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQC/7nBdoGKW62pL/wEGkPO6IiFoZ55xgE/Bjln1W8fs7GTolpd6a070K5qZZDjOPpYOf3LnBdDJVxZOB911oHEUVjxvZrOFaIvxDIgsD8w1VDvBkoWc75p/9/4T9TKpVdnm+Nru2qsHxTLFlfByU7zrnoJNTAdN19vMFF99VcYgvS+JqLQ+CPtCdSnVeMOJ6UZSl7NbieqwqNB6WpIS+P9IvUkAap4I59hekVwXVlkp7sKAZ2/k6VldRLZL0U/JxILx78/4UdS6W+qWQRQOKjv8RqCo+oEEnKqyqJBDiAXyaV+5yqlgNGch67H41UFfVqDRUp1dzWAnc59CoXUSgqGdAgMBAAECggEBAJLMfqk92Opx2PWTb2dkSuac4EyIpIlPiLEUff3KW1n9XRkAxY9esB7VkwOrwj8NY5xnIq1Mp2q371lzbpFfS94wLz1NohVx50dRPH3m/1vyt1nRBd0I7nTeazk8m6kk/utIf7udCJ+C1tiFXEk1fxQAQs14T0XcykKHepnUlxvGAljM20BAUrb47JbtmHcLi5hQ+UbyVtQGsz7pIeihu1B4xvWD9PeHhezPk5FcNpeqwOI3t1vy2xqUhA54c8/sJqN2dIVc/AYP0mjzY2U8saBTd4EDbe8nKRQK9+IYs5NC+gGkkHowSimLjQFb5CQFxdNAE5ZfBPMbZgVZIpgzrkECgYEA+KmuIWBI7WhDBowHKPqeG7eqhiIkRzwcxpqb+tRR/awQrNJxqrKCfN5M2JWNcMNZHYCr5on+XBnLrwP6id9lzlRZJ1L0NEQeaqLrWHmuv7MzVXqXvlJFlssseiQT59B/GprVvYDMNUxVAsOwKZt3vggAe9yo1KKfzkc7Q3LB13kCgYEAxZg6Sjy24HQbLazwnwUlTIRpTWuCmNnavj/mTLZlXqcVBe0jS+CXpR+shgkLC56+8rwXNjE+HigHbBSCe8yGKQzGfgM3hUGGdTYd471B2ityzEGf6y7YrNN0TbQUC0by/ykJCFs9o+h0sEPmYTyaiGfJDR1Q0StUCldsfHJmfkUCgYEAlf1rrnAwrRtFyq4uJ3a8ZgWsU0pGzb0hsl5SFcN6TWQ7jrNFouwYL4+7lKzo6wq6N0SE7ANyrUVWChSBTCa3dvSD7mdoLBqmqjXpda24TaOfJEFVRJTFqwaUpnwakaq+8GJS6QLGqqOHZ6p4GTDQ0oxrFktsfw41DK0Ev1N6PykCgYEAoy5rfSGU+dqJE3iOIxWRzxZPDIPsD6Vis9l7rQC4yKl9gC5uBtzM/64fwXW4bHlGQpo5JSUXorn2Hzj05Z5rcX0+c5TehisuCqCNoKWMu8tD/BF6KzS99kSO+RjtsL6AMV9HOWJ4jmOL8oGwwb7V9C6Z6D28n3GD7yJx0nh70m0CgYEAxzVZqSd+EgFmA0/5aWhR+ZLA+7BweQwqiZopTHpbazNp+dkB1mdQ0IrkLipAR0lFfjy4CwNzZS+oojAs7e8wNVDpbU2pE8BtJr0kmaY9M0ndJriXsiWzUI5ZtVaUqttWnWd8c9aVcStjbZ1il1snd6FXVTRLJIP74dcWOMpdwCc=";

    /**
     * 参数返回格式，只支持json（固定）
     */
    public static final String FORMAT = "json";

    /**
     * 请求和签名使用的字符编码格式，支持GBK和UTF-8
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 支付宝公钥，由支付宝生成
     */
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhY/VOEAyKz2NgWxeDrsYO/yOAZd+UwUmZ6YeoRAicKb3So9UGxUkyeB4eeqnWuDFnzclLJ2uaJm5P+1K3uwMaLuhnACDB4pzouhLcK+ke6rVK/v/yNN9WC5ka53Looc6KPtyVnT0pjnehC4q2lFMCapuIrlSBNXtb1u/rEUTTXJrqVnpqm+lHcsYduzdYShXSBHKMo7k29AvMSGCK1X35TLCefaDEeqP1qC5rNj2np7PF0alASOmz5hdGWVIQ7dDETkq91WmOUR4tqERjc32J5nmeyCds0lRo86TXYoNvVgZ5pKsOOA/TIpTmSNcj33C2HYRhgKYBmi0G52i0W97jwIDAQAB";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    public static final String SIGN_TYPE = "RSA2";

    //实例化客户端
    public static AlipayClient ALIPAY_CLIENT;

    private static int maxQueryRetry = 17280;   // 最大查询次数(24小时都在查询需要17280次)
    private static long queryDuration = 5000;  // 查询间隔（毫秒）

    public static final String SUCCESS = "10000"; // 成功

    public static int getMaxQueryRetry() {
        return maxQueryRetry;
    }

    public static long getQueryDuration() {
        return queryDuration;
    }
}

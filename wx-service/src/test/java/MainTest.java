import com.zxx.wxservice.config.WxCp.WxCpConfiguration;
import com.zxx.wxservice.config.WxMa.WxMaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class MainTest {


    @Test
    public  void test() {

        System.out.println(WxMaConfiguration.getMaService());

        System.out.println(WxCpConfiguration.getWxCpService());

    }
}

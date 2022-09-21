package easy_excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.yuf.demo.excel.model.Settlement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2022/9/21 17:51
 */
@Slf4j
public class EasyExcelTest {


    @Test
    public void test(){
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        String fileName = "D:\\用户\\Downloads\\" + "220627024827637_ATL-5813 Lenovo SC Spire Services  St Louis Value Back Booking Report.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(fileName, Settlement.class, new PageReadListener<Settlement>(dataList -> {
            for (Settlement demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).sheet(0).headRowNumber(19).doRead();



    }

}

package easy_excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import com.yuf.demo.excel.model.Settlement;
import com.yuf.demo.excel.model.SettlementHead;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Hash;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: dyf
 * @Date: 2022/9/21 17:51
 */
@Slf4j
public class EasyExcelTest {

    final String fileName = "D:\\用户\\Downloads\\" + "220627024827637_ATL-5813 Lenovo SC Spire Services  St Louis Value Back Booking Report.xlsx";

    @Test
    public void test(){
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        List<Settlement> settlementList = new ArrayList<>();
        EasyExcel.read(fileName, Settlement.class, new PageReadListener<Settlement>(dataList -> {
            for (Settlement demoData : dataList) {
                settlementList.add(demoData);
            }
        })).sheet(0).headRowNumber(19).doRead();
        log.info("读取总条数：{}",settlementList.size());
        log.info("读取的第一条数据{}",settlementList.get(0));

    }

    @Test
    public void testReadHead(){
        EasyExcel.read(fileName, SettlementHead.class, new ReadListener<SettlementHead>() {

            private List<SettlementHead> list = new ArrayList<>();

            @Override
            public void invoke(SettlementHead data, AnalysisContext context) {
//                log.info("解析到数据:{}", JSON.toJSONString(data));
                list.add(data);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData(list);
            }

            /**
             * 加上存储数据库
             */
            private void saveData(List<SettlementHead> list) {
                for (int i = 0; i < 15; i++) {
                    log.info("第{}条表头数据： {}} ！", i ,list.get(i));
                }
            }
        }).sheet(0).headRowNumber(2).doRead();

    }


}

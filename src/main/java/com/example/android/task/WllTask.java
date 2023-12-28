package com.example.android.task;


import com.example.android.entity.RecoAttraction;
import com.example.android.service.RecoAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.android.controller.RecoAttractionController.dailyRecoAttractionList;

@Configuration      //主要用于标记配置类，兼备Component的效果。
@EnableScheduling   //开启定时任务
public class WllTask {
    private static RecoAttractionService recoAttractionService;

    @Autowired
    public WllTask(RecoAttractionService recoAttractionService) {
        WllTask.recoAttractionService = recoAttractionService;
    }

    static int needUpdateNum = 5;
    static int[] randomArray = new int[2 * needUpdateNum];
    static int baseNum = 0;

    @Scheduled(cron = "0 0 0 1/1 * ?")//每天00:00更新数据
//    @Scheduled(cron = "0/6 * * * * ?")//Test
    public static void updateTodayRecoAttraction() {
        List<RecoAttraction> recoAttractionList = recoAttractionService.getRecoAttractionList();
        if (recoAttractionList.size() != 0) {
            // 使用循环生成随机数并放入数组
            for (int i = 0; i < needUpdateNum; i++) {
                int num = ThreadLocalRandom.current().nextInt(0, recoAttractionList.size());
                // 使用循环检查随机数是否已经存在于数组中
                boolean contains = false;
                for (int j = 0; j < randomArray.length; j++) {
                    if (randomArray[j] == num) {
                        contains = true;
                        break;
                    }
                }
                // 如果随机数已经存在于数组中，则重新生成
                if (contains) {
                    i--;
                } else {
                    randomArray[i + (baseNum * needUpdateNum)] = num;
                }
            }
            System.out.println(Arrays.toString(randomArray));
            for (int i = baseNum * needUpdateNum; i < (baseNum + 1) * needUpdateNum; i++) {
                dailyRecoAttractionList.add(recoAttractionList.get(randomArray[i]));
                System.out.println(randomArray[i]);
            }
            baseNum++;
            if (baseNum > 1) {
                baseNum = 0;
            }
        }
            System.out.println("执行：更新软件每日推荐");
        }
}

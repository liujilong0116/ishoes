package com.iai.ishoes.utils;


import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.style.ItemStyle;

public class EchartOptionUtil {
    private static String[] pressure_color_list = new String[] {"#afdde0", "#35a8d9", "#67af32", "#9ac22e", "#eac92d", "#e68421", "#df6047", "#b91d22", "#5a0d11", "#231815"};

    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
//        option.title("折线图");
//        option.legend("销量");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);
        System.out.println(yAxis[0]);

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        Bar bar = new Bar();
        for(int i = 0; i < yAxis.length; i ++){
            ItemStyle dataStyle = new ItemStyle();
            dataStyle.normal().color(pressure_color_list[select_color(Integer.parseInt(yAxis[i].toString()))]);
            bar.data(new Data("压力值", yAxis[i]).itemStyle(dataStyle));
        }
        option.series(bar);
        return option;
    }

    private static int select_color(int pressure){
        if(pressure < 5)
            return 0;
        else if(pressure < 10)
            return 1;
        else if(pressure < 15)
            return 2;
        else if(pressure < 20)
            return 3;
        else if(pressure < 25)
            return 4;
        else if(pressure < 30)
            return 5;
        else if(pressure < 35)
            return 6;
        else if(pressure < 40)
            return 7;
        else
            return 8;
    }
}

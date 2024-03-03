/*
 * @time 2024/3/3 15:48
 * @package PACKAGE_NAME
 * @project wenjelly-generators
 * @author WenJelly
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FreeMarkerTest {

    @Test
    public void test() throws IOException, TemplateException {
        // new 出 Configuration 对象，参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        // 获取模板文件并加载
        Template template = configuration.getTemplate("myweb.html.ftl");
        /*
          创建数据模型
          myweb.html.ftl所需要的数据模型
          currentYear ： 当前年份
          menuItems:
               item：
                   item.url ： 网站
                   item.label ： 标签
         */
        HashMap<String, Object> dataModel = getStringObjectHashMap();

        // 指定生成的文件,注意：目录一定要存在，否则会报错
        FileWriter fileWriter = new FileWriter("src/main/resources/templatesOut/myweb.html");
        // 将数据模型传递给模板并生成目标文件
        template.process(dataModel,fileWriter);
        // 关闭输出流
        fileWriter.close();
    }

    private static HashMap<String, Object> getStringObjectHashMap() {
        HashMap<String, Object> dataModel  = new HashMap<>();
        dataModel.put("currentYear",2024);
        ArrayList<Object> menuItems = new ArrayList<>();

        HashMap<String, Object> menuItem1  = new HashMap<>();
        menuItem1.put("url","www.wenguodong.com");
        menuItem1.put("label","我的博客");


        HashMap<String, Object> menuItem2  = new HashMap<>();
        menuItem2.put("url","www.wenguodong.com");
        menuItem2.put("label","我的博客");

        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        dataModel.put("menuItems",menuItems);
        return dataModel;
    }

}

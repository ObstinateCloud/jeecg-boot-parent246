package org.jeecg.modules.word.controller;

import java.net.URL;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.word.bo.AffectedProvinceBo;
import org.jeecg.modules.word.bo.OrderlyExectionStatusBo;
import org.jeecg.modules.word.entity.WordQuota;
import org.jeecg.modules.word.service.IWordQuotaService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.word.view.MyTemplateWordView;
import org.jeecgframework.poi.excel.def.TemplateWordConstants;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: word指标表
 * @Author: jeecg-boot
 * @Date: 2021-11-04
 * @Version: V1.0
 */
@Api(tags = "word指标表")
@RestController
@RequestMapping("/word/wordQuota")
@Slf4j
public class WordQuotaController extends JeecgController<WordQuota, IWordQuotaService> {
    @Autowired
    private IWordQuotaService wordQuotaService;

    @Value("${word.templatePath}")
    private String templatePath;

    /**
     * 分页列表查询
     *
     * @param wordQuota
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "word指标表-分页列表查询")
    @ApiOperation(value = "word指标表-分页列表查询", notes = "word指标表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(WordQuota wordQuota,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<WordQuota> queryWrapper = QueryGenerator.initQueryWrapper(wordQuota, req.getParameterMap());
        Page<WordQuota> page = new Page<WordQuota>(pageNo, pageSize);
        IPage<WordQuota> pageList = wordQuotaService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param wordQuota
     * @return
     */
    @AutoLog(value = "word指标表-添加")
    @ApiOperation(value = "word指标表-添加", notes = "word指标表-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody WordQuota wordQuota) {
        wordQuotaService.save(wordQuota);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param wordQuota
     * @return
     */
    @AutoLog(value = "word指标表-编辑")
    @ApiOperation(value = "word指标表-编辑", notes = "word指标表-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody WordQuota wordQuota) {
        wordQuotaService.updateById(wordQuota);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "word指标表-通过id删除")
    @ApiOperation(value = "word指标表-通过id删除", notes = "word指标表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        wordQuotaService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "word指标表-批量删除")
    @ApiOperation(value = "word指标表-批量删除", notes = "word指标表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.wordQuotaService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "word指标表-通过id查询")
    @ApiOperation(value = "word指标表-通过id查询", notes = "word指标表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        WordQuota wordQuota = wordQuotaService.getById(id);
        if (wordQuota == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(wordQuota);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wordQuota
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WordQuota wordQuota) {
        return super.exportXls(request, wordQuota, WordQuota.class, "word指标表");
    }


    @GetMapping(value = "/exportWord")
    @ApiOperation(value = "word-通过id查询", notes = "word指标表-通过id查询")
    public ModelAndView exportWord(HttpServletRequest request, WordQuota wordQuota, HttpServletResponse response) {
        Map map = this.getDemoData();
        // Step.3 AutoPoi 导出word
//        String templatePath = "";
        try {
//            String resourcePath = JeecgSystemApplication.class.getResource("/").getPath();
//            log.info("original resourcePath:{}",resourcePath);
//            if (resourcePath.startsWith("/") || resourcePath.startsWith("\\")) {
//                resourcePath = resourcePath.substring(1, resourcePath.length());
//                log.info("delete '!' first '/' or '\\',resourcePath:{}",resourcePath);
//                resourcePath = resourcePath.replaceAll("!","");
//                log.info("delete '!' end,,resourcePath:{}",resourcePath);
//                templatePath = resourcePath+"templates/test.docx";
//            }
//        templatePath=property+"\\jeecg-boot-module-system\\src\\main\\resources\\templates\\test.docx";
//        templatePath="C:/zjywork/personwork/jeecg-boot-2.4.6/jeecg-boot/jeecg-boot-module-system/target/classes/../../src/main/resources/templates/test.docx";

            // 编译后，项目根路径，写法1
            String url1 = JeecgSystemApplication.class.getClassLoader().getResource("").toString();
            System.out.println("url1:"+url1);
            // 编译后，项目根路径，写法2
            String url2 = JeecgSystemApplication.class.getResource("/").toString();
            System.out.println("url2:"+url2);
            // 编译后，文件根路径
            String url3 = JeecgSystemApplication.class.getResource("").toString();
            System.out.println("url3:"+url3);
            String url4 = JeecgSystemApplication.class.getClassLoader().getResource("").getFile();
            String url5 = JeecgSystemApplication.class.getResource("/").getFile();
            String url6 = JeecgSystemApplication.class.getResource("").getFile();
            System.out.println("url4:"+url4);
            System.out.println("url5:"+url5);
            System.out.println("url6:"+url6);
            String url7 = JeecgSystemApplication.class.getClassLoader().getResource("").getPath();
            String url8 = JeecgSystemApplication.class.getResource("/").getPath();
            String url9 = JeecgSystemApplication.class.getResource("").getPath();
            System.out.println("url7:"+url7);
            System.out.println("url8:"+url8);
            System.out.println("url9:"+url9);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //导出word
        ModelAndView mv = new ModelAndView(new MyTemplateWordView());
//        ModelAndView mv = new ModelAndView(new JeecgTemplateWordView());
        mv.addObject(TemplateWordConstants.FILE_NAME, "报表"); //此处设置的filename无效 ,前端会重更新设置一下
//        mv.addObject(TemplateWordConstants.URL, "D:\\test.docx");
        System.out.println("templatePath:"+templatePath);
        mv.addObject(TemplateWordConstants.URL, templatePath);
        mv.addObject(TemplateWordConstants.MAP_DATA, map);
        return mv;
    }


    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, WordQuota.class);
    }

    public static void main(String[] args) {
        // 编译后，项目根路径，写法1
//        String url1 = JeecgSystemApplication.class.getClassLoader().getResource("").toString();
//        System.out.println("url1:"+url1);
//       // 编译后，项目根路径，写法2
//        String url2 = JeecgSystemApplication.class.getResource("/").toString();
//        System.out.println("url2:"+url2);
//        // 编译后，文件根路径
//        String url3 = JeecgSystemApplication.class.getResource("").toString();
//        System.out.println("url3:"+url3);
//        String url4 = JeecgSystemApplication.class.getClassLoader().getResource("").getFile();
//        String url5 = JeecgSystemApplication.class.getResource("/").getFile();
//        String url6 = JeecgSystemApplication.class.getResource("").getFile();
//        System.out.println("url4:"+url4);
//        System.out.println("url5:"+url5);
//        System.out.println("url6:"+url6);
//        String url7 = JeecgSystemApplication.class.getClassLoader().getResource("").getPath();
//        String url8 = JeecgSystemApplication.class.getResource("/").getPath();
//        String url9 = JeecgSystemApplication.class.getResource("").getPath();
//        System.out.println("url7:"+url7);
//        System.out.println("url8:"+url8);
//        System.out.println("url9:"+url9);

        String str="url5:file:/C:/zjywork/personwork/jeecg-boot-2.4.6/jeecg-boot/jeecg-boot-module-system/target/jeecg-boot-module-system-2.4.6.jar!/BOOT-INF/classes!/";
        System.out.println("str1:"+str);
        str = str.replaceAll("!","");
        System.out.println("str2:"+str);
    }

    private Map geySimpleData(){
        Map map = new HashMap();
        map.put("name", "张健云");
        map.put("age", "23");
        map.put("like", "book");
        map.put("fileName", "word");

        List<Map<String, Object>> maplist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("aa", "aa" + i);
            objectMap.put("bb", "bb" + i);
            objectMap.put("cc", "cc" + i);
            objectMap.put("dd", "dd" + i);
            maplist.add(objectMap);
        }
        map.put("userlist", maplist);
        return map;
    }

    private Map<String, Object> getDemoData() {
        List<Object> list1 = new ArrayList<>();
        AffectedProvinceBo entity1 = new AffectedProvinceBo();
        entity1.setCompany("浙江");
        entity1.setAffectedHousehold(536.0);

        AffectedProvinceBo entity2 = new AffectedProvinceBo();
        entity2.setCompany("河南");
        entity2.setAffectedHousehold(303.5);

        AffectedProvinceBo entity3= new AffectedProvinceBo();
        entity3.setCompany("江西");
        entity3.setAffectedHousehold(140.5);

        AffectedProvinceBo entity4 = new AffectedProvinceBo();
        entity4.setCompany("青海");
        entity4.setAffectedHousehold(59.4);

        AffectedProvinceBo entity5 = new AffectedProvinceBo();
        entity5.setCompany("福建");
        entity5.setAffectedHousehold(56.1);

        list1.add(entity1);
        list1.add(entity2);
        list1.add(entity3);
        list1.add(entity4);
        list1.add(entity5);


        List<Object> list2 = new ArrayList<>();
        AffectedProvinceBo en1 = new AffectedProvinceBo();
        en1.setCompany("浙江");
        en1.setAffectedHousehold(450);

        AffectedProvinceBo en2 = new AffectedProvinceBo();
        en2.setCompany("江西");
        en2.setAffectedHousehold(130);

        AffectedProvinceBo en3= new AffectedProvinceBo();
        en3.setCompany("福建");
        en3.setAffectedHousehold(33);

        list2.add(en1);
        list2.add(en2);
        list2.add(en3);

        ArrayList<OrderlyExectionStatusBo> list3 = new ArrayList<>();
        OrderlyExectionStatusBo exe1 = new OrderlyExectionStatusBo("浙江","01",6310.0,500.0,"00：00-06：00 06：00-11：00",26482,536.0,0,0.0,333.1,"62.1%",8700.0,"107.2%");
        OrderlyExectionStatusBo exe2 = new OrderlyExectionStatusBo("河南","02",4107.0,250.0,"00:00-24:00",2382,140.5,0,0.0,209.9,"69.2%",5012.4,"121.4%");
        OrderlyExectionStatusBo exe3 = new OrderlyExectionStatusBo("江西","03",1826.5,170.0,"16:30-20:00",1798,303.5,0,0.0,129.3,"92.0%",432.5,"82.6%");

        list3.add(exe1);
        list3.add(exe2);
        list3.add(exe3);

        Map<String, Object> testMap = new HashMap<>();
        testMap.put("today","2021年10月31日");
        testMap.put("yday", "2021年10月30日");
        testMap.put("dbyday", "2021年10月29日");
        testMap.put("ydayProvinceNum", "5");
        testMap.put("ydayMaxPowerLoad", "1095.5");
        testMap.put("reducePowerLoad", "262.1");
        testMap.put("highEnergyIndustryLimitLoad", "797.7");
        testMap.put("highEnergyIndustryLoadRate", "71.9%");
        testMap.put("ydayProvince", "河南、山东" );

        testMap.put("totalAffectedHousehold", "33173");
        testMap.put("orderlyExectionStatus", list3);
        testMap.put("todayProvinceNumber", "3");
        testMap.put("todayProvince", "河南、山东");

        return testMap;
    }

}

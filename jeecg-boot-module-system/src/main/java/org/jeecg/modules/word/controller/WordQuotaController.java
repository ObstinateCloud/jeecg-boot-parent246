package org.jeecg.modules.word.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.word.entity.WordQuota;
import org.jeecg.modules.word.service.IWordQuotaService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.def.TemplateWordConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.view.JeecgTemplateWordView;
import org.jeecgframework.poi.word.WordExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: word指标表
 * @Author: jeecg-boot
 * @Date:   2021-11-04
 * @Version: V1.0
 */
@Api(tags="word指标表")
@RestController
@RequestMapping("/word/wordQuota")
@Slf4j
public class WordQuotaController extends JeecgController<WordQuota, IWordQuotaService> {
	@Autowired
	private IWordQuotaService wordQuotaService;
	
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
	@ApiOperation(value="word指标表-分页列表查询", notes="word指标表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WordQuota wordQuota,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WordQuota> queryWrapper = QueryGenerator.initQueryWrapper(wordQuota, req.getParameterMap());
		Page<WordQuota> page = new Page<WordQuota>(pageNo, pageSize);
		IPage<WordQuota> pageList = wordQuotaService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param wordQuota
	 * @return
	 */
	@AutoLog(value = "word指标表-添加")
	@ApiOperation(value="word指标表-添加", notes="word指标表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WordQuota wordQuota) {
		wordQuotaService.save(wordQuota);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param wordQuota
	 * @return
	 */
	@AutoLog(value = "word指标表-编辑")
	@ApiOperation(value="word指标表-编辑", notes="word指标表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WordQuota wordQuota) {
		wordQuotaService.updateById(wordQuota);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "word指标表-通过id删除")
	@ApiOperation(value="word指标表-通过id删除", notes="word指标表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		wordQuotaService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "word指标表-批量删除")
	@ApiOperation(value="word指标表-批量删除", notes="word指标表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
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
	@ApiOperation(value="word指标表-通过id查询", notes="word指标表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WordQuota wordQuota = wordQuotaService.getById(id);
		if(wordQuota==null) {
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


	 /**
	  * 导出word
	  *
	  * @param request
	  * @param wordQuota
	  */
	 @RequestMapping(value = "/exportWord")
	 public ModelAndView exportWord(HttpServletRequest request, WordQuota wordQuota) {

 		Map map = new HashMap();
 		map.put("name","张健云");
 		map.put("age","23");
 		map.put("like","book");

		 // Step.3 AutoPoi 导出word
		 ModelAndView mv = new ModelAndView(new JeecgTemplateWordView());
		 mv.addObject(TemplateWordConstants.FILE_NAME, "报表"); //此处设置的filename无效 ,前端会重更新设置一下
		 mv.addObject(TemplateWordConstants.URL, "../../src/main/resources/templates/test.docx");
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

}

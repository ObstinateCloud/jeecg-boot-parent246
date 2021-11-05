package org.jeecg.modules.word.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: word指标表
 * @Author: jeecg-boot
 * @Date:   2021-11-04
 * @Version: V1.0
 */
@Data
@TableName("word_quota")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="word_quota对象", description="word指标表")
public class WordQuota implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**总容量*/
	@Excel(name = "总容量", width = 15)
    @ApiModelProperty(value = "总容量")
    private java.math.BigDecimal totalPower;
	/**昨日容量*/
	@Excel(name = "昨日容量", width = 15)
    @ApiModelProperty(value = "昨日容量")
    private java.math.BigDecimal yesterdayPower;
	/**今日容量*/
	@Excel(name = "今日容量", width = 15)
    @ApiModelProperty(value = "今日容量")
    private java.math.BigDecimal todayPower;
	/**今日说明*/
	@Excel(name = "今日说明", width = 15)
    @ApiModelProperty(value = "今日说明")
    private java.lang.String todayExplain;
	/**总体说明*/
	@Excel(name = "总体说明", width = 15)
    @ApiModelProperty(value = "总体说明")
    private java.lang.String totalExplain;
	/**文档类型*/
	@Excel(name = "文档类型", width = 15, dicCode = "wordType")
	@Dict(dicCode = "wordType")
    @ApiModelProperty(value = "文档类型")
    private java.lang.Integer wordType;
}

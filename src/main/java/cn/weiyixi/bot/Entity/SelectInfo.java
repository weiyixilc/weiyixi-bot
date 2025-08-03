package cn.weiyixi.bot.Entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询信息")
public class SelectInfo {

    @ApiModelProperty(value = "分级",required = true)
    private String cat_id;
    @ApiModelProperty(value = "页码(从哪里开始)",required = true)
    private String start;
    @ApiModelProperty(value = "每页条数",required = true)
    private String page_size;
    @ApiModelProperty(value = "排序字段（sold_number（销售数量），addtime（添加时间），goods_price（商品价格））",required = false)
    private String sort_order;

    @ApiModelProperty(value = "排序方式（asc（低到高），desc（高到低））",required = false)
    private String sort_desc;
    private String is_first;
    @ApiModelProperty(value = "查询参数",required = false)
    private String keywords;
    private String brand_id;
    private String country_id;
    @ApiModelProperty(value = "查询方法",required = false)
    private String method;



}

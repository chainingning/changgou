package com.changgou.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.changgou.entity.Result;
import com.changgou.goods.feign.SkuFeign;
import com.changgou.goods.pojo.Brand;
import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spec;
import com.changgou.search.dao.SkuEsMapper;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SkuService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SkuServiceImpl
 * @Description:
 * @Author ning.chai@foxmail.com
 * @Date 2020/8/27 0027
 * @Version V1.0
 **/
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    /**
     * ElasticsearchTemplate：可以实现索引库的增删改查[高级搜索]
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 条件搜索
     *
     * @param searchMap
     * @return Map
     */
    @Override
    public Map search(Map<String, String> searchMap) {
        //1.获取关键字的值
        String keywords = null;
        if (!CollectionUtils.isEmpty(searchMap)) {
            keywords = searchMap.get("keywords").toString();
            if (StringUtils.isEmpty(keywords)) {
                //赋值给一个默认的值
                keywords = "华为";
            }
        }

        /**
         * NativeSearchQueryBuilder：搜索条件构建对象，用于封装各种搜索条件
         */
        //2.创建查询对象 的构建对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //BoolQuery must,not_must.should
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


        //3.设置查询的条件
        //nativeSearchQueryBuilder.withQuery(QueryBuilders.queryStringQuery(keywords).field("name"));
        boolQueryBuilder.must(QueryBuilders.queryStringQuery(keywords).field("name"));

        //输入了分类——》category
        if (!StringUtils.isEmpty(searchMap.get("category"))) {
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryName",searchMap.get("category")));
        }


        //输入了品牌->brand
        if (!StringUtils.isEmpty(searchMap.get("brand"))) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brandName",searchMap.get("brand")));
        }

        //规格过滤实现spec_网络=联通3G&spec_颜色=红
        if (searchMap != null) {
            for (String key : searchMap.keySet()) {
                if (key.startsWith("spec_")) {
                    //spec_网络，spec_前五个去掉
                    boolQueryBuilder.filter(QueryBuilders.termQuery("specMap." + key.substring(5) + ".keyword", searchMap.get(key)));
                }
            }
        }

        //价格区间过滤
        String price = searchMap.get("price");
        if (!StringUtils.isEmpty(price)) {
            price = price.replace("元", "").replace("以上", "");
            String[] priceArr = price.split("-");
            if (!priceArr[1].equalsIgnoreCase("*")) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").from(priceArr[0], true).to(priceArr[1], true));
            } else {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(priceArr[0]));
            }
        }


        //分页，用户如果不传分页参数，则默认第一页
        Integer pageNum = covertPage(searchMap);//默认第一页
        Integer size = 10;//默认查询数据条数
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageNum-1,size));



        //排序
        String sortField = searchMap.get("sortField");//指定排序的域
        String sortRule = searchMap.get("sortRule");//指定排序的规则
        if (!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)) {
            nativeSearchQueryBuilder.withSort(new FieldSortBuilder(sortField).order(SortOrder.valueOf(sortRule)));
        }


        //将boolQueryBuilder填充给NativeSearchQuery
        nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        NativeSearchQuery query = nativeSearchQueryBuilder.build();



        //添加高亮
        HighlightBuilder.Field field = new HighlightBuilder.Field("name");//指定高亮域
        //前缀<em style="color:red">
        field.preTags("<em style=\"color:red\">");
        //后缀</em>
        field.postTags("</em>");
        //碎片长度，关键词数据的长度，有默认值
        field.fragmentSize(100);

        nativeSearchQueryBuilder.withHighlightFields(field);

        /**
         * 执行搜索，响应结果给我
         * 1.搜索条件封装对象
         * 2.搜索的结果集（集合数据)需要转换的类型
         * 3.AggregatedPage<SkuInfo>:搜索结果集的封装
         */
        AggregatedPage<SkuInfo> page = elasticsearchTemplate.queryForPage(
                query,                      //搜索条件封装
                SkuInfo.class,              //数据结果集要转换的类型的字节码
                new SearchResultMapper(){   //执行搜索后，将数据结果集封装到该对象种

                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        //储存所有转换后的高亮数据对象
                        ArrayList<T> list = new ArrayList<>();


                        //执行查询，获取所有数据->结果集[非高亮数据|高亮数据]
                        for (SearchHit hit : response.getHits()) {
                            //分析结果集，获取非高亮数据
                            SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(), SkuInfo.class);

                            //分析结果集，获取高亮数据->只有某个域的高亮数据
                            HighlightField highlightField = hit.getHighlightFields().get("name");

                            //非高亮数据中指定的域替换成高亮数据
                            if (highlightField!=null&&highlightField.getFragments()!=null) {
                                //高亮数据读取
                                Text[] fragments = highlightField.getFragments();
                                StringBuffer buffer = new StringBuffer();
                                for (Text fragment : fragments) {
                                    buffer.append(fragment.toString());
                                }
                                //非高亮数据中指定的域替换成高亮数据
                                skuInfo.setName(buffer.toString());
                            }

                            //将数据返回
                            list.add((T)skuInfo);
                        }

                        /**
                         * 1.搜索的集合数据：携带高亮List<T> content
                         * 2.分页对象信息：pageable
                         * 3.搜索记录的总条数:long total
                         */
                        return new AggregatedPageImpl<T>(list,pageable,response.getHits().getTotalHits());
                    }
                }
        );




        //分页参数-总记录数
        long totalElements = page.getTotalElements();
        //分页参数-总页数
        int totalPages = page.getTotalPages();
        //获取数据结果集
        List<SkuInfo> contents = page.getContent();


        //封装一个Map存储所有数据，并返回
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("rows", contents);
        resultMap.put("total", totalElements);
        resultMap.put("totalPages", totalPages);

        //分类分组查询实现
        //当用户选择了分类，将分类作为搜索条件，则不需要对分类进行分组搜索，因为分组搜索的数据是用于显示分类搜索条件的
        if (searchMap == null || StringUtils.isEmpty(searchMap.get("category"))) {
            List<String> categoryList = searchCategoryList(nativeSearchQueryBuilder);
            resultMap.put("categoryList", categoryList);
        }

        //查询品牌集合[搜索条件]
        //跟分类场景相同
        if (searchMap == null || StringUtils.isEmpty(searchMap.get("brand"))) {
            List<String> brandList = searchBrandList(nativeSearchQueryBuilder);
            resultMap.put("brandList", brandList);
        }




        //规格查询
        return resultMap;
    }


    /**
     * 接收前端传入的分页参数
     * @param searchMap
     * @return
     */
    public Integer covertPage(Map<String,String> searchMap){
        if (!CollectionUtils.isEmpty(searchMap)) {
            String pageNum = searchMap.get("pageNum");
            try {
                return Integer.parseInt(pageNum);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
        return 1;
    }


    /**
     * 分类分组查询
     *
     * @param nativeSearchQueryBuilder
     * @return
     */
    private List<String> searchCategoryList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        /**
         * 分组查询分类集合
         * 1.addAggregation():添加一个聚合操作
         * terms:取别名
         * field:根据哪个域进行分组
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName").size(50));
        AggregatedPage<SkuInfo> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), SkuInfo.class);


        /**
         * 获取分组结果
         * aggregatePage.getAggregation():获取的是集合，可以根据多个域进行分组
         * .get("skuCategory")：获取指定域的集合数据
         */
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuCategory");
        List<String> categoryList = new ArrayList<>();

        if (stringTerms != null) {
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//其中的一个分类名字
                categoryList.add(keyAsString);
            }
        }
        return categoryList;
    }


    /**
     * 品牌分组查询
     *
     * @param nativeSearchQueryBuilder
     * @return
     */
    private List<String> searchBrandList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        /**
         * 分组查询分类集合
         * 1.addAggregation():添加一个聚合操作
         * terms:取别名
         * field:根据哪个域进行分组
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName").size(50));
        AggregatedPage<Brand> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), Brand.class);


        /**
         * 获取分组结果
         * aggregatePage.getAggregation():获取的是集合，可以根据多个域进行分组
         * .get("skuCategory")：获取指定域的集合数据
         */
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuBrand");
        List<String> brandList = new ArrayList<>();

        if (stringTerms != null) {
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//其中的一个品牌名字
                brandList.add(keyAsString);
            }
        }
        return brandList;
    }


    /**
     * 规格分组查询
     *
     * @param nativeSearchQueryBuilder
     * @return
     */
    private List<String> searchSpecList(NativeSearchQueryBuilder nativeSearchQueryBuilder) {
        /**
         * 分组查询规格集合
         * 1.addAggregation():添加一个聚合操作
         * terms:取别名
         * field:根据哪个域进行分组
         * size如果不加，默认就是10条
         */
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));
        AggregatedPage<Spec> aggregatedPage = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), Spec.class);


        /**
         * 获取分组结果
         * aggregatePage.getAggregation():获取的是集合，可以根据多个域进行分组
         * .get("skuSpec")：获取指定域的集合数据 [{"电视音响效果":"小影院","电视屏幕尺寸":"20英寸","尺码":"165"}
         * {"电视音响效果":"立体声","电视屏幕尺寸":"20英寸","尺码":"170"}
         * ]
         */
        StringTerms stringTerms = aggregatedPage.getAggregations().get("skuSpec");
        List<String> specList = new ArrayList<>();

        if (stringTerms != null) {
            for (StringTerms.Bucket bucket : stringTerms.getBuckets()) {
                String keyAsString = bucket.getKeyAsString();//其中的一个分类名字
                specList.add(keyAsString);
            }
        }

        //合并后的Map对象
        HashMap<String, Set<String>> allSpec = new HashMap<>();


        //1.循环specList  spec={"电视音响效果":"立体声","电视屏幕尺寸":"20英寸","尺码":"165"}
        for (String spec : specList) {

            // 2.将每个json字符串转成Map<String,String>
            Map<String,String> specMap = JSON.parseObject(spec, Map.class);

            //3.将每个Map对象合并成一个Map<String,Set<String>>
            /**
             * 4.合并流程
             */
            for (Map.Entry<String, String> entry : specMap.entrySet()) {
                //4.1取出当前Map,并且获取对应的key，以及对应的value
                String key = entry.getKey();//规格名字
                String spceValue = entry.getValue();//规格值

                //4.2将当前循环的数据合并到一个Map<String,Set<String>>中
                //从allSpec中获取当前规格对应的Set集合数据
                Set<String> specSet = allSpec.get(key);
                if (CollectionUtils.isEmpty(specSet)) {
                    //之前allSpec中没有数据
                    specSet = new HashSet<>();
                }
                specSet.add(spceValue);
                allSpec.put(key,specSet);
            }

        }
        return specList;
    }


    @Override
    public void importData() {
        //Feign调用，查询List<Sku>
        Result<List<Sku>> skuResult = skuFeign.findAll();


        //将List<Sku>转成List<SkuInfo>
        List<SkuInfo> skuInfoList = JSON.parseArray(JSON.toJSONString(skuResult.getData()), SkuInfo.class);

        for (SkuInfo skuInfo : skuInfoList) {
            //获取spec->Map(String)->Map类型({"电视音响效果":"立体声","电视屏幕尺寸":"20英寸","尺码":"165"})
            Map<String, Object> specMap = JSON.parseObject(skuInfo.getSpec(), Map.class);
            //如果需要生成动态的域，只需要将该域存入到一个Map<String,Object>对象中即可，该Map<String,Object>的key会生成一个域，域的名字为该map的key
            //当前Map<String,Object>后面Object的值会作为当前Ｓｋｕ对象该域（key)对应的值
            skuInfo.setSpecMap(specMap);
        }


        //调用Dao实现数据批量导入
        skuEsMapper.saveAll(skuInfoList);
    }
}

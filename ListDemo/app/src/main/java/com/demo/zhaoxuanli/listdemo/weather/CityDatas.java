package com.demo.zhaoxuanli.listdemo.weather;

/**
 * Created by zhaoxuan.li on 2015/10/19.
 */
public class CityDatas {

    public static String findCityCode(String city){
        for(int i =0;i<length;i++){
            if(city.equals(code[i]))
                return code[i];
        }
        return "";
    }

    protected static final String [] city = { "北京","上海","天津","重庆", "香港","澳门", "哈尔滨","齐齐哈尔","牡丹江","大庆","伊春","双鸭山","鹤岗","鸡西","佳木斯","七台河","黑河","绥化","大兴安岭",
            "长春","延吉","吉林","白山","白城","四平","松原","辽源","大安","通化","沈阳","大连","葫芦岛","盘锦","本溪","抚顺","铁岭","辽阳","营口","阜新","朝阳","锦州","丹东","鞍山",
            "呼和浩特","呼伦贝尔","锡林浩特","包头","赤峰","海拉尔","乌海","鄂尔多斯","通辽", "石家庄","唐山","张家口","廊坊","邢台","邯郸","沧州","衡水","承德","保定","秦皇岛",
            "郑州","开封","洛阳","平顶山","焦作","鹤壁","新乡","安阳","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店",
            "济南","青岛","淄博","威海","曲阜","临沂","烟台","枣庄","聊城","济宁","菏泽","泰安","日照","东营","德州","滨州","莱芜","潍坊",
            "太原","阳泉","晋城","晋中","临汾","运城","长治","朔州","忻州","大同","吕梁",
            "南京","苏州","昆山","南通","太仓","吴县","徐州","宜兴","镇江","淮安","常熟","盐城","泰州","无锡","连云港","扬州","常州","宿迁",
            "合肥","巢湖","蚌埠","安庆","六安","滁州","马鞍山","阜阳","宣城","铜陵","淮北","芜湖","毫州","宿州","淮南","池州",
            "西安","韩城","安康","汉中","宝鸡","咸阳","榆林","渭南","商洛","铜川","延安",
            "银川","固原","中卫","石嘴山","吴忠",
            "兰州","白银","庆阳","酒泉","天水","武威","张掖","甘南","临夏","平凉","定西","金昌",
            "西宁","海北","海西","黄南","果洛","玉树","海东","海南",
            "武汉","宜昌","黄冈","恩施","荆州","神农架","十堰","咸宁","襄阳","孝感","随州","黄石","荆门","鄂州",
            "长沙","邵阳","常德","郴州","吉首","株洲","娄底","湘潭","益阳","永州","岳阳","衡阳","怀化","韶山","张家界",
            "杭州","湖州","金华","宁波","丽水","绍兴","衢州","嘉兴","台州","舟山","温州",
            "南昌","萍乡","九江","上饶","抚州","吉安","鹰潭","宜春","新余","景德镇","赣州",
            "福州","厦门","龙岩","南平","宁德","莆田","泉州","三明","漳州",
            "贵阳","安顺","赤水","遵义","铜仁","六盘水","毕节","凯里","都匀",
            "成都","泸州","内江","凉山","阿坝","巴中","广元","乐山","绵阳","德阳","攀枝花","雅安","宜宾","自贡","甘孜州","达州","资阳","广安","遂宁","眉山","南充",
            "广州","深圳","潮州","韶关","湛江","惠州","清远","东莞","江门","茂名","肇庆","汕尾","河源","揭阳","梅州","中山","德庆","阳江","云浮","珠海","汕头","佛山",
            "南宁","桂林","阳朔","柳州","梧州","玉林","桂平","贺州","钦州","贵港","防城港","百色","北海","河池","来宾","崇左",
            "昆明","保山","楚雄","德宏","红河","临沧","怒江","曲靖","思茅","文山","玉溪","昭通","丽江","大理",
            "海口","三亚","儋州","琼山","通什","文昌",
            "乌鲁木齐","阿勒泰","阿克苏","昌吉","哈密","和田","喀什","克拉玛依","石河子","塔城","库尔勒","吐鲁番","伊宁",
            "拉萨","阿里","昌都","那曲","日喀则","山南","林芝",
            "台北","高雄"};
    protected static final String [] code = {"101010100","101020100","101030100","101040100","101320101","101330101",
            "101050101","101050201","101050301","101050901","101050801","101051301","101051201","101051101","101050401","101051002","101050601","101050501","101050701",
            "101060101","101060301","101060201","101060901","101060601","101060401","101060801","101060701","101060603","101060501",
            "101070101","101070201","101071401","101071301","101070501","101070401","101071101","101071001","101070801","101070901","101071201","101070701","101070601","101070301",
            "101080101","101081000","101080901","101080201","101080601","101081001","101080301","101080701","101080501",
            "101090101","101090501","101090301","101090601","101090901","101091001","101090701","101090801","101090402","101090201","101091101",
            "101180101","101180801","101180901","101180501","101181101","101181201","101180301","101180201","101181301","101180401","101181501","101181701","101180701","101181001","101180601","101181401","101181601",
            "101120101","101120201","101120301","101121301","101120710","101120901","101120501","101121401","101121701","101120701","101121001","101120801","101121501","101121201","101120401","101121101","101121601","101120601",
            "101100101","101100301","101100601","101100401","101100701","101100801","101100501","101100901","101101001","101100201","101101101",
            "101190101","101190401","101190404","101190501","101190408","101190406","101190801","101190203","101190301","101190901","101190402","101190701","101191201","101190201","101191001","101190601","101191101","101191301",
            "101220101","101221601","101220201","101220601","101221501","101221101","101220501","101220801","101221401","101221301","101221201","101220301","101220901","101220701","101220401","101221701",
            "101110101","101110510","101110701","101110801","101110901","101110200","101110401","101110501","101110601","101111001","101110300",
            "101170101","101170401","101170501","101170201","101170301",
            "101160101","101161301","101160401","101160801","101160901","101160501","101160701","101050204","101161101","101160301","101160201","101160601",
            "101150101","101150801","101150701","101150301","101150501","101150601","101150201","101150401",
            "101200101","101200901","101200501","101201001","101200801","101201201","101201101","101200701","101200201","101200401","101201301","101200601","101201401","101200301",
            "101250101","101250901","101250601","101250501","101251501","101250301","101250801","101250201","101250701","101251401","101251001","101250401","101251201","101250202","101251101",
            "101210101","101210201","101210901","101210401","101210801","101210501","101211001","101210301","101210601","101211101","101210701",
            "101240101","101240901","101240201","101240301","101240401","101240601","101241101","101240501","101241001","101240801","101240701",
            "101230101","101230201","101230701","101230901","101230301","101230401","101230501","101230801","101230601",
            "101260101","101260301","101260208","101260201","101260601","101260801","101260701","101260501","101260401",
            "101270101","101271001","101271201","101271601","101271901","101270901","101272101","101271401","101270401","101272001","101270201","101271701","101271101","101270301","101271801","101270601","101271301","101270801","101270701","101271501","101270501",
            "101280101","101280601","101281501","101280201","101281001","101280301","101281301","101281601","101281101","101282001","101280901","101282101","101281201","101281901","101280401","101281701","101280905","101281801","101281401","101280701","101280501","101280800",
            "101300101","101300501","101300510","101300301","101300601","101300901","101300802","101300701","101301101","101300801","101301401","101301001","101301301","101301201","101300401","101300201",
            "101290101","101290501","101290801","101291501","101290301","101291101","101291201","101290401","101290901","101290601","101290701","101291001","101291401","101290201",
            "101310101","101310201","101310205","101310102","101310222","101310212",
            "101130101","101131401","101130801","101130401","101131201","101131301","101130901","101130201","101130301","101131101","101130601","101130501","101131001",
            "101140101","101140701","101140501","101140601","101140201","101140301","101140401",
            "101340102","101340201"
    };

    protected static final int length = city.length;

}

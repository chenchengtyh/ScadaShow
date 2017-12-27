package com.nti56.scadashow.scadashow.interfaces;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.bean.Alarm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by chencheng on 2017/7/28.
 */

public class ScadaApplication extends Application {

    private static final String VALUE = "SCADA";

    private String value;
    public Gson gson = new Gson();
    private LinkedList<Alarm> alarmLinkedList;

    String jsonStr = "[" +
            "{" +
            "'ID': 'F8D5795F83154CFA92DF0CC73F434827'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '4'," +
            "'ERRORCODE': '0'," +
            "'ERRORSTRING': '未知错误'," +
            "'ERRORTIME': '2017/8/9 13:41:07'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'A844D64BDC38480B9719DE7FE8FC73B9'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '5'," +
            "'ERRORCODE': '13056'," +
            "'ERRORSTRING': '空取'," +
            "'ERRORTIME': '2017/8/9 12:47:25'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'BF26332BF182452B8CF7BEA2DA23BFD9'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '2'," +
            "'ERRORCODE': '13056'," +
            "'ERRORSTRING': '空取'," +
            "'ERRORTIME': '2017/8/9 12:31:05'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': '19CC7CC22BCB4789BCC755EEABA81916'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '3'," +
            "'ERRORCODE': '0'," +
            "'ERRORSTRING': '未知错误'," +
            "'ERRORTIME': '2017/8/9 15:24:22'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': '53CC31DCEA8E45928BEECD49D45B7D08'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '4'," +
            "'ERRORCODE': '14'," +
            "'ERRORSTRING': '前门没关'," +
            "'ERRORTIME': '2017/8/9 14:17:53'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'EB4240F5DA964B6097A26306D9E2EE17'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '3'," +
            "'STATIONNO': '1'," +
            "'ERRORCODE': '13056'," +
            "'ERRORSTRING': '空取'," +
            "'ERRORTIME': '2017/8/9 17:15:51'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'CBAD9B5F6A3D4B48AE6C545E0135562D'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '3'," +
            "'ERRORCODE': '9005'," +
            "'ERRORSTRING': '载货平台左光电检测超限'," +
            "'ERRORTIME': '2017/8/9 15:40:23'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'E8DAAE67E09343AFA1455C085B85745F'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '5'," +
            "'ERRORCODE': '125'," +
            "'ERRORSTRING': '载货平台左光电检测超限'," +
            "'ERRORTIME': '2017/8/9 12:52:09'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': '03CB43D7090A4F5D8948BE60CB6F1AC0'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '3'," +
            "'ERRORCODE': '30'," +
            "'ERRORSTRING': '反馈单元失去信号'," +
            "'ERRORTIME': '2017/8/9 15:42:20'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}," +
            "{" +
            "'ID': 'F3279C9E2A8A453A85AC1CA2ED0233AF'," +
            "'WAREHOUSENAME': '成品库'," +
            "'TYPE': 'SC'," +
            "'STATUS': '2'," +
            "'STATIONNO': '3'," +
            "'ERRORCODE': '9054'," +
            "'ERRORSTRING': '左超宽'," +
            "'ERRORTIME': '2017/8/9 15:36:19'," +
            "'TASKNO': '12345'," +
            "'FROMSTA': '1001'," +
            "'TOSTA': '1050'," +
            "'GRADE': '1'," +
            "'USERNAME': 'user1'," +
            "'METHOD': '111'" +
            "}" +
            "]";

    String ddjInfo = "厂商|德国德马泰克&" +
            "型号|SR-U 1200/1 SD&" +
            "规格|单货叉&" +
            "型式|单深度单立柱单工位&" +
            "自重（不含天、地轨）（Kg）|约9000KG&" +
            "地轨型号|S41&" +
            "天轨类型|125*65型钢&" +
            "行走驱动方式|矢量变频调速&" +
            "行走机构制动方式|电磁刹车&" +
            "行走电机功率（Kw）|7.5&" +
            "定位方式（X向）|激光测距&" +
            "定位（X向）精度（mm）|±3mm&" +
            "行走机构空载速度|200m/min&" +
            "行走机构载荷速度|200m/min&" +
            "载货外形（L×W×H）（mm）|1300L*900W*1000H&" +
            "荷载能力（Kg）|300KG&" +
            "载货台升降方式|链条传动&" +
            "载货台制动方式|电磁制动&" +
            "升降电机功率（Kw）|15&" +
            "载货台空载速度|85m/min&" +
            "载货台荷载速度|85m/min&" +
            "货叉类型|欧标货叉&" +
            "货叉定位方式|旋转编码器+微动开关&" +
            "货叉定位（Z向）精度（mm）|±3mm&" +
            "货叉伸叉速度|90m/min&" +
            "货叉电机功率（Kw）|1.5&" +
            "供电方式|防尘防溅七线滑触线&" +
            "电源规格|380三相五线制，无二次变压，进口底部滑触线&" +
            "单机噪声（高速）|≤75dB&" +
            "电机|SEW";

    String AGVInfo = "厂商|本体芬兰Rocla，控制今天国际&" +
            "型号规格|ATX12ff&" +
            "AGV小车形式|激光导引&" +
            "AGV小车数量|14台&" +
            "移载方式|落地后叉式&" +
            "车体外形（L×W×H）(mm）|2545×1176×2100&" +
            "最大外延（L×W×H）(mm）|2545×1176×2100&" +
            "前后轮距（mm）|1357mm&" +
            "车体质量（kg）不含蓄电池|932kg&" +
            "搬运货物质量（Kg）|1000kg&" +
            "搬运实托盘最大尺寸（L×W×H）(mm）|1250×1050×1200mm&" +
            "提升方式（液压或电动）|液压&" +
            "提升高度（mm）|1400mm&" +
            "空载提升速度（m/min）|0-12m/min&" +
            "实载提升速度（m/min）|0-12m/min&" +
            "驱动方式及转向方式|前轮驱动、前轮导向&" +
            "制动方式|电磁制动&" +
            "空载前进行走速度(m/min)|最大速度120m/min&" +
            "空载后退行走速度(m/min)|18m/min&" +
            "空载转弯行走速度(m/min)|18m/min&" +
            "载荷前进行走速度(m/min)|最大速度120m/min&" +
            "载荷后退行走速度(m/min)|18m/min&" +
            "载荷转弯行走速度(m/min)|18m/min&" +
            "行走电机型式|直流电机&" +
            "行走电机功率（kw）|4KW&" +
            "导引方式|激光导引方式&" +
            "通讯传输速度|11Mbit/s&" +
            "通讯发射功率|100mW&" +
            "激光器型号|4-2.0 DM&" +
            "激光器频率|2.4GHz&" +
            "激光波长|820nm&" +
            "激光直径|3mm&" +
            "控制系统|NDC8控制系统&" +
            "工作噪声,dB(A)|工作噪声,dB(A)&" +
            "主机颜色|标准色";

    String CSCInfo = "厂商|今天国际&" +
            "规格型号|CSC160L&" +
            "车体质量|750kg&" +
            "最大载重|1000kg&" +
            "移栽方式|链条&" +
            "移栽定位方式|光电&" +
            "移栽速度|12M/min&" +
            "行走速度|2.67m/sec&" +
            "载荷速度|2.67m/sec&" +
            "载荷行走定位精度|±5mm&" +
            "空载行走定位精度|±5mm&" +
            "行走电机|2.2-3.8KW&" +
            "移栽电机|0.55KW&" +
            "防撞方式|有&" +
            "控制系统|PLC&" +
            "认址方式|条码定位&" +
            "控制器|西门子PLC&" +
            "驱动技术|伺服&" +
            "通讯方式|光通讯&" +
            "供电方式|滑触线&" +
            "电源规格|380/220V三相五线制&" +
            "噪声|≦75DB";

    String InWareHouseSystem = "厂商|中国 德马&" +
            "分拣系统型号|717W&" +
            "分拣能力|2500件/小时&" +
            "设备形式|模块化&" +
            "转向拨岔机构|气缸驱动转向机构（荷兰CSI）&" +
            "条码识别器选型|Sick&" +
            "分拣差错率|0.00%&" +
            "皮带机皮带选型|西格林&" +
            "辊道机辊子要求|Φ50&" +
            "主机输送速度|54m/min&" +
            "件箱输送机输送速度|30m/min&" +
            "输送面高度|1500mm&" +
            "主机分拣角度|30度角&" +
            "抗干扰能力|强&" +
            "控制系统|PLC&" +
            "电机选型|SEW&" +
            "供电需求|380/220V，三相五线制&" +
            "装机容量（kVA）|45&" +
            "运行噪声,dB(A)|≤75&" +
            "主机颜色|标准色";
    String OutWareHouseSystem = "厂商|荷兰/范德兰德工业控股有限公司&" +
            "分拣系统型号|Posi2soter&" +
            "分拣能力|7500件/小时&" +
            "设备形式|滑块式分拣机&" +
            "转向拨岔机构|电动式&" +
            "条码识别器选型|Sick单面全方位条码阅读器&" +
            "分拣差错率|0.0001%，条码本身原因除外&" +
            "皮带机皮带选型|Siegling&" +
            "辊道机辊子要求|国产优质品牌&" +
            "主机输送速度|分拣机速度为132米/分钟&" +
            "件箱输送机输送速度|36米/分钟-120米/分钟&" +
            "输送面高度|导入输送机输送面高度2200mm；分拣机输送面高度约1180mm；分拣出口输送机接口输送面高约980mm&" +
            "主机分拣角度|20度角&" +
            "抗干扰能力|强&" +
            "控制系统|FSC(范德兰德专用控制器)&" +
            "电机选型|SEW&" +
            "供电需求|3N ~ + 380 ±10%VAC - 50 Hz&" +
            "装机容量（kVA）|90kw (105kVA)&" +
            "运行噪声,dB(A)|≤72&" +
            "主机颜色|标准色";

    String CpmjInfo = "厂商|中国德马&" +
            "型号规格|CT21&" +
            "形式|一体式&" +
            "拆（叠）盘高度（数量）|10个/组（1500mm,成品库）&" +
            "码盘定位精度（㎜）|±5㎜&" +
            "叠（拆）盘速度|200个/小时&" +
            "装机容量（kw）|1.1+0.18&" +
            "主机颜色|标准色";
    String LsssjInfo = "厂商|中国德马&" +
            "型号规格|DL-D1000&" +
            "形式|链式输送机&" +
            "机体材质|铝型材&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|最大1000 Kg/m&" +
            "输送能力（托盘/h）|最大180&" +
            "最大输送速度（m/min）|12--18&" +
            "货物定位方式|光电控制&" +
            "防货物冲出装置|阻挡装置&" +
            "装机容量（kw）|0.37-0.75&" +
            "主机颜色|标准色";

    String ksssjInfo = "厂商|中国德马&" +
            "型号规格|DG-S1000&" +
            "形式|辊筒输送机&" +
            "机体材质|碳钢折制&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|最大1000 Kg/m&" +
            "输送能力（托盘/h）|最大180&" +
            "最大输送速度（m/min）|12--18&" +
            "货物定位方式|光电控制&" +
            "防货物冲出装置|阻挡装置&" +
            "装机容量（kw）|0.37-0.75&" +
            "主机颜色|标准色";

    String ksdsInfo = "厂商|中国德马&" +
            "型号规格|DG-D1000&" +
            "形式|辊筒&" +
            "机体材质|碳钢折制&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|最大1000 Kg/m&" +
            "输送能力（托盘/h）|与设备长度有关&" +
            "最大输送速度（m/min）|12--18&" +
            "货物定位方式|光电控制&" +
            "防货物冲出装置|阻挡装置&" +
            "装机容量（kw）|0.37-0.75&" +
            "主机颜色|标准色";

    String lsdsInfo = "厂商|中国德马&" +
            "型号规格|DSL-D1000&" +
            "形式|双排链式&" +
            "机体材质|铝合金&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|最大1000 Kg/m&" +
            "输送能力（托盘/h）|与设备长度有关&" +
            "最大输送速度（m/min）|18m/min&" +
            "货物定位方式|光电定位&" +
            "防货物冲出装置|有&" +
            "装机容量（kw）|0.55+0.75B&" +
            "主机颜色|标准色";

    String ybksssjInfo = "厂商|中国德马&" +
            "型号规格|DG-Y1000&" +
            "形式|辊筒&" +
            "机体材质|冷弯型钢&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|300kg&" +
            "输送能力（托盘/h）|与设备长度有关&" +
            "最大输送速度（m/min）|12-18 m/min&" +
            "货物定位方式|变频控制&" +
            "防货物冲出装置|有&" +
            "装机容量（kw）|0.55kw&" +
            "主机颜色|标准色";

    String ybyzjInfo = "厂商|中国德马&" +
            "型号规格|DL-Y1000&" +
            "形式|链式&" +
            "机体材质|冷弯型钢&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|300kg&" +
            "输送能力（托盘/h）|与设备长度有关&" +
            "最大输送速度（m/min）|12-18 m/min&" +
            "货物定位方式|变频控制&" +
            "防货物冲出装置|有&" +
            "装机容量（kw）|0.75kw+0.37kw&" +
            "主机颜色|标准色";

    String yztInfo = "厂商|中国德马&" +
            "型号规格|DL-Y1000&" +
            "形式|链式&" +
            "机体材质|碳钢折制&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机&" +
            "输送质量（Kg）|最大1000 Kg/m&" +
            "输送能力（托盘/h）|与设备长度有关&" +
            "最大输送速度（m/min）|12-18 m/min&" +
            "货物定位方式|光电控制&" +
            "防货物冲出装置|阻挡装置&" +
            "装机容量（kw）|0.37-0.75&" +
            "主机颜色|标准色";

    String cztsjInfo = "输送单元载荷(Max)|300KG&" +
            "输送物尺寸|L1300*W900*H1000mm&" +
            "提升速度|45m/min&" +
            "升降电机|7.5KW,制动减速电机380V&" +
            "外框|钢板网&" +
            "搭载输送机|辊式输送机&" +
            "能力|50托盘/小时";

    String ybjbjInfo = "厂商|中国德马&" +
            "型号规格|JBJ&" +
            "形式|框架式&" +
            "机体材质|冷弯型钢&" +
            "涂装标准|喷塑&" +
            "驱动方式|电机驱动&" +
            "夹抱货物最大质量（Kg）|300&" +
            "夹抱能力（来料单元/h）|160&" +
            "最大输送速度（m/min）|9.5&" +
            "夹抱货物尺寸 (㎜)|1300×900×850&" +
            "货物定位方式|机械死挡&" +
            "防货物冲出装置|有&" +
            "装机容量（kw）|2.2&" +
            "主机颜色|标准色";

    String tpzdsdInfo = "额定升降能力pcs/h|180&" +
            "货物单元重量kg|600&" +
            "升降定位精度（mm）|±3&" +
            "货物单元规格(Max)：L×W×H，mm|1300×1010×1900&" +
            "载货辊台升降速度m/min|8-24(变频可调)&" +
            "辊道输送速度m/min|8-12(变频可调)&" +
            "电机功率：kW|3&" +
            "机体结构|钢结构";

    String MAGVInfo = "厂商|德国库卡（KUKA）&" +
            "机器人型号|KR150 R3100 Prime&" +
            "机器人规格|Prime&" +
            "自由度数|6关节&" +
            "工位数|码垛：四个工位&" +
            "有效抓举质量（kg）|150kg&" +
            "自重（kg）|1,114 kg&" +
            "动作半径（mm）|3100mm&" +
            "重复定位精度（mm）|±0.06mm&" +
            "水平旋转角度|±185o(370°)&" +
            "掉箱率|≤0.0001%&" +
            "平衡方式|机械式&" +
            "电气防护等级|IP65&" +
            "夹具（或吸盘）型式|多路小孔抗泄漏吸盘&" +
            "单机码垛（件/min）能力|12件/分钟&" +
            "单机拆垛（件/min）能力 |14件/分钟&" +
            "系统码垛（件/min）能力|10件/分钟&" +
            "系统拆垛（件/min）能力|12件/分钟&" +
            "与上位系统接口形式|ProfiBUS现场总线&" +
            "控制系统|工业PC，WindowsXP操作系统&" +
            "供电需求|380V，三相五线制&" +
            "装机容量（kVA）|7.3kw&" +
            "单机运行噪声,dB(A)|< 75db&" +
            "主机颜色|标准色";

    String CAGVInfo = "厂商|德国库卡（KUKA）&" +
            "机器人型号|KR150R2700 Extra&" +
            "机器人规格|Extra&" +
            "自由度数|6关节&" +
            "工位数|拆垛：2个工位&" +
            "有效抓举质量（kg）|150kg&" +
            "自重（kg）|1,068 kg&" +
            "动作半径（mm）|2700mm&" +
            "重复定位精度（mm）|±0.06mm&" +
            "水平旋转角度|±185o(370°)&" +
            "掉箱率|≤0.0001%&" +
            "平衡方式|机械式&" +
            "电气防护等级|IP65&" +
            "夹具（或吸盘）型式|多路小孔抗泄漏吸盘&" +
            "单机码垛（件/min）能力|12件/分钟&" +
            "单机拆垛（件/min）能力 |14件/分钟&" +
            "系统码垛（件/min）能力|10件/分钟&" +
            "系统拆垛（件/min）能力|12件/分钟&" +
            "与上位系统接口形式|ProfiBUS现场总线&" +
            "控制系统|工业PC，WindowsXP操作系统&" +
            "供电需求|380V，三相五线制&" +
            "装机容量（kVA）|7.3kw&" +
            "单机运行噪声,dB(A)|< 75db&" +
            "主机颜色|标准色";

    String VersionDetail = "v1.00|2017-08-20|1.UI初始化 2.数据初始化 3.逻辑初始化" +
            "&v2.01|2017-08-22|1.增加查看资产设备功能 2.增加数据报警功能 3.增加工单模块功能 4.增加个人信息功能" +
            "&v2.02|2017-08-24|1.增加巡更功能 2.增加数据监视功能,可监视当月每天数据并形成折线图 3.增加资产设备基本参数配置" +
            "&v2.03|2017-08-26|1.完善数据监视日历空间 2.完善数据报警详情页 3.增加资产设备图片" +
            "&v2.04|2017-08-27|1.更改APP图标 2.更改登录页UI界面 3.增加扫码功能"+
            "&v2.05|2017-08-30|1.增加版本信息查看功能 2.完善巡更功能，界面优化"+
            "&v2.06|2017-09-10|1.完善巡更功能,界面优化";

    @Override
    public void onCreate() {
        super.onCreate();

        alarmLinkedList = new LinkedList<Alarm>();

        java.lang.reflect.Type type = new TypeToken<Alarm>() {
        }.getType();
        try {
            JSONArray arr = new JSONArray(jsonStr);
            Log.d("jochen", "arr=" + arr.length());
//Toast.makeText(getApplicationContext(),"arr="+arr.length(),Toast.LENGTH_SHORT).show();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                Alarm alarmInstance = gson
                        .fromJson(temp.toString(), type);
                if (!alarmLinkedList.contains(alarmInstance)) {
                    alarmLinkedList.addLast(alarmInstance);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setValue(VALUE); // 初始化全局变量
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public LinkedList<Alarm> getData() {
        return alarmLinkedList;
    }

    public String getInfo(String name) {
        if (name.equals("AGV")) {
            return AGVInfo;
        } else if (name.equals("堆垛机")) {
            return ddjInfo;
        } else if (name.equals("链式输送机")) {
            return LsssjInfo;
        } else if (name.equals("辊式输送机")||name.equals("入库输送机")) {
            return ksssjInfo;
        } else if (name.equals("辊式顶升移栽机")) {
            return ksdsInfo;
        } else if (name.equals("链式顶升移栽机")||name.equals("出库输送机")) {
            return lsdsInfo;
        } else if (name.equals("拆码盘机")||name.equals("拆盘机")||name.equals("码盘机")) {
            return CpmjInfo;
        } else if (name.equals("旋转台")) {
            return yztInfo;
        } else if (name.equals("入库分拣系统")||name.equals("成品入库分拣")) {
            return InWareHouseSystem;
        } else if (name.equals("出库分拣系统")) {
            return OutWareHouseSystem;
        } else if (name.equals("码垛机器人")) {
            return MAGVInfo;
        } else if (name.equals("拆垛机器人")) {
            return CAGVInfo;
        } else if (name.equals("托盘自动落地设备")) {
            return tpzdsdInfo;
        } else if (name.equals("穿梭车")) {
            return CSCInfo;
        } else if (name.equals("烟包夹包机")||name.equals("夹抱机")) {
            return ybjbjInfo;
        } else if (name.equals("垂直提升机")||name.equals("升降机")) {
            return cztsjInfo;
        } else {
            return "null";
        }
    }

    public String GetVersionDetail() {
        return VersionDetail;
    }
}

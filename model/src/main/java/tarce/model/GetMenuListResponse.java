package tarce.model;

import java.util.List;

/**
 * Created by Daniel.Xu on 2017/2/6.
 */

public class GetMenuListResponse {

    /**
     * res_data : [{"user_id":1,"name":"讨论","web_icon":"mail,static/description/icon.png","sequence":1,"children":[],"parent_id":false,"action":"ir.actions.client,88","id":79},{"user_id":1,"name":"日历","web_icon":"calendar,static/description/icon.png","sequence":2,"children":[],"parent_id":false,"action":"ir.actions.act_window,324","id":236},{"user_id":1,"name":"联系人","web_icon":"contacts,static/description/icon.png","sequence":4,"children":[],"parent_id":false,"action":"ir.actions.act_window,548","id":390},{"xmlid":"sales_team.menu_base_partner","user_id":1,"name":"销售","web_icon":"crm,static/description/icon.png","sequence":6,"children":[{"web_icon_data":false,"name":"仪表板","web_icon":false,"sequence":1,"children":[],"parent_id":[144,"销售"],"action":"ir.actions.act_window,234","id":158},{"web_icon_data":false,"xmlid":"sales_team.menu_sales","name":"销售","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"客户","web_icon":false,"sequence":2,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.act_window,549","id":146},{"web_icon_data":false,"name":"我的漏斗","web_icon":false,"sequence":4,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.server,348","id":248},{"web_icon_data":false,"name":"下一步活动","web_icon":false,"sequence":5,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.act_window,350","id":249},{"web_icon_data":false,"name":"报价单","web_icon":false,"sequence":11,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.act_window,243","id":179},{"web_icon_data":false,"name":"销售订单","web_icon":false,"sequence":12,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.act_window,240","id":175},{"web_icon_data":false,"name":"产品","web_icon":false,"sequence":13,"children":[],"parent_id":[145,"销售/销售"],"action":"ir.actions.act_window,550","id":164}],"parent_id":[144,"销售"],"action":false,"id":145},{"web_icon_data":false,"name":"开票","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"客户对账单","web_icon":false,"sequence":1,"children":[],"parent_id":[176,"销售/开票"],"action":"ir.actions.act_window,580","id":413},{"web_icon_data":false,"name":"待开发票销售","web_icon":false,"sequence":2,"children":[],"parent_id":[176,"销售/开票"],"action":"ir.actions.act_window,241","id":177},{"web_icon_data":false,"name":"收款登记","web_icon":false,"sequence":2,"children":[],"parent_id":[176,"销售/开票"],"action":"ir.actions.act_window,554","id":393},{"web_icon_data":false,"name":"超售订单","web_icon":false,"sequence":5,"children":[],"parent_id":[176,"销售/开票"],"action":"ir.actions.act_window,242","id":178}],"parent_id":[144,"销售"],"action":false,"id":176},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":99,"children":[{"web_icon_data":false,"name":"漏斗","web_icon":false,"sequence":5,"children":[],"parent_id":[159,"销售/报告"],"action":"ir.actions.act_window,337","id":241},{"web_icon_data":false,"name":"活动","web_icon":false,"sequence":7,"children":[],"parent_id":[159,"销售/报告"],"action":"ir.actions.act_window,335","id":240},{"web_icon_data":false,"name":"销售","web_icon":false,"sequence":10,"children":[],"parent_id":[159,"销售/报告"],"action":"ir.actions.act_window,238","id":161}],"parent_id":[144,"销售"],"action":false,"id":159},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[142,"销售/配置"],"action":"ir.actions.act_window,233","id":147},{"web_icon_data":false,"name":"价格表","web_icon":false,"sequence":3,"children":[{"web_icon_data":false,"name":"价格表","web_icon":false,"sequence":1,"children":[],"parent_id":[173,"销售/配置/价格表"],"action":"ir.actions.act_window,118","id":174}],"parent_id":[142,"销售/配置"],"action":false,"id":173},{"web_icon_data":false,"name":"线索及商机","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"活动","web_icon":false,"sequence":10,"children":[],"parent_id":[243,"销售/配置/线索及商机"],"action":"ir.actions.act_window,340","id":245}],"parent_id":[142,"销售/配置"],"action":false,"id":243},{"web_icon_data":false,"name":"销售团队","web_icon":false,"sequence":10,"children":[],"parent_id":[142,"销售/配置"],"action":"ir.actions.act_window,236","id":160},{"web_icon_data":false,"name":"交货","web_icon":false,"sequence":12,"children":[{"web_icon_data":false,"name":"交货方式","web_icon":false,"sequence":1,"children":[],"parent_id":[387,"销售/配置/交货"],"action":"ir.actions.act_window,547","id":389}],"parent_id":[142,"销售/配置"],"action":false,"id":387}],"parent_id":[144,"销售"],"action":false,"id":142}],"parent_id":false,"action":false,"id":144},{"user_id":1,"name":"采购","web_icon":"purchase,static/description/icon.png","sequence":25,"children":[{"web_icon_data":false,"name":"采购","web_icon":false,"sequence":1,"children":[{"web_icon_data":false,"name":"询价单","web_icon":false,"sequence":0,"children":[],"parent_id":[278,"采购/采购"],"action":"ir.actions.act_window,415","id":292},{"web_icon_data":false,"name":"采购订单","web_icon":false,"sequence":6,"children":[],"parent_id":[278,"采购/采购"],"action":"ir.actions.act_window,416","id":293},{"web_icon_data":false,"name":"供应商","web_icon":false,"sequence":15,"children":[],"parent_id":[278,"采购/采购"],"action":"ir.actions.act_window,57","id":279},{"web_icon_data":false,"name":"供应商价格表","web_icon":false,"sequence":16,"children":[],"parent_id":[278,"采购/采购"],"action":"ir.actions.act_window,116","id":281},{"web_icon_data":false,"name":"产品","web_icon":false,"sequence":20,"children":[],"parent_id":[278,"采购/采购"],"action":"ir.actions.act_window,411","id":289}],"parent_id":[277,"采购"],"action":false,"id":278},{"web_icon_data":false,"name":"控制","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"付款申请","web_icon":false,"sequence":5,"children":[],"parent_id":[286,"采购/控制"],"action":"ir.actions.act_window,553","id":391},{"web_icon_data":false,"name":"入库确认","web_icon":false,"sequence":5,"children":[],"parent_id":[286,"采购/控制"],"action":"ir.actions.act_window,578","id":411},{"web_icon_data":false,"name":"进货的产品","web_icon":false,"sequence":11,"children":[],"parent_id":[286,"采购/控制"],"action":"ir.actions.act_window,304","id":287},{"web_icon_data":false,"name":"供应商帐单","web_icon":false,"sequence":13,"children":[],"parent_id":[286,"采购/控制"],"action":"ir.actions.act_window,407","id":288}],"parent_id":[277,"采购"],"action":false,"id":286},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":99,"children":[],"parent_id":[277,"采购"],"action":"ir.actions.act_window,420","id":294},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[280,"采购/配置"],"action":"ir.actions.act_window,421","id":295}],"parent_id":[277,"采购"],"action":false,"id":280}],"parent_id":false,"action":false,"id":277},{"user_id":1,"name":"库存","web_icon":"stock,static/description/icon.png","sequence":30,"children":[{"web_icon_data":false,"name":"仪表板","web_icon":false,"sequence":0,"children":[],"parent_id":[181,"库存"],"action":"ir.actions.act_window,275","id":202},{"web_icon_data":false,"name":"操作","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"所有调拨","web_icon":false,"sequence":2,"children":[],"parent_id":[182,"库存/操作"],"action":"ir.actions.act_window,277","id":204}],"parent_id":[181,"库存"],"action":false,"id":182},{"web_icon_data":false,"name":"库存管理","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"产品","web_icon":false,"sequence":1,"children":[],"parent_id":[195,"库存/库存管理"],"action":"ir.actions.act_window,309","id":220},{"web_icon_data":false,"name":"订货规则","web_icon":false,"sequence":10,"children":[],"parent_id":[195,"库存/库存管理"],"action":"ir.actions.act_window,300","id":218},{"web_icon_data":false,"name":"库存调整","web_icon":false,"sequence":30,"children":[],"parent_id":[195,"库存/库存管理"],"action":"ir.actions.act_window,271","id":199},{"web_icon_data":false,"name":"报废","web_icon":false,"sequence":99,"children":[],"parent_id":[195,"库存/库存管理"],"action":"ir.actions.act_window,270","id":198}],"parent_id":[181,"库存"],"action":false,"id":195},{"web_icon_data":false,"name":"调度","web_icon":false,"sequence":50,"children":[{"web_icon_data":false,"name":"运行调度","web_icon":false,"sequence":1,"children":[],"parent_id":[211,"库存/调度"],"action":"ir.actions.act_window,219","id":213},{"web_icon_data":false,"name":"运行订货规则","web_icon":false,"sequence":2,"children":[],"parent_id":[211,"库存/调度"],"action":"ir.actions.act_window,269","id":212}],"parent_id":[181,"库存"],"action":false,"id":211},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":99,"children":[{"web_icon_data":false,"name":"库存计价","web_icon":false,"sequence":120,"children":[],"parent_id":[196,"库存/报告"],"action":"ir.actions.act_window,288","id":205},{"web_icon_data":false,"name":"库存移动","web_icon":false,"sequence":130,"children":[],"parent_id":[196,"库存/报告"],"action":"ir.actions.act_window,303","id":219},{"web_icon_data":false,"name":"补货异常","web_icon":false,"sequence":150,"children":[],"parent_id":[196,"库存/报告"],"action":"ir.actions.act_window,221","id":215}],"parent_id":[181,"库存"],"action":false,"id":196},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[183,"库存/配置"],"action":"ir.actions.act_window,310","id":222},{"web_icon_data":false,"name":"仓库管理","web_icon":false,"sequence":1,"children":[{"web_icon_data":false,"name":"地点","web_icon":false,"sequence":2,"children":[],"parent_id":[184,"库存/配置/仓库管理"],"action":"ir.actions.act_window,294","id":208},{"web_icon_data":false,"name":"作业类型","web_icon":false,"sequence":4,"children":[],"parent_id":[184,"库存/配置/仓库管理"],"action":"ir.actions.act_window,276","id":203}],"parent_id":[183,"库存/配置"],"action":false,"id":184},{"web_icon_data":false,"name":"路线","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"路线","web_icon":false,"sequence":1,"children":[],"parent_id":[185,"库存/配置/路线"],"action":"ir.actions.act_window,296","id":210}],"parent_id":[183,"库存/配置"],"action":false,"id":185},{"web_icon_data":false,"name":"产品","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"产品类别","web_icon":false,"sequence":2,"children":[],"parent_id":[188,"库存/配置/产品"],"action":"ir.actions.act_window,114","id":189}],"parent_id":[183,"库存/配置"],"action":false,"id":188},{"web_icon_data":false,"name":"交货","web_icon":false,"sequence":50,"children":[{"web_icon_data":false,"name":"交货方式","web_icon":false,"sequence":1,"children":[],"parent_id":[386,"库存/配置/交货"],"action":"ir.actions.act_window,547","id":388}],"parent_id":[183,"库存/配置"],"action":false,"id":386}],"parent_id":[181,"库存"],"action":false,"id":183}],"parent_id":false,"action":false,"id":181},{"user_id":1,"name":"制造","web_icon":"mrp,static/description/icon.png","sequence":35,"children":[{"web_icon_data":false,"name":"操作","web_icon":false,"sequence":10,"children":[{"web_icon_data":false,"name":"制造订单","web_icon":false,"sequence":1,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,388","id":268},{"web_icon_data":false,"name":"拆解单","web_icon":false,"sequence":20,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,402","id":275},{"web_icon_data":false,"name":"已确认的订单","web_icon":false,"sequence":21,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,597","id":428},{"web_icon_data":false,"name":"等待备料的订单","web_icon":false,"sequence":22,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,598","id":429},{"web_icon_data":false,"name":"备料中的订单","web_icon":false,"sequence":23,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,599","id":430},{"web_icon_data":false,"name":"已完成备料的订单","web_icon":false,"sequence":24,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,600","id":431},{"web_icon_data":false,"name":"报废","web_icon":false,"sequence":25,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,270","id":262},{"web_icon_data":false,"name":"Already Picking Orders","web_icon":false,"sequence":25,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,601","id":432},{"web_icon_data":false,"name":"工单消息","web_icon":false,"sequence":26,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,369","id":263},{"web_icon_data":false,"name":"生产中的订单","web_icon":false,"sequence":26,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,602","id":433},{"web_icon_data":false,"name":"等待品检的订单","web_icon":false,"sequence":27,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,603","id":434},{"web_icon_data":false,"name":"品检中的订单","web_icon":false,"sequence":28,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,604","id":435},{"web_icon_data":false,"name":"等待清点物料的订单","web_icon":false,"sequence":29,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,605","id":436},{"web_icon_data":false,"name":"等待仓库检验退料的订单","web_icon":false,"sequence":30,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,606","id":437},{"web_icon_data":false,"name":"等待入库的订单","web_icon":false,"sequence":31,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,607","id":438},{"web_icon_data":false,"name":"已完成的订单","web_icon":false,"sequence":32,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,608","id":439},{"web_icon_data":false,"name":"已取消的生产单","web_icon":false,"sequence":32,"children":[],"parent_id":[254,"制造/操作"],"action":"ir.actions.act_window,609","id":440}],"parent_id":[253,"制造"],"action":false,"id":254},{"web_icon_data":false,"name":"主数据","web_icon":false,"sequence":20,"children":[{"web_icon_data":false,"name":"产品","web_icon":false,"sequence":10,"children":[],"parent_id":[255,"制造/主数据"],"action":"ir.actions.act_window,400","id":274},{"web_icon_data":false,"name":"ProductProduct","web_icon":false,"sequence":10,"children":[],"parent_id":[255,"制造/主数据"],"action":"ir.actions.act_window,579","id":412},{"web_icon_data":false,"name":"物料清单","web_icon":false,"sequence":99,"children":[],"parent_id":[255,"制造/主数据"],"action":"ir.actions.act_window,396","id":273}],"parent_id":[253,"制造"],"action":false,"id":255},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":25,"children":[{"web_icon_data":false,"name":"制造订单","web_icon":false,"sequence":11,"children":[],"parent_id":[256,"制造/报告"],"action":"ir.actions.act_window,392","id":270}],"parent_id":[253,"制造"],"action":false,"id":256},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[257,"制造/配置"],"action":"ir.actions.act_window,403","id":276}],"parent_id":[253,"制造"],"action":false,"id":257}],"parent_id":false,"action":false,"id":253},{"xmlid":"account.menu_finance","user_id":1,"name":"会计","web_icon":"account,static/description/icon.png","sequence":40,"children":[{"web_icon_data":false,"name":"仪表板","web_icon":false,"sequence":1,"children":[],"parent_id":[86,"会计"],"action":"ir.actions.act_window,207","id":128},{"web_icon_data":false,"name":"销售","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"客户发票","web_icon":false,"sequence":1,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,196","id":118},{"web_icon_data":false,"name":"收款登记","web_icon":false,"sequence":5,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,554","id":392},{"web_icon_data":false,"name":"销售收据","web_icon":false,"sequence":10,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,593","id":426},{"web_icon_data":false,"name":"付款","web_icon":false,"sequence":20,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,122","id":101},{"web_icon_data":false,"name":"客户","web_icon":false,"sequence":100,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,56","id":112},{"web_icon_data":false,"name":"可销售产品","web_icon":false,"sequence":110,"children":[],"parent_id":[87,"会计/销售"],"action":"ir.actions.act_window,117","id":113}],"parent_id":[86,"会计"],"action":false,"id":87},{"web_icon_data":false,"name":"采购","web_icon":false,"sequence":3,"children":[{"web_icon_data":false,"name":"供应商帐单","web_icon":false,"sequence":1,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,197","id":119},{"web_icon_data":false,"name":"采购收据","web_icon":false,"sequence":4,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,594","id":427},{"web_icon_data":false,"name":"付款","web_icon":false,"sequence":20,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,123","id":102},{"web_icon_data":false,"name":"供应商","web_icon":false,"sequence":100,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,57","id":114},{"web_icon_data":false,"name":"可采购产品","web_icon":false,"sequence":110,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,411","id":290},{"web_icon_data":false,"name":"付款申请","web_icon":false,"sequence":110,"children":[],"parent_id":[88,"会计/采购"],"action":"ir.actions.act_window,595","id":394}],"parent_id":[86,"会计"],"action":false,"id":88},{"web_icon_data":false,"name":"顾问","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"日记账分录","web_icon":false,"sequence":2,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,165","id":110},{"web_icon_data":false,"name":"科目表","web_icon":false,"sequence":20,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,129","id":103},{"web_icon_data":false,"name":"Manual Payments & Invoices Matching","web_icon":false,"sequence":25,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.client,177","id":117},{"web_icon_data":false,"name":"分析账户","web_icon":false,"sequence":30,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,99","id":121},{"web_icon_data":false,"name":"Make Manual Tax Adjustments","web_icon":false,"sequence":30,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,217","id":137},{"web_icon_data":false,"name":"分析分录","web_icon":false,"sequence":35,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,101","id":122},{"web_icon_data":false,"name":"预算","web_icon":false,"sequence":60,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,539","id":379},{"web_icon_data":false,"name":"资产","web_icon":false,"sequence":101,"children":[],"parent_id":[89,"会计/顾问"],"action":"ir.actions.act_window,544","id":382}],"parent_id":[86,"会计"],"action":false,"id":89},{"web_icon_data":false,"xmlid":"account.menu_finance_reports","name":"报告","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"管理","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"预算","web_icon":false,"sequence":20,"children":[],"parent_id":[91,"会计/报告/管理"],"action":"ir.actions.act_window,540","id":380},{"web_icon_data":false,"name":"资产","web_icon":false,"sequence":21,"children":[],"parent_id":[91,"会计/报告/管理"],"action":"ir.actions.act_window,546","id":385}],"parent_id":[90,"会计/报告"],"action":false,"id":91},{"web_icon_data":false,"name":"商务智能","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"发票","web_icon":false,"sequence":20,"children":[],"parent_id":[92,"会计/报告/商务智能"],"action":"ir.actions.act_window,205","id":127},{"web_icon_data":false,"name":"分析分录","web_icon":false,"sequence":35,"children":[],"parent_id":[92,"会计/报告/商务智能"],"action":"ir.actions.act_window,201","id":124}],"parent_id":[90,"会计/报告"],"action":false,"id":92},{"web_icon_data":false,"name":"PDF报告","web_icon":false,"sequence":10,"children":[{"web_icon_data":false,"name":"销售/采购日记账","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,209","id":129},{"web_icon_data":false,"name":"业务伙伴分类账","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,210","id":130},{"web_icon_data":false,"name":"总账","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,211","id":131},{"web_icon_data":false,"name":"试算表","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,212","id":132},{"web_icon_data":false,"name":"资产负债表","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,213","id":133},{"web_icon_data":false,"name":"损益表","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,214","id":134},{"web_icon_data":false,"name":"到期的业务伙伴余额","web_icon":false,"sequence":10,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,216","id":136},{"web_icon_data":false,"name":"财务报告","web_icon":false,"sequence":100,"children":[],"parent_id":[93,"会计/报告/PDF报告"],"action":"ir.actions.act_window,215","id":135}],"parent_id":[90,"会计/报告"],"action":false,"id":93}],"parent_id":[86,"会计"],"action":false,"id":90},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":15,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[94,"会计/配置"],"action":"ir.actions.act_window,203","id":126},{"web_icon_data":false,"name":"会计","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"税金","web_icon":false,"sequence":1,"children":[],"parent_id":[95,"会计/配置/会计"],"action":"ir.actions.act_window,158","id":108},{"web_icon_data":false,"name":"财政状况","web_icon":false,"sequence":3,"children":[],"parent_id":[95,"会计/配置/会计"],"action":"ir.actions.act_window,200","id":120},{"web_icon_data":false,"name":"银行帐户","web_icon":false,"sequence":10,"children":[],"parent_id":[95,"会计/配置/会计"],"action":"ir.actions.act_window,143","id":104},{"web_icon_data":false,"name":"日记账","web_icon":false,"sequence":10,"children":[],"parent_id":[95,"会计/配置/会计"],"action":"ir.actions.act_window,144","id":105}],"parent_id":[94,"会计/配置"],"action":false,"id":95},{"web_icon_data":false,"name":"管理","web_icon":false,"sequence":3,"children":[{"web_icon_data":false,"name":"预算状况","web_icon":false,"sequence":1,"children":[],"parent_id":[96,"会计/配置/管理"],"action":"ir.actions.act_window,538","id":378},{"web_icon_data":false,"name":"资产类型","web_icon":false,"sequence":1,"children":[],"parent_id":[96,"会计/配置/管理"],"action":"ir.actions.act_window,545","id":384},{"web_icon_data":false,"name":"付款条款","web_icon":false,"sequence":4,"children":[],"parent_id":[96,"会计/配置/管理"],"action":"ir.actions.act_window,168","id":111}],"parent_id":[94,"会计/配置"],"action":false,"id":96},{"web_icon_data":false,"name":"分析会计","web_icon":false,"sequence":5,"children":[{"web_icon_data":false,"name":"分析账户","web_icon":false,"sequence":0,"children":[],"parent_id":[97,"会计/配置/分析会计"],"action":"ir.actions.act_window,100","id":123}],"parent_id":[94,"会计/配置"],"action":false,"id":97},{"web_icon_data":false,"name":"财务报告","web_icon":false,"sequence":10,"children":[{"web_icon_data":false,"name":"会计报告","web_icon":false,"sequence":10,"children":[],"parent_id":[100,"会计/配置/财务报告"],"action":"ir.actions.act_window,175","id":115},{"web_icon_data":false,"name":"会计报告层级","web_icon":false,"sequence":10,"children":[],"parent_id":[100,"会计/配置/财务报告"],"action":"ir.actions.act_window,176","id":116}],"parent_id":[94,"会计/配置"],"action":false,"id":100},{"web_icon_data":false,"name":"付款","web_icon":false,"sequence":10,"children":[{"web_icon_data":false,"name":"付款方式","web_icon":false,"sequence":10,"children":[],"parent_id":[138,"会计/配置/付款"],"action":"ir.actions.act_window,229","id":139},{"web_icon_data":false,"name":"付款交易","web_icon":false,"sequence":20,"children":[],"parent_id":[138,"会计/配置/付款"],"action":"ir.actions.act_window,230","id":140}],"parent_id":[94,"会计/配置"],"action":false,"id":138}],"parent_id":[86,"会计"],"action":false,"id":94}],"parent_id":false,"action":false,"id":86},{"user_id":1,"name":"工资表","web_icon":"hr_payroll,static/description/icon.png","sequence":45,"children":[{"web_icon_data":false,"name":"员工工资单","web_icon":false,"sequence":10,"children":[],"parent_id":[353,"工资表"],"action":"ir.actions.act_window,508","id":357},{"web_icon_data":false,"name":"工资单批处理","web_icon":false,"sequence":10,"children":[],"parent_id":[353,"工资表"],"action":"ir.actions.act_window,515","id":362},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"薪资结构","web_icon":false,"sequence":2,"children":[],"parent_id":[354,"工资表/配置"],"action":"ir.actions.act_window,505","id":355},{"web_icon_data":false,"name":"薪资结构层级","web_icon":false,"sequence":2,"children":[],"parent_id":[354,"工资表/配置"],"action":"ir.actions.act_window,506","id":356},{"web_icon_data":false,"name":"薪资规则","web_icon":false,"sequence":12,"children":[],"parent_id":[354,"工资表/配置"],"action":"ir.actions.act_window,513","id":361},{"web_icon_data":false,"name":"供款记录","web_icon":false,"sequence":14,"children":[],"parent_id":[354,"工资表/配置"],"action":"ir.actions.act_window,512","id":360}],"parent_id":[353,"工资表"],"action":false,"id":354}],"parent_id":false,"action":false,"id":353},{"xmlid":"project.menu_main_pm","user_id":1,"name":"项目","web_icon":"project,static/description/icon.png","sequence":50,"children":[{"web_icon_data":false,"xmlid":"project.menu_projects","name":"仪表板","web_icon":false,"sequence":1,"children":[],"parent_id":[303,"项目"],"action":"ir.actions.act_window,435","id":309},{"web_icon_data":false,"name":"搜索","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"任务","web_icon":false,"sequence":5,"children":[],"parent_id":[304,"项目/搜索"],"action":"ir.actions.act_window,437","id":306}],"parent_id":[303,"项目"],"action":false,"id":304},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":99,"children":[{"web_icon_data":false,"name":"任务分析","web_icon":false,"sequence":10,"children":[],"parent_id":[312,"项目/报告"],"action":"ir.actions.act_window,430","id":313}],"parent_id":[303,"项目"],"action":false,"id":312},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[305,"项目/配置"],"action":"ir.actions.act_window,443","id":314},{"web_icon_data":false,"name":"阶段","web_icon":false,"sequence":3,"children":[],"parent_id":[305,"项目/配置"],"action":"ir.actions.act_window,440","id":308},{"web_icon_data":false,"name":"项目","web_icon":false,"sequence":10,"children":[],"parent_id":[305,"项目/配置"],"action":"ir.actions.act_window,436","id":310}],"parent_id":[303,"项目"],"action":false,"id":305}],"parent_id":false,"action":false,"id":303},{"user_id":1,"name":"员工","web_icon":"hr,static/description/icon.png","sequence":75,"children":[{"web_icon_data":false,"name":"员工","web_icon":false,"sequence":3,"children":[],"parent_id":[296,"员工"],"action":"ir.actions.act_window,424","id":300},{"web_icon_data":false,"name":"合约","web_icon":false,"sequence":4,"children":[],"parent_id":[296,"员工"],"action":"ir.actions.act_window,502","id":352},{"web_icon_data":false,"name":"部门","web_icon":false,"sequence":90,"children":[],"parent_id":[296,"员工"],"action":"ir.actions.act_window,429","id":302}],"parent_id":false,"action":false,"id":296},{"user_id":1,"name":"出勤","web_icon":"hr_attendance,static/description/icon.png","sequence":90,"children":[{"web_icon_data":false,"name":"我的出勤","web_icon":false,"sequence":10,"children":[],"parent_id":[329,"出勤"],"action":"ir.actions.client,471","id":330},{"web_icon_data":false,"name":"管理出勤","web_icon":false,"sequence":20,"children":[{"web_icon_data":false,"name":"出勤","web_icon":false,"sequence":10,"children":[],"parent_id":[331,"出勤/管理出勤"],"action":"ir.actions.act_window,467","id":332},{"web_icon_data":false,"name":"员工","web_icon":false,"sequence":15,"children":[],"parent_id":[331,"出勤/管理出勤"],"action":"ir.actions.act_window,424","id":333},{"web_icon_data":false,"name":"Kiosk Mode","web_icon":false,"sequence":20,"children":[],"parent_id":[331,"出勤/管理出勤"],"action":"ir.actions.client,470","id":334}],"parent_id":[329,"出勤"],"action":false,"id":331},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":30,"children":[],"parent_id":[329,"出勤"],"action":"ir.actions.act_window,468","id":335},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":99,"children":[],"parent_id":[329,"出勤"],"action":"ir.actions.act_window,475","id":336}],"parent_id":false,"action":false,"id":329},{"user_id":1,"name":"休假","web_icon":"hr_holidays,static/description/icon.png","sequence":95,"children":[{"web_icon_data":false,"name":"仪表板","web_icon":false,"sequence":1,"children":[],"parent_id":[337,"休假"],"action":"ir.actions.act_window,476","id":338},{"web_icon_data":false,"name":"我的休假","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"休假摘要","web_icon":false,"sequence":21,"children":[],"parent_id":[339,"休假/我的休假"],"action":"ir.actions.act_window,481","id":344},{"web_icon_data":false,"name":"休假申请","web_icon":false,"sequence":22,"children":[],"parent_id":[339,"休假/我的休假"],"action":"ir.actions.act_window,477","id":342},{"web_icon_data":false,"name":"分配申请","web_icon":false,"sequence":40,"children":[],"parent_id":[339,"休假/我的休假"],"action":"ir.actions.act_window,479","id":343}],"parent_id":[337,"休假"],"action":false,"id":339},{"web_icon_data":false,"name":"待批准休假","web_icon":false,"sequence":3,"children":[{"web_icon_data":false,"name":"休假","web_icon":false,"sequence":1,"children":[],"parent_id":[340,"休假/待批准休假"],"action":"ir.actions.act_window,483","id":345},{"web_icon_data":false,"name":"休假分配","web_icon":false,"sequence":2,"children":[],"parent_id":[340,"休假/待批准休假"],"action":"ir.actions.act_window,484","id":346}],"parent_id":[337,"休假"],"action":false,"id":340},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":99,"children":[{"web_icon_data":false,"name":"休假详细描述","web_icon":false,"sequence":4,"children":[],"parent_id":[341,"休假/报告"],"action":"ir.actions.act_window,482","id":347},{"web_icon_data":false,"name":"休假","web_icon":false,"sequence":5,"children":[],"parent_id":[341,"休假/报告"],"action":"ir.actions.act_window,498","id":350},{"web_icon_data":false,"name":"休假按部门","web_icon":false,"sequence":10,"children":[],"parent_id":[341,"休假/报告"],"action":"ir.actions.act_window,495","id":349}],"parent_id":[337,"休假"],"action":false,"id":341},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[],"parent_id":[337,"休假"],"action":"ir.actions.act_window,485","id":348}],"parent_id":false,"action":false,"id":337},{"xmlid":"hr_expense.menu_hr_expense_root","user_id":1,"name":"费用","web_icon":"hr_expense,static/description/icon.png","sequence":100,"children":[{"web_icon_data":false,"name":"我的费用","web_icon":false,"sequence":1,"children":[{"web_icon_data":false,"name":"待提交的费用","web_icon":false,"sequence":1,"children":[],"parent_id":[396,"费用/我的费用"],"action":"ir.actions.act_window,558","id":397},{"web_icon_data":false,"name":"否决的费用","web_icon":false,"sequence":2,"children":[],"parent_id":[396,"费用/我的费用"],"action":"ir.actions.act_window,559","id":398},{"web_icon_data":false,"xmlid":"hr_expense.menu_hr_expense_sheet_my_reports","name":"费用报告","web_icon":false,"sequence":3,"children":[],"parent_id":[396,"费用/我的费用"],"action":"ir.actions.act_window,563","id":399}],"parent_id":[395,"费用"],"action":false,"id":396},{"web_icon_data":false,"name":"待批准","web_icon":false,"sequence":2,"children":[{"web_icon_data":false,"name":"待批准的费用报告","web_icon":false,"sequence":1,"children":[],"parent_id":[400,"费用/待批准"],"action":"ir.actions.act_window,564","id":401},{"web_icon_data":false,"name":"已审核的费用报告","web_icon":false,"sequence":1,"children":[],"parent_id":[400,"费用/待批准"],"action":"ir.actions.act_window,581","id":414},{"web_icon_data":false,"name":"所有报销单","web_icon":false,"sequence":1,"children":[],"parent_id":[400,"费用/待批准"],"action":"ir.actions.act_window,582","id":415}],"parent_id":[395,"费用"],"action":false,"id":400},{"web_icon_data":false,"name":"会计师","web_icon":false,"sequence":3,"children":[{"web_icon_data":false,"name":"待过账的费用报告","web_icon":false,"sequence":2,"children":[],"parent_id":[402,"费用/会计师"],"action":"ir.actions.act_window,565","id":403},{"web_icon_data":false,"name":"待支付的费用报告","web_icon":false,"sequence":3,"children":[],"parent_id":[402,"费用/会计师"],"action":"ir.actions.act_window,566","id":404}],"parent_id":[395,"费用"],"action":false,"id":402},{"web_icon_data":false,"name":"报告","web_icon":false,"sequence":4,"children":[{"web_icon_data":false,"name":"所有费用","web_icon":false,"sequence":0,"children":[],"parent_id":[405,"费用/报告"],"action":"ir.actions.act_window,557","id":406},{"web_icon_data":false,"name":"所有费用报告","web_icon":false,"sequence":4,"children":[],"parent_id":[405,"费用/报告"],"action":"ir.actions.act_window,567","id":407}],"parent_id":[395,"费用"],"action":false,"id":405},{"web_icon_data":false,"name":"暂支","web_icon":false,"sequence":10,"children":[{"web_icon_data":false,"name":"暂支申请","web_icon":false,"sequence":0,"children":[],"parent_id":[420,"费用/暂支"],"action":"ir.actions.act_window,585","id":421},{"web_icon_data":false,"name":"所有暂支","web_icon":false,"sequence":0,"children":[],"parent_id":[420,"费用/暂支"],"action":"ir.actions.act_window,589","id":424},{"web_icon_data":false,"name":"待付款暂支","web_icon":false,"sequence":0,"children":[],"parent_id":[420,"费用/暂支"],"action":"ir.actions.act_window,586","id":425},{"web_icon_data":false,"name":"待审核暂支","web_icon":false,"sequence":10,"children":[],"parent_id":[420,"费用/暂支"],"action":"ir.actions.act_window,587","id":422},{"web_icon_data":false,"name":"已审核暂支","web_icon":false,"sequence":10,"children":[],"parent_id":[420,"费用/暂支"],"action":"ir.actions.act_window,588","id":423}],"parent_id":[395,"费用"],"action":false,"id":420},{"web_icon_data":false,"name":"配置","web_icon":false,"sequence":100,"children":[{"web_icon_data":false,"name":"设置","web_icon":false,"sequence":0,"children":[],"parent_id":[408,"费用/配置"],"action":"ir.actions.act_window,575","id":410},{"web_icon_data":false,"name":"费用产品","web_icon":false,"sequence":10,"children":[],"parent_id":[408,"费用/配置"],"action":"ir.actions.act_window,560","id":409}],"parent_id":[395,"费用"],"action":false,"id":408}],"parent_id":false,"action":false,"id":395},{"user_id":1,"name":"应用","web_icon":"base,static/description/modules.png","sequence":310,"children":[{"web_icon_data":false,"name":"应用","web_icon":false,"sequence":5,"children":[],"parent_id":[5,"应用"],"action":"ir.actions.act_window,37","id":52},{"web_icon_data":false,"name":"更新","web_icon":false,"sequence":20,"children":[],"parent_id":[5,"应用"],"action":"ir.actions.client,39","id":54}],"parent_id":false,"action":false,"id":5},{"xmlid":"base.menu_administration","user_id":1,"name":"设置","web_icon":"base,static/description/settings.png","sequence":500,"children":[{"web_icon_data":false,"name":"仪表板","web_icon":false,"sequence":-1,"children":[],"parent_id":[4,"设置"],"action":"ir.actions.client,76","id":69},{"web_icon_data":false,"name":"用户","web_icon":false,"sequence":0,"children":[{"web_icon_data":false,"name":"用户","web_icon":false,"sequence":0,"children":[],"parent_id":[7,"设置/用户"],"action":"ir.actions.act_window,71","id":65},{"web_icon_data":false,"name":"公司","web_icon":false,"sequence":10,"children":[],"parent_id":[7,"设置/用户"],"action":"ir.actions.act_window,51","id":61}],"parent_id":[4,"设置"],"action":false,"id":7},{"web_icon_data":false,"name":"通用设置","web_icon":false,"sequence":1,"children":[],"parent_id":[4,"设置"],"action":"ir.actions.act_window,78","id":71},{"web_icon_data":false,"name":"翻译","web_icon":false,"sequence":7,"children":[{"web_icon_data":false,"name":"加载翻译","web_icon":false,"sequence":2,"children":[],"parent_id":[8,"设置/翻译"],"action":"ir.actions.act_window,42","id":56}],"parent_id":[4,"设置"],"action":false,"id":8}],"parent_id":false,"action":false,"id":4}]
     * res_msg :
     * res_code : 1
     */

    private String res_msg;
    private int res_code;
    private List<ResDataBean> res_data;

    public String getRes_msg() {
        return res_msg;
    }

    public void setRes_msg(String res_msg) {
        this.res_msg = res_msg;
    }

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public List<ResDataBean> getRes_data() {
        return res_data;
    }

    public void setRes_data(List<ResDataBean> res_data) {
        this.res_data = res_data;
    }

    public static class ResDataBean {
        /**
         * user_id : 1
         * name : 讨论
         * web_icon : mail,static/description/icon.png
         * sequence : 1
         * children : []
         * parent_id : false
         * action : ir.actions.client,88
         * id : 79
         * xmlid : sales_team.menu_base_partner
         */

        private int user_id;
        private String name;
        private String web_icon;
        private int sequence;
        private boolean parent_id;
        private String action;
        private int id;
        private String xmlid;
        private List<ChildBean> children;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWeb_icon() {
            return web_icon;
        }

        public void setWeb_icon(String web_icon) {
            this.web_icon = web_icon;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public boolean isParent_id() {
            return parent_id;
        }

        public void setParent_id(boolean parent_id) {
            this.parent_id = parent_id;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getXmlid() {
            return xmlid;
        }

        public void setXmlid(String xmlid) {
            this.xmlid = xmlid;
        }

        public List<ChildBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildBean> children) {
            this.children = children;
        }
    }
    public static class ChildBean{
        private int user_id;
        private String name;

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public List<ChildBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildBean> children) {
            this.children = children;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getParent_id() {
            return parent_id;
        }

        public void setParent_id(String[] parent_id) {
            this.parent_id = parent_id;
        }

        public int getSequence() {
            return sequence;
        }

        public void setSequence(int sequence) {
            this.sequence = sequence;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getWeb_icon() {
            return web_icon;
        }

        public void setWeb_icon(String web_icon) {
            this.web_icon = web_icon;
        }

        public String getXmlid() {
            return xmlid;
        }

        public void setXmlid(String xmlid) {
            this.xmlid = xmlid;
        }

        private String web_icon;
        private int sequence;
        private String[] parent_id;
        private String action;
        private int id;
        private String xmlid;
        private List<ChildBean> children;


    }


}

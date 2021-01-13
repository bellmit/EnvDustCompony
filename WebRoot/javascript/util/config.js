//系统名称
const SYS_NAME = "综合环境在线监控报警系统";
//公司名称
const COMPANY_NAME = "天津七一二通信广播股份有限公司";
//版权信息
const COPYRIGHT_INFO = "&nbsp;天津七一二通信广播股份有限公司&nbsp;&nbsp;联系电话：022-65388782";

//大屏监控表头标题
const BIG_SCREEN_TITLE = "天津712通信广播股份有限公司 - 大屏数据全域监控";
//左侧数据来源信息
const BIG_SCREEN_DATASOURCE = "数据来源：天津七一二综合监控平台(24小时内)";
const COMPANY_SUB1 = "TIANJIN 712 COMMUNICATION &amp;";
const COMPANY_SUB2 = "BROADCASTING CO.,LTD";

const CHECK_TITLE = "折算值";

/**
 * 设置checkob标题
 */
function setCheckTitle(){
    $(".vauleCheckTitle").html(CHECK_TITLE);
}

/**
 * 加载web版登录界面的logo
 * @param path 路径
 */
function setLogoWeb(path){
    $("#companyLogoId").html("");
    var htmlStr = '<img src="'+ path + 'images/logo.png" alt="公司标志1" class="titleImg" />';
    $("#companyLogoId").append(htmlStr);
}

/**
 * 加载web版主页logo
 * @param path
 */
function setMainLogoWeb(path){
    $("#mainLogoId").html("");
    var htmlStr = '<img src="' + path + 'images/logox.png" alt="712logo" style="float:left;margin-top:13px;"/>';
    $("#mainLogoId").append(htmlStr);
}

/**
 * 加载web版大屏监控logo
 * @param path
 */
function setBigScreenLogo(path){
    $("#bigScreenLogoId").html("");
    var htmlStr = '<img src="' + path + 'images/logox.png" alt="712logo"  class="imglogo"/>';
    $("#bigScreenLogoId").append(htmlStr);
}


/**
 * 加载移动版登录界面的logo
 * @param path 路径
 */
function setLogoMobile(path){
    $("#companyLogoMobileId").html("");
    var htmlStr = '<img src="' + path + 'images/logo.png" alt="公司标志" class="titleImg" style="height:50px;width:90px;margin-top:40px;"/>';
    $("#companyLogoMobileId").append(htmlStr);
}

/**
 * 加载移动版主页logo
 * @param path
 */
function setMainLogoMobile(path){
    $("#mainLogoMobileId").html("");
    var htmlStr = '<img src="' + path + 'images/logox.png" alt="712logo" style=" style="float:left;margin-top:16px;""/>';
    $("#mainLogoMobileId").append(htmlStr);
}
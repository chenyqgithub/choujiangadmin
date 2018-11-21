/**
 * ckac管理初始化
 */
var LjInfo = {
    id: "LjInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
LjInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '领取人', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {
            title: '领取类型',
            field: 'rewardtype',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
               if(value==0){
                   return "一等奖";
               }else if(value==1){
                   return "二等奖";
               }else if(value==2){
                   return "三等奖";
               }else if(value==3){
                   return "四等奖";
               }
            }
        },
        {title: '领取人地址', field: 'address', visible: true,width:"400px", align: 'center', valign: 'middle'},
        {title: '领取人电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle'},
        {title: '是否处理', field: 'isdeal', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value==0){
                    return "未处理";
                }else if(value==1){
                    return "已处理";
                }
            }
        },
        {title: '处理时间', field: 'updatetime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
LjInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        LjInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加ckac
 */
LjInfo.openAddLjInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加ckac',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/ljInfo/ljInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看ckac详情
 */
LjInfo.openLjInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'ckac详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/ljInfo/ljInfo_update/' + LjInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除ckac
 */
LjInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/ljInfo/delete", function (data) {
            Feng.success("更改成功!");
            LjInfo.table.refresh();
        }, function (data) {
            Feng.error("更改失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ljInfoId", this.seItem.id);
        ajax.start();
    }
};
LjInfo.refresh = function () {
        var ajax = new $ax(Feng.ctxPath + "/ljInfo/findData", function (data) {

            $("#one").html(data.one);
            $("#two").html(data.two);
            $("#three").html(data.three);
            $("#four").html(data.four);
            Feng.success("刷新成功!");
        }, function (data) {
            Feng.error("刷新失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
};

/**
 * 查询ckac列表
 */
LjInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['isdeal'] = $("#isdeal").val();
    LjInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = LjInfo.initColumn();
    var table = new BSTable(LjInfo.id, "/ljInfo/list", defaultColunms);
    table.setPaginationType("client");
    LjInfo.table = table.init();
});

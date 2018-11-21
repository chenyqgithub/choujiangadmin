/**
 * 初始化ckac详情对话框
 */
var LjInfoInfoDlg = {
    ljInfoInfoData : {}
};

/**
 * 清除数据
 */
LjInfoInfoDlg.clearData = function() {
    this.ljInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LjInfoInfoDlg.set = function(key, val) {
    this.ljInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LjInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LjInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.LjInfo.layerIndex);
}

/**
 * 收集数据
 */
LjInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('rewardtype')
    .set('address')
    .set('phone')
    .set('createtime')
    .set('isdeal')
    .set('updatetime');
}

/**
 * 提交添加
 */
LjInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ljInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.LjInfo.table.refresh();
        LjInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ljInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
LjInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/ljInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.LjInfo.table.refresh();
        LjInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ljInfoInfoData);
    ajax.start();
}

$(function() {

});

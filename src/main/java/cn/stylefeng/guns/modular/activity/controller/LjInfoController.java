package cn.stylefeng.guns.modular.activity.controller;

import cn.stylefeng.guns.core.util.HttpUtils;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.stylefeng.guns.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.stylefeng.guns.modular.system.model.LjInfo;
import cn.stylefeng.guns.modular.activity.service.ILjInfoService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * ckac控制器
 *
 * @author fengshuonan
 * @Date 2018-11-21 10:39:45
 */
@Controller
@RequestMapping("/ljInfo")
public class LjInfoController extends BaseController {

    private String PREFIX = "/activity/ljInfo/";

    @Autowired
    private ILjInfoService ljInfoService;

    /**
     * 跳转到ckac首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "ljInfo.html";
    }

    /**
     * 跳转到添加ckac
     */
    @RequestMapping("/ljInfo_add")
    public String ljInfoAdd() {
        return PREFIX + "ljInfo_add.html";
    }

    /**
     * 跳转到修改ckac
     */
    @RequestMapping("/ljInfo_update/{ljInfoId}")
    public String ljInfoUpdate(@PathVariable Integer ljInfoId, Model model) {
        LjInfo ljInfo = ljInfoService.selectById(ljInfoId);
        model.addAttribute("item",ljInfo);
        LogObjectHolder.me().set(ljInfo);
        return PREFIX + "ljInfo_edit.html";
    }

    /**
     * 获取ckac列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,Integer isdeal) {
        EntityWrapper<LjInfo> ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.in("rewardtype","0,1,2");
        if(!StringUtils.isEmpty(condition)){
            ljInfoEntityWrapper.like("phone",condition);
        }
        if(isdeal!=null&&isdeal!=-1){
            ljInfoEntityWrapper.eq("isdeal",isdeal);
        }
        return ljInfoService.selectList(ljInfoEntityWrapper);
    }

    /**
     * 新增ckac
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(LjInfo ljInfo) {
        ljInfoService.insert(ljInfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除ckac
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer ljInfoId) {
//        ljInfoService.deleteById(ljInfoId);
        LjInfo ljInfo = ljInfoService.selectById(ljInfoId);
        if(ljInfo!=null){
            Integer isdeal = ljInfo.getIsdeal();
            if(isdeal==0){
                ljInfo.setIsdeal(1);
                SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ljInfo.setUpdatetime(s.format(new Date()));
            }else {
                ljInfo.setIsdeal(0);
            }
            ljInfoService.updateById(ljInfo);
        }
        return SUCCESS_TIP;
    }

    /**
     * 修改ckac
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(LjInfo ljInfo) {
        ljInfoService.updateById(ljInfo);
        return SUCCESS_TIP;
    }

    /**
     * ckac详情
     */
    @RequestMapping(value = "/detail/{ljInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("ljInfoId") Integer ljInfoId) {
        return ljInfoService.selectById(ljInfoId);
    }

    @RequestMapping(value = "/findData")
    @ResponseBody
    public Object findData(){
        String result =HttpUtils.sendPost("https://hybc.ikeek.cn:8443/api/code/getResultInfo", new HashMap<>());
       return JSON.parse(result);
    }
}

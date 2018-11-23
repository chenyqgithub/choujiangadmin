package cn.stylefeng.guns.modular.activity.controller;

import cn.stylefeng.guns.core.util.HttpUtils;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    /**
     * 数据导出
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("export_excel")
    public void export(HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<Map<String, Object>> memberExcels = new ArrayList<>();
        EntityWrapper<LjInfo> ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.in("rewardtype","0,1,2");
        List<LjInfo> ljInfos = ljInfoService.selectList(ljInfoEntityWrapper);
        for (LjInfo m : ljInfos) {
            Map<String, Object> mMap = new LinkedHashMap<>();
            mMap.put("name", m.getName());
            mMap.put("phone", m.getPhone());
            mMap.put("address", m.getAddress());
            mMap.put("createtime", m.getCreatetime());
            mMap.put("rewardtype", m.getRewardtype()==0?"一等奖":m.getRewardtype()==1?"二等奖":"三等奖");
            mMap.put("isdeal", m.getIsdeal()==0?"否":"是");
            mMap.put("updatetime", m.getUpdatetime());
            memberExcels.add(mMap);
        }
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(100);
        SXSSFSheet sxssfSheet = sxssfWorkbook.createSheet();
        Map<String, Object> mapTile = memberExcels.get(0);
        //创建excel 数据列名
        SXSSFRow rowTitle = sxssfSheet.createRow(0);
        Integer j = 0;
        for (Map.Entry<String, Object> entry : mapTile.entrySet()) {
            if (entry.getKey().equals("name")) {
                CellUtil.createCell(rowTitle, j, "姓名");
            } else if (entry.getKey().equals("phone")) {
                CellUtil.createCell(rowTitle, j, "联系电话");
            }  else if (entry.getKey().equals("address")) {
                CellUtil.createCell(rowTitle, j, "联系地址");
            }else if (entry.getKey().equals("createtime")) {
                CellUtil.createCell(rowTitle, j, "抽奖时间");
            }  else if (entry.getKey().equals("rewardtype")) {
                CellUtil.createCell(rowTitle, j, "奖励类型");
            }else if (entry.getKey().equals("isdeal")) {
                CellUtil.createCell(rowTitle, j, "是否处理");
            }else if (entry.getKey().equals("updatetime")) {
                CellUtil.createCell(rowTitle, j, "处理时间");
            }
            j++;
        }
        for (int i = 0; i < memberExcels.size(); i++) {
            Map<String, Object> nMap = memberExcels.get(i);
            SXSSFRow row = sxssfSheet.createRow(i + 1);
            // 数据
            Integer k = 0;
            for (Map.Entry<String, Object> ma : nMap.entrySet()) {
                String value = "";
                if (ma.getValue() != null) {
                    value = ma.getValue().toString();
                }
                CellUtil.createCell(row, k, value);
                k++;
            }
        }
        response.setHeader("content-Type", "application/vnc.ms-excel;charset=utf-8");
        //文件名使用uuid，避免重复
        response.setHeader("Content-Disposition", "attachment;filename=" + "中奖信息" + ".xlsx");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            sxssfWorkbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            memberExcels.clear();
            outputStream.close();
        }
    }
}

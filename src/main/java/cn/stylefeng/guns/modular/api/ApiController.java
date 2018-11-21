/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.activity.service.ILjInfoService;
import cn.stylefeng.guns.modular.system.dao.UserMapper;
import cn.stylefeng.guns.modular.system.model.LjInfo;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {

    @Autowired
    private ILjInfoService ljInfoService;

    @RequestMapping(value = "/getabcd", method = RequestMethod.POST)
    public Object getabcd() {
        EntityWrapper<LjInfo> ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.eq("rewardtype",0);
        int i = ljInfoService.selectCount(ljInfoEntityWrapper);
        ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.eq("rewardtype",1);
        int i1 = ljInfoService.selectCount(ljInfoEntityWrapper);
        ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.eq("rewardtype",2);
        int i2 = ljInfoService.selectCount(ljInfoEntityWrapper);
        ljInfoEntityWrapper = new EntityWrapper<>();
        ljInfoEntityWrapper.eq("rewardtype",3);
        int i3 = ljInfoService.selectCount(ljInfoEntityWrapper);
        Map<String,Object> result=new HashMap<>();
        result.put("a",i);
        result.put("b",i1);
        result.put("c",i2);
        result.put("d",i3);
        return result;
    }

}


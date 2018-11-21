package cn.stylefeng.guns.modular.system.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylefeng
 * @since 2018-11-21
 */
@TableName("lj_info")
public class LjInfo extends Model<LjInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 领取人
     */
    private String name;
    /**
     * 领取类型（0一等奖 1二等奖 2三等奖 3四等奖）
     */
    private Integer rewardtype;
    /**
     * 领取人地址
     */
    private String address;
    /**
     * 领取人电话
     */
    private String phone;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 是否处理
     */
    private Integer isdeal;
    /**
     * 发货时间
     */
    private String updatetime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRewardtype() {
        return rewardtype;
    }

    public void setRewardtype(Integer rewardtype) {
        this.rewardtype = rewardtype;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public Integer getIsdeal() {
        return isdeal;
    }

    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LjInfo{" +
        ", id=" + id +
        ", name=" + name +
        ", rewardtype=" + rewardtype +
        ", address=" + address +
        ", phone=" + phone +
        ", createtime=" + createtime +
        ", isdeal=" + isdeal +
        ", updatetime=" + updatetime +
        "}";
    }
}

package cn.z.controller;

import cn.itcast.utils.Page;
import cn.z.pojo.BaseDict;
import cn.z.pojo.Customer;
import cn.z.pojo.QueryVo;
import cn.z.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Value("${customer.dict.source}")
    private String source;
    @Value("${customer.dict.industry}")
    private String industry;
    @Value("${customer.dict.level}")
    private String level;

    @RequestMapping("/list")
    public String list(QueryVo vo, Model model) throws Exception{
        //客户来源
        List<BaseDict> sourceList = customerService.findDictByCode(source);
        //所属行业
        List<BaseDict> industryList = customerService.findDictByCode(industry);
        //客户级别
        List<BaseDict> levelList = customerService.findDictByCode(level);

        if(vo.getPage() == null){
            vo.setPage(1);
        }
        //设置查询的起始记录条数
        vo.setStart((vo.getPage() - 1) * vo.getSize());

        //查询数据列表和数据总数
        List<Customer> resultList = customerService.findCustomerByVo(vo);
        Integer count = customerService.findCunstomerByVoCount(vo);

        Page<Customer> page = new Page<Customer>();
        page.setTotal(count); //数据总数
        page.setSize(vo.getSize()); //每页显示条数
        page.setPage(vo.getPage()); //当前页数
        page.setRows(resultList); //数据列表

        model.addAttribute("page", page);

        //高级查询下拉列表数据
        model.addAttribute("fromType", sourceList);
        model.addAttribute("industryType", industryList);
        model.addAttribute("levelType", levelList);

        //get请求可能会post乱码
        if(vo.getCustName() != null){
            vo.setCustName(new String(vo.getCustName().getBytes("iso8859-1"), "utf-8"));
        }



        //高级查询数据回显
        model.addAttribute("custName", vo.getCustName());
        model.addAttribute("custSource", vo.getCustSource());
        model.addAttribute("custIndustry", vo.getCustIndustry());
        model.addAttribute("custLevel", vo.getCustLevel());
        return "customer";
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Customer detail(Long id) throws Exception{
        Customer customer = customerService.findCustomerById(id);
        return customer;
    }

    @RequestMapping("/update")
    public String update(Customer customer) throws Exception{
        customerService.updateCustomerById(customer);
        return "customer";
    }


    @RequestMapping("/delete")
    public String delete(Long id) throws  Exception{
        customerService.delCustomerById(id);
        return "customer";    }
}

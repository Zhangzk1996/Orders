package com.team4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.team4.pojo.Comments;
import com.team4.pojo.Goods;
import com.team4.pojo.GoodsExtend;
import com.team4.pojo.Image;
import com.team4.pojo.Notice;
import com.team4.pojo.User;
import com.team4.service.CommentsService;
import com.team4.service.GoodsService;
import com.team4.service.ImageService;
import com.team4.service.NoticeService;
import com.team4.service.UserService;
import com.team4.util.DateUtil;
import com.team4.util.MD5;

import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private ImageService imageService;
    @Resource
    private CommentsService commentsService;
    @Resource
    private NoticeService noticeService;

    /**
     * 用户注册
     * @param user1
     * @return
     */
    @RequestMapping(value = "/addUser")
    public ModelAndView addUser(HttpServletRequest request,@ModelAttribute("user") User user1) {
        String url=request.getHeader("Referer");
        User user=userService.getUserByPhone(user1.getPhone());
        boolean ifhave = (Boolean) request.getSession().getAttribute("ifhave");
        System.out.println("ifhave:"+ifhave);
        if(user == null) {//检测该用户是否已经注册
            String t = DateUtil.getNowDate();
            //对密码进行MD5加密
            String str = MD5.md5(user1.getPassword());
            user1.setCreateAt(t);//创建开始时间
            user1.setPassword(str);
            user1.setGoodsNum(0);
            userService.addUser(user1);
        }
        if(user != null) {
        	request.getSession().setAttribute("ifhave", !ifhave);
        	System.out.println("request.getSession().getAttribute():"+request.getSession().getAttribute("ifhave"));
        }
        return new ModelAndView("redirect:"+url);
    }

    /**
     * 验证登录
     * @param request
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login")
    public ModelAndView loginValidate(HttpServletRequest request, HttpServletResponse response,User user, ModelMap modelMap) {
    	System.out.println("到了login这里了.......");
        User cur_user = userService.getUserByPhone(user.getPhone());
        String name = cur_user.getUsername();
        String password = cur_user.getPassword();
        if(name.equals("admin") && cur_user.getStatus() == 1){
        	System.out.println("admin界面。。。。。。");
        	if(password.equals(MD5.md5(user.getPassword()))){
        		return new ModelAndView("redirect:/admin/userList");
        	}
        }
        String url=request.getHeader("Referer");
        System.out.println("referer:"+url);
        if(cur_user != null) {
            String pwd = MD5.md5(user.getPassword());
            if(pwd.equals(cur_user.getPassword())) {
                request.getSession().setAttribute("cur_user",cur_user);
                return new ModelAndView("redirect:"+url);
            }
        }
        return new ModelAndView("redirect:"+url);
    }

    /**
     * 更改用户名
     * @param request
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/changeName")
    public ModelAndView changeName(HttpServletRequest request,User user,ModelMap modelMap) {
        String url=request.getHeader("Referer");
        System.out.println("url:"+url);
        //从session中获取出当前用户
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        cur_user.setUsername(user.getUsername());//更改当前用户的用户名
        userService.updateUserName(cur_user);//执行修改操作
        request.getSession().setAttribute("cur_user",cur_user);//修改session值
        return new ModelAndView("redirect:"+url);
    }

    /**
     * 完善或修改信息
     * @param request
     * @param user
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/updateInfo")
    public ModelAndView updateInfo(HttpServletRequest request,User user,ModelMap modelMap) {
    	System.out.println("到了这一步了。。。。。");
    	String url = request.getHeader("Referer");
    	System.out.println("referee:"+url);
        //从session中获取出当前用户
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        cur_user.setUsername(user.getUsername());
        cur_user.setQq(user.getQq());
        userService.updateUserName(cur_user);//执行修改操作
        request.getSession().setAttribute("cur_user",cur_user);//修改session值
        return new ModelAndView("redirect:/user/home");
    }
    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().setAttribute("cur_user",null);
        String url = request.getHeader("Referer");
        return new ModelAndView("redirect:"+url);
    }


    /**
     * 个人中心
     * @return
     */
    @RequestMapping(value = "/home")
    public String home() {
        return "/user/home";
    }

    /**
     * 个人信息设置
     * @return
     */
    @RequestMapping(value = "/basic")
    public String basic() {
        return "/user/basic";
    }

    /**
     * 我的闲置
     * 查询出所有的用户商品以及商品对应的图片
     * @return  返回的model为 goodsAndImage对象,该对象中包含goods 和 images，参考相应的类
     */
    @RequestMapping(value = "/allGoods")
    public ModelAndView goods(HttpServletRequest request) {
        User cur_user = (User)request.getSession().getAttribute("cur_user");
        Integer userId = cur_user.getId();
        List<Goods> goodsList = goodsService.getGoodsByUserId(userId);
        List<GoodsExtend> goodsAndImage = new ArrayList<GoodsExtend>();
        for (int i = 0; i < goodsList.size() ; i++) {
            //将用户信息和image信息封装到GoodsExtend类中，传给前台
            GoodsExtend goodsExtend = new GoodsExtend();
            Goods goods = goodsList.get(i);
            List<Image> images = imageService.getImagesByGoodsPrimaryKey(goods.getId());
            goodsExtend.setGoods(goods);
            goodsExtend.setImages(images);
            goodsAndImage.add(i, goodsExtend);
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("goodsAndImage",goodsAndImage);
        mv.setViewName("/user/goods");
        return mv;
    }
    
    /**
     * 我的商品评价
     * 根据userId查找我的所有的商品的评论显示在页面上边
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/myComments",method = RequestMethod.GET)
    public ModelAndView getUserComments(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView();
    	//获取当前登录的账户，得到账户的id便于后边根据user_id获取用户所有的评价
    	User cur_user = (User) request.getSession().getAttribute("cur_user");
    	int user_id = cur_user.getId();
    	List<Comments> user_comments = commentsService.getomByUserId(user_id);
    	modelAndView.addObject("myComments", user_comments);
    	modelAndView.setViewName("/user/comments");
    	return modelAndView;
    }
    
    /**
     * 我的记录
     * 根据userId查找显示我所有的生活记录
     * @param id
     * @return 
     */
    @ResponseBody
    @RequestMapping(value = "/myNotice",method = RequestMethod.GET)
    public ModelAndView getUserNotice(HttpServletRequest request) {
    	ModelAndView  modelAndView  = new ModelAndView();
    	//获取当前登录的账户，得到账户的id便于后边根据user_id获取用户所有的评价
    	User cur_user = (User) request.getSession().getAttribute("cur_user");
    	int user_id = cur_user.getId();
    	List<Notice> user_notice = noticeService.getByUserId(user_id);
    	modelAndView.addObject("myNotice",user_notice);
    	modelAndView.setViewName("/user/notice");
    	return modelAndView;
    }
    
    @ResponseBody
    @RequestMapping(value = "/deleteNotice/{id}")
    public ModelAndView deleteNotice(HttpServletRequest request,@RequestParam(value = "id") Integer noticeId) {
    	String url = request.getHeader("Referer");
    	noticeService.deleteNotice(noticeId);
    	return new ModelAndView("redirect:"+url);
    }
    
    
    
    
}
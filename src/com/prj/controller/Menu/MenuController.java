package com.prj.controller.Menu;

import com.prj.entity.ClassmenuVO;
import com.prj.server.menu.MenuServer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MenuController {


    @Autowired
    @Qualifier("MenuServerImpl")
    private MenuServer menuServer;
    private File lastFile;
    public MenuServer getMenuServer() {
        return menuServer;
    }

    public void setMenuServer(MenuServer menuServer) {
        this.menuServer = menuServer;
    }

    @ResponseBody
    @RequestMapping("/addMenu")
    public String addMenu(@RequestBody ClassmenuVO classmenu)throws Exception{
        int sum=0;//考试总分钟
        //考试时间转换成分钟
        if(classmenu.getScoreTime()!=null){
            String scoreTime=classmenu.getScoreTime();

            //小时
            int xiaoshi=Integer.parseInt(scoreTime.substring(0,scoreTime.indexOf(":")));
            int fenzhong=Integer.parseInt(scoreTime.substring(scoreTime.indexOf(":")+1,scoreTime.length()));

            sum=fenzhong+xiaoshi*60;

        }

        classmenu.getMenu().setScoreTime(sum);
        //判断试题是否是定时发布
        if(classmenu.getMenu().getIspublic()==0){
            //获取用户的定时时间
            String mytime=classmenu.getMytime();
            //把字符串转换成date对象
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //时间对象
            Date date= dateFormat.parse(mytime);
            //启动定时任务
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    //获取试题ID
                    long mid=classmenu.getMenu().getId();
                    menuServer.updateTimerIsPublic(mid);
                }
            },date);
        }

        //判断当前试题是否置顶
        if(classmenu.getMenu().getIstop()!=1){
            classmenu.getMenu().setIstop(0);
        }
        int i= menuServer.addMenu(classmenu,lastFile);

        if(i>0){
            lastFile=null;

            return "ok";
        }

        return "error";
    }



    @ResponseBody
    @RequestMapping("/upload")
    public Map<String,Object> upload(@RequestParam("myfile") MultipartFile myfile, HttpServletRequest request) throws Exception {

        if(!myfile.isEmpty()){
            if(lastFile!=null){
                lastFile.delete();
                lastFile=null;
            }

            String url=request.getSession().getServletContext().getRealPath("/upload/");
            File file=new File(url+System.currentTimeMillis()+myfile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(myfile.getInputStream(),file);

            lastFile=file;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        //返回json
        map.put("msg","ok");
        map.put("code",200);
        return map;
    }

    @ResponseBody
    @RequestMapping("/queryMenu")
    public Map<String,Object> queryMenu(String title,int page,int limit){

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","0");
        map.put("data",menuServer.queryMenu(title));

        return map;
    }

    @RequestMapping("/updateIsTop/{id}/{istop}")
    public String updateIsTop(@PathVariable long id, @PathVariable int istop){

        //用户修改是否置顶
        if(istop==0){
            istop=1;
        }else {
            istop=0;
        }

        menuServer.updateIsTop(id,istop);

        return "err";
    }

    //批量删除试题
    @ResponseBody
    @RequestMapping("/delMenu")
    public String delMenu(@RequestParam() Long[] ids){

        menuServer.delMenu(ids);

        return "ok";
    }
}

package com.prj.controller.Menu;

import com.prj.entity.ClassmenuVO;
import com.prj.server.menu.MenuServer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    public String addMenu(@RequestBody ClassmenuVO classmenu){
       int i= menuServer.addMenu(classmenu);

       if(i>0){
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
}

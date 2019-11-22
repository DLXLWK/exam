package com.prj.server.menu;

import com.prj.entity.Classes;
import com.prj.entity.Classmenu;
import com.prj.entity.ClassmenuVO;
import com.prj.entity.Menu;
import com.prj.mapper.menu.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MenuServerImpl")
public class MenuServerImpl implements MenuServer{

    @Autowired
    private MenuMapper menuMapper;

    public MenuMapper getMenuMapper() {
        return menuMapper;
    }

    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }


    @Override
    public int addMenu(ClassmenuVO classmenu) {
        long mid=System.currentTimeMillis();

        classmenu.getMenu().setId(mid);
        //添加科目
        int w=menuMapper.addMenu(classmenu.getMenu());
        if (w>0){
            List<Classes> classes=classmenu.getClassesList();
            Classmenu classmenu1=new Classmenu();

            for (int k=0;k<classes.size();k++){
                Menu menu=new Menu();
                menu.setId(mid);

                Classes classes1=classes.get(k);
                classmenu1.setMenu(menu);
                classmenu1.setClasses(classes1);
                this.addMenuClasses(classmenu1);
            }
        }

        return w;
    }

    @Override
    public int addMenuClasses(Classmenu classesmenu) {
        return menuMapper.addMenuClasses(classesmenu);
    }
}
